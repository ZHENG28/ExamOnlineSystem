package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.Major;
import com.zj.examsystem.mapper.MajorMapper;
import com.zj.examsystem.service.MajorService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {
    @Resource
    private MajorMapper majorMapper;

    @Override
    public Object findAll(Integer pageno, Integer size) {
        if (pageno != null && size != null) {
            IPage<Major> page = new Page<>(pageno, size);
            return majorMapper.selectPage(page, null);
        } else {
            return majorMapper.selectList(null);
        }
    }

    @Override
    public Major findById(Integer majorId) {
        return majorMapper.selectById(majorId);
    }

    @Override
    public Integer saveMajor(Major major) {
        try {
            return major.getMajorId() != null ? majorMapper.updateById(major) : majorMapper.insert(major);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                return 0;
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public Integer deleteMajor(Integer[] id) {
        List<Integer> ids = new ArrayList<>(Arrays.asList(id));
        return majorMapper.deleteBatchIds(ids);
    }
}
