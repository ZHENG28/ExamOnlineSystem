package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.Subject;
import com.zj.examsystem.mapper.SubjectMapper;
import com.zj.examsystem.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //    @Autowired
    //    private TeacherMapper teacherMapper;

    public IPage<Subject> findAll(Integer pageno, Integer size) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        Page<Subject> page = new Page<>(pageno, size);
        return subjectMapper.selectPageWithTeacherAndClazz(page, queryWrapper);
    }

    public List<Map<String, Object>> findDistinctSubject() {
        List<Map<String, Object>> tmp = subjectMapper.selectDistinctSubject();
        List<Map<String, Object>> List = new ArrayList<>();
        for (Map<String, Object> resMap : tmp) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", resMap.get("sub_name"));
            map.put("value", resMap.get("sub_name"));
            map.put("subId", resMap.get("sub_id"));
            List.add(map);
        }
        return List;
    }

    public int saveSubject(Subject subject) {
        return subject.getSubjectId() != null ? subjectMapper.updateById(subject) : subjectMapper.insert(subject);
    }

    @Transactional
    public int deleteSubject(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return subjectMapper.deleteBatchIds(ids);
    }

    public Subject findById(Integer subjectId) {
        return subjectMapper.findByIdWithTeacherAndClazz(subjectId);
    }
}
