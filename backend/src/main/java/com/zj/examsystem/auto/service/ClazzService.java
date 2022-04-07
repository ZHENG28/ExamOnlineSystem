package com.zj.examsystem.auto.service;

import com.zj.examsystem.auto.entity.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.Page;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface ClazzService extends IService<Clazz> {

    public Page<Clazz> findAll(Integer pageno, Integer size);

    public int addClazz(Clazz clazz);

    public void deleteClazz(Integer[] id);

    public Clazz findById(Integer clazzId);

    public int modify(Clazz clazz);
}
