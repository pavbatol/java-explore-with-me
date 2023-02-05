package ru.practicum.ewm.request;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.EventDtoFull;
import ru.practicum.ewm.event.model.EventDtoNew;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {

    /*
    Обратите внимание:
    нельзя добавить повторный запрос (Ожидается код ошибки 409)
    инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
    нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
    если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
    если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
     */

//    @PostMapping
//    @Operation(summary = "add")
//    public ResponseEntity<EventDtoFull> add(
//            @PathVariable("userId") Long userId,
//            @RequestBody @Valid EventDtoNew dto) {
//        log.debug("POST add() with {}", dto);
//        EventDtoFull body = eventService.add(userId, dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(body);
//    }
}
