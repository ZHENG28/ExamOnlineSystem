package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.Question;

import java.util.List;

public interface QuestionService extends IService<Question> {
    IPage<Question> findAllByTeacherId(Integer pageno, Integer size, Integer userId);

    Question findById(Integer questionId);

    List<Question> findQuestionBySubjectId(Integer subjectId);

    List<Question> findQuestionListByTestId(Integer testId);

    List<Question> findQuestionListByQuestionIds(List<Integer> questionIds);

    public Boolean saveQuestion(Question question);

    public Boolean updateQuestion(Question question);

    Boolean saveQuestion(Question question);

    Boolean updateQuestion(Question question);

    Integer deleteQuestion(Integer[] id);
}
