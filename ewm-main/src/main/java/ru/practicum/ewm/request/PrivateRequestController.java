package ru.practicum.ewm.request;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.model.RequestDtoParticipation;
import ru.practicum.ewm.request.service.RequestService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {

    private final RequestService requestService;

    @PostMapping
    @Operation(summary = "add")
    public ResponseEntity<RequestDtoParticipation> add(
            @PathVariable("userId") Long userId,
            @RequestParam("eventId") Long eventId) {
        log.debug("POST add() with userId: {}, eventId:{}", userId, eventId);
        RequestDtoParticipation body = requestService.add(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PatchMapping("/{requestId}/cancel")
    @Operation(summary = "cancelById")
    public ResponseEntity<RequestDtoParticipation> cancelById(
            @PathVariable("userId") Long userId,
            @PathVariable("requestId") Long requestId) {
        log.debug("PATCH cancelById() with userId: {}, requestId:{}", userId, requestId);

        RequestDtoParticipation body = requestService.cancelById(userId, requestId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping
    @Operation(summary = "findByUserId")
    public ResponseEntity<List<RequestDtoParticipation>> findByUserId(
            @PathVariable("userId") Long userId) {
        log.debug("GET findByUserId() with userId: {}", userId);
        List<RequestDtoParticipation> body = requestService.findByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
