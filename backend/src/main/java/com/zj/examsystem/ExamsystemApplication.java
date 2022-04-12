package com.zj.examsystem;

import com.zj.examsystem.repository.ManagerRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zj.examsystem.auto.mapper")
@SpringBootApplication
public class ExamsystemApplication
{
    @Autowired
    ManagerRepository managerRepo;

    public static void main(String[] args)
    {
        SpringApplication.run(ExamsystemApplication.class, args);
    }
}
