package ru.practicum.ewm.event.model;

import org.mapstruct.*;
import ru.practicum.ewm.app.BaseMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.storage.CategoryRepository;
import ru.practicum.ewm.event.model.enums.ActionState;
import ru.practicum.ewm.event.model.enums.EventState;

import java.util.List;

import static ru.practicum.ewm.app.validation.ValidatorManager.getNonNullObject;

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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "dto.categoryId", qualifiedByName = "setCategory")
    @Mapping(target = "latitude", source = "dto.location.lat")
    @Mapping(target = "longitude", source = "dto.location.lon")
    @Mapping(target = "state", source = "dto.stateAction", qualifiedByName = "setEventState")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event updateEntity(EventDtoUpdateUserRequest dto, @MappingTarget Event targetEntity, @Context CategoryRepository categoryRepository);

    @Named("setCategory")
    default Category getCategory(Long catId, @Context CategoryRepository categoryRepository) {
        return getNonNullObject(categoryRepository, catId);
    }

    @Named("setEventState")
    default EventState getEventState(ActionState actionState) {
        return actionState.equals(ActionState.CANCEL_REVIEW) ? EventState.CANCELED : EventState.PENDING;
    }
}
