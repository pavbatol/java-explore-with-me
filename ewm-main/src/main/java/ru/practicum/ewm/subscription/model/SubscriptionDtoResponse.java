package ru.practicum.ewm.subscription.model;

import lombok.Value;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Value
public class SubscriptionDtoResponse {

    String title;

    List<User> favorites;
}
