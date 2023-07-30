package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.TestQuestionList;

import java.util.List;

public interface TestQuestionListMapper extends BaseMapper<TestQuestionList> {
    List<Integer> findQuestionIdsByTestId(Integer testId);

    List<Question> findObjectQuestionIdsByTestId(Integer testId);
}
