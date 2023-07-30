package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.ShortAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShortAnswerMapper extends BaseMapper<ShortAnswer> {
    List<Integer> findShortAnswerIdsByTestId(Integer testId);

    ShortAnswer findByCompoundId(@Param("questionId") Integer questionId, @Param("testId") Integer testId);

    List<Question> findQuestionsByTestId(Integer testId);
}
