package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface TestService extends IService<Test> {
    public IPage<Test> findAll(Integer pageno, Integer size, Integer userId);

    public Map<String, Object> findById(Integer testId);

    public Boolean findExamTimeByTestId(Integer userId, Integer testId);

    public Boolean saveTest(Test test, Integer[] questionList, String[] shortAnswer);

    public Boolean updateTest(Test test, Integer[] questionList, String[] shortAnswer);

    public Integer deleteTest(Integer[] id);
}
