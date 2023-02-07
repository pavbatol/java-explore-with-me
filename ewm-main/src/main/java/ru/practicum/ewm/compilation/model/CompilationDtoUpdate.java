package ru.practicum.ewm.compilation.model;

import lombok.Value;

import java.util.List;

@Value
public class CompilationDtoUpdate {

    List<Long> events;

    Boolean pinned;

    String title;
}
