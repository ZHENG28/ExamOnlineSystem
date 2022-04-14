package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;


public interface SubjectMapper extends BaseMapper<Subject> {
    List<Map<String, Object>> selectDistinctSubject();

    IPage<Subject> selectPageWithTeacherAndClazz(Page<Subject> page, QueryWrapper<Subject> queryWrapper);

    Subject findByIdWithTeacherAndClazz(Integer subjectId);
}
