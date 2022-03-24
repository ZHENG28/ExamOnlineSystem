package com.zj.examsystem.repository;

import com.zj.examsystem.model.Clazz;
import com.zj.examsystem.model.Student;
import com.zj.examsystem.model.Test;
import com.zj.examsystem.model.TestHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestRepository extends JpaRepository<Test, Integer>
{
    Page<Test> findAllByTestClazz(Clazz testClazz, Pageable pageable);
}
