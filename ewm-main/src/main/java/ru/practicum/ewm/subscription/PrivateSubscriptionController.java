package ru.practicum.ewm.subscription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.EventDtoShort;
import ru.practicum.ewm.event.model.enums.EventSort;
import ru.practicum.ewm.subscription.model.SubscriptionDtoRequest;
import ru.practicum.ewm.subscription.model.SubscriptionDtoResponse;
import ru.practicum.ewm.subscription.model.SubscriptionDtoUpdate;
import ru.practicum.ewm.subscription.model.SubscriptionFilter;
import ru.practicum.ewm.subscription.service.SubscriptionService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
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

    @PatchMapping("/{sbrId}")
    @Operation(summary = "update")
    public ResponseEntity<SubscriptionDtoResponse> update(
            @PathVariable("userId") Long userId,
            @PathVariable("sbrId") Long sbrId,
            @Valid @RequestBody SubscriptionDtoUpdate dto) {
        log.debug("POST update() with userId: {}, sbr_id: {},dto {}", userId, sbrId, dto);
        SubscriptionDtoResponse body = subscriptionService.update(userId, sbrId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/{sbrId}")
    @Operation(summary = "remove")
    public ResponseEntity<SubscriptionDtoResponse> remove(
            @PathVariable("userId") Long userId,
            @PathVariable("sbrId") Long sbrId) {
        log.debug("DELETE remove() with userId: {}, sbrId {}", userId, sbrId);
        SubscriptionDtoResponse body = subscriptionService.remove(userId, sbrId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);
    }

    @GetMapping
    @Operation(summary = "find")
    public ResponseEntity<SubscriptionDtoResponse> find(
            @PathVariable("userId") Long userId) {
        log.debug("GET find() with userId: {}", userId);
        SubscriptionDtoResponse body = subscriptionService.find(userId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("/events")
    @Operation(summary = "findFavoriteEvents")
    public ResponseEntity<List<EventDtoShort>> findFavoriteEvents(
            SubscriptionFilter filter,
            @PathVariable("userId") Long userId,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.debug("GET findFavoriteEvents() with userId: {}, filter: {}, sort: {},  from: {}, size {}",
                userId, filter, sort, from, size);
        EventSort eventSort = sort != null ? EventSort.by(sort) : EventSort.EVENT_DATE;
        List<EventDtoShort> body = subscriptionService.findFavoriteEvents(userId, filter, eventSort, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
