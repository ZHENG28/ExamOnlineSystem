package com.zj.examsystem.service.impl;

import com.zj.examsystem.entity.UserMajor;
import com.zj.examsystem.mapper.UserMajorMapper;
import com.zj.examsystem.service.UserMajorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@SuppressWarnings("all")
public class UserMajorServiceImpl extends ServiceImpl<UserMajorMapper, UserMajor> implements UserMajorService {
    @Autowired
    private UserMajorMapper userMajorMapper;
}
