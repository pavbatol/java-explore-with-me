package ru.practicum.ewm.subscription.service;

import ru.practicum.ewm.subscription.model.SubscriptionDtoRequest;
import ru.practicum.ewm.subscription.model.SubscriptionDtoResponse;
import ru.practicum.ewm.subscription.model.SubscriptionFilter;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDtoResponse add(Long userId, SubscriptionDtoRequest dto);

    SubscriptionDtoResponse remove(Long userId, SubscriptionDtoRequest dto);

    SubscriptionDtoResponse findAllFavorites(Long userId, Integer from, Integer size);

    SubscriptionDtoResponse findAllEvents(Long userId, SubscriptionFilter filter, Integer from, Integer size);
}
