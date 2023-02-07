package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.model.CompilationDtoNew;
import ru.practicum.ewm.compilation.model.CompilationDtoUpdate;

public interface CompilationService {
    CompilationDto add(CompilationDtoNew dto);

    CompilationDto update(Long compId, CompilationDtoUpdate dto);

    void remove(Long compId);
}
