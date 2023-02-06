package ru.practicum.ewm.event.model;

import lombok.Value;
import ru.practicum.ewm.event.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class AdminSearchFilter {
    List<Long> userIds;
    List<EventState> states;
    List<Long> categoryIds;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
}
