package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface SubjectService extends IService<Subject> {
    public IPage<Subject> findAll(Integer pageno, Integer size, Map<String, Object> condition);

    public List<Subject> loadSubjectByUserId(Map<String, Object> condition);

    public Subject findById(Integer subjectId);

    public Integer saveSubject(Subject subject);

    public Integer deleteSubject(Integer[] id);
}
