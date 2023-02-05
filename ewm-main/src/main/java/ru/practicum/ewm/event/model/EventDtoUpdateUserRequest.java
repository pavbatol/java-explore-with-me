package ru.practicum.ewm.event.model;

import lombok.Value;
import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.event.model.enums.ActionState;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
public class EventDtoUpdateUserRequest {

    Long id;

    @Size(min = 20, max = 2000)
    String annotation;

    @Min(value = 1L)
    CategoryDto category;

    @Size(min = 20, max = 7000)
    String description;

    LocalDateTime eventDate;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    ActionState stateAction;

    @Size(min = 3, max = 120)
    String title;
}
