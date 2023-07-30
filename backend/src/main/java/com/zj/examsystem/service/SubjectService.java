package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectService extends IService<Subject> {
    IPage<Subject> findAll(Integer pageno, Integer size, Map<String, Object> condition);

    List<Subject> loadSubjectByUserId(Map<String, Object> condition);

    Subject findById(Integer subjectId);

    Integer saveSubject(Subject subject);

    Integer deleteSubject(Integer[] id);
}
