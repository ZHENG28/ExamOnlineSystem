package com.zj.examsystem.controller;

import com.zj.examsystem.entity.Role;
import com.zj.examsystem.service.RoleService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/findAll")
    @ResponseBody
    public BaseResponseEntity<List<Role>> findAll() {
        return BaseResponseEntity.ok("", roleService.findAll());
    }
}

