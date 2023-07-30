package com.zj.examsystem.test;

import com.zj.examsystem.entity.Answer;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.TestHistory;
import com.zj.examsystem.mapper.TestMapper;
import com.zj.examsystem.mapper.UserMapper;
import com.zj.examsystem.service.CompareShortAnswerService;
import com.zj.examsystem.service.QuestionService;
import com.zj.examsystem.service.TestHistoryService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestHistoryTest extends BaseSpringBootTest {
    @Resource
    private TestHistoryService testHistoryService;

    @Resource
    private QuestionService questionService;

    @Resource
    private CompareShortAnswerService compareShortAnswerService;

    @Resource
    private TestMapper testMapper;

    @Resource
    private UserMapper userMapper;

    // 1h27min
    @Test
    public void insertTestHistory() {
        List<com.zj.examsystem.entity.Test> testList = testMapper.selectList(null);
        for (com.zj.examsystem.entity.Test test : testList) {
            List<Integer> userIds = userMapper.selectUserIdsByTestId(test.getTestId());
            List<Question> questionList = questionService.findQuestionListByTestId(test.getTestId());
            for (Integer userId : userIds) {
                TestHistory testHistory = new TestHistory();
                testHistory.setTestId(test.getTestId());
                testHistory.setStudentId(userId);
                testHistory.setTakeTime(new Random().nextInt(60 * 60) + 30 * 60);
                testHistory.setFinishTime(LocalDateTime.now());

                List<String> replyList = new ArrayList<>();
                for (Question question : questionList) {
                    StringBuilder sb = new StringBuilder(question.getQuestionId() + " ");
                    switch (question.getTypeId()) {
                        case 1:
                            List<String> answerList = new ArrayList<>();
                            for (Answer a : question.getAnswer()) {
                                answerList.add(a.getAnswerSign());
                            }
                            sb.append(answerList.get(new Random().nextInt(answerList.size())));
                            break;
                        case 2:
                            sb.append(new Random().nextInt() % 2 == 0);
                            break;
                        case 3:
                            String[] splitText =
                                    "我们在比较事物时，往往会用到“不同”，“一样”，“相似”等词语，这些词语背后都涉及到一个动作——双方的比较。只有通过比较才能得出结论，究竟是相同还是不同。但是万物真的有这么极端的区分吗？在我看来不是的，生活中通过“相似度”这词来描述可能会更加准确。比如男人和女人，虽然生理器官和可能思想有些不同，但也有相同的地方，那就是都是人，就是说相似度不为0；比如石头与小草，它们对于虚拟类都是一种实体类，相似度也不为0；两个句子词和词的顺序都一致，相似度就是1。一个概念可以应用到不同于相同的两个方面的定义。可真谓方便至极了。".split("[。；？！]");
                            for (int i = 0; i < splitText.length; i++) {
                                StringBuilder tmp = new StringBuilder(splitText[i]);
                                if (new Random().nextInt() % 2 == 0) {
                                    tmp.insert(new Random().nextInt(tmp.length()), tmp.substring(new Random().nextInt(tmp.length())));
                                }
                                splitText[i] = tmp.toString();
                            }
                            // 句子顺序不影响文本相似度
                            sb.append(String.join("。", splitText)).append("。");
                            break;
                    }
                    replyList.add(sb.toString());
                }
                String[] reply = replyList.toArray(new String[0]);
                if (testHistoryService.commit(testHistory, reply, questionList)) {
                    compareShortAnswerService.compareReplyWithOther(testHistory);
                }
            }
        }
    }
}
