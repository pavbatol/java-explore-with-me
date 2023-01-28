package ru.practicum.ewm.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.model.Stats;
import ru.practicum.ewm.stats.model.StatsDtoRequest;
import ru.practicum.ewm.stats.model.StatsDtoResponse;
import ru.practicum.ewm.stats.model.StatsMapper;

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

        if (unique) {
            cr.groupBy(root.get(IP), root.get(APP), root.get(URI));
        } else {
            cr.groupBy(root.get(APP), root.get(URI));
        }
        Expression<Long> count = cb.count(root.get(IP));
        cr.multiselect(root.get(APP), root.get(URI), count);
        cr.where(conditions.toArray(new Predicate[]{}));
        cr.orderBy(cb.desc(count));

        return entityManager.createQuery(cr).getResultList();
    }
}

