package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.CompareShortAnswer;
import com.zj.examsystem.mapper.CompareShortAnswerMapper;
import com.zj.examsystem.mapper.TestHistoryMapper;
import com.zj.examsystem.service.CompareShortAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class CompareShortAnswerServiceImpl extends ServiceImpl<CompareShortAnswerMapper, CompareShortAnswer> implements CompareShortAnswerService {

    @Autowired
    private CompareShortAnswerMapper compareShortAnswerMapper;

    @Autowired
    private TestHistoryMapper testHistoryMapper;

    @Override
    public IPage<CompareShortAnswer> loadCompareText(Integer pageno, Integer size, Integer testId) {
        return null;
    }
}
