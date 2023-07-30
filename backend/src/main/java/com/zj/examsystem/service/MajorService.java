package com.zj.examsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.Major;

public interface MajorService extends IService<Major> {
    Object findAll(Integer pageno, Integer size);

    Major findById(Integer majorId);

    Integer saveMajor(Major major);

    Integer deleteMajor(Integer[] id);
}
