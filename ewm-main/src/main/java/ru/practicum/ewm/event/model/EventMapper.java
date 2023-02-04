package ru.practicum.ewm.event.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.app.BaseMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper extends BaseMapper<Event, EventDtoFull> {

    @Mapping(target = "category.id", source = "dto.categoryId")
    @Mapping(target = "latitude", source = "dto.location.lat")
    @Mapping(target = "longitude", source = "dto.location.lon")
    @Mapping(target = "confirmedRequests", expression = "java(0L)")
    @Mapping(target = "createdOn", expression = "java(LocalDateTime.now())")
    @Mapping(target = "initiator.id", source = "initiatorId")
    @Mapping(target = "state", expression = "java(EventState.PENDING)")
    Event toEntity(EventDtoNew dto, Long initiatorId);

    @Override
    @Mapping(target = "location.lat", source = "latitude")
    @Mapping(target = "location.lon", source = "longitude")
    EventDtoFull toDto(Event entity);

    EventDtoShort toShortDto(Event entity);

    List<EventDtoShort> toShortDtos(List<Event> entities);
}
