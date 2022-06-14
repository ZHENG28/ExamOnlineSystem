package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface ClazzService extends IService<Clazz> {
    public IPage<Clazz> findAll(Integer pageno, Integer size);

    public Clazz findById(Integer clazzId);

    public List<Map<String, Object>> findMajorAndClazzList();

    public List<Clazz> loadClazzByMajorId(Integer majorId);

    public Integer saveClazz(Clazz clazz);

    public Integer deleteClazz(Integer[] id);
}
