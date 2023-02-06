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

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {

    private final RequestService requestService;

    /*
    Обратите внимание:
    нельзя добавить повторный запрос (Ожидается код ошибки 409)
    инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
    нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
    если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
    если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
     */

    @PostMapping  ///users/{userId}/requests
    @Operation(summary = "add")
    public ResponseEntity<RequestDtoParticipation> add(
            @PathVariable("userId") Long userId,
            @RequestParam("eventId") Long eventId) {
        log.debug("POST add() with userId: {}, eventId:{}", userId, eventId);
        RequestDtoParticipation body = requestService.add(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
