package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.Subject;
import com.zj.examsystem.mapper.SubjectMapper;
import com.zj.examsystem.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@SuppressWarnings("all")
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public IPage<Subject> findAll(Integer pageno, Integer size, Map<String, Object> condition) {
        Page<Subject> page = new Page<>(pageno, size);
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq((String) condition.get("column"), (Integer) condition.get("val"));
        return subjectMapper.selectPageWithTeacherAndClazz(page, queryWrapper);
    }

    @Override
    public List<Subject> loadSubjectByUserId(Map<String, Object> condition) {
        QueryWrapper<Map<String, Object>> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq((String) condition.get("column"), (Integer) condition.get("val"));
        return subjectMapper.selectListByUserId(queryWrapper);
    }

    @Override
    public Subject findById(Integer subjectId) {
        return subjectMapper.findByIdWithTeacherAndClazz(subjectId);
    }

    @Override
    public Integer saveSubject(Subject subject) {
        try {
            return subject.getSubjectId() != null ? subjectMapper.updateById(subject) : subjectMapper.insert(subject);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                return 0;
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public Integer deleteSubject(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return subjectMapper.deleteBatchIds(ids);
    }
}
