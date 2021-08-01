package com.zj.examsystem.web;

import com.zj.examsystem.model.Teacher;
import com.zj.examsystem.serviceImpl.ClazzServiceImpl;
import com.zj.examsystem.serviceImpl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/tch")
public class TeacherController
{
    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private ClazzServiceImpl clazzService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size)
    {
        return teacherService.findAll(pageno, size);
    }

    @PostMapping("/findAllMajor")
    @ResponseBody
    public Object findAllMajor()
    {
        return clazzService.findAllMajorAndClazz("major");
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseEntity<?> add(@Valid @RequestBody Teacher tch)
    {
        return teacherService.addTch(tch);
    }

    @PostMapping("/del")
    public ModelAndView delete(String[] account, Integer pageno, Integer size)
    {
        teacherService.deleteTch(account);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageno", pageno);
        mv.addObject("size", size);
        mv.setViewName("forward:/tch/findAll");
        return mv;
    }

    @PostMapping("/findByAccount")
    @ResponseBody
    public Object findByAccount(String account)
    {
        return teacherService.findByAccount(account);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public ResponseEntity<?> modify(@Valid @RequestBody Teacher tch)
    {
        return teacherService.modifyTch(tch);
    }
}
