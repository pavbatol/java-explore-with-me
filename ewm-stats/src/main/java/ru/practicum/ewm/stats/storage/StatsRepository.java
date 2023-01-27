package ru.practicum.ewm.stats.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.stats.model.Stats;
import ru.practicum.ewm.stats.model.StatsDtoResponse;

import java.util.List;

public interface StatsRepository extends JpaRepository<Stats, Long> {


    @Query("SELECT new ru.practicum.ewm.stats.model.StatsDtoResponse(st.app, st.uri, COUNT(DISTINCT st.ip)) " +
            "FROM Stats st " +
            "WHERE st.uri IN :uris AND (st.timestamp >= :start AND st.timestamp < :end) " +
            "GROUP BY st.app, st.uri"
    )
    StatsDtoResponse findByPeriod(String start, String end, List<String> uris);
}
