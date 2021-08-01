package com.zj.examsystem.config.security;

import com.zj.examsystem.model.Manager;
import com.zj.examsystem.model.Student;
import com.zj.examsystem.model.Teacher;
import com.zj.examsystem.repository.ManagerRepository;
import com.zj.examsystem.repository.StudentRepository;
import com.zj.examsystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    StudentRepository studentRepo;

    @Autowired
    TeacherRepository teacherRepo;

    @Autowired
    ManagerRepository managerRepo;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException
    {
        String[] str = account.split(" ");
        Integer identity = null;
        account = str[1];
        identity = Integer.valueOf(str[0]);
        List<GrantedAuthority> authList = new ArrayList<>();
        if (StringUtils.hasText(account)) {
            switch (identity) {
                case 1: // stu
                    Optional<Student> stu = studentRepo.findByAccount(account);
                    if (stu.isPresent()) {
                        authList.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
                        return new User(account, stu.get().getPassword(), authList);
                    } else {
                        throw new RuntimeException("学生账户【" + account + "】无法查找到");
                    }
                case 2: // tch
                    Optional<Teacher> tch = teacherRepo.findByAccount(account);
                    if (tch.isPresent()) {
                        authList.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
                        return new User(account, tch.get().getPassword(), authList);
                    } else {
                        throw new RuntimeException("教师账户【" + account + "】无法查找到");
                    }
                case 3: // mgr
                    Optional<Manager> mgr = managerRepo.findByAccount(account);
                    if (mgr.isPresent()) {
                        authList.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                        return new User(account, mgr.get().getPassword(), authList);
                    } else {
                        throw new RuntimeException("管理员账户【" + account + "】无法查找到");
                    }
            }
        } else {
            throw new RuntimeException("com.zj.examsystem.config.security.CustomUserDetailsService -> account is null");
        }
        return null;
    }
}
