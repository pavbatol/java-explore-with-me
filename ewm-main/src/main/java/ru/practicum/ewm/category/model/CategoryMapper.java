package ru.practicum.ewm.category.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.common.BaseMapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<Category, CategoryDto> {
}
