package ru.practicum.ewm.subscription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.subscription.model.SubscriptionDtoRequest;
import ru.practicum.ewm.subscription.model.SubscriptionDtoResponse;
import ru.practicum.ewm.subscription.model.SubscriptionFilter;
import ru.practicum.ewm.subscription.service.SubscriptionService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Private Subscription")
@RequestMapping("/users/{userId}/subscriptions")
public class PrivateSubscriptionController {

    private final SubscriptionService subscriptionService;


    @PostMapping
    @Operation(summary = "add")
    public ResponseEntity<SubscriptionDtoResponse> add(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionDtoRequest dto) {
        log.debug("POST add() with userId: {}, dto {}", userId, dto);
        SubscriptionDtoResponse body = subscriptionService.add(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @DeleteMapping
    @Operation(summary = "remove")
    public ResponseEntity<SubscriptionDtoResponse> remove(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionDtoRequest dto) {
        log.debug("DELETE remove() with userId: {}, dto {}", userId, dto);
        SubscriptionDtoResponse body = subscriptionService.remove(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }


    @GetMapping("/favorites")
    @Operation(summary = "findAllFavorites")
    public ResponseEntity<SubscriptionDtoResponse> findAllFavorites(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAllFavorites() with userId: {}, from: {}, size {}", userId, from, size);
        SubscriptionDtoResponse body = subscriptionService.findAllFavorites(userId, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("/events")
    @Operation(summary = "findAllEvents")
    public ResponseEntity<SubscriptionDtoResponse> findAllEvents(
            @RequestBody SubscriptionFilter filter,
            @PathVariable("userId") Long userId,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findAllEvents() with userId: {}, filter: {}, from: {}, size {}", userId, filter, from, size);
        SubscriptionDtoResponse body = subscriptionService.findAllEvents(userId, filter, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

}
