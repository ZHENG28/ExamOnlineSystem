package com.zj.examsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.CompareShortAnswer;
import com.zj.examsystem.service.CompareShortAnswerService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/compareShortAnswer")
public class CompareShortAnswerController {
    @Resource
    CompareShortAnswerService compareShortAnswerService;

    @GetMapping("/loadCompareTextData")
    @ResponseBody
    public BaseResponseEntity<IPage<CompareShortAnswer>> loadCompareTextData(Integer pageno, Integer size, Integer testId) {
        return BaseResponseEntity.ok("", compareShortAnswerService.loadCompareTextData(pageno, size, testId));
    }

    @GetMapping("/findById")
    @ResponseBody
    public BaseResponseEntity<CompareShortAnswer> findById(Integer compareId, Integer threshold) {
        return BaseResponseEntity.ok("", compareShortAnswerService.findById(compareId, threshold));
    }

    @GetMapping("/getCompareList")
    @ResponseBody
    public BaseResponseEntity<CompareShortAnswer> getCompareList(String reply1Text, String reply2Text, Integer threshold) {
        CompareShortAnswer compareShortAnswer = new CompareShortAnswer();
        compareShortAnswer.setReply1Text(reply1Text);
        compareShortAnswer.setReply2Text(reply2Text);
        return BaseResponseEntity.ok("", compareShortAnswerService.getCompareList(compareShortAnswer, threshold));
    }
}

