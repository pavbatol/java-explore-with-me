package ru.practicum.ewm.subscription.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sbr_id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    User owner;

    @ManyToMany
    @JoinTable(name = "subscriptions_favorites",
            joinColumns = @JoinColumn(name = "sbr_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "fav_id", nullable = false))
    @ToString.Exclude
    Set<User> favorites;
}

/*
@Setter
@Getter
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sbr_id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    User subscriber;

    @ManyToOne
    @JoinColumn(name = "favorite_id", nullable = false)
    User favorite;

    @Enumerated(EnumType.STRING)
    SubscriptionState state;
}

 */
