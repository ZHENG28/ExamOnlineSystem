package com.zj.examsystem.auto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.auto.entity.Clazz;
import com.zj.examsystem.auto.entity.SingleQuestion;
import com.zj.examsystem.auto.entity.Teacher;
import com.zj.examsystem.auto.mapper.ClazzMapper;
import com.zj.examsystem.auto.service.ClazzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.repository.ClazzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    public IPage<Clazz> findAll(Integer pageno, Integer size) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        IPage<Clazz> page = new Page<>();
        page.setCurrent(pageno);
        page.setSize(size);

        return clazzMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Object getDistinctMajorOrClazz(String condition) {
        List<String> tmp = null;
        switch (condition) {
            case "major":
                tmp = clazzMapper.selectDistinctMajor();
                break;
            case "clazz":
                tmp = clazzMapper.selectDistinctClazzName();
        }
        List<Map<String, Object>> results = new ArrayList<>();
        for (String major : tmp) {
            Map<String, Object> result = new HashMap<>();
            result.put("text", major);
            result.put("value", major);
            results.add(result);
        }
        return results;
    }

    public List<Map<String, Object>> findAllMajorClazz() {
        QueryWrapper<Clazz> clazzQueryWrapper = new QueryWrapper<>();

        List<String> majorList = clazzMapper.selectDistinctMajor();
        List<Clazz> clazzList = clazzMapper.selectList(clazzQueryWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (String major : majorList) {
            Map<String, Object> majorMap = new HashMap<>();
            majorMap.put("value", major);
            majorMap.put("label", major);
            List<Map<String, Object>> child = new ArrayList<>();
            for (Clazz clazz : clazzList) {
                if (major.equals(clazz.getMajor())) {
                    Map<String, Object> clazzMap = new HashMap<>();
                    clazzMap.put("value", clazz.getClazzId());
                    clazzMap.put("label", clazz.getClazzName());
                    child.add(clazzMap);
                }
            }
            majorMap.put("children", child);
            result.add(majorMap);
        }
        return result;
    }

    public int saveClazz(Clazz clazz) {
        return clazz.getClazzId() != null ? clazzMapper.updateById(clazz) : clazzMapper.insert(clazz);
    }

    @Transactional
    public int deleteClazz(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return clazzMapper.deleteBatchIds(ids);
    }

    public Clazz findById(Integer clazzId) {
        return clazzMapper.selectById(clazzId);
    }
}
