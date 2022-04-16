package com.zj.examsystem.controller;


import com.zj.examsystem.entity.QuestionType;
import com.zj.examsystem.service.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/questionType")
public class QuestionTypeController {
    @Autowired
    private QuestionTypeService questionTypeService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size, Integer... userId) {
        return questionTypeService.findAll(pageno, size, userId);
    }

//    @PostMapping("/findAllQuestionTypeId")
//    @ResponseBody
//    public Object findAllQuestionTypeId() {
//        return questionTypeService.findDistinctQuestionType();
//    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(QuestionType questionType) {
        int result = questionTypeService.saveQuestionType(questionType);
        return result != 0;
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] typeId, Integer pageno, Integer size) {
        int result = questionTypeService.deleteQuestionType(typeId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.setViewName("forward:/questionType/findAll");
        }
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer questionTypeId) {
        return questionTypeService.findById(questionTypeId);
    }

}

