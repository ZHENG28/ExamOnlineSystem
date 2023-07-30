package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestMapper extends BaseMapper<Test> {
    IPage<Test> selectPageWithSubject(Page<Test> page, @Param(Constants.WRAPPER) QueryWrapper<Test> queryWrapper);

    Integer countByTeacherId(Integer userId);

    List<Test> countNotFinishByUserId(Integer userId);

    Test findById(Integer testId);
}
