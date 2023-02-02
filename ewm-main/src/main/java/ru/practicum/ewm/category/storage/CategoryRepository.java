package ru.practicum.ewm.category.storage;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.category.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
