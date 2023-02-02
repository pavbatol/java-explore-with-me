package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.common.utill.CustomPageRequest;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserDto;
import ru.practicum.ewm.user.model.UserMapper;
import ru.practicum.ewm.user.storage.UserRepository;

import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.common.validation.ValidatorManager.checkId;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ID = "id";
    public static final String ENTITY_SIMPLE_NAME = Category.class.getSimpleName();
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto add(UserDto dto) {
        User saved = userRepository.save(userMapper.toEntity(dto));
        log.debug("New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return userMapper.toDto(saved);
    }

    @Override
    public List<UserDto> find(List<Long> userIds, Integer from, Integer size) {
        Sort sort = Sort.by(ID).ascending();
        CustomPageRequest pageable = CustomPageRequest.by(from, size, sort);
        Page<User> users = Objects.nonNull(userIds) && !userIds.isEmpty()
                ? userRepository.findByIdIn(userIds, pageable)
                : userRepository.findAll(pageable);
        log.debug("Found {}: {}, pages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                users.getTotalElements(), users.getTotalPages(), pageable.getFrom(), users.getSize(), users.getSort());
        return userMapper.toDtos(users.getContent());
    }

    @Override
    public void remove(Long userId) {
        checkId(userRepository, userId, ENTITY_SIMPLE_NAME);
        log.debug("Removed {} by id #{}:", ENTITY_SIMPLE_NAME, userId);
        userRepository.deleteById(userId);
    }
}
