package ru.practicum.stats.server.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.server.stats.model.Stats;
import ru.practicum.stats.dto.StatsDtoRequest;
import ru.practicum.stats.dto.StatsDtoResponse;
import ru.practicum.stats.server.stats.model.StatsMapper;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    public static final String URI = "uri";
    public static final String TIMESTAMP = "timestamp";
    public static final String APP = "app";
    public static final String IP = "ip";
    private final EntityManager entityManager;
    private final StatsMapper statsMapper;

    @Transactional
    @Override
    public void add(StatsDtoRequest dto) {
        entityManager.persist(statsMapper.toEntity(dto));
    }

    @Override
    public List<StatsDtoResponse> find(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatsDtoResponse> cr = cb.createQuery(StatsDtoResponse.class);
        Root<Stats> root = cr.from(Stats.class);

        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.greaterThanOrEqualTo(root.get(TIMESTAMP), start));
        conditions.add(cb.lessThan(root.get(TIMESTAMP), end));
        if (!Objects.isNull(uris) && !uris.isEmpty()) {
            conditions.add(cb.in(root.get(URI)).value(uris));
        }

        Expression<Long> count = unique
                ? cb.countDistinct(root.get(IP))
                : cb.count(root.get(IP));

        cr.multiselect(root.get(APP), root.get(URI), count)
                .where(conditions.toArray(new Predicate[]{}))
                .groupBy(root.get(APP), root.get(URI))
                .orderBy(cb.desc(count));
        return entityManager.createQuery(cr).getResultList();
    }
}

