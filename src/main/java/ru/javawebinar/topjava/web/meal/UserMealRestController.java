package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by tmr on 7/24/16.
 */
@Controller
public class UserMealRestController extends AbstractUserMealController {

    private static final String FILTER = "filter";
    private static final String CREATE = "create";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(Model model) {
        model.addAttribute("mealList", getAll());
        return "mealList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String mealListAction(HttpServletRequest request, Model model) {
        String action = request.getParameter("action");
        if(FILTER.equals(action)) {
            LocalDate startDate = TimeUtil.parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = TimeUtil.parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = TimeUtil.parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = TimeUtil.parseLocalTime(request.getParameter("endTime"));
            model.addAttribute("mealList", getBetween(startDate,startTime,endDate,endTime));
            return "mealList";
        }
        return "userList";
    }
}
