package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zj.examsystem.entity.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzMapper extends BaseMapper<Clazz> {
    IPage<Clazz> selectPageWithMajor(IPage<Clazz> page, @Param(Constants.WRAPPER) QueryWrapper<Clazz> queryWrapper);

    Clazz findById(Integer clazzId);

    List<String> selectDistinctClazzName();
}
