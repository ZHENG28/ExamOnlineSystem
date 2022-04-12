package com.zj.examsystem.auto.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.auto.entity.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.Page;

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
public interface ClazzService extends IService<Clazz> {

    public IPage<Clazz> findAll(Integer pageno, Integer size);

    public Object getDistinctMajorOrClazz(String condition);

    public List<Map<String, Object>> findAllMajorClazz();

    public int saveClazz(Clazz clazz);

    public int deleteClazz(Integer[] id);

    public Clazz findById(Integer clazzId);
}
