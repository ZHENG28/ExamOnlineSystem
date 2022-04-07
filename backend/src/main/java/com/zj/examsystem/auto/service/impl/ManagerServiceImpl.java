package com.zj.examsystem.auto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zj.examsystem.auto.entity.Manager;
import com.zj.examsystem.auto.mapper.ManagerMapper;
import com.zj.examsystem.auto.service.ManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Service
@SuppressWarnings("all")
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

//    public void findByAccount(String account){
//        // WHERE account = ?
//        QueryWrapper<Manager> wrapper = new QueryWrapper<>();
//        wrapper.eq("account",account);
//        managerMapper.selectMaps(wrapper);
//    }

}
