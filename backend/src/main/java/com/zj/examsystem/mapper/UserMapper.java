package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zj.examsystem.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    IPage<User> selectPageWithRole(IPage<User> page, @Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);

    User findByIdWithRole(Integer userId);

    List<User> selectTeacherWithRoleAndClazz(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);

    List<User> selectStudentWithRoleAndClazz(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);

    List<Integer> selectUserIdsByTestId(Integer testId);
}
