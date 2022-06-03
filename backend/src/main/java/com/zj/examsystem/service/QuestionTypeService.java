package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.QuestionType;

import java.util.List;
import java.util.Map;


public interface QuestionTypeService extends IService<QuestionType> {

    public IPage<QuestionType> findAll(Integer pageno, Integer size);

    public List<QuestionType> findAll();

    public QuestionType findById(Integer questionTypeId);

    public Integer saveQuestionType(QuestionType questionType);

    public Integer deleteQuestionType(Integer[] id);
}
