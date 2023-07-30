package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.UserClazz;
import com.zj.examsystem.mapper.UserClazzMapper;
import com.zj.examsystem.service.UserClazzService;
import org.springframework.stereotype.Service;

@Service
public class UserClazzServiceImpl extends ServiceImpl<UserClazzMapper, UserClazz> implements UserClazzService {
}
