package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.TestHistory;
import com.zj.examsystem.entity.TestHistoryQuestionReply;
import com.zj.examsystem.entity.TestHistoryWithUserTestSubject;
import com.zj.examsystem.mapper.TestHistoryMapper;
import com.zj.examsystem.mapper.TestHistoryQuestionReplyMapper;
import com.zj.examsystem.mapper.TestQuestionListMapper;
import com.zj.examsystem.service.TestHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestHistoryServiceImpl extends ServiceImpl<TestHistoryMapper, TestHistory> implements TestHistoryService {
    @Resource
    private TestHistoryMapper testHistoryMapper;

    @Resource
    private TestQuestionListMapper testQuestionListMapper;

    @Resource
    private TestHistoryQuestionReplyMapper testHistoryQuestionReplyMapper;

    @Override
    public List<Map<String, Object>> loadObjectChartData(Integer testId) {
        List<Integer> historyIds = testHistoryMapper.findIdByTestId(testId);
        List<Question> objectQuestionList = testQuestionListMapper.findObjectQuestionIdsByTestId(testId);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<Integer, Integer> correct = new HashMap<>();
        for (Question question : objectQuestionList) {
            correct.put(question.getQuestionId(), 0);
        }
        for (Integer historyId : historyIds) {
            List<Map<String, Object>> reply = testHistoryQuestionReplyMapper.findReplyByHistoryIdWithCorrect(historyId);
            for (Map<String, Object> map : reply) {
                switch ((Integer) map.get("type_id")) {
                    case 1: // choice
                        correct.put((Integer) map.get("question_id"), correct.get(map.get("question_id")) + (map.get("answer_sign").equals(map.get(
                                "reply")) ? 1 : 0));
                        break;
                    case 2: // judge
                        String res = Boolean.parseBoolean((String) map.get("reply")) ? "1" : "0";
                        correct.put((Integer) map.get("question_id"), correct.get(map.get("question_id")) + (res.equals(map.get("answer_sign"))
                                ? 1 : 0));
                        break;
                }
            }
        }
        for (Question question : objectQuestionList) {
            Map<String, Object> map = new HashMap<>();
            map.put("questionTitle", question.getQuestionTitle());
            map.put("correctNumber", correct.get(question.getQuestionId()));
            result.add(map);
        }
        return result;
    }

    @Override
    public IPage<TestHistoryWithUserTestSubject> findAllByUserId(Integer pageno, Integer size, Integer userId) {
        Page<TestHistory> page = new Page<>(pageno, size);
        QueryWrapper<TestHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);
        return testHistoryMapper.selectPageWithTest(page, queryWrapper);
    }

    @Override
    public IPage<TestHistoryWithUserTestSubject> findAllByTestId(Integer pageno, Integer size, Integer testId) {
        Page<TestHistory> page = new Page<>(pageno, size);
        QueryWrapper<TestHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_id", testId);
        return testHistoryMapper.selectPageWithTest(page, queryWrapper);
    }

    public Integer judgeAnswerByQuestionId(Integer questionId, List<Question> questionList, String answer) {
        for (Question question : questionList) {
            // 封装类型的比较，直接使用==是在比较两者内存地址，建议均使用equals()
            if (question.getQuestionId().equals(questionId)) {
                switch (question.getTypeId()) {
                    case 1:
                    case 2:
                        return answer.equals(question.getCorrect()) ? 1 : 0;
                    case 3:
                        return !answer.trim().isEmpty() ? 1 : 0;
                }
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public Boolean commit(TestHistory testHistory, String[] reply, List<Question> questionList) {
        // 特殊情况：提交空白卷
        if (null == reply) {
            testHistory.setCorrect(0);
            return testHistoryMapper.insert(testHistory) == 1;
        }

        // 1. 计算分数
        Integer correct = 0;
        for (String r : reply) {
            String[] arr = r.split(" ");
            correct += judgeAnswerByQuestionId(Integer.valueOf(arr[0]), questionList, arr[1]);
        }
        testHistory.setCorrect(correct);

        // 2. 保存历史记录
        int length = 0;
        if (testHistoryMapper.insert(testHistory) == 1) {
            for (String r : reply) {
                String[] arr = r.split(" ");
                TestHistoryQuestionReply questionReply = new TestHistoryQuestionReply(testHistory.getHistoryId(), Integer.valueOf(arr[0])
                        , arr[1]);
                length += testHistoryQuestionReplyMapper.insert(questionReply);
            }
        }
        return length == reply.length;
    }

    @Override
    public TestHistoryWithUserTestSubject findTestInfoByCompoundId(Integer testId, Integer studentId) {
        return testHistoryMapper.findTestInfoByCompoundId(testId, studentId);
    }

    @Override
    public TestHistory findHistoryByCompoundId(Integer testId, Integer studentId, Integer order) {
        QueryWrapper<TestHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_id", testId);
        queryWrapper.eq("student_id", studentId);
        queryWrapper.orderByDesc("take_time");
        queryWrapper.last("LIMIT " + order + ",1");
        return testHistoryMapper.selectOne(queryWrapper);
    }
}
