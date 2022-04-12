package com.zj.examsystem.auto.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.auto.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.auto.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface StudentService extends IService<Student> {
    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size);

    public int saveStudent(Student student);

    public int deleteStudent(Integer[] id);

    public Student findByAccount(String account);
}
