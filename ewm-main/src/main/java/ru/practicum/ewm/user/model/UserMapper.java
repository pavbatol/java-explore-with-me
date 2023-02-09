package ru.practicum.ewm.user.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.app.BaseMapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {
    @Override
    @Mapping(target = "observable", source = "observable", defaultValue = "true")
    User toEntity(UserDto dto);
}
