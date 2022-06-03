package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.Role;
import com.zj.examsystem.mapper.RoleMapper;
import com.zj.examsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@SuppressWarnings("all")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.selectList(null);
    }

    @Override
    public List<Map<String, Object>> getDistinctRole() {
        List<Role> list = roleMapper.selectList(null);
        List<Map<String, Object>> results = new ArrayList<>();
        for (Role role : list) {
            Map<String, Object> result = new HashMap<>();
            result.put("text", role.getDescription());
            result.put("value", role.getDescription());
            results.add(result);
        }
        return results;
    }
}
