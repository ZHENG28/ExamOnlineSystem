package com.zj.examsystem.service;

import com.zj.examsystem.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface RoleService extends IService<Role> {
    public List<Role> findAll();
}
