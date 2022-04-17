package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.Answer;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.mapper.AnswerMapper;
import com.zj.examsystem.mapper.SubjectMapper;
import com.zj.examsystem.mapper.QuestionMapper;
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


@Service
@SuppressWarnings("all")
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    public IPage<Question> findAllByTeacherId(Integer pageno, Integer size, Integer userId) {
        Page<Question> page = new Page<>(pageno, size);
        IPage<Question> questionIPage = questionMapper.findAllByTeacherId(userId, page);
        for (Question question : questionIPage.getRecords()) {
            QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
            answerQueryWrapper.eq("question_id", question.getQuestionId());
            question.setAnswer(answerMapper.selectList(answerQueryWrapper));
            if (question.getTypeId() == 1) {
                answerQueryWrapper.eq("is_correct", "1");
                question.setCorrect(answerMapper.selectOne(answerQueryWrapper).getAnswerSign());
            }
        }
        return questionIPage;
    }

    @Transactional
    public Boolean saveQuestion(Question question) {
        if (question.getQuestionId() != null) {
            String[] answers = question.getCorrect().split(",");
            question.setCorrect(null);
            int result = questionMapper.updateById(question);
            if (result == 1 && question.getTypeId() != 3) {
                switch (question.getTypeId()) {
                    case 1: // choice
                        String correct = answers[0];
                        List<Integer> answerIds = answerMapper.findIdByQuestionId(question.getQuestionId());
                        Integer insertResult = 1;
                        for (int i = 1; i < answers.length; i++) {
                            String[] arr = answers[i].split(" "); // id sign content
                            Integer id = Integer.valueOf(arr[0]);
                            Answer answer = new Answer(id, arr[1], arr[2], correct.equals(arr[1]) ? 1 : 0,
                                    question.getQuestionId());
                            if (answerIds.contains(id)) {
                                answerIds.remove(id);
                            }
                            insertResult += answerMapper.updateById(answer);
                        }
                        System.out.println(answerIds);
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
                return result == 1 && question.getTypeId() == 3;
            }
        } else {
            String[] answers = question.getCorrect().split(",");
            question.setCorrect(null);
            int result = questionMapper.insert(question);
            if (result == 1 && question.getTypeId() != 3) {
                QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("question_title", question.getQuestionTitle());
                Question insertQues = questionMapper.selectOne(queryWrapper);
                switch (question.getTypeId()) {
                    case 1: // choice
                        String correct = answers[0];
                        Integer insertResult = 1;
                        for (int i = 1; i < answers.length; i++) {
                            String[] arr = answers[i].split(" "); // sign content
                            Answer answer = new Answer(arr[0], arr[1], correct.equals(arr[0]) ? 1 : 0, insertQues.getQuestionId());
                            insertResult += answerMapper.insert(answer);
                        }
                        return insertResult == answers.length;
                    case 2: // judge
                        Answer answer = new Answer(Boolean.valueOf(answers[0]) ? "1" : "0", 1, insertQues.getQuestionId());
                        return answerMapper.insert(answer) != 0;
                }
            } else {
                return result == 1 && question.getTypeId() == 3;
            }
        }
        return false;
    }

    @Transactional
    public int deleteQuestion(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return questionMapper.deleteBatchIds(ids);
    }

    public Question findById(Integer questionId) {
        Question question = questionMapper.selectById(questionId);

        QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
        answerQueryWrapper.eq("question_id", question.getQuestionId());
        question.setAnswer(answerMapper.selectList(answerQueryWrapper));
        if (question.getTypeId() == 1 || question.getTypeId() == 2) {
            answerQueryWrapper.eq("is_correct", "1");
            question.setCorrect(answerMapper.selectOne(answerQueryWrapper).getAnswerSign());
        }
        return question;
    }

    @Override
    public List<Question> findQuesBySubId(Integer subId) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sub_id", subId);

        return questionMapper.selectList(queryWrapper);
    }
}
