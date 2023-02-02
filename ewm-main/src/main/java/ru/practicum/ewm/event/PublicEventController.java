package ru.practicum.ewm.event;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.dto.StatsDtoResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;
    private final StatsClient statsClient;

    /**
     * This is a temporary solution for testing the client
     */

    @GetMapping("/stats")
    @Operation(summary = "findStats")
    public ResponseEntity<List<StatsDtoResponse>> findStats() {
        return statsClient.find(
                LocalDateTime.now().minusDays(50),
                LocalDateTime.now().plusDays(1),
                List.of("/events", "/events/1", "/events/2"),
                true);
    }

    @GetMapping
    @Operation(summary = "findAll")
    public ResponseEntity<Object> findAll(HttpServletRequest servletRequest) {
        log.debug("Get findAll() Test request received");
        statsClient.add(servletRequest);
        return null;
    }

    @GetMapping("/{id}")
    @Operation(summary = "findById")
    public ResponseEntity<Object> findById(@PathVariable("id") Long eventId,
                                           HttpServletRequest servletRequest) {
        log.debug("GET findById() Test request received");
        statsClient.add(servletRequest);
        return null;
    }
}
