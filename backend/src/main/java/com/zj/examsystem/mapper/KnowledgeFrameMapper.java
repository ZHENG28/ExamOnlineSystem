package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.KnowledgeFrame;
import com.zj.examsystem.entity.Subject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface KnowledgeFrameMapper extends BaseMapper<KnowledgeFrame> {
    IPage<Subject> selectPageWithTeacherAndClazz(Page<Subject> page, @Param(Constants.WRAPPER) QueryWrapper<Subject> queryWrapper);

    List<Subject> selectListByUserId(@Param(Constants.WRAPPER) QueryWrapper<Map<String, Object>> queryWrapper);

    Subject findByIdWithTeacherAndClazz(Integer subjectId);

    List<Integer> findIdBySubjectId(Integer subjectId);

    Integer selectIdByKnowledgeFrame(@Param(Constants.WRAPPER) QueryWrapper<Map<String, Object>> queryWrapper);
}
