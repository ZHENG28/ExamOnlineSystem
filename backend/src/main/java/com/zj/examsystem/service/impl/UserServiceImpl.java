package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.*;
import com.zj.examsystem.mapper.*;
import com.zj.examsystem.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserClazzMapper userClazzMapper;

    @Resource
    private UserMajorMapper userMajorMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Boolean saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            switch (user.getRoleId()) {
                case 1:
                    return userMapper.insert(user) == 1;
                case 2: // teacher
                    Integer majorId = user.getMajorId();
                    user.setMajorId(null);
                    if (userMapper.insert(user) == 1) {
                        UserMajor userMajor = new UserMajor(user.getUserId(), majorId);
                        return userMajorMapper.insert(userMajor) != 0;
                    }
                case 3: // student
                    Integer clazzId = user.getClazzId();
                    user.setClazzId(null);
                    if (userMapper.insert(user) == 1) {
                        UserClazz userClazz = new UserClazz(user.getUserId(), clazzId);
                        return userClazzMapper.insert(userClazz) != 0;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                return false;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateUser(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        try {
            switch (user.getRoleId()) {
                case 1:
                    return userMapper.updateById(user) == 1;
                case 2: // teacher
                    Integer majorId = user.getMajorId();
                    user.setMajorId(null);
                    if (userMapper.updateById(user) == 1) {
                        UserMajor userMajor = new UserMajor(user.getUserId(), majorId);
                        QueryWrapper<UserMajor> userMajorQueryWrapper = new QueryWrapper<>();
                        userMajorQueryWrapper.eq("teacher_id", user.getUserId());
                        return userMajorMapper.update(userMajor, userMajorQueryWrapper) != 0;
                    }
                case 3: // student
                    Integer clazzId = user.getClazzId();
                    user.setClazzId(null);
                    if (userMapper.updateById(user) == 1) {
                        UserClazz userClazz = new UserClazz(user.getUserId(), clazzId);
                        QueryWrapper<UserClazz> userClazzQueryWrapper = new QueryWrapper<>();
                        userClazzQueryWrapper.eq("student_id", user.getUserId());
                        return userClazzMapper.update(userClazz, userClazzQueryWrapper) != 0;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Integer deleteUser(Integer[] userId) {
        List<Integer> ids = new ArrayList<>(Arrays.asList(userId));
        return userMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<User> findAll(Integer pageno, Integer size) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> page = new Page<>(pageno, size);
        return userMapper.selectPageWithRole(page, queryWrapper);
    }

    @Override
    public User findById(Integer userId) {
        User user = userMapper.findByIdWithRole(userId);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        switch (user.getRoleId()) {
            case 1:
                return user;
            case 2: // teacher
                List<User> teacherList = userMapper.selectTeacherWithRoleAndClazz(queryWrapper);
                if (!teacherList.isEmpty()) {
                    return teacherList.get(0);
                } else {
                    break;
                }
            case 3: // student
                List<User> studentList = userMapper.selectStudentWithRoleAndClazz(queryWrapper);
                if (!studentList.isEmpty()) {
                    return studentList.get(0);
                } else {
                    break;
                }
        }
        return null;
    }

    @Override
    public List<User> findTeacherByMajorId(Integer majorId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("major_id", majorId);
        return userMapper.selectTeacherWithRoleAndClazz(queryWrapper);
    }

    @Override
    public List<User> findStudentByClazzId(Integer clazzId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("clazz_id", clazzId);
        return userMapper.selectStudentWithRoleAndClazz(queryWrapper);
    }

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private TestMapper testMapper;

    @Resource
    private TestHistoryMapper testHistoryMapper;

    @Resource
    private ClazzMapper clazzMapper;

    @Override
    public Map<String, Object> loadAdminData(Integer userId) {
        Map<String, Object> result = new HashMap<>();

        // clazz total
        result.put("clazz", clazzMapper.selectCount(null));

        // user total
        result.put("user", userMapper.selectCount(null));

        // subject total
        result.put("subject", subjectMapper.selectCount(null));

        // login total: []

        return result;
    }

    @Override
    public Map<String, Object> loadTeacherData(Integer userId) {
        Map<String, Object> result = new HashMap<>();

        // subject total
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("teacher_id", userId);
        result.put("subject", subjectMapper.selectCount(subjectQueryWrapper));

        // question total
        result.put("question", questionMapper.countByTeacherId(userId));

        // test total
        result.put("test", testMapper.countByTeacherId(userId));

        // test history: []

        return result;
    }

    @Override
    public Map<String, Object> loadStudentData(Integer userId) {
        Map<String, Object> result = new HashMap<>();

        // not complete total
        List<Test> finishList = testMapper.countNotFinishByUserId(userId);
        int complete = 0;
        for (Test test : finishList) {
            complete += testHistoryMapper.findTestInfoByCompoundId(test.getTestId(), userId) == null ? 1 : 0;
        }
        result.put("complete", complete);

        List<Float> correctList = testHistoryMapper.findCorrectnessByStudentId(userId);
        // average correctness
        Float sum = 0.0f;
        for (Float f : correctList) {
            sum += f;
        }
        result.put("average", sum / correctList.size());

        // max correctness
        result.put("max", correctList.get(0));

        // test history: []

        return result;
    }
}
