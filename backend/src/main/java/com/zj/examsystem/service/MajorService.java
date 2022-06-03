package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Major;
import com.zj.examsystem.entity.Major;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface MajorService extends IService<Major> {
    public Object findAll(Integer pageno, Integer size);

    public Major findById(Integer majorId);

    public Integer saveMajor(Major major);

    public Integer deleteMajor(Integer[] id);
}
