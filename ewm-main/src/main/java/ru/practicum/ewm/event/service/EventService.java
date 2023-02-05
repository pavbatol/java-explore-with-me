package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.EventDtoFull;
import ru.practicum.ewm.event.model.EventDtoNew;
import ru.practicum.ewm.event.model.EventDtoShort;
import ru.practicum.ewm.event.model.EventDtoUpdateUserRequest;

import java.util.List;

public interface EventService {
    EventDtoFull add(Long userId, EventDtoNew dto);

    List<EventDtoShort> findAllByInitiatorId(Long initiatorId, Integer from, Integer size);

    EventDtoFull findById(Long initiatorId, Long eventId);

    EventDtoFull updateById(Long initiatorId, Long eventId, EventDtoUpdateUserRequest dto);
}
