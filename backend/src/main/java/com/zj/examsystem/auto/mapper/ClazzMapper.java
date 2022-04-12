package com.zj.examsystem.auto.mapper;

import com.zj.examsystem.auto.entity.Clazz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface ClazzMapper extends BaseMapper<Clazz> {
    //    Integer getMaxId();

    List<String> selectDistinctMajor();

    List<String> selectDistinctClazzName();
}
