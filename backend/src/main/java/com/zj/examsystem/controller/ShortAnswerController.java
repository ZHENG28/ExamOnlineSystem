package com.zj.examsystem.controller;


import com.zj.examsystem.service.ShortAnswerService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shortAnswer")
public class ShortAnswerController {
    @Autowired
    ShortAnswerService shortAnswerService;

    @GetMapping("/findAllQuestion")
    @ResponseBody
    public Object findAllQuestion(Integer testId) {
        return BaseResponseEntity.ok("", shortAnswerService.findAllQuestion(testId));
    }
}

