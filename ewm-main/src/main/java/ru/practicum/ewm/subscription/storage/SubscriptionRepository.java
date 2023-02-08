package ru.practicum.ewm.subscription.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.subscription.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
