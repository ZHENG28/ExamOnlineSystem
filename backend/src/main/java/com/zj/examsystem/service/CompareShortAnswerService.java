package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.CompareShortAnswer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.TestHistory;


public interface CompareShortAnswerService extends IService<CompareShortAnswer> {
    public IPage<CompareShortAnswer> loadCompareTextData(Integer pageno, Integer size, Integer testId);

    public CompareShortAnswer findById(Integer compareId, Integer threshold);

    public CompareShortAnswer getCompareList(CompareShortAnswer compareShortAnswer, Integer threshold);

    public Boolean compareReplyWithOther(TestHistory testHistory);
}
