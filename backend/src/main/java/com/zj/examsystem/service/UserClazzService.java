package com.zj.examsystem.service;

import com.zj.examsystem.entity.User;
import com.zj.examsystem.entity.UserClazz;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface UserClazzService extends IService<UserClazz> {

    public List<User> findUserByClazzId(Integer clazzId);
}
