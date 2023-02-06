package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.request.model.RequestDtoParticipation;

import java.util.List;

public interface EventService {
    EventDtoFull add(Long userId, EventDtoNew dto);

    List<EventDtoShort> findAllByInitiatorId(Long initiatorId, Integer from, Integer size);

    EventDtoFull findById(Long initiatorId, Long eventId);

    EventDtoFull updateById(Long initiatorId, Long eventId, EventDtoUpdateUserRequest dto);

    List<RequestDtoParticipation> findRequestsByEventId(Long initiatorId, Long eventId);

    EventRequestStatusUpdateResult updateRequestState(Long initiatorId, Long eventId, EventRequestStatusUpdateRequest dto);
}
