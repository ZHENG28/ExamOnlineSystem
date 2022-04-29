package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.CompareShortAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CompareShortAnswerService extends IService<CompareShortAnswer> {

    IPage<CompareShortAnswer> loadCompareText(Integer pageno, Integer size, Integer testId);
}
