package com.zj.examsystem.auto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.auto.entity.Clazz;
import com.zj.examsystem.auto.entity.Subject;
import com.zj.examsystem.auto.entity.Teacher;
import com.zj.examsystem.auto.mapper.ClazzMapper;
import com.zj.examsystem.auto.mapper.SubjectMapper;
import com.zj.examsystem.auto.mapper.TeacherMapper;
import com.zj.examsystem.auto.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Service
@SuppressWarnings("all")
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public List<Map<String, Object>> findTchSortByMajor(Integer tchId) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();

        List<String> majorList = clazzMapper.selectDistinctMajor();
        List<Teacher> teacherList = teacherMapper.selectList(teacherQueryWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (String major : majorList) {
            Map<String, Object> majorMap = new HashMap<>();
            majorMap.put("value", major);
            majorMap.put("label", major);
            List<Map<String, Object>> child = new ArrayList<>();
            for (Teacher teacher : teacherList) {
                if (major.equals(teacher.getMajor())) {
                    Map<String, Object> tchMap = new HashMap<>();
                    tchMap.put("value", teacher.getId());
                    tchMap.put("label", teacher.getTchName());
                    child.add(tchMap);
                }
            }
            majorMap.put("children", child);
            result.add(majorMap);
        }
        return result;
    }


    public IPage<Teacher> findAll(Integer pageno, Integer size) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        IPage<Teacher> page = new Page<>();
        page.setCurrent(pageno);
        page.setSize(size);

        return teacherMapper.selectPage(page, queryWrapper);
    }

    public int saveTeacher(Teacher teacher) {
        return teacher.getId() != null ? teacherMapper.updateById(teacher) : teacherMapper.insert(teacher);
    }

    @Transactional
    public int deleteTeacher(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return teacherMapper.deleteBatchIds(ids);
    }

    @Override
    public Teacher findByAccount(String account) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);

        return teacherMapper.selectOne(queryWrapper);
    }
}
