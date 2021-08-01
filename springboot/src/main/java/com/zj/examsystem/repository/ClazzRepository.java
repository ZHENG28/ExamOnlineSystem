package com.zj.examsystem.repository;

import com.zj.examsystem.model.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClazzRepository extends JpaRepository<Clazz, Integer>
{
    @Query("SELECT coalesce(max(clazzId), 0) FROM Clazz")
    Integer getMaxId();

    @Query("SELECT distinct(major) FROM Clazz")
    List<String> selectDistinctMajor();

    @Query("SELECT distinct(clazzName) FROM Clazz")
    List<String> selectDistinctClazzName();

    List<Clazz> findByMajor(String major);
}