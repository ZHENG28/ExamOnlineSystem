package com.zj.examsystem.eos.mapper;

import com.zj.examsystem.eos.entity.Clazz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface ClazzMapper extends BaseMapper<Clazz> {
//    Integer getMaxId();

    List<String> selectDistinctMajor();

    List<String> selectDistinctClazzName();
}
