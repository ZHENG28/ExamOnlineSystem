package com.zj.examsystem.auto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.auto.entity.Student;
import com.zj.examsystem.auto.entity.Test;
import com.zj.examsystem.auto.mapper.StudentMapper;
import com.zj.examsystem.auto.mapper.TestMapper;
import com.zj.examsystem.auto.mapper.TesthistoryMapper;
import com.zj.examsystem.auto.service.TestService;
import com.zj.examsystem.model.TestHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Autowired
    private TesthistoryMapper testhistoryMapper;

    @Autowired
    private StudentMapper studentMapper;

    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size, Integer... id) {
        QueryWrapper<Map<String, Object>> queryWrapper = new QueryWrapper<>();
        Page<Map<String, Object>> page = new Page<>(pageno, size);
        if (id == null) {
            return testMapper.selectPageWithClazzAndSubject(page, queryWrapper);
        } else {
            Student stu = studentMapper.selectById(id);
            queryWrapper.eq("test_clazz", stu.getClazzId());
            return testMapper.selectPageWithClazzAndSubject(page, queryWrapper);
        }
    }

    public int saveTest(Test test) {
        return test.getTestId() != null ? testMapper.updateById(test) : testMapper.insert(test);
    }

    @Transactional
    public int deleteTest(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return testMapper.deleteBatchIds(ids);
    }

    public Test findById(Integer testId) {
        return testMapper.selectWithClazzAndSubjectById(testId);
    }

    public Boolean findExamTimeByTestId(String stuAccount, Integer testId) {
        Test test = findById(testId);
        return test.getExamTime() == testhistoryMapper.findByTestAndStudent(stuAccount, testId);
    }
}
