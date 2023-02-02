package ru.practicum.ewm.app;

import org.mapstruct.*;

import java.util.List;

public interface BaseMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E updateEntity(D dto, @MappingTarget E targetEntity);

    List<D> toDtos(List<E> entities);
}