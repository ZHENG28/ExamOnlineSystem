package com.zj.examsystem.web;

import com.zj.examsystem.model.Clazz;
import com.zj.examsystem.serviceImpl.ClazzServiceImpl1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/clazz")
public class ClazzController1
{
    @Autowired
    private ClazzServiceImpl1 clazzService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size)
    {
        return clazzService.findAll(pageno, size);
    }

    @GetMapping("/add")
    @ResponseBody
    public String add(Clazz clazz)
    {
        Clazz c = clazzService.addClazz(clazz);
        if (c == null) {
            return "failed";
        }
        return "success";
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] clazzId, Integer pageno, Integer size)
    {
        clazzService.deleteClazz(clazzId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageno", pageno);
        mv.addObject("size", size);
        mv.setViewName("forward:/clazz/findAll");
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer clazzId)
    {
        return clazzService.findById(clazzId);
    }

    @PostMapping("/modify")
    @ResponseBody
    public String modify(Clazz clazz)
    {
        Clazz c = clazzService.modify(clazz);
        if (c == null) {
            return "failed";
        }
        return "success";
    }
}
