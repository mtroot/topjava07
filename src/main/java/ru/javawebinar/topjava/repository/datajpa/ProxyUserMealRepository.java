package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Query("SELECT FROM UserMeal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<UserMeal> findAll(@Param("userId") int userId);

    @Override
    @Transactional
    UserMeal save(UserMeal entity);

    @Override
    UserMeal findOne(Integer integer);

    @Transactional
    @Modifying
    @Query(name = UserMeal.DELETE)
    int delete(@Param("id") int id, @Param("userId") int userId);
}
