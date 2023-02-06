package ru.practicum.ewm.request.model;

import lombok.Value;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.enums.RequestState;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@Value
public class RequestDtoParticipation {

    Long id;

    LocalDateTime created;

    Event event;

    User requester;

    RequestState status;
}
