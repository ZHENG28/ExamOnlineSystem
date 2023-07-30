package com.zj.examsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.QuestionType;
import com.zj.examsystem.service.QuestionTypeService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/questionType")
public class QuestionTypeController {
    @Resource
    private QuestionTypeService questionTypeService;

    @GetMapping("/findAll")
    @ResponseBody
    public BaseResponseEntity<IPage<QuestionType>> findAll(Integer pageno, Integer size) {
        return BaseResponseEntity.ok("", questionTypeService.findAll(pageno, size));
    }

    @GetMapping("/loadQuestionType")
    @ResponseBody
    public BaseResponseEntity<List<QuestionType>> loadQuestionType() {
        return BaseResponseEntity.ok("", questionTypeService.findAll());
    }

    @GetMapping("/findById")
    @ResponseBody
    public BaseResponseEntity<QuestionType> findById(Integer typeId) {
        return BaseResponseEntity.ok("", questionTypeService.findById(typeId));
    }

    @PostMapping("/save")
    @ResponseBody
    public BaseResponseEntity<Integer> save(QuestionType questionType, String status) {
        int result = questionTypeService.saveQuestionType(questionType);
        return result != 0 ? BaseResponseEntity.ok(status + "成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public BaseResponseEntity<Integer> delete(Integer[] typeId) {
        int result = questionTypeService.deleteQuestionType(typeId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }
}

