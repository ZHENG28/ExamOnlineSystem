package com.zj.examsystem.eos.service.impl;

import com.zj.examsystem.eos.entity.User;
import com.zj.examsystem.eos.mapper.UserMapper;
import com.zj.examsystem.eos.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
