package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.model.CategoryDto;

public interface CategoryService {
    CategoryDto add(CategoryDto dto);

    CategoryDto update(Long catId, CategoryDto dto);

    void remove(Long catId);
}
