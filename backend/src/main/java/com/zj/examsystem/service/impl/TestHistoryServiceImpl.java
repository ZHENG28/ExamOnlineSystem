package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.ShortAnswer;
import com.zj.examsystem.entity.TestHistory;
import com.zj.examsystem.mapper.ShortAnswerMapper;
import com.zj.examsystem.mapper.TestHistoryMapper;
import com.zj.examsystem.service.TestHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.utils.PythonExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.zj.examsystem.utils.Const.SHORT_ANSWER_QUESTION_LIST_SPLIT;


@Service
@SuppressWarnings("all")
public class TestHistoryServiceImpl extends ServiceImpl<TestHistoryMapper, TestHistory> implements TestHistoryService {
    @Autowired
    private TestHistoryMapper testHistoryMapper;

    @Autowired
    private ShortAnswerMapper shortAnswerMapper;

    @Override
    public List<Map<String, Object>> findScoreByTestId(Integer testId) {
        //        Test test = testRepo.findById(testId).orElseThrow();
        //        List<Integer> histories = testHistoryMapper.findByTestId(test);
        List<Map<String, Object>> scoreList = new ArrayList<>();
        int[] scoreArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        //        for (Integer score : histories) {
        //            if (score == 100) {
        //                scoreArr[9]++;
        //                continue;
        //            }
        //            scoreArr[score / 10]++;
        //        }
        int[] arr = {scoreArr[0] + scoreArr[1] + scoreArr[2] + scoreArr[3] + scoreArr[4] + scoreArr[5], scoreArr[6], scoreArr[7], scoreArr[8],
                scoreArr[9]};
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", arr[i]);
            String str = "";
            switch (i) {
                case 0:
                    str = "60以下";
                    break;
                case 1:
                    str = "60-69";
                    break;
                case 2:
                    str = "70-79";
                    break;
                case 3:
                    str = "80-89";
                    break;
                case 4:
                    str = "90-100";
                    break;
            }
            map.put("name", str);
            scoreList.add(map);
        }
        return scoreList;
    }

    @Override
    public IPage<TestHistory> findAllByAccount(Integer pageno, Integer size, Integer userId) {
        QueryWrapper<Map<String, Object>> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Page<Map<String, Object>> page = new Page<>(pageno, size);
        return testHistoryMapper.selectPageWithTest(page, queryWrapper);
    }

    @Override
    public IPage<TestHistory> findAllByTestId(Integer pageno, Integer size, Integer testId) {
        Page<TestHistory> page = new Page<>(pageno, size);
        QueryWrapper<TestHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("th.test_id", testId);
        return testHistoryMapper.selectPageByTestId(page, queryWrapper);
    }

    public String[][] getReplyArrayFromString(String replyStr) {
        String[] replys = replyStr.split(SHORT_ANSWER_QUESTION_LIST_SPLIT);
        String[][] result = new String[replys.length][2];
        for (int i = 0; i < replys.length; i++) {
            String reply = replys[i];
            result[i] = !reply.trim().isEmpty() ? reply.split(" ") : new String[2];
        }
        return result;
    }

    public Integer judgeAnswerByQuestionId(Integer questionId, List<Question> questionList, String answer) {
        for (Question question : questionList) {
            if (questionId == question.getQuestionId()) {
                switch (question.getTypeId()) {
                    case 1:
                    case 2:
                        return answer.equals(question.getCorrect()) ? 1 : 0;
                    case 3:
                        return !answer.trim().isEmpty() ? 1 : 0;
                }
            }
        }
        return null;
    }

    public List<String> findShortAnswerListByTestIdAndQuestionId(TestHistory insert, Integer testId, Integer questionId) {
        List<String> shortAnswerList = new ArrayList<>();
        QueryWrapper<TestHistory> testHistoryQueryWrapper = new QueryWrapper<>();
        testHistoryQueryWrapper.eq("test_id", testId);
        List<TestHistory> testHistoryList = testHistoryMapper.selectList(testHistoryQueryWrapper);
        testHistoryList.remove(insert);
        for (TestHistory testHistory : testHistoryList) {
            String answer = findShortAnswerBytestHistory(testHistory, questionId);
            if (null != answer) {
                shortAnswerList.add(answer);
            }
        }
        return shortAnswerList;
    }

    public String findShortAnswerBytestHistory(TestHistory testHistory, Integer questionId) {
        String[][] replys = getReplyArrayFromString(testHistory.getReply());
        for (String[] reply : replys) {
            if (null != reply[0] && questionId == Integer.valueOf(reply[0])) {
                return reply[1];
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean commit(TestHistory testHistory, List<Question> questionList) {
        // 1. 计算分数
        Integer correct = 0;
        String[][] replys = getReplyArrayFromString(testHistory.getReply());
        for (String[] reply : replys) {
            if (null != reply[0]) {
                correct += judgeAnswerByQuestionId(Integer.valueOf(reply[0]), questionList, reply[1]);
            }
        }
        testHistory.setCorrect(correct);
        // 3. 比较已有回答和新增回答之间的文本相似度
        //        QueryWrapper<ShortAnswer> shortAnswerQueryWrapper = new QueryWrapper<>();
        //        shortAnswerQueryWrapper.eq("test_id", testHistory.getTestId());
        //        List<ShortAnswer> shortAnswerQuestionList = shortAnswerMapper.selectList(shortAnswerQueryWrapper);
        //        for (ShortAnswer shortAnswer : shortAnswerQuestionList) {
        //            List<String> shortAnswerList = findShortAnswerListByTestIdAndQuestionId(testHistory,
        //                    testHistory.getTestId(), shortAnswer.getQuestionId());
        //            //            System.out.println(shortAnswerList);
        //            if (!shortAnswerList.isEmpty()) {
        //                String insertAnswer = findShortAnswerBytestHistory(testHistory, shortAnswer.getQuestionId());
        //                PythonExecute.getTextSimilarity(shortAnswerList, insertAnswer, shortAnswer.getThreshold());
        //            }
        //        }
        // 2. 保存历史记录
        return testHistoryMapper.insert(testHistory) == 1;
    }
}
