package ru.practicum.ewm.category;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "add")
    public ResponseEntity<CategoryDto> add(@Valid @RequestBody CategoryDto dto) {
        log.debug("POST add() with {}", dto);
        CategoryDto body = categoryService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PatchMapping("/{catId}")
    @Operation(summary = "update")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto dto,
                                              @PathVariable Long catId) {
        log.debug("PATCH update() with {}", dto);
        CategoryDto body = categoryService.update(catId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/{catId}")
    @Operation(summary = "remove")
    public ResponseEntity<CategoryDto> remove(@PathVariable Long catId) {
        log.debug("DELETE remove() with userId: {}", catId);
        categoryService.remove(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
