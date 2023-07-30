package com.zj.examsystem.test;

import com.zj.examsystem.entity.Question;
import com.zj.examsystem.service.QuestionService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Random;

public class QuestionTest extends BaseSpringBootTest {
    @Resource
    private QuestionService questionService;

    @Test
    public void insertChoiceQuestion() {
        Integer[] subjectIds = new Integer[]{11, 13, 19, 20, 21, 22, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42};
        for (Integer subjectId : subjectIds) {
            for (int i = 0; i < 5; i++) {
                Question question = new Question();
                question.setQuestionTitle("这里是选择题选择题选择题" + (i + 1));
                question.setSubjectId(subjectId);
                question.setTypeId(1);
                StringBuilder str = new StringBuilder();
                int correctIndex = new Random().nextInt(4);
                for (int j = 0; j < 4; j++) {
                    String content;
                    if (j == correctIndex) {
                        content = "123789";
                    } else {
                        content = "选项" + (char) (((int) 'A') + j);
                    }
                    str.append((char) (((int) 'A') + j)).append(" ").append(content).append(", ");
                }
                question.setCorrect((char) (((int) 'A') + correctIndex) + ", " + str.substring(0, str.length() - 2));
                questionService.saveQuestion(question);
            }
        }
    }

    @Test
    public void insertJudgeQuestion() {
        Integer[] subjectIds = new Integer[]{11, 13, 19, 20, 21, 22, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42};
        for (Integer subjectId : subjectIds) {
            Question question = new Question();
            question.setQuestionTitle("这里是错的错的错的错的判断题");
            question.setSubjectId(subjectId);
            question.setTypeId(2);
            question.setCorrect(String.valueOf(false));
            questionService.saveQuestion(question);
        }
    }

    @Test
    public void insertShortAnswerQuestion() {
        Integer[] subjectIds = new Integer[]{11, 13, 19, 20, 21, 22, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42};
        for (Integer subjectId : subjectIds) {
            Question question = new Question();
            question.setQuestionTitle("这里是第二种简答题哦，也得做文本相似度对比欸");
            question.setSubjectId(subjectId);
            question.setTypeId(3);
            questionService.saveQuestion(question);
        }
    }


    @Test
    public void insertChoiceQuestionByKnowledgeIds() {
        Integer subjectId = 11;
        Integer[] knowledgeIds = new Integer[]{4, 5, 10, 11, 13, 15, 22, 25, 32, 34, 36, 38, 39, 41, 42, 43, 54, 57, 58};
        for (Integer knowledgeId : knowledgeIds) {
            for (int i = 0; i < 5; i++) {
                Question question = new Question();
                question.setQuestionTitle("这里是选择题选择题选择题" + (i + 1));
                question.setSubjectId(subjectId);
                question.setKnowledgeId(knowledgeId);
                question.setQuestionDifficulty((float) (Math.round(new Random().nextFloat() * 100.0) / 100.0));
                question.setTypeId(1);
                StringBuilder str = new StringBuilder();
                int correctIndex = new Random().nextInt(4);
                for (int j = 0; j < 4; j++) {
                    String content;
                    if (j == correctIndex) {
                        content = "123789";
                    } else {
                        content = "选项" + (char) (((int) 'A') + j);
                    }
                    str.append((char) (((int) 'A') + j)).append(" ").append(content).append(", ");
                }
                question.setCorrect((char) (((int) 'A') + correctIndex) + ", " + str.substring(0, str.length() - 2));
                questionService.saveQuestion(question);
            }
        }
    }

    @Test
    public void insertJudgeQuestionByKnowledgeIds() {
        Integer subjectId = 11;
        Integer[] knowledgeIds = new Integer[]{4, 5, 10, 11, 13, 15, 22, 25, 32, 34, 36, 38, 39, 41, 42, 43, 54, 57, 58};
        for (Integer knowledgeId : knowledgeIds) {
            Question question = new Question();
            question.setQuestionTitle("这里是对的对的对的对的判断题");
            question.setSubjectId(subjectId);
            question.setTypeId(2);
            question.setCorrect(String.valueOf(true));
            question.setKnowledgeId(knowledgeId);
            question.setQuestionDifficulty((float) (Math.round(new Random().nextFloat() * 100.0) / 100.0));
            questionService.saveQuestion(question);
        }
    }

    @Test
    public void insertShortAnswerQuestionByKnowledgeIds() {
        Integer subjectId = 11;
        Integer[] knowledgeIds = new Integer[]{4, 5, 10, 11, 13, 15, 22, 25, 32, 34, 36, 38, 39, 41, 42, 43, 54, 57, 58};
        int count = 1;
        for (Integer knowledgeId : knowledgeIds) {
            Question question = new Question();
            question.setQuestionTitle("这里是第三种简答题" + (count++) + "，做相似度对比");
            question.setSubjectId(subjectId);
            question.setKnowledgeId(knowledgeId);
            question.setQuestionDifficulty((float) (Math.round(new Random().nextFloat() * 100.0) / 100.0));
            question.setTypeId(3);
            questionService.saveQuestion(question);
        }
    }
}
