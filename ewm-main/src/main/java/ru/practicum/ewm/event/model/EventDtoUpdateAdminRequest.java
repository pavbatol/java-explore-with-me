package ru.practicum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.practicum.ewm.event.model.enums.AdminActionState;

import java.time.LocalDateTime;

@Value
public class EventDtoUpdateAdminRequest {
    String annotation;
    @JsonProperty("category")
    Long categoryId;
    String description;
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    AdminActionState stateAction;
    String title;
}
