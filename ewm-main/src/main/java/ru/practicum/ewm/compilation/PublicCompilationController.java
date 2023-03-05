package ru.practicum.ewm.compilation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    @Operation(summary = "findAll")
    public ResponseEntity<List<CompilationDto>> findAll(
            @RequestParam(value = "pinned", defaultValue = "false") Boolean pinned,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAll() with from: {}, size {}", from, size);
        List<CompilationDto> found = compilationService.findAll(pinned, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> findById(@PathVariable("compId") Long compId) {
        CompilationDto found = compilationService.findById(compId);
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }
}
