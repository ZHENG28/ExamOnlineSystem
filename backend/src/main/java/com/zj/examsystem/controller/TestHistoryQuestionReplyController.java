package com.zj.examsystem.controller;

import com.zj.examsystem.entity.TestHistoryQuestionReply;
import com.zj.examsystem.service.TestHistoryQuestionReplyService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/testHistoryQuestionReply")
public class TestHistoryQuestionReplyController {
    @Resource
    private TestHistoryQuestionReplyService testHistoryQuestionReplyService;

    @GetMapping("/findTestHistoryReplyByHistoryId")
    @ResponseBody
    public BaseResponseEntity<List<TestHistoryQuestionReply>> findTestHistoryReplyByHistoryId(Integer historyId) {
        return BaseResponseEntity.ok("", testHistoryQuestionReplyService.findTestHistoryReplyByHistoryId(historyId));
    }
}

