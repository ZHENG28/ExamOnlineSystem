package com.zj.examsystem.repository;

import com.zj.examsystem.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer>
{
    Optional<Manager> findByAccount(String account);
}
