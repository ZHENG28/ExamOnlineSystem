package com.zj.examsystem.controller;

import com.zj.examsystem.entity.Question;
import com.zj.examsystem.service.ShortAnswerService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/shortAnswer")
public class ShortAnswerController {
    @Resource
    ShortAnswerService shortAnswerService;

    @GetMapping("/findAllQuestion")
    @ResponseBody
    public BaseResponseEntity<List<Question>> findAllQuestion(Integer testId) {
        return BaseResponseEntity.ok("", shortAnswerService.findAllQuestion(testId));
    }
}

