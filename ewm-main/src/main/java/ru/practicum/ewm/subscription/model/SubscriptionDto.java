package ru.practicum.ewm.subscription.model;

import lombok.Value;
import ru.practicum.ewm.subscription.enums.SubscriptionState;
import ru.practicum.ewm.user.model.User;

@Value
public class SubscriptionDto {

    Long id;

    User favorite;

    SubscriptionState state;
}
