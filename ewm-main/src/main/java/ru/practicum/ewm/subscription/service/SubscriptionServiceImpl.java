package ru.practicum.ewm.subscription.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.subscription.model.*;
import ru.practicum.ewm.subscription.storage.SubscriptionRepository;
import ru.practicum.ewm.user.model.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public SubscriptionDtoResponse add(Long userId, SubscriptionDtoRequest dto) {
        //check userId
        //check for null elements
        //check favorite  observable
        Subscription subscription = subscriptionMapper.toEntity(dto);
        subscription.setOwner(new User().setId(userId));
        Subscription entity = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDtoResponse(entity);
    }

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
