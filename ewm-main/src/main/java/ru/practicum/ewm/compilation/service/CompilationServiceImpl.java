package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.app.utill.CustomPageRequest;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.compilation.model.*;
import ru.practicum.ewm.compilation.storage.CompilationRepository;

import java.util.List;

import static ru.practicum.ewm.app.validation.ValidatorManager.checkId;
import static ru.practicum.ewm.app.validation.ValidatorManager.getNonNullObject;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private static final String ENTITY_SIMPLE_NAME = Category.class.getSimpleName();
    public static final String ID = "id";
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto add(CompilationDtoNew dto) {
        Compilation compilation = compilationMapper.toEntity(dto);
        compilation = compilationRepository.save(compilation);
        log.debug("Added {} : {}", ENTITY_SIMPLE_NAME, compilation);
        return compilationMapper.toDto(compilation);
    }

    @Override
    public CompilationDto update(Long compId, CompilationDtoUpdate dto) {
        Compilation entity = getNonNullObject(compilationRepository, compId);
        Compilation updated = compilationMapper.updateEntity(dto, entity);
        updated = compilationRepository.save(updated);
        log.debug("Updated {} : {}", ENTITY_SIMPLE_NAME, updated);
        return compilationMapper.toDto(updated);
    }

    @Override
    public void remove(Long compId) {
        checkId(compilationRepository, compId);
        log.debug("Removed {} by id #{}:", ENTITY_SIMPLE_NAME, compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public List<CompilationDto> findAll(Boolean pinned, Integer from, Integer size) {
        Sort sort = Sort.by(ID).ascending();
        CustomPageRequest pageable = CustomPageRequest.by(from, size, sort);
        Page<Compilation> page = compilationRepository.findAllByPinned(pinned, pageable);
        log.debug("Found {}: {}, pages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                page.getTotalElements(), page.getTotalPages(), pageable.getFrom(), page.getSize(), page.getSort());
        return compilationMapper.toDtos(page.getContent());
    }

    @Override
    public CompilationDto findById(Long compId) {
        Compilation entity = getNonNullObject(compilationRepository, compId);
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, entity);
        return compilationMapper.toDto(entity);
    }
}
