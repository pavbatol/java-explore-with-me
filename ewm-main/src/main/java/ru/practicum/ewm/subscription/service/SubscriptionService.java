package ru.practicum.ewm.subscription.service;

import ru.practicum.ewm.event.model.EventDtoShort;
import ru.practicum.ewm.event.model.enums.EventSort;
import ru.practicum.ewm.subscription.model.SubscriptionDtoRequest;
import ru.practicum.ewm.subscription.model.SubscriptionDtoResponse;
import ru.practicum.ewm.subscription.model.SubscriptionDtoUpdate;
import ru.practicum.ewm.subscription.model.SubscriptionFilter;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDtoResponse add(Long userId, SubscriptionDtoRequest dto);

    SubscriptionDtoResponse update(Long userId, Long subscriptionId, SubscriptionDtoUpdate dto);

    void remove(Long userId, Long sbrId);

    SubscriptionDtoResponse find(Long userId);

    List<EventDtoShort> findFavoriteEvents(Long userId, SubscriptionFilter filter, EventSort eventSort, Integer from, Integer size);
}
