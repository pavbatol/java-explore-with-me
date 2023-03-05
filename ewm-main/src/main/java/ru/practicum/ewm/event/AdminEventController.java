package ru.practicum.ewm.event;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.AdminSearchFilter;
import ru.practicum.ewm.event.model.EventDtoFull;
import ru.practicum.ewm.event.model.EventDtoUpdateAdminRequest;
import ru.practicum.ewm.event.model.enums.EventState;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.stats.client.StatsClient;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class AdminEventController {

    private final EventService eventService;
    private final StatsClient statsClient;
    @Value("${app.format.date-time}")
    private String format;

    @PatchMapping("/admin/events/{eventId}")
    @Operation(summary = "adminUpdateById")
    public ResponseEntity<EventDtoFull> adminUpdateById(
            @PathVariable Long eventId,
            @RequestBody EventDtoUpdateAdminRequest dto) {
        log.debug("PATCH adminUpdateById() with eventId: {}, dto: {}", eventId, dto);
        EventDtoFull body = eventService.adminUpdateById(eventId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("/admin/events")
    @Operation(summary = "adminFindAllByFilter")
    public ResponseEntity<List<EventDtoFull>> adminFindAllByFilter(
            @RequestParam(value = "users", required = false) List<Long> userIds,
            @RequestParam(value = "states", required = false) List<EventState> states,
            @RequestParam(value = "categories", required = false) List<Long> categoryIds,
            @RequestParam(value = "rangeStart", required = false) String rangeStart,
            @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        log.debug("GET adminFindAllByFilter() " +
                        "with userIds: {}, states: {}, categoryIds: {}, rangeStart: {}, rangeEnd {}, from: {}, size: {}",
                userIds, states, categoryIds, rangeStart, rangeEnd, from, size);
        AdminSearchFilter filter = new AdminSearchFilter(
                userIds,
                states,
                categoryIds,
                toLocalDateTime(rangeStart),
                toLocalDateTime(rangeEnd),
                null,
                null,
                null
        );
        List<EventDtoFull> body = eventService.adminFindAllByFilter(filter, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    private LocalDateTime toLocalDateTime(String value) {
        return value != null ? LocalDateTime.parse(value, DateTimeFormatter.ofPattern(format)) : null;
    }
}
