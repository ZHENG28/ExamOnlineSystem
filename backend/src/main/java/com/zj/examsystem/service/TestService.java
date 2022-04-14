package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface TestService extends IService<Test> {
    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size, Integer... id);

    public int saveTest(Test test);

    public int deleteTest(Integer[] id);

    public Test findById(Integer testId);

    public Boolean findExamTimeByTestId(String stuAccount, Integer testId);
}
