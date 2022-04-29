package com.zj.examsystem.controller;


import com.zj.examsystem.entity.Answer;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;


@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/findAllByTeacherId")
    @ResponseBody
    public Object findAllByTeacherId(Integer pageno, Integer size, Integer userId) {
        return questionService.findAllByTeacherId(pageno, size, userId);
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(Question question) {
        return questionService.saveQuestion(question);
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] questionId, Integer pageno, Integer size, Integer userId) {
        int result = questionService.deleteQuestion(questionId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.addObject("userId", userId);
            mv.setViewName("forward:/question/findAllByTeacherId");
        }
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer questionId) {
        return questionService.findById(questionId);
    }

    @PostMapping("/findQuesBySubId")
    @ResponseBody
    public Object findQuesBySubId(Integer subjectId) {
        return questionService.findQuesBySubId(subjectId);
    }

    @PostMapping("/findQuestionListByTestId")
    @ResponseBody
    public Object findQuestionListByTestId(Integer testId) {
        return questionService.findQuestionListByTestId(testId);
    }
}

