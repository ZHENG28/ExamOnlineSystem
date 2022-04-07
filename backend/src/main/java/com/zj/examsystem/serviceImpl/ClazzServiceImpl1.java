package com.zj.examsystem.serviceImpl;

import com.zj.examsystem.model.Clazz;
import com.zj.examsystem.repository.ClazzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClazzServiceImpl1
{
    @Autowired
    private ClazzRepository clazzRepo;

    public Page<Clazz> findAll(Integer pageno, Integer size)
    {
        Pageable pageable = PageRequest.of(pageno, size);
        return clazzRepo.findAll(pageable);
    }

    public Clazz addClazz(Clazz clazz)
    {
        return clazzRepo.save(clazz);
    }

    @Transactional
    public void deleteClazz(Integer[] id)
    {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        clazzRepo.deleteAllByIdInBatch(ids);
    }

    public Clazz findById(Integer clazzId)
    {
        return clazzRepo.findById(clazzId).orElseThrow();
    }

    public Clazz modify(Clazz clazz)
    {
        return clazzRepo.save(clazz);
    }

    public List<Map<String, String>> findAllMajorAndClazz(String cond)
    {
        List<String> tmp = null;
        switch (cond) {
            case "major":
                tmp = clazzRepo.selectDistinctMajor();
                break;
            case "clazz":
                tmp = clazzRepo.selectDistinctClazzName();
        }
        List<Map<String, String>> List = new ArrayList<>();
        for (String str : tmp) {
            Map<String, String> map = new HashMap<>();
            map.put("text", str);
            map.put("value", str);
            List.add(map);
        }
        return List;
    }

    public List<Map<String, Object>> findAllMajorClazz()
    {
        List<Clazz> clazzList = clazzRepo.findAll();
        List<String> majorList = clazzRepo.selectDistinctMajor();
        List<Map<String, Object>> mcList = new ArrayList<>();
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
            mcList.add(majorMap);
        }
        return mcList;
    }
}
