package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.Clazz;
import com.zj.examsystem.entity.Major;
import com.zj.examsystem.mapper.ClazzMapper;
import com.zj.examsystem.mapper.MajorMapper;
import com.zj.examsystem.service.ClazzService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Resource
    private ClazzMapper clazzMapper;

    @Resource
    private MajorMapper majorMapper;

    @Override
    public IPage<Clazz> findAll(Integer pageno, Integer size) {
        IPage<Clazz> page = new Page<>(pageno, size);
        return clazzMapper.selectPageWithMajor(page, null);
    }

    @Override
    public List<Map<String, Object>> findMajorAndClazzList() {
        List<Major> majorList = majorMapper.selectList(null);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Major major : majorList) {
            Map<String, Object> majorMap = new HashMap<>();
            majorMap.put("label", major.getMajorName());
            majorMap.put("value", major.getMajorId());
            List<Map<String, Object>> child = new ArrayList<>();
            QueryWrapper<Clazz> clazzQueryWrapper = new QueryWrapper<>();
            clazzQueryWrapper.eq("major_id", major.getMajorId());
            List<Clazz> clazzList = clazzMapper.selectList(clazzQueryWrapper);
            for (Clazz clazz : clazzList) {
                Map<String, Object> clazzMap = new HashMap<>();
                clazzMap.put("label", clazz.getClazzName());
                clazzMap.put("value", clazz.getClazzId());
                child.add(clazzMap);
            }
            majorMap.put("children", child);
            result.add(majorMap);
        }
        return result;
    }

    @Override
    public List<Clazz> loadClazzByMajorId(Integer majorId) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("major_id", majorId);
        return clazzMapper.selectList(queryWrapper);
    }

    @Override
    public Integer saveClazz(Clazz clazz) {
        try {
            return clazz.getClazzId() != null ? clazzMapper.updateById(clazz) : clazzMapper.insert(clazz);
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
    public Integer deleteClazz(Integer[] id) {
        List<Integer> ids = new ArrayList<>(Arrays.asList(id));
        return clazzMapper.deleteBatchIds(ids);
    }

    @Override
    public Clazz findById(Integer clazzId) {
        return clazzMapper.findById(clazzId);
    }
}
