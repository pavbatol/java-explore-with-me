package ru.practicum.ewm.subscription.model;

import lombok.Value;
import ru.practicum.ewm.user.model.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Value
public class SubscriptionDtoRequest {

    Long id;

    @NotNull
    String title;

    User owner;

    @NotNull
    @NotEmpty
    Set<Long> favorites;
}
