package ru.practicum.ewm.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.model.UserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "add")
    public ResponseEntity<UserDto> add(@Valid @RequestBody UserDto dto) {
        UserDto body = userService.add(dto);
        return ResponseEntity.status(201).body(body);
    }

    @GetMapping
    @Operation(summary = "find")
    public ResponseEntity<List<UserDto>> findAllByParams(
            @RequestParam(value = "ids", required = false) List<Long> userIds,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<UserDto> body = userService.findAllByParams(userIds, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> removeById(@PathVariable("userId") Long userId) {
        userService.removeById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
