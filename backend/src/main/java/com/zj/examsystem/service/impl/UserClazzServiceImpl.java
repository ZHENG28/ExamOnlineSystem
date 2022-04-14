package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zj.examsystem.entity.User;
import com.zj.examsystem.entity.UserClazz;
import com.zj.examsystem.mapper.UserClazzMapper;
import com.zj.examsystem.mapper.UserMapper;
import com.zj.examsystem.service.UserClazzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("all")
public class UserClazzServiceImpl extends ServiceImpl<UserClazzMapper, UserClazz> implements UserClazzService {
    @Autowired
    private UserClazzMapper userClazzMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findUserByClazzId(Integer clazzId) {
        QueryWrapper<UserClazz> userClazzQueryWrapper = new QueryWrapper<>();
        userClazzQueryWrapper.eq("clazz_id", clazzId);
        List<UserClazz> userClazzList = userClazzMapper.selectList(userClazzQueryWrapper);
        List<User> userList = new ArrayList<>();
        for (UserClazz userClazz : userClazzList) {
            userList.add(userMapper.selectById(userClazz.getUserId()));
        }
        return userList;
    }
}
