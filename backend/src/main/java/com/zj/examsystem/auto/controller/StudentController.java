package com.zj.examsystem.auto.controller;


import com.zj.examsystem.auto.entity.Student;
import com.zj.examsystem.auto.entity.Student;
import com.zj.examsystem.auto.service.StudentService;
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
@RequestMapping("/auto/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size) {
        return studentService.findAll(pageno, size);
    }

    @GetMapping("/save")
    @ResponseBody
    public Object save(Student student) {
        int result = studentService.saveStudent(student);
        return result != 0;
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] studentId, Integer pageno, Integer size) {
        int result = studentService.deleteStudent(studentId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.setViewName("forward:/auto/student/findAll");
        }
        return mv;
    }

    @PostMapping("/findByAccount")
    @ResponseBody
    public Object findByAccount(String account) {
        return studentService.findByAccount(account);
    }
}

