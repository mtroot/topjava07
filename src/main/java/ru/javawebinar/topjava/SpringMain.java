package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.mock.MockUserRepository;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        String[] s = appCtx.getBeanDefinitionNames();
        System.out.println();
        for (String d : s) {
            System.out.println(d);
        }
        System.out.println();
        MockUserRepository mockUserRepository = (MockUserRepository) appCtx.getBean("mockUserRepository");
        mockUserRepository = appCtx.getBean(MockUserRepository.class);
        appCtx.close();
    }
}
