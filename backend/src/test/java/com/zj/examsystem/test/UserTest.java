package com.zj.examsystem.test;

import com.zj.examsystem.entity.User;
import com.zj.examsystem.service.UserService;
import com.zj.examsystem.utils.AutoNameUtil;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

public class UserTest extends BaseSpringBootTest {
    @Resource
    private UserService userService;

    @Test
    public void insertTeacher() {
        int num = 10;
        Integer majorId = 3;
        for (int i = 0; i < num; i++) {
            User user = new User();
            user.setAccount(AutoNameUtil.getEnglishName());
            user.setUsername(AutoNameUtil.getChineseName());
            user.setPassword("123");
            user.setRoleId(2);
            user.setMajorId(majorId);
            userService.saveUser(user);
        }
    }

    @Test
    public void insertStudent() {
        int num = 50;
        Integer[] clazzIds = new Integer[]{23, 24, 26, 27, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
        for (Integer clazzId : clazzIds) {
            for (int i = 0; i < num; i++) {
                User user = new User();
                user.setAccount(AutoNameUtil.getEnglishName());
                user.setUsername(AutoNameUtil.getChineseName());
                user.setPassword("123");
                user.setRoleId(3);
                user.setClazzId(clazzId);
                userService.saveUser(user);
            }
        }
    }
}
