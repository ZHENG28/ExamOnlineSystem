package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.examsystem.entity.QuestionType;

import java.util.List;
import java.util.Map;

public interface QuestionTypeMapper extends BaseMapper<QuestionType> {
    List<Map<String, Object>> selectDistinctQuestionType();
}
