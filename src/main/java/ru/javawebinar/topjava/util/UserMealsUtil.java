package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).stream().forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }
    public static List<UserMealWithExceed> getFilteredMealsWithExceeded (List<UserMeal> mealList, LocalTime startTime,
                                                                         LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = mealList.stream()
                .collect(
                        Collectors.toMap(um -> um.getDateTime().toLocalDate(),
                        UserMeal::getCalories, Integer::sum));
        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(),
                        um.getCalories(), map.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime,
                                                                                LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> result = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        List<UserMeal> list = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            if(TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(userMeal);
            }
            if (map.size() == 0 || !map.containsKey(userMeal.getDateTime().toLocalDate())){
                map.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
                continue;
            }
            LocalDate ld = userMeal.getDateTime().toLocalDate();
            map.put(ld, map.get(ld) + userMeal.getCalories());
        }
        for (UserMeal userMeal : list) {
            result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(),
                    userMeal.getCalories(), map.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
        }
        return result;
    }
}
