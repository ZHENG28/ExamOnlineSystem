package com.zj.examsystem.auto.controller;


import com.zj.examsystem.auto.entity.Test;
import com.zj.examsystem.auto.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Controller
@RequestMapping("/auto/test")
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping("/findAllByStuIdOrNot")
    @ResponseBody
    public Object findAllByStuId(Integer pageno, Integer size, Integer... id) {
        return testService.findAll(pageno, size, id);
    }

    @GetMapping("/save")
    @ResponseBody
    public Object save(Test test) {
        int result = testService.saveTest(test);
        return result != 0;
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] testId, Integer pageno, Integer size) {
        int result = testService.deleteTest(testId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.setViewName("forward:/auto/test/findAllByStuIdOrNot");
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
    public Object findExamTimeByTestId(String account, Integer testId)
    {
        return testService.findExamTimeByTestId(account, testId);
    }

}

