package com.zj.examsystem.eos.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.eos.entity.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;


public interface TestMapper extends BaseMapper<Test> {
    IPage<Map<String, Object>> selectPageWithClazzAndSubject(Page<Map<String, Object>> page, QueryWrapper<Map<String, Object>> queryWrapper);

    Test selectWithClazzAndSubjectById(Integer testId);
}
