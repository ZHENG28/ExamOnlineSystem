package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.TestHistory;
import com.zj.examsystem.entity.TestHistoryWithUserTestSubject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestHistoryMapper extends BaseMapper<TestHistory> {
    IPage<TestHistoryWithUserTestSubject> selectPageWithTest(Page<TestHistory> page,
                                                             @Param(Constants.WRAPPER) QueryWrapper<TestHistory> queryWrapper);

    TestHistoryWithUserTestSubject findTestInfoByCompoundId(@Param("testId") Integer testId, @Param("studentId") Integer studentId);

    List<Integer> findIdByTestId(Integer testId);

    List<Float> findCorrectnessByStudentId(Integer userId);
}
