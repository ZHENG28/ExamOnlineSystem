package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.TestHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.TestHistoryWithUserTestSubject;

import java.util.List;
import java.util.Map;


public interface TestHistoryService extends IService<TestHistory> {
    public Boolean commit(TestHistory testHistory, String[] reply, List<Question> questionList);

    public List<Map<String, Object>> loadObjectChartData(Integer testId);

    public IPage<TestHistoryWithUserTestSubject> findAllByUserId(Integer pageno, Integer size, Integer userId);

    public IPage<TestHistoryWithUserTestSubject> findAllByTestId(Integer pageno, Integer size, Integer testId);

    public TestHistoryWithUserTestSubject findTestInfoByCompoundId(Integer testId, Integer studentId);

    public TestHistory findHistoryByCompoundId(Integer testId, Integer studentId,Integer order);
}
