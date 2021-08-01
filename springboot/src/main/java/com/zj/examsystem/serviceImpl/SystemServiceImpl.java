package com.zj.examsystem.serviceImpl;

import com.zj.examsystem.config.security.payload.response.MessageResponse;
import com.zj.examsystem.model.Manager;
import com.zj.examsystem.model.Student;
import com.zj.examsystem.model.Teacher;
import com.zj.examsystem.model.TestHistory;
import com.zj.examsystem.repository.ManagerRepository;
import com.zj.examsystem.repository.StudentRepository;
import com.zj.examsystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SystemServiceImpl
{
    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    PasswordEncoder encoder;

    public Integer[] countChartData(List<TestHistory> historyList)
    {
        int currMonth = new Date().getMonth();
        int currDate = new Date().getDate();
        Integer[] dataList = {0, 0, 0, 0, 0, 0, 0};
        for (TestHistory testHistory : historyList) {
            if (testHistory.getFinishDate().getMonth() == currMonth) {
                int date = testHistory.getFinishDate().getDate();
                dataList[currDate - date]++;
            }
        }
        return dataList;
    }

    public Object findUsername(Integer role, String account)
    {
        switch (role) {
            case 2: // teacher
                return teacherRepo.findByAccount(account);
            case 1: // student
                return studentRepo.findByAccount(account);
        }
        return null;
    }

    public Object findUserInfo(Integer role, String account)
    {
        switch (role) {
            case 3: // manager
                return managerRepo.findByAccount(account);
            case 2: // teacher
                return teacherRepo.findByAccount(account);
            case 1: // student
                return studentRepo.findByAccount(account);
        }
        return null;
    }

    public ResponseEntity<?> updateInfo(String role, String account, String username, String password)
    {
        switch (role) {
            case "ROLE_MANAGER":
                Manager mgr = managerRepo.findByAccount(account).orElseThrow();
                if (!mgr.getPassword().equals(password)) {
                    mgr.setPassword(encoder.encode(password));
                }
                managerRepo.save(mgr);
                return ResponseEntity.ok(new MessageResponse("OK"));
            case "ROLE_TEACHER":
                Teacher tch = teacherRepo.findByAccount(account).orElseThrow();
                if (!tch.getPassword().equals(password)) {
                    tch.setPassword(encoder.encode(password));
                }
                tch.setTchName(username);
                teacherRepo.save(tch);
                return ResponseEntity.ok(new MessageResponse("OK"));
            case "ROLE_STUDENT":
                Student stu = studentRepo.findByAccount(account).orElseThrow();
                if (!stu.getPassword().equals(password)) {
                    stu.setPassword(encoder.encode(password));
                }
                stu.setStuName(username);
                studentRepo.save(stu);
                return ResponseEntity.ok(new MessageResponse("OK"));
        }
        return null;
    }
}
