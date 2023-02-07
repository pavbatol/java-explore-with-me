package ru.practicum.ewm.compilation.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.app.BaseMapper;

@Mapper(componentModel = "spring")
public interface CompilationMapper extends BaseMapper<Compilation, CompilationDto> {
}
