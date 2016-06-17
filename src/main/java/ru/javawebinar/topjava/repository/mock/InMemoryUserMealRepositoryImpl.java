package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.ADMIN_ID;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_ID;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    public static final Comparator<UserMeal> USER_MEAL_COMPARATOR = (um1, um2) -> um2.getDateTime().compareTo(um1.getDateTime());

    // Map userId -> (mealId, meal)
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 17, 10, 0), "Завтрак", 500), USER_ID);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 17, 13, 0), "Обед", 1000), USER_ID);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 17, 17, 0), "Ужин", 500), USER_ID);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 18, 10, 0), "Завтрак", 500), USER_ID);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 18, 13, 0), "Обед", 1000), USER_ID);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 18, 17, 0), "Ужин", 600), USER_ID);

        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 17, 10, 0), "Завтрак админа", 600), ADMIN_ID);
        save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 17, 17, 0), "Ужин админа", 600), ADMIN_ID);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        Integer mealId = userMeal.getId();

        if (userMeal.isNew()) {
            mealId = counter.incrementAndGet();
            userMeal.setId(mealId);
        } else if (get(mealId, userId) == null){
            return null;
        }
        Map<Integer, UserMeal> userMeals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        userMeals.put(mealId, userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, UserMeal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public UserMeal get(int id, int userId) {
        Map<Integer, UserMeal> userMeals = repository.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        Map<Integer, UserMeal> meals = repository.get(userId);
        return meals == null ?
                Collections.emptyList() :
                meals.values().stream().sorted(USER_MEAL_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);
        return getAll(userId).stream()
                .filter(userMeal -> userMeal.getDateTime().compareTo(startDateTime) >= 0 &&
                        userMeal.getDateTime().compareTo(endDateTime) <= 0)
                .sorted(USER_MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll(int userId) {

    }
}

