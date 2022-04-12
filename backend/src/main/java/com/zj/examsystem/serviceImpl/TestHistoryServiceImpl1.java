package com.zj.examsystem.serviceImpl;

import com.zj.examsystem.model.Clazz;
import com.zj.examsystem.model.Student;
import com.zj.examsystem.model.Test;
import com.zj.examsystem.model.TestHistory;
import com.zj.examsystem.repository.StudentRepository;
import com.zj.examsystem.repository.TestHistoryRepository;
import com.zj.examsystem.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestHistoryServiceImpl1 {
    @Autowired
    private TestHistoryRepository testHistoryRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private TestRepository testRepo;

    public TestHistory commit(TestHistory testHistory) {
        return testHistoryRepo.save(testHistory);
    }

    //    public List<TestHistory> findAll()
    //    {
    //        return testHistoryRepo.findAll();
    //    }
    //
    //    public Page<TestHistory> findAll(String account, Integer pageno, Integer size)
    //    {
    //        Pageable pageable = PageRequest.of(pageno, size);
    //        if (!"".equals(account)) {
    //            Student stu = studentRepo.findByAccount(account).orElseThrow();
    //            return testHistoryRepo.findAllByStu(stu, pageable);
    //        } else {
    //            return testHistoryRepo.findAll(pageable);
    //        }
    //    }

    public TestHistory findById(Integer id) {
        return testHistoryRepo.findById(id).orElseThrow();
    }

    public List<TestHistory> getStuChartData(Integer stuId) {
        return testHistoryRepo.findByStuId(stuId);
    }

    //    public List<Map<String, Object>> findScoreByTestId(Integer testId)
    //    {
    //        Test test = testRepo.findById(testId).orElseThrow();
    //        List<Integer> histories = testHistoryRepo.findByTestId(test);
    //        List<Map<String, Object>> scoreList = new ArrayList<>();
    //        int[] scoreArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //        for (Integer score : histories) {
    //            if (score == 100) {
    //                scoreArr[9]++;
    //                continue;
    //            }
    //            scoreArr[score / 10]++;
    //        }
    //        int[] arr = {scoreArr[0] + scoreArr[1] + scoreArr[2] + scoreArr[3] + scoreArr[4] + scoreArr[5], scoreArr[6], scoreArr[7], scoreArr[8],
    //                scoreArr[9]};
    //        for (int i = 0; i < 5; i++) {
    //            Map<String, Object> map = new HashMap<>();
    //            map.put("value", arr[i]);
    //            String str = "";
    //            switch (i) {
    //                case 0:
    //                    str = "60以下";
    //                    break;
    //                case 1:
    //                    str = "60-69";
    //                    break;
    //                case 2:
    //                    str = "70-79";
    //                    break;
    //                case 3:
    //                    str = "80-89";
    //                    break;
    //                case 4:
    //                    str = "90-100";
    //                    break;
    //            }
    //            map.put("name", str);
    //            scoreList.add(map);
    //        }
    //        return scoreList;
    //    }
}
