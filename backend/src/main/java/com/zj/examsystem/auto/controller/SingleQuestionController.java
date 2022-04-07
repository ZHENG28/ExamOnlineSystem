package com.zj.examsystem.auto.controller;


import com.zj.examsystem.repository.SubjectRepository;
import com.zj.examsystem.serviceImpl.SingleQuesServiceImpl1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Controller
@RequestMapping("/auto/singleQuestion")
public class SingleQuestionController {

    @Autowired
    private SingleQuesServiceImpl1 singleQuesService;

    @Autowired
    private SubjectRepository subjectRepo;

    @PostMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size)
    {
        return singleQuesService.findAll(pageno, size);
    }
}

