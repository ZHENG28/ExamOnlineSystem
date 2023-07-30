package com.zj.examsystem.config.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zj.examsystem.entity.User;
import com.zj.examsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("all")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        String[] str = account.split(" ");
        Integer identity = Integer.valueOf(str[0]);
        account = str[1];
        List<GrantedAuthority> authList = new ArrayList<>();
        if (StringUtils.hasText(account)) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", account);
            queryWrapper.eq("role_id", identity);
            User user = userMapper.selectOne(queryWrapper);
            if (user != null) {
                switch (identity) {
                    case 1: // admin
                        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                        break;
                    case 2: // teacher
                        authList.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
                        break;
                    case 3: // student
                        authList.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
                        break;
                }
                user.setAuthorities(authList);
                return user;
            }
        }
        throw new UsernameNotFoundException("查找失败，不存在与身份相匹配的用户名");
    }
}