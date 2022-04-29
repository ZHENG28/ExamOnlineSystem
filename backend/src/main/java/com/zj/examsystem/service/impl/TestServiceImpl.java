package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.Answer;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.ShortAnswer;
import com.zj.examsystem.entity.Test;
import com.zj.examsystem.mapper.*;
import com.zj.examsystem.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zj.examsystem.utils.Const.SHORT_ANSWER_QUESTION_LIST_SPLIT;


@Service
@SuppressWarnings("all")
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Autowired
    private TestHistoryMapper testHistoryMapper;

    @Autowired
    private ShortAnswerMapper shortAnswerMapper;

    @Override
    public IPage<Test> findAll(Integer pageno, Integer size, Integer userId) {
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u.user_id", userId);
        Page<Test> page = new Page<>(pageno, size);
        return testMapper.selectPageWithClazzAndSubject(page, queryWrapper);
    }

    @Override
    @Transactional
    public Boolean saveTest(Test test) {
        if (test.getTestId() == null) {
            String[] shortAnswerStr = test.getShortAnswer().split(SHORT_ANSWER_QUESTION_LIST_SPLIT);
            test.setShortAnswer(null);
            if (testMapper.insert(test) == 1) {
                QueryWrapper<Test> testQueryWrapper = new QueryWrapper<>();
                testQueryWrapper.eq("test_name", test.getTestName());
                Test inserted = testMapper.selectOne(testQueryWrapper);

                int length = 0;
                for (String s : shortAnswerStr) {
                    String[] arr = s.split(" ");
                    ShortAnswer answer = new ShortAnswer();
                    answer.setTestId(inserted.getTestId());
                    answer.setQuestionId(Integer.valueOf(arr[0]));
                    answer.setThreshold(Integer.valueOf(arr[1]));
                    length += shortAnswerMapper.insert(answer);
                }
                return length == shortAnswerStr.length;
            } else {
                return false;
            }
        } else {
            String[] shortAnswerStr = test.getShortAnswer().split(SHORT_ANSWER_QUESTION_LIST_SPLIT);
            test.setShortAnswer(null);
            if (testMapper.updateById(test) == 1) {
                int length = 0;
                QueryWrapper<ShortAnswer> shortAnswerQueryWrapper = new QueryWrapper<>();
                shortAnswerQueryWrapper.eq("test_id", test.getTestId());
                List<ShortAnswer> shortAnswerList = shortAnswerMapper.selectList(shortAnswerQueryWrapper);
                for (String s : shortAnswerStr) {
                    String[] arr = s.split(" ");
                    ShortAnswer answer = new ShortAnswer();
                    for (int i = 0; i < shortAnswerList.size(); i++) {
                        ShortAnswer temp = shortAnswerList.get(i);
                        if (Integer.valueOf(arr[0]) == temp.getQuestionId()) {
                            answer = temp;
                            break;
                        }
                    }
                    answer.setThreshold(Integer.valueOf(arr[1]));
                    length += shortAnswerMapper.updateById(answer);
                }
                return length == shortAnswerStr.length;
            } else {
                return false;
            }
        }
    }

    @Override
    @Transactional
    public int deleteTest(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return testMapper.deleteBatchIds(ids);
    }

    @Override
    public Test findById(Integer testId) {
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t.test_id", testId);
        Page<Test> page = new Page<>();
        IPage<Test> testIpage = testMapper.selectPageWithClazzAndSubject(page, queryWrapper);
        Test test = testIpage.getRecords().get(0);
        System.out.println(test);

        QueryWrapper<ShortAnswer> shortAnswerQueryWrapper = new QueryWrapper<>();
        shortAnswerQueryWrapper.eq("test_id", testId);
        List<ShortAnswer> shortAnswers = shortAnswerMapper.selectList(shortAnswerQueryWrapper);
        String str = "";
        for (ShortAnswer shortAnswer : shortAnswers) {
            str += shortAnswer.getQuestionId() + " " + shortAnswer.getThreshold() + SHORT_ANSWER_QUESTION_LIST_SPLIT;
        }
        test.setShortAnswer(str.length() != 0 ? str.substring(0, str.length() - 1) : str);
        return test;
    }

    @Override
    public Boolean findExamTimeByTestId(Integer userId, Integer testId) {
        return findById(testId).getExamTime() == testHistoryMapper.countByTestAndStudent(userId, testId);
    }
}
