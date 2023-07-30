package com.zj.examsystem.test;

import com.zj.examsystem.service.TestService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestTest extends BaseSpringBootTest {
    @Resource
    private TestService testService;

    @Test
    public void insertTestWithoutShortAnswer() {
        List<Integer> subjectIds =
                Arrays.stream(new int[]{11, 13, 19, 20, 21, 22, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42}).boxed().collect(Collectors.toList());
        Integer[] judgeIds = new Integer[]{87, 111};
        Integer[] choiceIds = new Integer[]{160, 161, 164};
        for (Integer subjectId : subjectIds) {
            com.zj.examsystem.entity.Test test = new com.zj.examsystem.entity.Test();
            test.setTestName("这里是没有简答题的考试哦");
            test.setDescription("这次考试没有简答题没有简答题没有简答题");
            test.setBeginDate(LocalDateTime.now());
            test.setEndDate(LocalDateTime.of(2022, 5, 20, 13, 14, 0));
            test.setTestDuration(60);
            test.setTestTime(3);
            test.setSubjectId(subjectId);

            Integer[] questionList = new Integer[choiceIds.length + judgeIds.length];
            for (int i = 0; i < judgeIds.length; i++) {
                questionList[i] = judgeIds[i] + subjectIds.indexOf(subjectId);
            }
            for (int i = 0; i < choiceIds.length; i++) {
                questionList[judgeIds.length + i] = choiceIds[i] + subjectIds.indexOf(subjectId) * 5;
            }
            test.setQuestionTotal(questionList.length);

            testService.saveTest(test, questionList, null);
        }
    }

    @Test
    public void insertTestWithShortAnswer() {
        List<Integer> subjectIds =
                Arrays.stream(new int[]{11, 13, 19, 20, 21, 22, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42}).boxed().collect(Collectors.toList());
        Integer[] shortAnswerIds = new Integer[]{38, 62};
        Integer[] judgeIds = new Integer[]{111, 135};
        Integer[] choiceIds = new Integer[]{160, 161, 162, 163};
        for (Integer subjectId : subjectIds) {
            com.zj.examsystem.entity.Test test = new com.zj.examsystem.entity.Test();
            test.setTestName("这里是有简答题的考试");
            test.setDescription("这次考试有简答题，需要做相似度对比对比");
            test.setBeginDate(LocalDateTime.now());
            test.setEndDate(LocalDateTime.of(2022, 5, 21, 5, 5, 20));
            test.setTestDuration(60);
            test.setTestTime(3);
            test.setSubjectId(subjectId);

            Integer[] questionList = new Integer[choiceIds.length + judgeIds.length + shortAnswerIds.length];
            for (int i = 0; i < shortAnswerIds.length; i++) {
                questionList[i] = shortAnswerIds[i] + subjectIds.indexOf(subjectId);
            }
            for (int i = 0; i < judgeIds.length; i++) {
                questionList[shortAnswerIds.length + i] = judgeIds[i] + subjectIds.indexOf(subjectId);
            }
            for (int i = 0; i < choiceIds.length; i++) {
                questionList[shortAnswerIds.length + judgeIds.length + i] = choiceIds[i] + subjectIds.indexOf(subjectId) * 5;
            }
            test.setQuestionTotal(questionList.length);

            String[] shortAnswerList = new String[shortAnswerIds.length];
            for (int i = 0; i < shortAnswerIds.length; i++) {
                shortAnswerList[i] = (shortAnswerIds[i] + subjectIds.indexOf(subjectId)) + " 55";
            }

            testService.saveTest(test, questionList, shortAnswerList);
        }
    }
}
