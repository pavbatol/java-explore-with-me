package ru.practicum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
public class EventDtoNew {

    Long id;

    @NotNull
    @Size(min = 20, max = 2000)
    String annotation;

    @NotNull
    @Min(value = 1L)
    @JsonProperty("category")
    Long categoryId;

    @NotNull
    @Size(min = 20, max = 7000)
    String description;

    @NotNull
    LocalDateTime eventDate;

    @NotNull
    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @NotNull
    @Size(min = 3, max = 120)
    String title;
}
