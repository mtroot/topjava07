package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFounException;

import java.util.List;

public interface UserService {
    public User save (User user);

    public void delete (int id) throws NotFounException;

    public User get (int id) throws NotFounException;

    public User getByEmail (String email) throws NotFounException;

    public List<User> getAll();

    public void update (User user) throws NotFounException;
}
