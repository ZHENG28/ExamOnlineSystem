package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.TestHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface TestHistoryMapper extends BaseMapper<TestHistory> {
    public Integer countByTestAndStudent(@Param("userId") Integer userId, @Param("testId") Integer testId);

    public IPage<TestHistory> selectPageWithTest(Page<Map<String, Object>> page, QueryWrapper<Map<String, Object>> queryWrapper);

    public IPage<TestHistory> selectPageByTestId(Page<TestHistory> page, @Param(Constants.WRAPPER) QueryWrapper<TestHistory> queryWrapper);
}
