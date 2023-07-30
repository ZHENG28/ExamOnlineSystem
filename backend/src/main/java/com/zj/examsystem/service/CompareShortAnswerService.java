package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.CompareShortAnswer;
import com.zj.examsystem.entity.TestHistory;

public interface CompareShortAnswerService extends IService<CompareShortAnswer> {
    IPage<CompareShortAnswer> loadCompareTextData(Integer pageno, Integer size, Integer testId);

    CompareShortAnswer findById(Integer compareId, Integer threshold);

    CompareShortAnswer getCompareList(CompareShortAnswer compareShortAnswer, Integer threshold);

    Boolean compareReplyWithOther(TestHistory testHistory);
}
