package ru.practicum.ewm.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.practicum.ewm.request.model.enums.RequestState;

import java.time.LocalDateTime;

@Value
public class RequestDtoParticipation {

    Long id;

    LocalDateTime created;

    @JsonProperty("event")
    Long eventId;

    @JsonProperty("requester")
    Long requesterId;

    RequestState status;
}
