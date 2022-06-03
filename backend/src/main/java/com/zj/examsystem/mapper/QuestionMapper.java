package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface QuestionMapper extends BaseMapper<Question> {
    IPage<Question> findAllByTeacherId(@Param("userId") Integer userId, Page<Question> page);

    Integer countByTeacherId(Integer userId);

    Question findById(Integer questionId);

    List<Question> findListById(@Param(Constants.WRAPPER) QueryWrapper<Question> wrapper);
}
