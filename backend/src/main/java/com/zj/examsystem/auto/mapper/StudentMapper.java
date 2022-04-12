package com.zj.examsystem.auto.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.auto.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface StudentMapper extends BaseMapper<Student> {
    IPage<Map<String,Object>> findAllWithClazz();

    IPage<Map<String, Object>> selectPageWithClazz(Page<Map<String, Object>> page, QueryWrapper<Map<String, Object>> queryWrapper);
}
