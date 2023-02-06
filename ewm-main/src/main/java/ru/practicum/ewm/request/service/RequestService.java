package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.model.RequestDtoParticipation;

public interface RequestService {
    RequestDtoParticipation add(Long userId, Long eventId);
}
