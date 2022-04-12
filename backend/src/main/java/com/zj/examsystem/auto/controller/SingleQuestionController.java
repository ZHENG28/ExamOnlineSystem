package com.zj.examsystem.auto.controller;


import com.zj.examsystem.auto.entity.SingleQuestion;
import com.zj.examsystem.auto.service.SingleQuestionService;
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
@RequestMapping("/auto/singleQuestion")
public class SingleQuestionController {

    @Autowired
    private SingleQuestionService singleQuestionService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size) {
        return singleQuestionService.findAll(pageno, size);
    }

    @GetMapping("/save")
    @ResponseBody
    public Object save(SingleQuestion singleQuestion) {
        int result = singleQuestionService.saveSingleQuestion(singleQuestion);
        return result != 0;
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] quesId, Integer pageno, Integer size) {
        int result = singleQuestionService.deleteSingleQuestion(quesId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.setViewName("forward:/auto/singleQuestion/findAll");
        }
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer quesId) {
        return singleQuestionService.findById(quesId);
    }

    @PostMapping("/findQuesBySubId")
    @ResponseBody
    public Object findQuesBySubId(Integer subId)
    {
        return singleQuestionService.findQuesBySubId(subId);
    }
}

