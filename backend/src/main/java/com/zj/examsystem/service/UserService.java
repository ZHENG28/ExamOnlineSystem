package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    Boolean saveUser(User user);

    Boolean updateUser(User user);

    Integer deleteUser(Integer[] userId);

    IPage<User> findAll(Integer pageno, Integer size);

    User findById(Integer userId);

    List<User> findTeacherByMajorId(Integer majorId);

    List<User> findStudentByClazzId(Integer clazzId);

    Map<String, Object> loadAdminData(Integer userId);

    Map<String, Object> loadTeacherData(Integer userId);

    Map<String, Object> loadStudentData(Integer userId);
}
