package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFounException;

import java.util.List;

/**
 * Created by tmr on 6/14/16.
 */
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(int id) throws NotFounException {

    }

    @Override
    public User get(int id) throws NotFounException {
        return null;
    }

    @Override
    public User getByEmail(String email) throws NotFounException {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User user) throws NotFounException {

    }
}
