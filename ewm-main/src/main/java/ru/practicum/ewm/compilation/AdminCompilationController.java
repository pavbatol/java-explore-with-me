package ru.practicum.ewm.compilation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.model.CompilationDtoNew;
import ru.practicum.ewm.compilation.model.CompilationDtoUpdate;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @Operation(summary = "add")
    public ResponseEntity<CompilationDto> add(@Valid @RequestBody CompilationDtoNew dto) {
        log.debug("POST add() with {}", dto);
        CompilationDto body = compilationService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PatchMapping("/{compId}")
    @Operation(summary = "update")
    public ResponseEntity<CompilationDto> update(@Valid @RequestBody CompilationDtoUpdate dto,
                                                 @PathVariable Long compId) {
        log.debug("PATCH update() with {}", dto);
        CompilationDto body = compilationService.update(compId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/{compId}")
    @Operation(summary = "remove")
    public ResponseEntity<Object> remove(@PathVariable("compId") Long compId) {
        log.debug("DELETE remove() with userId: {}", compId);
        compilationService.remove(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
