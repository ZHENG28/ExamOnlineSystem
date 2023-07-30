package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.QuestionType;

import java.util.List;

public interface QuestionTypeService extends IService<QuestionType> {
    IPage<QuestionType> findAll(Integer pageno, Integer size);

    List<QuestionType> findAll();

    QuestionType findById(Integer questionTypeId);

    Integer saveQuestionType(QuestionType questionType);

    Integer deleteQuestionType(Integer[] id);
}
