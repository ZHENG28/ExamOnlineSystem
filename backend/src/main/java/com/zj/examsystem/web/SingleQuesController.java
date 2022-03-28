package com.zj.examsystem.web;

import com.zj.examsystem.model.SingleQuestion;
import com.zj.examsystem.serviceImpl.SingleQuesServiceImpl;
import com.zj.examsystem.serviceImpl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/singleQues")
public class SingleQuesController
{
    @Autowired
    private SingleQuesServiceImpl singleQuesService;

    @Autowired
    private SubjectServiceImpl subjectService;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size)
    {
        return singleQuesService.findAll(pageno, size);
    }

    @PostMapping("/findAllSubId")
    @ResponseBody
    public Object findAllSubId()
    {
        return subjectService.findAllSubject();
    }

    @GetMapping("/add")
    @ResponseBody
    public String add(SingleQuestion singleQues)
    {
        SingleQuestion sq = singleQuesService.addSingleQues(singleQues);
        if (sq == null) {
            return "failed";
        }
        return "success";
    }

    @PostMapping("/del")
    public ModelAndView delete(Integer[] quesId, Integer pageno, Integer size)
    {
        singleQuesService.deleteSingleQues(quesId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageno", pageno);
        mv.addObject("size", size);
        mv.setViewName("forward:/singleQues/findAll");
        return mv;
    }

    @PostMapping("/findById")
    @ResponseBody
    public Object findById(Integer quesId)
    {
        return singleQuesService.findById(quesId);
    }

    @PostMapping("/modify")
    @ResponseBody
    public String modify(SingleQuestion singleQues)
    {
        SingleQuestion sq = singleQuesService.modifySingleQues(singleQues);
        if (sq == null) {
            return "failed";
        }
        return "success";
    }
}
