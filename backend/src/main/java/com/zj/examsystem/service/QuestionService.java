package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.Question;

import java.util.List;


public interface QuestionService extends IService<Question> {
    public IPage<Question> findAllByTeacherId(Integer pageno, Integer size, Integer userId);

    public Question findById(Integer questionId);

    public List<Question> findQuestionBySubjectId(Integer subjectId);

    public List<Question> findQuestionListByTestId(Integer testId);

    public Boolean saveQuestion(Question question);

    public Boolean updateQuestion(Question question);

    public Integer deleteQuestion(Integer[] id);
}
