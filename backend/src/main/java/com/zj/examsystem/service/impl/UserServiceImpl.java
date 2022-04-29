package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.User;
import com.zj.examsystem.entity.UserClazz;
import com.zj.examsystem.mapper.UserClazzMapper;
import com.zj.examsystem.mapper.UserMapper;
import com.zj.examsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("all")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserClazzMapper userClazzMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findInfoById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public ResponseEntity<?> saveUserWithClazzId(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", user.getAccount());
        if (userMapper.selectOne(queryWrapper) != null) { // account is exist
            return ResponseEntity
                    .badRequest()
                    .body(new String("账号已存在，请重新填写"));
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (userMapper.insertUser(user) == 1) { // success insert
                User userWithId = userMapper.selectOne(queryWrapper);
                UserClazz userClazz = new UserClazz(userWithId.getUserId(), user.getClazzId());
                return ResponseEntity
                        .ok(userClazzMapper.insert(userClazz) != 0);
            } else {
                return ResponseEntity
                        .ok(false);
            }
        }
    }

    @Override
    public ResponseEntity<?> updateUser(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getClazzId() != null) {
            UserClazz userClazz = new UserClazz(user.getUserId(), user.getClazzId());
            user.setClazzId(null);
            if (userMapper.updateById(user) == 1) {
                QueryWrapper<UserClazz> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id", user.getUserId());
                return ResponseEntity
                        .ok(userClazzMapper.update(userClazz, queryWrapper) != 0);
            }
        }
        return ResponseEntity.ok(user.getClazzId() == null);
    }

    @Override
    public IPage<User> findAll(Integer pageno, Integer size) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> page = new Page<>(pageno, size);
        return userMapper.selectPageWithRoleAndClazzName(page, queryWrapper);
    }

    @Override
    public Integer deleteUser(String[] account) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < account.length; i++) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", account[i]);
            User user = userMapper.selectOne(queryWrapper);
            ids.add(user.getUserId());
        }
        return userMapper.deleteBatchIds(ids);
    }

    @Override
    public User findByAccount(String account) {
        return userMapper.selectOneWithRoleAndClazzName(account);
    }

    @Override
    public List<User> findAllTeacher() {
        return userMapper.selectTeacher();
    }
}
