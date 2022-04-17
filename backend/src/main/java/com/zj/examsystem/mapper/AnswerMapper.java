package com.zj.examsystem.mapper;

import com.zj.examsystem.entity.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface AnswerMapper extends BaseMapper<Answer> {
    List<Integer> findIdByQuestionId(Integer questionId);
}
