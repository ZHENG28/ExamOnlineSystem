package com.zj.examsystem.auto.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.auto.entity.Subject;
import com.zj.examsystem.auto.entity.Teacher;
import com.zj.examsystem.auto.service.SubjectService;
import com.zj.examsystem.auto.service.SubjectService;
import com.zj.examsystem.auto.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Controller
@RequestMapping("/auto/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size) {
        return subjectService.findAll(pageno, size);
    }

    @PostMapping("/findAllSubId")
    @ResponseBody
    public Object findAllSubId()
    {
        return subjectService.findAllSubject();
    }

    @GetMapping("/save")
    @ResponseBody
    public Object save(Subject subject) {
        int result = subjectService.saveSubject(subject);
        return result != 0;
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] subjectId, Integer pageno, Integer size) {
        int result = subjectService.deleteSubject(subjectId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.setViewName("forward:/auto/subject/findAll");
        }
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer subjectId) {
        return subjectService.findById(subjectId);
    }
}

