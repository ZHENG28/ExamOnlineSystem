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

    Double calculateActualDifficulty(Integer[] questionIds);

    List<Question> intelligentGenerate(List<List<Question>> parent,
                                       Float knowledgeWeight, Float examDifficulty, Float difficultyWeight, Integer iteration);

    Boolean saveQuestion(Question question);

    Boolean updateQuestion(Question question);

    Integer deleteQuestion(Integer[] id);
}
