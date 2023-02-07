package ru.practicum.ewm.compilation.model;

import lombok.Value;
import ru.practicum.ewm.event.model.EventDtoShort;

import java.util.Set;

@Value
public class CompilationDto {

    Long id;

    Set<EventDtoShort> events;

    Boolean pinned;

    String title;
}
