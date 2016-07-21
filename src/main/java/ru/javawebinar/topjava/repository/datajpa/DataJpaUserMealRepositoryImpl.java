package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{

    private static final Sort SORT_DATETIME = new Sort(new Sort.Order(Sort.Direction.DESC, "date_time"));

    @Autowired
    ProxyUserMealRepository proxy;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        User user = userMeal.getUser();
        if (user == null) {
            User u = userRepository.get(userId);
            if(u == null) {
                return null;
            } else {
                userMeal.setUser(u);
            }
        } else {
            user.setId(userId);
        }
        return proxy.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        if (proxy.findOne(id).getUser().getId() == userId) {
            return proxy.findOne(id);
        }
        return null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        List<UserMeal> meals = proxy.findAll(SORT_DATETIME)
                .stream()
                .filter(m -> m.getUser().getId() == userId)
                .collect(Collectors.toList());
        return meals;
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<UserMeal> meals = getAll(userId)
                .stream()
                .filter(m -> m.getDateTime().isAfter(startDate) && m.getDateTime().isBefore(endDate))
                .collect(Collectors.toList());
        return meals;
    }
}
