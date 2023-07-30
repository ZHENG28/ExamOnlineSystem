package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.CompareShortAnswer;
import com.zj.examsystem.entity.TestHistory;
import org.apache.ibatis.annotations.Param;

public interface CompareShortAnswerMapper extends BaseMapper<CompareShortAnswer> {
    IPage<CompareShortAnswer> selectPageWithReply(Page<TestHistory> page, @Param(Constants.WRAPPER) QueryWrapper<TestHistory> queryWrapper);

    CompareShortAnswer findById(Integer compareId);
}
