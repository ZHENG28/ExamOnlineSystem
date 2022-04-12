package com.zj.examsystem.serviceImpl;

import com.zj.examsystem.model.SingleQuestion;
import com.zj.examsystem.model.Student;
import com.zj.examsystem.model.Test;
import com.zj.examsystem.model.TestHistory;
import com.zj.examsystem.repository.SingleQuesRepository;
import com.zj.examsystem.repository.StudentRepository;
import com.zj.examsystem.repository.TestHistoryRepository;
import com.zj.examsystem.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TestServiceImpl1
{
    @Autowired
    private TestRepository testRepo;

    @Autowired
    private TestHistoryRepository testHistoryRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private SingleQuesRepository singleQuesRepo;

//    public Page<Test> findAll(String account, Integer pageno, Integer size)
//    {
//        Pageable pageable = PageRequest.of(pageno, size);
//        if (!"".equals(account)) {
//            Student stu = studentRepo.findByAccount(account).orElseThrow();
//            return testRepo.findAllByTestClazz(stu.getClazz(), pageable);
//        } else {
//            return testRepo.findAll(pageable);
//        }
//    }
//
//    public Test addTest(Test test)
//    {
//        return testRepo.save(test);
//    }
//
//    @Transactional
//    public void deleteTest(Integer[] id)
//    {
//        List<Integer> ids = new ArrayList<>();
//        for (int i = 0; i < id.length; i++) {
//            ids.add(id[i]);
//        }
//        testRepo.deleteAllByIdInBatch(ids);
//    }
//
//    public Test findById(Integer testId)
//    {
//        return testRepo.findById(testId).orElseThrow();
//    }
//
//    @Transactional
//    public Test modifyTest(Test test)
//    {
//        return testRepo.save(test);
//    }

    public Object findQuesArrByTestId(Integer testId)
    {
        String quesArr = testRepo.findById(testId).orElseThrow().getQuesArr();
        String[] arr = quesArr.split(",");
        List<SingleQuestion> singleQuestionList = new ArrayList<>();
        for (String s : arr) {
            SingleQuestion sq = singleQuesRepo.findById(Integer.valueOf(s)).orElseThrow();
            singleQuestionList.add(sq);
        }
        return singleQuestionList;
    }

//    public Object findExamTimeByTestId(String account, Integer testId)
//    {
//        Test test = testRepo.findById(testId).orElseThrow();
//        Student stu = studentRepo.findByAccount(account).orElseThrow();
//        List<TestHistory> historyList = testHistoryRepo.findByTestAndStu(test, stu);
//        if (historyList.size() == test.getExamTime()) {
//            return "No";
//        }
//        return "OK";
//    }
}
