package com.zj.examsystem.eos.controller;


import com.zj.examsystem.eos.service.TestHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/eos/testHistory")
public class TestHistoryController {
    @Autowired
    private TestHistoryService testHistoryService;

    @PostMapping("/findScoreByTestId")
    @ResponseBody
    public Object findScoreByTestId(Integer testId) {
        return testHistoryService.findScoreByTestId(testId);
    }

    @PostMapping("/findAllByAccount")
    @ResponseBody
    public Object findAllByAccount(Integer pageno, Integer size, String account) {
        return testHistoryService.findAllByAccount(pageno, size, account);
    }
}

