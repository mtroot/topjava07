package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Override
    List<UserMeal> findAll(Sort sort);

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
