package com.zj.examsystem.auto.controller;


import com.zj.examsystem.auto.service.impl.ClazzServiceImpl;
import com.zj.examsystem.auto.entity.Clazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Controller
@RequestMapping("/auto/clazz")
public class ClazzController {

    @Autowired
    private ClazzServiceImpl clazzService;

//    @PostMapping("/findAll")
//    @ResponseBody
//    public Object findAll(Integer pageno, Integer size)
//    {
//        return clazzService.findAll(pageno, size);
//    }

    @GetMapping("/add")
    @ResponseBody
    public String add(Clazz clazz)
    {
        int result = clazzService.addClazz(clazz);
        System.out.println(clazz);
//        if (c == null) {
//            return "failed";
//        }
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
        int c = clazzService.modify(clazz);
//        if (c == null) {
//            return "failed";
//        }
        return "success";
    }
}

