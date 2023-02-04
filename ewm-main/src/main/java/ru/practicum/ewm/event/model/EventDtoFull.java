package ru.practicum.ewm.event.model;

import lombok.Value;
import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.event.model.enums.EventState;
import ru.practicum.ewm.user.model.UserDtoShort;

import java.time.LocalDateTime;

@Value
public class EventDtoFull {

    Long id;

    String annotation;

    CategoryDto category;

    Long confirmedRequests;

    LocalDateTime createdOn;

    String description;

    LocalDateTime eventDate;

    UserDtoShort initiator;

    Location location;

    Boolean paid;

    Integer participantLimit;

    LocalDateTime publishedOn;

    Boolean requestModeration;

    EventState state;

    String title;

    Long views;
}
