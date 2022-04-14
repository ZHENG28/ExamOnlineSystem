package com.zj.examsystem.service;

import com.zj.examsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserService extends IService<User> {

    public User findUserByAccount(String account);
}
