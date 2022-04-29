package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Answer;
import com.zj.examsystem.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface QuestionService extends IService<Question> {
    public IPage<Question> findAllByTeacherId(Integer pageno, Integer size, Integer userId);

    public Boolean saveQuestion(Question question);

    public int deleteQuestion(Integer[] id);

    public Question findById(Integer questionId);

    public List<Question> findQuesBySubId(Integer subjectId);

    public List<Question> findQuestionListByTestId(Integer testId);
}
