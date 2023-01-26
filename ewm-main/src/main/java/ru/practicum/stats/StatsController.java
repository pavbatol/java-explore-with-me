package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.model.StatsDtoRequest;
import ru.practicum.stats.model.StatsDtoResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;
    private final StatsMapper mapper;

    @PostMapping("/hit")
    public void add(@Valid @RequestBody StatsDtoRequest dto) {
        return statsService.add(dto);
    }

    @GetMapping("/stats")
    public List<StatsDtoResponse> get(@RequestParam("start") String start,
                                      @RequestParam("end") String end,
                                      @RequestParam("uris") List<String> uris,
                                      @RequestParam("unique") Boolean unique) {
        return statsService
    }
}
