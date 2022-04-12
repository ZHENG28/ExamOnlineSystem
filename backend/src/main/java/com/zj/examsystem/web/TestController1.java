package com.zj.examsystem.web;

import com.zj.examsystem.serviceImpl.TestHistoryServiceImpl1;
import com.zj.examsystem.serviceImpl.TestServiceImpl1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController1 {
    @Autowired
    private TestServiceImpl1 testService;

//    @Autowired
//    private ClazzServiceImpl1 clazzService;

//    @Autowired
//    private SubjectServiceImpl1 subjectService;
//
//    @Autowired
//    private SingleQuesServiceImpl1 singleQuesService;

    @Autowired
    private TestHistoryServiceImpl1 testHistoryService;

    //    // testBuild
    //    @PostMapping("/findAllMajorAndClazz")
    //    @ResponseBody
    //    public Object findAllMajorAndClazz()
    //    {
    //        return clazzService.findAllMajorClazz();
    //    }
    //
    //    @PostMapping("/findAllSubId")
    //    @ResponseBody
    //    public Object findAllSubId()
    //    {
    //        return subjectService.findAllSubject();
    //    }
    //
    //    @PostMapping("/findQuesBySubId")
    //    @ResponseBody
    //    public Object findQuesBySubId(Integer subId)
    //    {
    //        return singleQuesService.findQuesBySubId(subId);
    //    }
    //
    //    @GetMapping("/add")
    //    @ResponseBody
    //    public String add(Test test)
    //    {
    //        Test t = testService.addTest(test);
    //        if (t == null) {
    //            return "failed";
    //        }
    //        return "success";
    //    }
    //
    //    @PostMapping("/findById")
    //    @ResponseBody
    //    public Object findById(Integer testId)
    //    {
    //        return testService.findById(testId);
    //    }
    //
    //    @PostMapping("/modify")
    //    @ResponseBody
    //    public String modify(Test test)
    //    {
    //        Test t = testService.modifyTest(test);
    //        if (t == null) {
    //            return "failed";
    //        }
    //        return "success";
    //    }
    //
    //    // testInfo
    //    @PostMapping("/findAllClazz")
    //    @ResponseBody
    //    public Object findAllClazz()
    //    {
    //        return clazzService.findAllMajorAndClazz("clazz");
    //    }
    //
    //    @PostMapping("/findAllByAccount")
    //    @ResponseBody
    //    public Object findAll(String account, Integer pageno, Integer size)
    //    {
    //        return testService.findAll(account, pageno, size);
    //    }
    //
    //    @PostMapping("/findAll")
    //    @ResponseBody
    //    public Object findAll(Integer pageno, Integer size)
    //    {
    //        return testService.findAll("", pageno, size);
    //    }
    //
    //    @PostMapping("/del")
    //    public ModelAndView delete(Integer[] testId, Integer pageno, Integer size)
    //    {
    //        testService.deleteTest(testId);
    //        ModelAndView mv = new ModelAndView();
    //        mv.addObject("pageno", pageno);
    //        mv.addObject("size", size);
    //        mv.setViewName("forward:/test/findAll");
    //        return mv;
    //    }

    // testPaper
    @PostMapping("/findQuesArrByTestId")
    @ResponseBody
    public Object findQuesArrByTestId(Integer testId) {
        return testService.findQuesArrByTestId(testId);
    }

    //    @PostMapping("/findScoreByTestId")
    //    @ResponseBody
    //    public Object findScoreByTestId(Integer testId)
    //    {
    //        return testHistoryService.findScoreByTestId(testId);
    //    }

    //    @PostMapping("/findExamTimeByTestId")
    //    @ResponseBody
    //    public Object findExamTimeByTestId(String account, Integer testId)
    //    {
    //        return testService.findExamTimeByTestId(account, testId);
    //    }

}
