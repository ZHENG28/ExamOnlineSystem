package com.zj.examsystem.auto.service.impl;

import com.zj.examsystem.auto.entity.Clazz;
import com.zj.examsystem.auto.mapper.ClazzMapper;
import com.zj.examsystem.auto.service.ClazzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.repository.ClazzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Service
@SuppressWarnings("all")
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    public Page<Clazz> findAll(Integer pageno, Integer size)
    {
//        Pageable pageable = PageRequest.of(pageno, size);
//        return clazzMapper.select(pageable);
        return null;
    }

    public int addClazz(Clazz clazz){
        return clazzMapper.insert(clazz);
    }

    @Transactional
    public void deleteClazz(Integer[] id){
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        clazzMapper.deleteBatchIds(ids);
    }

    public Clazz findById(Integer clazzId)
    {
        return clazzMapper.selectById(clazzId);
    }

    public int modify(Clazz clazz)
    {
        return clazzMapper.insert(clazz);
    }
}
