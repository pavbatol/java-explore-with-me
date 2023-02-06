package ru.practicum.ewm.event;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.model.RequestDtoParticipation;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final EventService eventService;

    @PostMapping
    @Operation(summary = "add")
    public ResponseEntity<EventDtoFull> add(
            @PathVariable("userId") Long userId,
            @RequestBody @Valid EventDtoNew dto) {
        log.debug("POST add() with {}", dto);
        EventDtoFull body = eventService.add(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping
    @Operation(summary = "findAllByInitiatorId")
    public ResponseEntity<Object> findAllByInitiatorId(
            @PathVariable("userId") Long initiatorId,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        log.debug("GET findAllByInitiatorId() with initiatorId: {}, from: {}, size: {}", initiatorId, from, size);
        List<EventDtoShort> body = eventService.findAllByInitiatorId(initiatorId, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "findById")
    public ResponseEntity<EventDtoFull> findById(
            @PathVariable("userId") Long initiatorId,
            @PathVariable("eventId") Long eventId) {
        log.debug("GET findById() with initiatorId: {}, eventId: {}", initiatorId, eventId);
        EventDtoFull body = eventService.findById(initiatorId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PatchMapping("/{eventId}")
    @Operation(summary = "updateById")
    public ResponseEntity<EventDtoFull> updateById(
            @PathVariable("userId") Long initiatorId,
            @PathVariable("eventId") Long eventId,
            @RequestBody @Valid EventDtoUpdateUserRequest dto) {
        log.debug("PATCH updateById() with initiatorId: {}, eventId: {}, dto: {}", initiatorId, eventId, dto);
        EventDtoFull body = eventService.updateById(initiatorId, eventId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("/{eventId}/requests")
    @Operation(summary = "findRequestsByEventId")
    public ResponseEntity<List<RequestDtoParticipation>> findRequestsByEventId(
            @PathVariable("userId") Long initiatorId,
            @PathVariable("eventId") Long eventId) {
        log.debug("GET findRequestsByEventId() with initiatorId: {}, eventId: {}", initiatorId, eventId);
        List<RequestDtoParticipation> body = eventService.findRequestsByEventId(initiatorId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PatchMapping("/{eventId}/requests")
    @Operation(summary = "updateRequestState")
    public ResponseEntity<EventRequestStatusUpdateResult> updateRequestState(
            @PathVariable("userId") Long initiatorId,
            @PathVariable("eventId") Long eventId,
            @RequestBody EventRequestStatusUpdateRequest dto) {
        log.debug("PATCH updateRequestState() with initiatorId: {}, eventId: {}, dto: {}", initiatorId, eventId, dto);
        EventRequestStatusUpdateResult body = eventService.updateRequestState(initiatorId, eventId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
