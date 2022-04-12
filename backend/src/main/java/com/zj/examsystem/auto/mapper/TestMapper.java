package com.zj.examsystem.auto.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.auto.entity.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface TestMapper extends BaseMapper<Test> {

    IPage<Map<String, Object>> selectPageWithClazzAndSubject(Page<Map<String, Object>> page, QueryWrapper<Map<String, Object>> queryWrapper);

    Test selectWithClazzAndSubjectById(Integer testId);
}
