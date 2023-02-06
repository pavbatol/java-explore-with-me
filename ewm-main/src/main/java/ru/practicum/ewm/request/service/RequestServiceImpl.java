package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.app.exception.ConflictException;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.enums.EventState;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestDtoParticipation;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.request.model.enums.RequestState;
import ru.practicum.ewm.request.storage.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.app.validation.ValidatorManager.checkId;
import static ru.practicum.ewm.app.validation.ValidatorManager.getNonNullObject;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;

    @Override
    public RequestDtoParticipation add(Long userId, Long eventId) {
        checkId(userRepository, userId);
        Event event = getNonNullObject(eventRepository, eventId);
        checkNotInitiator(userId, event);
        checkNotRepeatedRequest(userId, eventId);
        checkPublished(event);
        checkNotReachedLimit(event);
        Request request = new Request()
                .setCreated(LocalDateTime.now())
                .setEvent(new Event().setId(eventId))
                .setRequester(new User().setId(userId))
                .setStatus(event.getRequestModeration() ? RequestState.PENDING : RequestState.CONFIRMED);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public RequestDtoParticipation cancelById(Long userId, Long requestId) {
        checkId(userRepository, userId);
        Request request = getNonNullObject(requestRepository, requestId);
        checkRequester(userId, request);
        if (request.getStatus() != RequestState.PENDING) {
            request.setStatus(RequestState.PENDING);
        }
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public List<RequestDtoParticipation> findByUserId(Long userId) {
        checkId(userRepository, userId);
        List<Request> requests = requestRepository.findAllByRequesterId(userId);
        return requestMapper.toDtos(requests);
    }

    private void checkRequester(Long userId, Request request) {
        if (!Objects.equals(userId, request.getRequester().getId())) {
            throw new ConflictException("You can only change your request");
        }
    }

    private void checkNotReachedLimit(Event event) {
        if (event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new ConflictException("The limit of participation requests has been reached");
        }
    }

    private void checkPublished(Event event) {
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("You cannot participate in an unpublished event");
        }
    }

    private void checkNotRepeatedRequest(Long userId, Long eventId) {
        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException("You cannot add a repeat request");
        }
    }

    private void checkNotInitiator(Long userId, Event event) {
        if (Objects.equals(userId, event.getInitiator().getId())) {
            throw new ConflictException("The initiator of the event cannot add a request to participate in his event");
        }
    }
}
