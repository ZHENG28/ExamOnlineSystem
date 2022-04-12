package com.zj.examsystem.auto.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.auto.entity.Clazz;
import com.zj.examsystem.auto.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface SubjectService extends IService<Subject> {
    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size);

    public List<Map<String, Object>> findAllSubject();

    public int saveSubject(Subject subject);

    public int deleteSubject(Integer[] id);

    public Map<String, Object> findById(Integer subId);
}
