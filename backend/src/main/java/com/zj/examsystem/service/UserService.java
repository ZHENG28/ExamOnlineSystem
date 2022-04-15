package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService extends IService<User> {

    public User findInfoById(Integer userId);

    public ResponseEntity<?> saveUserWithClazzId(User user);

    public ResponseEntity<?> updateUser(User user);

    public IPage<User> findAll(Integer pageno, Integer size);

    public Integer deleteUser(String[] account);

    public User findByAccount(String account);

    public List<User> findAllTeacher();
}
