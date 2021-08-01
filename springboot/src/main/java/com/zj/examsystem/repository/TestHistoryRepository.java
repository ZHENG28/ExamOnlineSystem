package com.zj.examsystem.repository;

import com.zj.examsystem.model.Student;
import com.zj.examsystem.model.Test;
import com.zj.examsystem.model.TestHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestHistoryRepository extends JpaRepository<TestHistory, Integer>
{
    List<TestHistory> findByStuId(Integer stuId);

    @Query(value = "select max(score) from testhistory group by stu_id", nativeQuery = true)
    List<Integer> findByTestId(Test test);

    Page<TestHistory> findAllByStu(Student stu, Pageable pageable);

    List<TestHistory> findByTestAndStu(Test test, Student stu);
}
