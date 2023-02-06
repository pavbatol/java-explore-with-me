package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.app.exception.ConflictException;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.request.model.RequestDtoParticipation;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.request.storage.RequestRepository;
import ru.practicum.ewm.user.storage.UserRepository;

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
        return null;
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
