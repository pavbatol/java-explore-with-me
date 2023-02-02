package ru.practicum.ewm.event.model;

import lombok.Value;
import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.user.model.UserDtoShort;

import java.time.LocalDateTime;

@Value
public class EventDtoShort {

    Long id;

    String annotation;

    CategoryDto category;

    Long confirmedRequests;

    String description;

    LocalDateTime eventDate;

    UserDtoShort initiator;

    Boolean paid;

    String title;

    Long views;
}
