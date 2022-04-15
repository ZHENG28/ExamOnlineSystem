package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.QuestionType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import java.util.List;
import java.util.Map;


public interface QuestionTypeMapper extends BaseMapper<QuestionType> {
    List<Map<String, Object>> selectDistinctQuestionType();

//    IPage<QuestionType> selectPageWithQuestionType(Page<QuestionType> page, QueryWrapper<QuestionType> queryWrapper);
//
//    QuestionType findByIdWithQuestionType(Integer questionTypeId);
}
