package com.zj.examsystem.serviceImpl;

import com.zj.examsystem.config.security.payload.response.MessageResponse;
import com.zj.examsystem.model.Student;
import com.zj.examsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl1
{
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    PasswordEncoder encoder;

    public Page<Student> findAll(Integer pageno, Integer size)
    {
        Pageable pageable = PageRequest.of(pageno, size);
        return studentRepo.findAll(pageable);
    }

    public ResponseEntity<?> addStu(Student stu)
    {
        if (studentRepo.existsByAccount(stu.getAccount())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Account is already taken!"));
        }
        stu.setPassword(encoder.encode(stu.getPassword()));
        studentRepo.save(stu);
        return ResponseEntity.ok(new MessageResponse("OK"));
    }

    @Transactional
    public void deleteStu(String[] accounts)
    {
        List<Integer> accountList = new ArrayList<>();
        for (String account : accounts) {
            Student stu = studentRepo.findByAccount(account).orElseThrow();
            accountList.add(stu.getId());
        }
        studentRepo.deleteAllByIdInBatch(accountList);
    }

    public Student findByAccount(String account)
    {
        return studentRepo.findByAccount(account).orElseThrow();
    }

    @Transactional
    public ResponseEntity<?> modifyStu(Student stu)
    {
        Student student = studentRepo.findById(stu.getId()).orElseThrow();
        if (!student.getPassword().equals(stu.getPassword())) {
            stu.setPassword(encoder.encode(stu.getPassword()));
        }
        studentRepo.save(stu);
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}
