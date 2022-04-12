package com.zj.examsystem.auto.controller;


import com.zj.examsystem.auto.service.ClazzService;
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
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Controller
@RequestMapping("/auto/clazz")
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
            mv.setViewName("forward:/auto/clazz/findAll");
        }
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer clazzId) {
        return clazzService.findById(clazzId);
    }
}

