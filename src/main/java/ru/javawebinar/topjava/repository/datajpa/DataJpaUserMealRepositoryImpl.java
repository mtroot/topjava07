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

    @Autowired
    ProxyUserMealRepository proxy;

    @Autowired
    ProxyUserRepository userProxy;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (!userMeal.isNew() && get(userMeal.getId(), userId) == null) {
            return null;
        }
        userMeal.setUser(userProxy.findOne(userId));
        return proxy.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return proxy.get(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.getAll(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.getBetween(startDate, endDate, userId);
    }
}
