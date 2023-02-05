package ru.practicum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.practicum.ewm.app.validation.annotated.MinDateTime;
import ru.practicum.ewm.event.model.enums.ActionState;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
public class EventDtoUpdateUserRequest {

    Long id;

    @Pattern(regexp = ".*\\S.*")
    @Size(min = 20, max = 2000)
    String annotation;

    @Positive
    @JsonProperty("category")
    Long categoryId;

    @Pattern(regexp = ".*\\S.*")
    @Size(min = 20, max = 7000)
    String description;

    @MinDateTime
    LocalDateTime eventDate;

    Location location;

    Boolean paid;

    @PositiveOrZero
    Integer participantLimit;

    Boolean requestModeration;

    ActionState stateAction;

    @Size(min = 3, max = 120)
    String title;
}
