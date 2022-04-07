package com.zj.examsystem.serviceImpl;

import com.zj.examsystem.config.security.payload.response.MessageResponse;
import com.zj.examsystem.model.Subject;
import com.zj.examsystem.model.Teacher;
import com.zj.examsystem.repository.ClazzRepository;
import com.zj.examsystem.repository.SubjectRepository;
import com.zj.examsystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl1
{
    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private ClazzRepository clazzRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    @Autowired
    PasswordEncoder encoder;

    public List<Map<String, Object>> findTchSortByMajor(Integer tchId)
    {
        List<Subject> subList = subjectRepo.findAll();
        List<Integer> subTch = new ArrayList<>();
        for (Subject subject : subList) {
            if (subject.getTeacher() != null) {
                subTch.add(subject.getTeacher().getId());
            }
        }
        if (tchId != null) {
            subTch.remove(tchId);
        }
        List<Teacher> tchList = new ArrayList<>();
        if (subTch.isEmpty()) {
            tchList = teacherRepo.findAll();
        } else {
            tchList = teacherRepo.findTeacherByIdNotIn(subTch);
        }
        List<String> majorList = clazzRepo.selectDistinctMajor();
        List<Map<String, Object>> teacherList = new ArrayList<>();
        for (String major : majorList) {
            Map<String, Object> majorMap = new HashMap<>();
            majorMap.put("value", major);
            majorMap.put("label", major);
            List<Map<String, Object>> child = new ArrayList<>();
            for (Teacher tch : tchList) {
                if (major.equals(tch.getMajor())) {
                    Map<String, Object> tchMap = new HashMap<>();
                    tchMap.put("value", tch.getId());
                    tchMap.put("label", tch.getTchName());
                    child.add(tchMap);
                }
            }
            majorMap.put("children", child);
            teacherList.add(majorMap);
        }
        return teacherList;
    }

    public Page<Teacher> findAll(Integer pageno, Integer size)
    {
        Pageable pageable = PageRequest.of(pageno, size);
        return teacherRepo.findAll(pageable);
    }

    public ResponseEntity<?> addTch(Teacher tch)
    {
        if (teacherRepo.existsByAccount(tch.getAccount())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Account is already taken!"));
        }
        tch.setPassword(encoder.encode(tch.getPassword()));
        teacherRepo.save(tch);
        return ResponseEntity.ok(new MessageResponse("OK"));
    }

    @Transactional
    public void deleteTch(String[] accounts)
    {
        List<Integer> accountList = new ArrayList<>();
        for (String account : accounts) {
            Teacher tch = teacherRepo.findByAccount(account).orElseThrow();
            accountList.add(tch.getId());
        }
        teacherRepo.deleteAllByIdInBatch(accountList);
    }

    public Teacher findByAccount(String account)
    {
        return teacherRepo.findByAccount(account).orElseThrow();
    }

    @Transactional
    public ResponseEntity<?> modifyTch(Teacher tch)
    {
        Teacher teacher = teacherRepo.findById(tch.getId()).orElseThrow();
        if (!teacher.getPassword().equals(tch.getPassword())) {
            tch.setPassword(encoder.encode(tch.getPassword()));
        }
        teacherRepo.save(tch);
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}
