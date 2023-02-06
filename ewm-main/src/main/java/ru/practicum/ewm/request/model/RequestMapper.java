package ru.practicum.ewm.request.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.app.BaseMapper;

@Mapper(componentModel = "spring")
public interface RequestMapper extends BaseMapper<Request, RequestDtoParticipation> {
}
