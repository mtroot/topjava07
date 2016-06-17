package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;

import java.util.Collections;
import java.util.Set;

/**
 * GKislin
 * 06.03.2015.
 *
 * Mock implementation/
 */
public class LoggedUser {
    protected int id = 2;
    protected Set<Role> roles = Collections.singleton(Role.ROLE_USER);
    protected boolean enabled = true;
    protected int caloriesPerDay = 2000;

    private static LoggedUser LOGGED_USER = new LoggedUser();

    public static LoggedUser get() { return LOGGED_USER; }

    public static int id() {
        return get().id;
    }

    public static void setId(int id) { get().id = id; }

    public Set<Role> getAuthorites() { return roles; }

    public boolean isEnabled() { return enabled; }

    public static int getCaloriesPerDay() {
        return get().caloriesPerDay;
    }
}
