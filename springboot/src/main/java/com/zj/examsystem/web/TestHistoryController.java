package com.zj.examsystem.web;

import com.zj.examsystem.model.TestHistory;
import com.zj.examsystem.serviceImpl.SubjectServiceImpl;
import com.zj.examsystem.serviceImpl.TestHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/testHistory")
public class TestHistoryController
{
    @Autowired
    private TestHistoryServiceImpl testHistoryService;

    @Autowired
    private SubjectServiceImpl subjectService;

    @PostMapping("/commit")
    @ResponseBody
    public Object commitScore(TestHistory testHistory)
    {
        TestHistory th = testHistoryService.commit(testHistory);
        if (th != null) {
            return "success";
        } else {
            return "fail";
        }
    }

    @PostMapping("/findAllSubId")
    @ResponseBody
    public Object findAllSubId()
    {
        return subjectService.findAllSubject();
    }

    @PostMapping("/findAllByAccount")
    @ResponseBody
    public Object findAll(String account, Integer pageno, Integer size)
    {
        return testHistoryService.findAll(account, pageno, size);
    }

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size)
    {
        return testHistoryService.findAll("", pageno, size);
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer id)
    {
        return testHistoryService.findById(id);
    }

}
