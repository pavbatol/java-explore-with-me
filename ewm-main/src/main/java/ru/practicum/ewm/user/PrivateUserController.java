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

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateUserController {

    private final UserService userService;

    @PatchMapping("/{userId}")
    @Operation(summary = "update")
    public ResponseEntity<UserDto> update(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "observable") Boolean observable) {
        log.debug("PATCH update() with userId: {}, observable: {}", userId, observable);
        UserDto body = userService.privateUpdate(userId, observable);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
