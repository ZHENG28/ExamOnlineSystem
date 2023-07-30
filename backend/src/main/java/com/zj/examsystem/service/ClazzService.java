package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.Clazz;

import java.util.List;
import java.util.Map;

public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> findAll(Integer pageno, Integer size);

    Clazz findById(Integer clazzId);

    List<Map<String, Object>> findMajorAndClazzList();

    List<Clazz> loadClazzByMajorId(Integer majorId);

    Integer saveClazz(Clazz clazz);

    Integer deleteClazz(Integer[] id);
}
