package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.Test;

import java.util.Map;

public interface TestService extends IService<Test> {
    IPage<Test> findAll(Integer pageno, Integer size, Integer userId);

    Map<String, Object> findById(Integer testId);

    Boolean findExamTimeByTestId(Integer userId, Integer testId);

    Boolean saveTest(Test test, Integer[] questionList, String[] shortAnswer);

    Boolean updateTest(Test test, Integer[] questionList, String[] shortAnswer);

    Integer deleteTest(Integer[] id);
}
