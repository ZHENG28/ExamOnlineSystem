package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.Answer;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.Test;
import com.zj.examsystem.mapper.*;
import com.zj.examsystem.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.zj.examsystem.utils.Const.TEST_QUESTION_LIST_SPLIT;


@Service
@SuppressWarnings("all")
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private TestQuestionListMapper testQuestionListMapper;

    @Override
    public IPage<Question> findAllByTeacherId(Integer pageno, Integer size, Integer userId) {
        Page<Question> page = new Page<>(pageno, size);
        IPage<Question> questionIPage = questionMapper.findAllByTeacherId(userId, page);
        for (Question question : questionIPage.getRecords()) {
            QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
            answerQueryWrapper.eq("question_id", question.getQuestionId());
            question.setAnswer(answerMapper.selectList(answerQueryWrapper));
            if (((Integer) 1).equals(question.getTypeId())) {
                answerQueryWrapper.eq("is_correct", "1");
                question.setCorrect(answerMapper.selectOne(answerQueryWrapper).getAnswerSign());
            }
        }
        return questionIPage;
    }

    @Override
    public Question findById(Integer questionId) {
        Question question = questionMapper.findById(questionId);

        QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
        answerQueryWrapper.eq("question_id", question.getQuestionId());
        question.setAnswer(answerMapper.selectList(answerQueryWrapper));
        if (((Integer) 1).equals(question.getTypeId()) || ((Integer) 2).equals(question.getTypeId())) {
            answerQueryWrapper.eq("is_correct", "1");
            question.setCorrect(answerMapper.selectOne(answerQueryWrapper).getAnswerSign());
        }
        return question;
    }

    @Override
    public List<Question> findQuestionBySubjectId(Integer subjectId) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id", subjectId);
        return questionMapper.findListById(queryWrapper);
    }

    @Override
    public List<Question> findQuestionListByTestId(Integer testId) {
        List<Integer> questionIds = testQuestionListMapper.findQuestionIdsByTestId(testId);
        List<Question> questionList = new ArrayList<>();
        for (Integer questionId : questionIds) {
            Question question = questionMapper.findById(questionId);
            QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
            answerQueryWrapper.eq("question_id", questionId);
            List<Answer> answerList = answerMapper.selectList(answerQueryWrapper);
            switch (question.getTypeId()) {
                case 1:
                    for (Answer answer : answerList) {
                        if (((Integer) 1).equals(answer.getIsCorrect())) {
                            question.setCorrect(answer.getAnswerSign());
                        }
                    }
                    break;
                case 2:
                    question.setCorrect("1".equals(answerList.get(0).getAnswerSign()) ? "true" : "false");
                    break;
            }
            question.setAnswer(answerList);
            questionList.add(question);
        }
        return questionList;
    }

    @Override
    @Transactional
    public Boolean saveQuestion(Question question) {
        String[] answers = new String[]{""};
        if (null != question.getCorrect()) {
            answers = question.getCorrect().split(TEST_QUESTION_LIST_SPLIT);
            question.setCorrect(null);
        }
        int result = questionMapper.insert(question);
        if (!((Integer) 3).equals(question.getTypeId()) && result == 1) {
            QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("question_title", question.getQuestionTitle());
            switch (question.getTypeId()) {
                case 1: // choice
                    String correct = answers[0];
                    int insertResult = 1;
                    for (int i = 1; i < answers.length; i++) {
                        String[] arr = answers[i].split(" "); // sign content
                        Answer answer = new Answer(arr[1], arr[2], correct.equals(arr[1]) ? 1 : 0, question.getQuestionId());
                        insertResult += answerMapper.insert(answer);
                    }
                    return insertResult == answers.length;
                case 2: // judge
                    Answer answer = new Answer(Boolean.valueOf(answers[0]) ? "1" : "0", 1, question.getQuestionId());
                    return answerMapper.insert(answer) != 0;
            }
        } else {
            return ((Integer) 3).equals(question.getTypeId()) && result == 1;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateQuestion(Question question) {
        String[] answers = question.getCorrect().split(TEST_QUESTION_LIST_SPLIT);
        question.setCorrect(null);
        int result = questionMapper.updateById(question);
        if (!((Integer) 3).equals(question.getTypeId()) && result == 1) {
            switch (question.getTypeId()) {
                case 1: // choice
                    String correct = answers[0];
                    List<Integer> answerIds = answerMapper.findIdByQuestionId(question.getQuestionId());
                    int insertResult = 1;
                    for (int i = 1; i < answers.length; i++) {
                        String[] arr = answers[i].split(" "); // id sign content
                        if (!arr[0].isEmpty()) { // update option
                            Integer id = Integer.valueOf(arr[0]);
                            Answer answer = new Answer(id, arr[1], arr[2], correct.equals(arr[1]) ? 1 : 0,
                                    question.getQuestionId());
                            insertResult += answerMapper.updateById(answer);
                            if (answerIds.contains(id)) {
                                answerIds.remove(id);
                            }
                        } else { // insert new option
                            Answer answer = new Answer(arr[1], arr[2], correct.equals(arr[1]) ? 1 : 0,
                                    question.getQuestionId());
                            insertResult += answerMapper.insert(answer);
                        }
                    }
                    if (answerIds.size() != 0) { // something need to delete
                        answerMapper.deleteBatchIds(answerIds);
                    }
                    return insertResult == answers.length;
                case 2: // judge
                    QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
                    answerQueryWrapper.eq("question_id", question.getQuestionId());
                    Answer answer = answerMapper.selectOne(answerQueryWrapper);
                    answer.setAnswerSign(Boolean.valueOf(answers[0]) ? "1" : "0");
                    return answerMapper.updateById(answer) != 0;
            }
        } else {
            return ((Integer) 3).equals(question.getTypeId()) && result == 1;
        }
        return false;
    }

    @Override
    @Transactional
    public Integer deleteQuestion(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return questionMapper.deleteBatchIds(ids);
    }
}
