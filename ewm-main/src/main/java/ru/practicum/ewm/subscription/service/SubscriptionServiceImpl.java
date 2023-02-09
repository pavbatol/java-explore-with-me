package ru.practicum.ewm.subscription.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.app.exception.ConflictException;
import ru.practicum.ewm.subscription.model.*;
import ru.practicum.ewm.subscription.storage.SubscriptionRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.storage.UserRepository;

import java.util.Objects;
import java.util.Set;

import static ru.practicum.ewm.app.validation.ValidatorManager.checkId;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public SubscriptionDtoResponse add(Long userId, SubscriptionDtoRequest dto) {
        checkId(userRepository, userId);
        checkSubscriptionExists(userId);
        checkFavoriteObservable(dto.getFavorites());
        Subscription subscription = subscriptionMapper.toEntity(dto);
        subscription.setOwner(new User().setId(userId));
        Subscription entity = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDtoResponse(entity);
    }

    private void checkFavoritesEmpty(SubscriptionDtoRequest dto) {
        if (Objects.isNull(dto.getFavorites()) || dto.getFavorites().isEmpty()) {
            throw new ConflictException(("Favourite must not be empty"));
        }
    }

    private void checkFavoriteObservable(Set<Long> favorites) {
        if (userRepository.existsByIdInAndObservable(favorites, false)) {
            throw new ConflictException(("Favourite must be 'observable'"));
        }
    }

    private void checkSubscriptionExists(Long userId) {
        if (subscriptionRepository.existsByOwnerId(userId)) {
            throw new ConflictException(String.format("Subscription for user with id=%s already exists", userId));
        }
    }

/*
    public SubscriptionDto add(Long userId, Long favorite_id) {
        checkId(userRepository, userId);
        checkId(userRepository, favorite_id);
        Subscription sbr = subscriptionRepository.findBySubscriberIdAndFavoriteId(userId, favorite_id);
        return null;

 */

    @Override
    public SubscriptionDtoResponse remove(Long userId, SubscriptionDtoRequest dto) {
        return null;
    }

    @Override
    public SubscriptionDtoResponse findAllFavorites(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public SubscriptionDtoResponse findAllEvents(Long userId, SubscriptionFilter filter, Integer from, Integer size) {
        return null;
    }
}
