package ru.practicum.ewm.user.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.user.model.User;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByIdIn(List<Long> userIds, Pageable pageable);

    boolean existsByIdInAndObservable(Set<Long> favorites, Boolean observable);
}
