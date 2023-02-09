package ru.practicum.ewm.subscription.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.subscription.model.Subscription;

import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Boolean existsByOwnerId(Long userId);

//    boolean existsByUserObservableAndIdIn(boolean b, Set<Long> favorites);
//    Subscription findBySubscriberIdAndFavoriteId(Long userId, Long favoriteId);
}
