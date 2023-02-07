package ru.practicum.ewm.compilation.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Value
public class CompilationDtoNew {

    List<Long> events;

    Boolean pinned;

    @NotBlank
    String title;
}
