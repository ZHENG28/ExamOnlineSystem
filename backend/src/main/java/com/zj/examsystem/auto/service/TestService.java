package com.zj.examsystem.auto.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.auto.entity.Test;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface TestService extends IService<Test> {
    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size, Integer... id);

    public int saveTest(Test test);

    public int deleteTest(Integer[] id);

    public Test findById(Integer testId);

    public Boolean findExamTimeByTestId(String stuAccount, Integer testId);
}
