package com.zj.examsystem.controller;


import com.zj.examsystem.entity.CompareShortAnswer;
import com.zj.examsystem.service.CompareShortAnswerService;
import com.zj.examsystem.utils.response.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/compareShortAnswer")
public class CompareShortAnswerController {
    @Autowired
    CompareShortAnswerService compareShortAnswerService;

    @PostMapping("/loadCompareText")
    @ResponseBody
    public ResponseEntity<CompareShortAnswer> loadCompareText(Integer pageno, Integer size, Integer testId) {
        compareShortAnswerService.loadCompareText(pageno, size, testId);
        return null;
    }
}

