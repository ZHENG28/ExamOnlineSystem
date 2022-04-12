package com.zj.examsystem.auto.controller;


import com.zj.examsystem.auto.entity.Teacher;
import com.zj.examsystem.auto.entity.Teacher;
import com.zj.examsystem.auto.service.TeacherService;
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
@RequestMapping("/auto/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size) {
        return teacherService.findAll(pageno, size);
    }

    @PostMapping("/findAllTch")
    @ResponseBody
    public Object findAllTch(Integer tchId) {
        return teacherService.findTchSortByMajor(tchId);
    }

    @GetMapping("/save")
    @ResponseBody
    public Object save(Teacher teacher) {
        int result = teacherService.saveTeacher(teacher);
        return result != 0;
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] teacherId, Integer pageno, Integer size) {
        int result = teacherService.deleteTeacher(teacherId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.setViewName("forward:/auto/teacher/findAll");
        }
        return mv;
    }

    @PostMapping("/findByAccount")
    @ResponseBody
    public Object findByAccount(String account) {
        return teacherService.findByAccount(account);
    }
}

