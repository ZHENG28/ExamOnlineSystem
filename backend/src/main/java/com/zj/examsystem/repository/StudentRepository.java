package com.zj.examsystem.repository;

import com.zj.examsystem.model.Student;
import com.zj.examsystem.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer>
{
    Optional<Student> findByAccount(String account);

    boolean existsByAccount(String account);
}
