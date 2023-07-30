package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.CompareShortAnswer;
import com.zj.examsystem.entity.ShortAnswer;
import com.zj.examsystem.entity.TestHistory;
import com.zj.examsystem.entity.TestHistoryQuestionReply;
import com.zj.examsystem.mapper.CompareShortAnswerMapper;
import com.zj.examsystem.mapper.ShortAnswerMapper;
import com.zj.examsystem.mapper.TestHistoryMapper;
import com.zj.examsystem.mapper.TestHistoryQuestionReplyMapper;
import com.zj.examsystem.service.CompareShortAnswerService;
import com.zj.examsystem.utils.PythonExecute;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompareShortAnswerServiceImpl extends ServiceImpl<CompareShortAnswerMapper, CompareShortAnswer> implements CompareShortAnswerService {

    @Resource
    private CompareShortAnswerMapper compareShortAnswerMapper;

    @Resource
    private TestHistoryMapper testHistoryMapper;

    @Resource
    private ShortAnswerMapper shortAnswerMapper;

    @Resource
    private TestHistoryQuestionReplyMapper testHistoryQuestionReplyMapper;

    @Override
    public IPage<CompareShortAnswer> loadCompareTextData(Integer pageno, Integer size, Integer testId) {
        Page<TestHistory> page = new Page<>(pageno, size);
        QueryWrapper<TestHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_id", testId);
        return compareShortAnswerMapper.selectPageWithReply(page, queryWrapper);
    }

    @Override
    public CompareShortAnswer findById(Integer compareId, Integer threshold) {
        CompareShortAnswer compareShortAnswer = compareShortAnswerMapper.findById(compareId);
        return getCompareList(compareShortAnswer, threshold);
    }

    @Override
    public CompareShortAnswer getCompareList(CompareShortAnswer compareShortAnswer, Integer threshold) {
        Map<String, int[]> map = PythonExecute.getSentenceSimilarityWithSocket(compareShortAnswer.getReply1Text(),
                compareShortAnswer.getReply2Text(),
                threshold);
        compareShortAnswer.setReply1CompareList(map.get("reply1"));
        compareShortAnswer.setReply2CompareList(map.get("reply2"));
        return compareShortAnswer;
    }

    @Override
    @Transactional
    public Boolean compareReplyWithOther(TestHistory testHistory) {
        QueryWrapper<ShortAnswer> shortAnswerQueryWrapper = new QueryWrapper<>();
        shortAnswerQueryWrapper.eq("test_id", testHistory.getTestId());
        List<ShortAnswer> shortAnswerQuestionList = shortAnswerMapper.selectList(shortAnswerQueryWrapper);
        if (shortAnswerQuestionList.isEmpty()) {
            return true;
        } else {
            for (ShortAnswer shortAnswer : shortAnswerQuestionList) {
                Map<String, Object> replyMap = findShortAnswerListByTestIdAndQuestionId(testHistory, shortAnswer.getQuestionId());
                List<String> replyList = (List<String>) replyMap.get("replyList");
                List<Integer> replyIds = (List<Integer>) replyMap.get("replyIds");
                if (!replyList.isEmpty()) {
                    TestHistoryQuestionReply reply = findShortAnswerReplyBytestHistory(testHistory.getHistoryId(), shortAnswer.getQuestionId());
                    replyIds.add(0, reply.getReplyId());
                    List<String> compareResult = PythonExecute.getArticleSimilarityWithSocket(replyList, reply.getReply(),
                            shortAnswer.getThreshold());
                    for (String result : compareResult) {
                        // replyId threshold
                        String[] arr = result.split(" ");
                        CompareShortAnswer compareShortAnswer = new CompareShortAnswer(shortAnswer.getShortAnswerId(), replyIds.get(0),
                                replyIds.get(Integer.parseInt(arr[0])), Float.valueOf(arr[1]));
                        compareShortAnswerMapper.insert(compareShortAnswer);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Map<String, Object> findShortAnswerListByTestIdAndQuestionId(TestHistory insert, Integer questionId) {
        List<String> replyList = new ArrayList<>();
        List<Integer> replyIds = new ArrayList<>();

        QueryWrapper<TestHistory> testHistoryQueryWrapper = new QueryWrapper<>();
        testHistoryQueryWrapper.eq("test_id", insert.getTestId());
        testHistoryQueryWrapper.ne("student_id", insert.getStudentId());
        List<TestHistory> testHistoryList = testHistoryMapper.selectList(testHistoryQueryWrapper);
        testHistoryList.remove(insert);

        for (TestHistory testHistory : testHistoryList) {
            TestHistoryQuestionReply reply = findShortAnswerReplyBytestHistory(testHistory.getHistoryId(), questionId);
            if (!reply.getReply().isEmpty()) {
                replyList.add(reply.getReply());
                replyIds.add(reply.getReplyId());
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("replyList", replyList);
        map.put("replyIds", replyIds);
        return map;
    }

    public TestHistoryQuestionReply findShortAnswerReplyBytestHistory(Integer historyId, Integer questionId) {
        QueryWrapper<TestHistoryQuestionReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("history_id", historyId);
        queryWrapper.eq("question_id", questionId);
        return testHistoryQuestionReplyMapper.selectOne(queryWrapper);
    }
}
