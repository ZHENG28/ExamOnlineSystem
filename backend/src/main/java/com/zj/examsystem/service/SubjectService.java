package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface SubjectService extends IService<Subject> {
    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size);

    public List<Map<String, Object>> findAllSubject();

    public int saveSubject(Subject subject);

    public int deleteSubject(Integer[] id);

    public Map<String, Object> findById(Integer subId);
}
