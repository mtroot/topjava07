package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Query("SELECT m FROM UserMeal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<UserMeal> getAll(@Param("userId") int userId);

    @Override
    @Transactional
    UserMeal save(UserMeal entity);

    @Query("SELECT m FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId")
    UserMeal get(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query(name = UserMeal.DELETE)
    int delete(@Param("id") int id, @Param("userId") int userId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT m FROM UserMeal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startTime AND :endTime ORDER BY m.dateTime DESC")
    List<UserMeal> getBetween(@Param("startTime") LocalDateTime startTime,
                              @Param("endTime") LocalDateTime endTime,
                              @Param("userId") int userId);
}
