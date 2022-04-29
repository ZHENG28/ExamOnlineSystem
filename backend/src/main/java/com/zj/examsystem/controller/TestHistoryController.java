package com.zj.examsystem.controller;


import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.TestHistory;
import com.zj.examsystem.service.QuestionService;
import com.zj.examsystem.service.TestHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/testHistory")
public class TestHistoryController {
    @Autowired
    private TestHistoryService testHistoryService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/findScoreByTestId")
    @ResponseBody
    public Object findScoreByTestId(Integer testId) {
        return testHistoryService.findScoreByTestId(testId);
    }

    @PostMapping("/findAllByAccount")
    @ResponseBody
    public Object findAllByAccount(Integer pageno, Integer size, Integer userId) {
        return testHistoryService.findAllByAccount(pageno, size, userId);
    }

    @PostMapping("/findAllByTestId")
    @ResponseBody
    public Object findAllByTestId(Integer pageno, Integer size, Integer testId) {
        return testHistoryService.findAllByTestId(pageno, size, testId);
    }

    @PostMapping("/commit")
    @ResponseBody
    public Object commit(TestHistory testHistory) {
        return testHistoryService.commit(testHistory, questionService.findQuestionListByTestId(testHistory.getTestId()));
    }
}

