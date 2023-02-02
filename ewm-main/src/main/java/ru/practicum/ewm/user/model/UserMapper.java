package ru.practicum.ewm.user.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.app.BaseMapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {
}
