package com.zj.examsystem.web;

import com.zj.examsystem.model.Subject;
import com.zj.examsystem.model.Teacher;
import com.zj.examsystem.serviceImpl.SubjectServiceImpl;
import com.zj.examsystem.serviceImpl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sub")
public class SubjectController
{
    @Autowired
    private SubjectServiceImpl subjectService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size)
    {
        return subjectService.findAll(pageno, size);
    }

    @PostMapping("/findAllTch")
    @ResponseBody
    public Object findAllTch(Integer tchId)
    {
        return teacherService.findTchSortByMajor(tchId);
    }

    @GetMapping("/add")
    @ResponseBody
    public String add(Subject subject)
    {
        Subject sub = subjectService.addSubject(subject);
        if (sub == null) {
            return "failed";
        }
        return "success";
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] subId, Integer pageno, Integer size)
    {
        subjectService.deleteSubject(subId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageno", pageno);
        mv.addObject("size", size);
        mv.setViewName("forward:/sub/findAll");
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer subId)
    {
        return subjectService.findById(subId);
    }

    @PostMapping("/modify")
    @ResponseBody
    public String modify(Subject subject)
    {
        Subject sub = subjectService.modify(subject);
        if (sub == null) {
            return "failed";
        }
        return "success";
    }
}
