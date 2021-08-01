package com.zj.examsystem.repository;

import com.zj.examsystem.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer>
{
    @Query("SELECT distinct(subName), subId FROM Subject")
    List<String> selectDistinctSubject();
}
