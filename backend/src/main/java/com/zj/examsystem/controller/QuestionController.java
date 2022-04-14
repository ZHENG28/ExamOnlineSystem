package com.zj.examsystem.controller;


import com.zj.examsystem.entity.Question;
import com.zj.examsystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size) {
        return questionService.findAll(pageno, size);
    }

    @GetMapping("/save")
    @ResponseBody
    public Object save(Question question) {
        int result = questionService.saveQuestion(question);
        return result != 0;
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] quesId, Integer pageno, Integer size) {
        int result = questionService.deleteQuestion(quesId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.setViewName("forward:/question/findAll");
        }
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer quesId) {
        return questionService.findById(quesId);
    }

    @PostMapping("/findQuesBySubId")
    @ResponseBody
    public Object findQuesBySubId(Integer subId)
    {
        return questionService.findQuesBySubId(subId);
    }

}

