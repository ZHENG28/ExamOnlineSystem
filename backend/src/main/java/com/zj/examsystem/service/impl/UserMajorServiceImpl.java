package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.UserMajor;
import com.zj.examsystem.mapper.UserMajorMapper;
import com.zj.examsystem.service.UserMajorService;
import org.springframework.stereotype.Service;

@Service
public class UserMajorServiceImpl extends ServiceImpl<UserMajorMapper, UserMajor> implements UserMajorService {
}
