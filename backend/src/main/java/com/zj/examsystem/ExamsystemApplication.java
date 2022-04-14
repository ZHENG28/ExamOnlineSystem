package com.zj.examsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zj.examsystem.mapper")
@SpringBootApplication
public class ExamsystemApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ExamsystemApplication.class, args);
    }
}
