package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.model.CompilationDtoNew;
import ru.practicum.ewm.compilation.model.CompilationDtoUpdate;
import ru.practicum.ewm.compilation.model.CompilationMapper;
import ru.practicum.ewm.compilation.storage.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.storage.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto add(CompilationDtoNew dto) {
        List<Event> events = eventRepository.findByIdIn(dto.getEvents());


        return null;
    }

    @Override
    public CompilationDto update(Long compId, CompilationDtoUpdate dto) {
        return null;
    }

    @Override
    public void remove(Long compId) {

    }
}
