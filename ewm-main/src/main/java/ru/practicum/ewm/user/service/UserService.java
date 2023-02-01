package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto add(UserDto dto);

    List<UserDto> findAllByParams(List<Long> userIds, Integer from, Integer size);

    void removeById(Long userId);
}
