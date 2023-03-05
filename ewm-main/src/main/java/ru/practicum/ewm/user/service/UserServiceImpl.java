package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.app.utill.CustomPageRequest;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserDto;
import ru.practicum.ewm.user.model.UserMapper;
import ru.practicum.ewm.user.storage.UserRepository;

import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.app.validation.ValidatorManager.checkId;
import static ru.practicum.ewm.app.validation.ValidatorManager.getNonNullObject;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ID = "id";
    public static final String ENTITY_SIMPLE_NAME = User.class.getSimpleName();
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
        Page<User> page = Objects.nonNull(userIds) && !userIds.isEmpty()
                ? userRepository.findByIdIn(userIds, pageable)
                : userRepository.findAll(pageable);
        log.debug("Found {}: {}, totalPages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                page.getTotalElements(), page.getTotalPages(), pageable.getFrom(), page.getSize(), page.getSort());
        return userMapper.toDtos(page.getContent());
    }

    @Override
    public void remove(Long userId) {
        checkId(userRepository, userId, User.class);
        log.debug("Removed {} by id #{}:", ENTITY_SIMPLE_NAME, userId);
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto privateUpdate(Long userId, Boolean observable) {
        User user = getNonNullObject(userRepository, userId);
        user.setObservable(observable);
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }
}
