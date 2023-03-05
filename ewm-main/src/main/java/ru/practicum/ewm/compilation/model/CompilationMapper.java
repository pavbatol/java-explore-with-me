package ru.practicum.ewm.compilation.model;

import org.mapstruct.*;
import ru.practicum.ewm.app.BaseMapper;
import ru.practicum.ewm.event.model.Event;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompilationMapper extends BaseMapper<Compilation, CompilationDto> {

    @Mapping(target = "events", source = "dto.eventIds", qualifiedByName = "idsToEvents")
    Compilation toEntity(CompilationDtoNew dto);

    @Named("idsToEvents")
    default Set<Event> getEvents(Set<Long> eventIds) {
        return eventIds.stream().map(id -> new Event().setId(id)).collect(Collectors.toSet());
    }

    @Mapping(target = "events", source = "dto.eventIds", qualifiedByName = "idsToEvents")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Compilation updateEntity(CompilationDtoUpdate dto, @MappingTarget Compilation targetEntity);

}
