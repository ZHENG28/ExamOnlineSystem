package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.QuestionType;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;
import java.util.Map;


public interface QuestionTypeService extends IService<QuestionType> {

    public IPage<QuestionType> findAll(Integer pageno, Integer size, Integer... userId);

    public List<Map<String, Object>> findDistinctQuestionType();

    public int saveQuestionType(QuestionType questionType);

    public int deleteQuestionType(Integer[] id);

    public QuestionType findById(Integer questionTypeId);
}
