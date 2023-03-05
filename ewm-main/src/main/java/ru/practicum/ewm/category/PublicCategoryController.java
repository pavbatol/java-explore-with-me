package ru.practicum.ewm.category;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "findAll")
    public ResponseEntity<List<CategoryDto>> findAll(
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAll() with from: {}, size {}", from, size);
        List<CategoryDto> found = categoryService.findAll(from, size);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> findById(@PathVariable("catId") Long catId) {
        CategoryDto found = categoryService.findById(catId);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }
}
