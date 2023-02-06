package ru.practicum.ewm.event.model;

import lombok.Value;
import ru.practicum.ewm.request.model.enums.RequestState;

import java.util.List;

@Value
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    RequestState status;
}
