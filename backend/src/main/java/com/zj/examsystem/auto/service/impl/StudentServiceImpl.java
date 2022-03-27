package com.zj.examsystem.auto.service.impl;

import com.zj.examsystem.auto.entity.Student;
import com.zj.examsystem.auto.mapper.StudentMapper;
import com.zj.examsystem.auto.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
