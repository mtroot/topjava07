package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            InMemoryUserRepositoryImpl userRepository = appCtx.getBean(InMemoryUserRepositoryImpl.class);
            System.out.println(userRepository.get(1));
            System.out.println();
            UserMealRestController mealController = appCtx.getBean(UserMealRestController.class);
            List<UserMealWithExceed> userMealWithExceeds =
                    mealController.getBetween(
                            LocalDate.of(2016, Month.JUNE, 1), LocalDate.of(2016, Month.JUNE, 30),
                            LocalTime.MIN, LocalTime.MAX
                    );
            userMealWithExceeds.forEach(System.out::println);
        }
    }
}
