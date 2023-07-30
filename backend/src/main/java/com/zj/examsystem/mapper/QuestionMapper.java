package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper extends BaseMapper<Question> {
    IPage<Question> findAllByTeacherId(@Param("userId") Integer userId, Page<Question> page);

    Integer countByTeacherId(Integer userId);

    Question findById(Integer questionId);

    List<Question> findListById(@Param(Constants.WRAPPER) QueryWrapper<Question> wrapper);

    List<Question> selectSameWithDifficultyAndTypeAndKnowledge(@Param("questionId") Integer questionId,
                                                               @Param("questionDifficulty") Float questionDifficulty,
                                                               @Param("difficultyLower") Float difficultyLower,
                                                               @Param("difficultyUpper") Float difficultyUpper, @Param("typeId") Integer typeId,
                                                               @Param("knowledgeId") Integer knowledgeId);

    List<Question> selectSameWithTypeAndKnowledge(@Param("questionId") Integer questionId,
                                                  @Param("questionDifficulty") Float questionDifficulty,
                                                  @Param("typeId") Integer typeId,
                                                  @Param("knowledgeId") Integer knowledgeId);
}
