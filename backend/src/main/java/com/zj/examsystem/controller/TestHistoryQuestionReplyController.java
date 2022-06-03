package com.zj.examsystem.controller;


import com.zj.examsystem.service.TestHistoryQuestionReplyService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/testHistoryQuestionReply")
public class TestHistoryQuestionReplyController {
    @Autowired
    private TestHistoryQuestionReplyService testHistoryQuestionReplyService;

    @GetMapping("/findTestHistoryReplyByHistoryId")
    @ResponseBody
    public Object findTestHistoryReplyByHistoryId(Integer historyId) {
        return BaseResponseEntity.ok("", testHistoryQuestionReplyService.findTestHistoryReplyByHistoryId(historyId));
    }
}

