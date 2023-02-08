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

    @Column(name = "title", nullable = false)
    String title;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;

    @ManyToMany
    @JoinTable(name = "subscriptions_favorites",
            joinColumns = @JoinColumn(name = "sbr_id"),
            inverseJoinColumns = @JoinColumn(name = "fav_id"))
    @ToString.Exclude
    Set<User> favorites;
}
