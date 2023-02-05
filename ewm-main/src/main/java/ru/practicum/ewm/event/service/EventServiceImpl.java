package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.practicum.ewm.app.utill.CustomPageRequest;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.user.storage.UserRepository;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.dto.StatsDtoResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.ewm.app.validation.ValidatorManager.checkId;
import static ru.practicum.ewm.app.validation.ValidatorManager.getNonNullObject;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    public static final String ID = "id";
    private static final String ENTITY_SIMPLE_NAME = Event.class.getSimpleName();
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final StatsClient statsClient;

    @Override
    public EventDtoFull add(Long userId, EventDtoNew dto) {
        checkId(userRepository, userId);
        Event entity = eventMapper.toEntity(dto, userId);
        Event added = eventRepository.save(entity);
        return eventMapper.toDto(added);
    }

    @Override
    public List<EventDtoShort> findAllByInitiatorId(Long initiatorId, Integer from, Integer size) {
        checkId(userRepository, initiatorId);
        Sort sort = Sort.by(ID).ascending();
        CustomPageRequest pageable = CustomPageRequest.by(from, size, sort);
        Page<Event> page = eventRepository.findAllByInitiatorId(initiatorId, pageable);
        log.debug("Found {}: {}, totalPages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                page.getTotalElements(), page.getTotalPages(), pageable.getFrom(), page.getSize(), page.getSort());
        List<Event> entities = page.getContent();
        setViews(entities);
        return eventMapper.toShortDtos(page.getContent());
    }

    @Override
    public EventDtoFull findById(Long initiatorId, Long eventId) {
        checkId(userRepository, initiatorId);
        Event entity = getNonNullObject(eventRepository, eventId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, entity);
        setViews(List.of(entity));
        return eventMapper.toDto(entity);
    }

    @Override
    public EventDtoFull updateById(Long initiatorId, Long eventId, EventDtoUpdateUserRequest dto) {
        checkId(userRepository, initiatorId);
        Event entity = getNonNullObject(eventRepository, eventId);
        entity = eventMapper.updateEntity(dto, entity);
        entity = eventRepository.save(entity);
        return eventMapper.toDto(entity);
    }

    private void setViews(List<Event> events) {
        setViews(events, LocalDateTime.of(0, 1, 1, 0, 0, 0), LocalDateTime.now());
    }

    private void setViews(List<Event> events, LocalDateTime start, LocalDateTime end) {
        if (Objects.isNull(events) || events.isEmpty()) {
            return;
        }
        List<String> uris = events.stream()
                .map(event -> "/events/" + event.getId())
                .collect(Collectors.toList());
        ResponseEntity<List<StatsDtoResponse>> response = null;
        try {
            response = statsClient.find(start, end, uris, true);
        } catch (RestClientException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return;
        }
        ResponseEntity<List<StatsDtoResponse>> finalResponse = response;
        events.forEach(event -> setViewsFromSources(event, finalResponse.getBody()));
    }

    private void setViewsFromSources(Event event, List<StatsDtoResponse> sources) {
        (Objects.isNull(sources) ? new ArrayList<StatsDtoResponse>(0) : sources)
                .stream()
                .filter(Objects::nonNull)
                .filter(dto -> ("/events/" + event.getId()).equals(dto.getUri()))
                .findFirst()
                .ifPresentOrElse(
                        dto -> event.setViews(dto.getHits()),
                        () -> event.setViews(0L));
    }
}
