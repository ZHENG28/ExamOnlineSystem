package com.zj.examsystem.controller;

import com.zj.examsystem.entity.QuestionType;
import com.zj.examsystem.service.QuestionTypeService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/questionType")
public class QuestionTypeController {
    @Autowired
    private QuestionTypeService questionTypeService;

    @GetMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size) {
        return BaseResponseEntity.ok("", questionTypeService.findAll(pageno, size));
    }

    @GetMapping("/loadQuestionType")
    @ResponseBody
    public Object loadQuestionType() {
        return BaseResponseEntity.ok("", questionTypeService.findAll());
    }

    @GetMapping("/findById")
    @ResponseBody
    public Object findById(Integer typeId) {
        return BaseResponseEntity.ok("", questionTypeService.findById(typeId));
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(QuestionType questionType, String status) {
        int result = questionTypeService.saveQuestionType(questionType);
        return result != 0 ? BaseResponseEntity.ok(status + "成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Integer[] typeId) {
        int result = questionTypeService.deleteQuestionType(typeId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }

}

