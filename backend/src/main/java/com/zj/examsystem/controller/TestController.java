package com.zj.examsystem.controller;


import com.zj.examsystem.entity.Test;
import com.zj.examsystem.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping("/findAllByUserId")
    @ResponseBody
    public Object findAllByUserId(Integer pageno, Integer size, Integer userId) {
        return testService.findAll(pageno, size, userId);
    }

    @GetMapping("/save")
    @ResponseBody
    public Object save(Test test) {
        return testService.saveTest(test);
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] testId, Integer pageno, Integer size) {
        int result = testService.deleteTest(testId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.setViewName("forward:/test/findAllByUserId");
        }
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer testId) {
        return testService.findById(testId);
    }

    @PostMapping("/findExamTimeByTestId")
    @ResponseBody
    public Object findExamTimeByTestId(Integer userId, Integer testId) {
        return testService.findExamTimeByTestId(userId, testId);
    }
}

