package ru.practicum.ewm.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.model.UserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "add")
    public ResponseEntity<UserDto> add(@Valid @RequestBody UserDto dto) {
        log.debug("POST add() with {}", dto);
        UserDto body = userService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping
    @Operation(summary = "find")
    public ResponseEntity<List<UserDto>> find(
            @RequestParam(value = "ids", required = false) List<Long> userIds,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET find() with userIds: {}, from: {}, size: {}", userIds, from, size);
        List<UserDto> body = userService.find(userIds, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "remove")
    public ResponseEntity<Object> remove(@PathVariable("userId") Long userId) {
        log.debug("DELETE remove() with userId: {}", userId);
        userService.remove(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
