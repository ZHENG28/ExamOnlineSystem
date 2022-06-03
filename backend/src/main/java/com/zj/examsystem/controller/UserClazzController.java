package com.zj.examsystem.controller;


import com.zj.examsystem.service.UserClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/userClazz")
public class UserClazzController {
    @Autowired
    private UserClazzService userClazzService;
}

