package ru.practicum.ewm.user.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.common.BaseMapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {
}
