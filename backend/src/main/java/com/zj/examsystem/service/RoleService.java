package com.zj.examsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> findAll();
}
