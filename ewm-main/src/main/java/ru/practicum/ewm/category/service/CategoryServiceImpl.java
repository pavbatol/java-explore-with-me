package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.category.model.CategoryMapper;
import ru.practicum.ewm.category.storage.CategoryRepository;
import ru.practicum.ewm.common.exception.ConflictException;

import static ru.practicum.ewm.common.validation.ValidatorManager.checkId;
import static ru.practicum.ewm.common.validation.ValidatorManager.getNonNullObject;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final String ENTITY_SIMPLE_NAME = Category.class.getSimpleName();
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto add(CategoryDto dto) {
        Category saved = categoryRepository.save(categoryMapper.toEntity(dto));
        log.debug("New {} saved: {}", ENTITY_SIMPLE_NAME, saved);
        return categoryMapper.toDto(saved);
    }

    @Override
    public CategoryDto update(Long catId, CategoryDto dto) {
        Category entity = getNonNullObject(categoryRepository, catId);
        Category updated = categoryMapper.updateEntity(dto, entity);
        updated = categoryRepository.save(updated);
        log.debug("Updated {} : {}", ENTITY_SIMPLE_NAME, updated);
        return categoryMapper.toDto(updated);
    }

    @Override
    public void remove(Long catId) {

        //!!! Check Category for empty
        if (catId > 0) {
            throw new ConflictException("The category is not empty");
        }

        checkId(categoryRepository, catId, ENTITY_SIMPLE_NAME);
        log.debug("Removed {} by id #{}:", ENTITY_SIMPLE_NAME, catId);
        categoryRepository.deleteById(catId);
    }
}
