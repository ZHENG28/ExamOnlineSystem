package com.zj.examsystem.auto.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.auto.entity.Teacher;
import com.zj.examsystem.auto.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface TeacherService extends IService<Teacher> {
    public List<Map<String, Object>> findTchSortByMajor(Integer tchId);

    public IPage<Teacher> findAll(Integer pageno, Integer size);

    public int saveTeacher(Teacher teacher);

    public int deleteTeacher(Integer[] id);

    public Teacher findByAccount(String account);
}
