package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: mtroot (Timur Muratov)
 * Date: 10.06.2016
 */
public class MealServlet extends HttpServlet{
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("getAll and forward to mealList.jsp");
        List<UserMealWithExceed> mealList = UserMealsUtil.getAllWithExceeded(UserMealsUtil.MEAL_LIST, 2000);
        req.setAttribute("mealList", mealList);
        req.getRequestDispatcher("mealList.jsp").forward(req, resp);
    }
}
