package com.zj.examsystem.controller;


import com.zj.examsystem.service.RoleService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/findAll")
    @ResponseBody
    public Object findAll() {
        return BaseResponseEntity.ok("", roleService.findAll());
    }
}

