package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.*;
import com.zj.examsystem.mapper.*;
import com.zj.examsystem.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {
    @Resource
    private TestMapper testMapper;

    @Resource
    private TestHistoryMapper testHistoryMapper;

    @Resource
    private ShortAnswerMapper shortAnswerMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TestQuestionListMapper testQuestionListMapper;

    @Override
    public IPage<Test> findAll(Integer pageno, Integer size, Integer userId) {
        User user = userMapper.findByIdWithRole(userId);
        Page<Test> page = new Page<>(pageno, size);
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        if ("ROLE_TEACHER".equals(user.getRoleName())) {
            queryWrapper.eq("teacher_id", userId);
            return testMapper.selectPageWithSubject(page, queryWrapper);
        } else {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("user_id", userId);
            List<User> studentList = userMapper.selectStudentWithRoleAndClazz(userQueryWrapper);
            if (!studentList.isEmpty()) {
                queryWrapper.eq("clazz_id", studentList.get(0).getClazzId());
                queryWrapper.orderByDesc("end_date");
                return testMapper.selectPageWithSubject(page, queryWrapper);
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> findById(Integer testId) {
        Map<String, Object> map = new HashMap<>();
        Test test = testMapper.findById(testId);
        map.put("test", test);

        List<Integer> questionList = testQuestionListMapper.findQuestionIdsByTestId(testId);
        map.put("questionList", questionList);

        QueryWrapper<ShortAnswer> shortAnswerQueryWrapper = new QueryWrapper<>();
        shortAnswerQueryWrapper.eq("test_id", testId);
        List<ShortAnswer> shortAnswerList = shortAnswerMapper.selectList(shortAnswerQueryWrapper);
        List<String> shortAnswers = new ArrayList<>();
        for (ShortAnswer shortAnswer : shortAnswerList) {
            shortAnswers.add(shortAnswer.getQuestionId() + " " + shortAnswer.getThreshold());
        }
        map.put("shortAnswer", shortAnswers);
        return map;
    }

    @Override
    public Boolean findExamTimeByTestId(Integer userId, Integer testId) {
        QueryWrapper<TestHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);
        queryWrapper.eq("test_id", testId);
        return ((Test) findById(testId).get("test")).getTestTime() > testHistoryMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional
    public Boolean saveTest(Test test, Integer[] questionList, String[] shortAnswer) {
        if (testMapper.insert(test) == 1) {
            int length = 0;
            for (Integer questionId : questionList) {
                TestQuestionList testQuestionList = new TestQuestionList(test.getTestId(), questionId);
                length += testQuestionListMapper.insert(testQuestionList);
            }
            if (shortAnswer != null) {
                for (String s : shortAnswer) {
                    String[] arr = s.split(" ");
                    ShortAnswer answer = new ShortAnswer(Integer.valueOf(arr[1]), Integer.valueOf(arr[0]), test.getTestId());
                    length += shortAnswerMapper.insert(answer);
                }
            }
            return length == questionList.length + (shortAnswer != null ? shortAnswer.length : 0);
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateTest(Test test, Integer[] questionList, String[] shortAnswer) {
        try {
            if (testMapper.updateById(test) == 1) {
                List<Integer> questionIds = testQuestionListMapper.findQuestionIdsByTestId(test.getTestId());
                for (Integer questionId : questionList) {
                    questionIds.remove(questionId);
                }
                if (!questionIds.isEmpty()) {
                    QueryWrapper<TestQuestionList> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("test_id", test.getTestId());
                    for (Integer questionId : questionIds) {
                        queryWrapper.eq("question_id", questionId);
                    }
                    testQuestionListMapper.delete(queryWrapper);
                }

                if (shortAnswer != null) {
                    List<Integer> shortAnswerIds = shortAnswerMapper.findShortAnswerIdsByTestId(test.getTestId());
                    for (String s : shortAnswer) {
                        String[] arr = s.split(" ");
                        ShortAnswer answer = shortAnswerMapper.findByCompoundId(Integer.valueOf(arr[0]),
                                test.getTestId());
                        if (answer != null) { // update
                            answer.setThreshold(Integer.valueOf(arr[1]));
                            shortAnswerMapper.updateById(answer);
                            shortAnswerIds.remove(answer.getShortAnswerId());
                        } else { // insert
                            ShortAnswer insert = new ShortAnswer(Integer.valueOf(arr[1]), Integer.valueOf(arr[0]), test.getTestId
                                    ());
                            shortAnswerMapper.insert(insert);
                        }
                    }
                    if (!shortAnswerIds.isEmpty()) {
                        shortAnswerMapper.deleteBatchIds(shortAnswerIds);
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public Integer deleteTest(Integer[] id) {
        List<Integer> ids = new ArrayList<>(Arrays.asList(id));
        return testMapper.deleteBatchIds(ids);
    }
}
