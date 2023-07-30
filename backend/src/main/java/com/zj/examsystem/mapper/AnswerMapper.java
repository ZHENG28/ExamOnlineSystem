package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.examsystem.entity.Answer;

import java.util.List;

public interface AnswerMapper extends BaseMapper<Answer> {
    List<Integer> findIdByQuestionId(Integer questionId);
}
