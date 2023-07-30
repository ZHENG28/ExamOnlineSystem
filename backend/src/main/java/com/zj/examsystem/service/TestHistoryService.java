package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.TestHistory;
import com.zj.examsystem.entity.TestHistoryWithUserTestSubject;

import java.util.List;
import java.util.Map;

public interface TestHistoryService extends IService<TestHistory> {
    Boolean commit(TestHistory testHistory, String[] reply, List<Question> questionList);

    List<Map<String, Object>> loadObjectChartData(Integer testId);

    IPage<TestHistoryWithUserTestSubject> findAllByUserId(Integer pageno, Integer size, Integer userId);

    IPage<TestHistoryWithUserTestSubject> findAllByTestId(Integer pageno, Integer size, Integer testId);

    TestHistoryWithUserTestSubject findTestInfoByCompoundId(Integer testId, Integer studentId);

    TestHistory findHistoryByCompoundId(Integer testId, Integer studentId, Integer order);
}
