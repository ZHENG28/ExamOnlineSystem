package com.zj.examsystem.repository;

import com.zj.examsystem.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>
{
    Optional<Teacher> findByAccount(String account);

    boolean existsByAccount(String account);

    List<Teacher> findTeacherByIdNotIn(Collection<Integer> id);
}
