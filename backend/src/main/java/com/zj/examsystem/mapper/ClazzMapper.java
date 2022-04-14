package com.zj.examsystem.mapper;

import com.zj.examsystem.entity.Clazz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface ClazzMapper extends BaseMapper<Clazz> {
//    Integer getMaxId();

    List<String> selectDistinctMajor();

    List<String> selectDistinctClazzName();
}
