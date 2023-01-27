package ru.practicum.ewm.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.model.Stats;
import ru.practicum.ewm.stats.model.StatsDtoRequest;
import ru.practicum.ewm.stats.model.StatsDtoResponse;
import ru.practicum.ewm.stats.model.StatsMapper;
import ru.practicum.ewm.stats.storage.StatsRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final EntityManager entityManager;
    private final StatsMapper statsMapper;
    private final StatsRepository statsRepository;

    @Override
    public void add(StatsDtoRequest dto) {
        statsRepository.save(statsMapper.toEntity(dto));
    }

    @Override
    public List<StatsDtoResponse> find(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
//        statsRepository.findByPeriod(start, end, uris, unique ? "DISTINCT" : "");

        final String uri = "uri";
        final String timestamp = "timestamp";
        final String app = "app";

//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Stats> cr = cb.createQuery(Stats.class);
//        Root<Stats> root = cr.from(Stats.class);


//        CriteriaQuery<Stats> where = cr.select(root).where(cb.between(root.get(timestamp), start, end));
//        if (!Objects.isNull(uris) && !uris.isEmpty()) {
//            where.where(root.get(uri).in(uris));
//        }
//        List<Stats> found = entityManager.createQuery(cr).getResultList();
//        return found.stream()
//                .map(stats -> statsMapper.dtoResponse(stats, 0L))
//                .collect(Collectors.toList());

        //---------------------------------------------------------------------

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> cr = cb.createQuery(Object[].class);
        CriteriaQuery<StatsDtoResponse> cr = cb.createQuery(StatsDtoResponse.class);
        Root<Stats> root = cr.from(Stats.class);

        List<Predicate> conditionsList = new ArrayList<>();
        Predicate onIn = cb.in(root.get(uri)).value(uris);
        Predicate onStart = cb.greaterThanOrEqualTo(root.get(timestamp), toTimeStamp(start));
        Predicate onEnd = cb.lessThan(root.get(timestamp), end);
        conditionsList.add(onIn);
//        conditionsList.add(onStart);
//        conditionsList.add(onEnd);
        cr.groupBy(root.get(app), root.get(uri), root.get("ip"));

//        cr.select(root).where(conditionsList.toArray(new Predicate[]{}));
//        cr.select(cb.count(root.get())).where(conditionsList.toArray(new Predicate[]{}));
//        cr.multiselect(root.get(app), root.get(uri), root.get("ip"), cb.count(root.get("ip")))
        cr.multiselect(root.get(app), root.get(uri), cb.count(root.get("ip")))
//        cr.multiselect(root.get(app), root.get(uri), root.get("ip"))
                .where(conditionsList.toArray(new Predicate[]{}));

//        List<Object[]> resultList = entityManager.createQuery(cr).getResultList();
        List<StatsDtoResponse> resultList = entityManager.createQuery(cr).getResultList();
        resultList.forEach(System.out::println);

//        resultList.forEach(objects -> {
//                    for (Object object : objects) {
//                        System.out.print(object + " ");
//                    }
//                    System.out.print("\n");
//                }
//        );

        return resultList;
    }

    private static Timestamp toTimeStamp(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

}
