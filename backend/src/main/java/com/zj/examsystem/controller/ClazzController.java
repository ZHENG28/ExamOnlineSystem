package com.zj.examsystem.controller;


import com.zj.examsystem.entity.Clazz;
import com.zj.examsystem.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size) {
        return clazzService.findAll(pageno, size);
    }

    @GetMapping("/getDistinctMajor")
    @ResponseBody
    public Object getDistinctMajor() {
        return clazzService.getDistinctMajorOrClazz("major");
    }

    @GetMapping("/getDistinctClazz")
    @ResponseBody
    public Object getDistinctClazz() {
        return clazzService.getDistinctMajorOrClazz("clazz");
    }

    @GetMapping("/findAllMajorAndClazz")
    @ResponseBody
    public Object findAllMajorAndClazz() {
        return clazzService.findAllMajorClazz();
    }

    @GetMapping("/save")
    @ResponseBody
    public Object save(Clazz clazz) {
        int result = clazzService.saveClazz(clazz);
        return result != 0;
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] clazzId, Integer pageno, Integer size) {
        int result = clazzService.deleteClazz(clazzId);

        ModelAndView mv = new ModelAndView();
        if (result != 0) {
            mv.addObject("pageno", pageno);
            mv.addObject("size", size);
            mv.setViewName("forward:/clazz/findAll");
        }
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer clazzId) {
        return clazzService.findById(clazzId);
    }
}