package ru.practicum.ewm.subscription.service;

import ru.practicum.ewm.subscription.model.SubscriptionDtoRequest;
import ru.practicum.ewm.subscription.model.SubscriptionDtoResponse;
import ru.practicum.ewm.subscription.model.SubscriptionDtoUpdate;
import ru.practicum.ewm.subscription.model.SubscriptionFilter;

public interface SubscriptionService {
    SubscriptionDtoResponse add(Long userId, SubscriptionDtoRequest dto);
//    SubscriptionDto add(Long userId, Long favorite_id);

    SubscriptionDtoResponse update(Long userId, Long subscriptionId, SubscriptionDtoUpdate dto);

    SubscriptionDtoResponse remove(Long userId, SubscriptionDtoRequest dto);

    SubscriptionDtoResponse findAllFavorites(Long userId, Integer from, Integer size);

    SubscriptionDtoResponse findAllEvents(Long userId, SubscriptionFilter filter, Integer from, Integer size);

}
