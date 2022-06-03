package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    public Boolean saveUser(User user);

    public Boolean updateUser(User user);

    public Integer deleteUser(Integer[] userId);

    public IPage<User> findAll(Integer pageno, Integer size);

    public User findById(Integer userId);

    public List<User> findTeacherByMajorId(Integer majorId);

    public List<User> findStudentByClazzId(Integer clazzId);

    public Map<String, Object> loadAdminData(Integer userId);

    public Map<String, Object> loadTeacherData(Integer userId);

    public Map<String, Object> loadStudentData(Integer userId);
}
