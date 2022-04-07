package com.zj.examsystem.serviceImpl;

import com.zj.examsystem.model.Subject;
import com.zj.examsystem.repository.SubjectRepository;
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
public class SubjectServiceImpl1
{
    @Autowired
    private SubjectRepository subjectRepo;

    public Page<Subject> findAll(Integer pageno, Integer size)
    {
        Pageable pageable = PageRequest.of(pageno, size);
        return subjectRepo.findAll(pageable);
    }

    public Subject addSubject(Subject subject)
    {
        return subjectRepo.save(subject);
    }

    @Transactional
    public void deleteSubject(Integer[] id)
    {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        subjectRepo.deleteAllByIdInBatch(ids);
    }

    public Subject findById(Integer subId)
    {
        return subjectRepo.findById(subId).orElseThrow();
    }

    public Subject modify(Subject subject)
    {
        return subjectRepo.save(subject);
    }

    public List<Map<String, Object>> findAllSubject()
    {
        List<String> tmp = subjectRepo.selectDistinctSubject();
        List<Map<String, Object>> List = new ArrayList<>();
        for (String str : tmp) {
            Map<String, Object> map = new HashMap<>();
            String[] strArr = str.split(",");
            map.put("text", strArr[0]);
            map.put("value", strArr[0]);
            map.put("subId", strArr[1]);
            List.add(map);
        }
        return List;
    }
}
