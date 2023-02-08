package ru.practicum.ewm.subscription.model;

import com.querydsl.core.BooleanBuilder;
import lombok.Value;
import ru.practicum.ewm.event.model.QEvent;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Value
public class SubscriptionFilter {
    List<Long> categoryIds;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Boolean paid;
    Boolean onlyAvailable;

    public Optional<BooleanBuilder> makeBooleanBuilder(List<Long> favoriteIds, SubscriptionFilter filter) {
        java.util.function.Predicate<Object> isNullOrEmpty = obj ->
                Objects.isNull(obj) || (obj instanceof Collection && ((Collection<?>) obj).isEmpty());
        QEvent qEvent = QEvent.event;
        return isNullOrEmpty.test(favoriteIds)
                ? Optional.empty()
                : Optional.of(
                new BooleanBuilder()
                        .and(qEvent.initiator.id.in(favoriteIds))
                        .and(!isNullOrEmpty.test(filter.getCategoryIds()) ? qEvent.category.id.in(filter.getCategoryIds()) : null)
                        .and(!isNullOrEmpty.test(filter.getRangeStart()) ? qEvent.eventDate.after(filter.getRangeStart()) : null)
                        .and(!isNullOrEmpty.test(filter.getPaid()) ? qEvent.paid.eq(filter.getPaid()) : null)
                        .and(!isNullOrEmpty.test(filter.getOnlyAvailable())
                                ? qEvent.participantLimit.eq(0).or(qEvent.confirmedRequests.lt(qEvent.participantLimit)) : null));
    }
}
