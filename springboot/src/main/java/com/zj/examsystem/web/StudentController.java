package com.zj.examsystem.web;

import com.zj.examsystem.model.Student;
import com.zj.examsystem.serviceImpl.ClazzServiceImpl;
import com.zj.examsystem.serviceImpl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/stu")
public class StudentController
{
    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private ClazzServiceImpl clazzService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size)
    {
        return studentService.findAll(pageno, size);
    }

    @PostMapping("/findAllMajor")
    @ResponseBody
    public Object findAllMajor()
    {
        return clazzService.findAllMajorAndClazz("major");
    }

    @PostMapping("/findAllClazz")
    @ResponseBody
    public Object findAllClazz()
    {
        return clazzService.findAllMajorAndClazz("clazz");
    }

    @PostMapping("/findAllMajorAndClazz")
    @ResponseBody
    public Object findAllMajorAndClazz()
    {
        return clazzService.findAllMajorClazz();
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody Student stu)
    {
        return studentService.addStu(stu);
    }

    @PostMapping("/del")
    public ModelAndView delete(String[] account, Integer pageno, Integer size)
    {
        studentService.deleteStu(account);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageno", pageno);
        mv.addObject("size", size);
        mv.setViewName("forward:/stu/findAll");
        return mv;
    }

    @PostMapping("/findByAccount")
    @ResponseBody
    public Object findByAccount(String account)
    {
        return studentService.findByAccount(account);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public ResponseEntity<?> modify(@RequestBody Student stu)
    {
        return studentService.modifyStu(stu);
    }
}
