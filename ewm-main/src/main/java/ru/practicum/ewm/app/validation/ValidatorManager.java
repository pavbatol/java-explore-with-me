package ru.practicum.ewm.app.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.app.exception.NotFoundException;

import javax.validation.constraints.NotNull;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidatorManager {

    @NotNull
    public static <T, I> T getNonNullObject(@NotNull CrudRepository<T, I> storage, I id) throws NotFoundException {
        return storage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Object by id %s not found", id)));
    }

    public static <T, I> void checkId(@NotNull CrudRepository<T, I> storage, I id, String entitySimpleName) {
        if (!storage.existsById(id)) {
            throw new NotFoundException(String.format("Id #%s not found for %s", id, entitySimpleName));
        }
    }
}
