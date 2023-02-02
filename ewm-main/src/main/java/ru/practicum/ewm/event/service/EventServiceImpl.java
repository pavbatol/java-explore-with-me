package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.app.utill.CustomPageRequest;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.user.storage.UserRepository;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.dto.StatsDtoResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        return eventMapper.toShortDtos(page.getContent());
    }

    @Override
    public EventDtoFull findById(Long initiatorId, Long eventId) {
        checkId(userRepository, initiatorId);
        Event entity = getNonNullObject(eventRepository, eventId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, entity);

        ResponseEntity<List<StatsDtoResponse>> responseEntity = statsClient.find(
                LocalDateTime.now().minusDays(50),
                LocalDateTime.now(),
                List.of("/events/" + eventId),
                true);

        List<StatsDtoResponse> body = responseEntity.getBody();
        if (Objects.nonNull(body)) {
            entity.setViews(body.isEmpty()
                    ? 0
                    : body.get(0).getHits());
        }
        return eventMapper.toDto(entity);
    }
}
