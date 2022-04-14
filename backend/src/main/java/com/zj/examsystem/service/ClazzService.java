package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface ClazzService extends IService<Clazz> {
    public IPage<Clazz> findAll(Integer pageno, Integer size);

    public Object getDistinctMajorOrClazz(String condition);

    public List<Map<String, Object>> findAllMajorClazz();

    public Integer saveClazz(Clazz clazz);

    public Integer deleteClazz(Integer[] id);

    public Clazz findById(Integer clazzId);
}
