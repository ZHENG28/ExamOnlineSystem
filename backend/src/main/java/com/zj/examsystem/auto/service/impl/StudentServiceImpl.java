package com.zj.examsystem.auto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.auto.entity.*;
import com.zj.examsystem.auto.entity.Student;
import com.zj.examsystem.auto.mapper.ClazzMapper;
import com.zj.examsystem.auto.mapper.StudentMapper;
import com.zj.examsystem.auto.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Service
@SuppressWarnings("all")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size) {
        QueryWrapper<Map<String, Object>> queryWrapper = new QueryWrapper<>();
        Page<Map<String, Object>> page = new Page<>(pageno, size);

        return studentMapper.selectPageWithClazz(page, queryWrapper);
    }

    public int saveStudent(Student student) {
        return student.getId() != null ? studentMapper.updateById(student) : studentMapper.insert(student);
    }

    @Transactional
    public int deleteStudent(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return studentMapper.deleteBatchIds(ids);
    }

    @Override
    public Student findByAccount(String account) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);

        return studentMapper.selectOne(queryWrapper);
    }
}
