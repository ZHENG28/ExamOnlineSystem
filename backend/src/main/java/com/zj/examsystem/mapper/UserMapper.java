package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


public interface UserMapper extends BaseMapper<User> {
    public Integer insertUser(@Param("user") User user);

    public Integer updateUser(@Param("user") User user);

    IPage<User> selectPageWithRoleAndClazzName(IPage<User> page, QueryWrapper<User> queryWrapper);

    User selectOneWithRoleAndClazzName(String account);
}
