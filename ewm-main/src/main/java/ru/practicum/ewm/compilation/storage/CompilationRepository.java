package ru.practicum.ewm.compilation.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
//    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
