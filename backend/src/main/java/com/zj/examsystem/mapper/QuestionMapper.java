package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;


public interface QuestionMapper extends BaseMapper<Question> {
    IPage<Map<String, Object>> selectPageWithSubject(Page<Map<String, Object>> page, QueryWrapper<Map<String, Object>> queryWrapper);

    Question selectById(Integer quesId);
}
