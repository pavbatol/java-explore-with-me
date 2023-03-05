package ru.practicum.ewm.subscription.model;

import lombok.Value;
import ru.practicum.ewm.user.model.UserDtoShort;

import java.util.List;

@Value
public class SubscriptionDtoResponse {

    Long id;

    List<UserDtoShort> favorites;
}
