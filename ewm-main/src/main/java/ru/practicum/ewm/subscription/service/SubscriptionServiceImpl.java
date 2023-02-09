package ru.practicum.ewm.subscription.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.app.exception.ConflictException;
import ru.practicum.ewm.app.exception.NotFoundException;
import ru.practicum.ewm.app.utill.CustomPageRequest;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventDtoShort;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.event.model.enums.EventSort;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.event.service.EventServiceImpl;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.subscription.model.*;
import ru.practicum.ewm.subscription.storage.SubscriptionRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserDtoShort;
import ru.practicum.ewm.user.storage.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.ewm.app.validation.ValidatorManager.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final String ENTITY_SIMPLE_NAME = Subscription.class.getSimpleName();
    public static final String EVENT_DATE = "eventDate";
    public static final String VIEWS = "views";
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;
    private final SubscriptionMapper subscriptionMapper;
    private final EventMapper eventMapper;

    @Override
    public SubscriptionDtoResponse add(Long userId, SubscriptionDtoRequest dto) {
        checkId(userRepository, userId);
        checkSubscriptionExists(userId);
        checkFavoriteObservable(dto.getFavorites());
        Subscription subscription = subscriptionMapper.toEntity(dto);
        subscription.setOwner(new User().setId(userId));
        subscriptionRepository.save(subscription);
        log.debug("Added {} : {}", ENTITY_SIMPLE_NAME, subscription);
        return subscriptionMapper.toDtoResponse(subscription);
    }

    @Override
    public SubscriptionDtoResponse update(Long userId, Long sbrId, SubscriptionDtoUpdate dto) {
        checkId(userRepository, userId);
        Subscription subscription = getNonNullObject(subscriptionRepository, sbrId);
        checkOwner(userId, subscription);
        checkFavoriteObservable(dto.getFavorites());
        subscription = subscriptionMapper.updateEntity(dto, subscription);
        subscriptionRepository.save(subscription);
        log.debug("Updated {} : {}", ENTITY_SIMPLE_NAME, subscription);
        return subscriptionMapper.toDtoResponse(subscription);
    }

    @Override
    public void remove(Long userId, Long sbrId) {
        Subscription subscription = getNonNullObject(subscriptionRepository, sbrId);
        checkOwner(userId, subscription);
        subscriptionRepository.deleteById(sbrId);
        log.debug("Removed {} by id #{}:", ENTITY_SIMPLE_NAME, sbrId);
    }

    @Override
    public SubscriptionDtoResponse find(Long userId) {
        Subscription subscription = subscriptionRepository.findByOwnerId(userId)
                .orElseThrow(() ->
                        new NotFoundException(String.format(S_WITH_ID_S_WAS_NOT_FOUND, ENTITY_SIMPLE_NAME, userId)));
        log.debug("Found {}: {}", ENTITY_SIMPLE_NAME, subscription);
        return subscriptionMapper.toDtoResponse(subscription);
    }

    @Override
    public List<EventDtoShort> findFavoriteEvents(Long userId, SubscriptionFilter filter, EventSort eventSort, Integer from, Integer size) {
        Sort sort = eventSort == EventSort.EVENT_DATE
                ? Sort.by(EVENT_DATE).ascending()
                : Sort.by(VIEWS).ascending();
        CustomPageRequest pageable = CustomPageRequest.by(from, size, sort);

        List<Long> favoriteIds = findFavoriteIds(userId);
        Optional<BooleanBuilder> oBuilder = filter.makeBooleanBuilder(favoriteIds, filter);
        if (oBuilder.isEmpty()) {
            return List.of();
        }

        BooleanBuilder booleanBuilder = oBuilder.get();
        Page<Event> page = eventRepository.findAll(booleanBuilder, pageable);
        log.debug("Found {}-count: {}, totalPages: {}, from: {}, size: {}, sort: {}", ENTITY_SIMPLE_NAME,
                page.getTotalElements(), page.getTotalPages(), pageable.getFrom(), page.getSize(), page.getSort());
        List<Event> entities = page.getContent();
        ((EventServiceImpl) eventService).setViews(entities);
        return eventMapper.toShortDtos(page.getContent());
    }

    private void checkOwner(Long userId, Subscription sbr) {
        if (!Objects.equals(sbr.getOwner().getId(), userId)) {
            throw new ConflictException(String.format("User with id=%s is not the owner of the subscription", userId));
        }
    }

    private void checkFavoriteObservable(Set<Long> favorites) {
        if (userRepository.existsByIdInAndObservable(favorites, false)) {
            throw new ConflictException(("Favourite must be 'observable'"));
        }
    }

    private void checkSubscriptionExists(Long userId) {
        if (subscriptionRepository.existsByOwnerId(userId)) {
            throw new ConflictException(String.format("Subscription for user with id=%s already exists", userId));
        }
    }

    private List<Long> findFavoriteIds(Long userId) {
        SubscriptionDtoResponse dto = find(userId);
        return dto.getFavorites().stream()
                .map(UserDtoShort::getId).collect(Collectors.toList());
    }
}
