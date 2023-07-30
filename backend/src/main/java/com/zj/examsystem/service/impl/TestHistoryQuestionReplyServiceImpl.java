package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.TestHistoryQuestionReply;
import com.zj.examsystem.mapper.TestHistoryQuestionReplyMapper;
import com.zj.examsystem.service.TestHistoryQuestionReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestHistoryQuestionReplyServiceImpl extends ServiceImpl<TestHistoryQuestionReplyMapper, TestHistoryQuestionReply> implements TestHistoryQuestionReplyService {
    @Resource
    private TestHistoryQuestionReplyMapper testHistoryQuestionReplyMapper;

    @Override
    public List<TestHistoryQuestionReply> findTestHistoryReplyByHistoryId(Integer historyId) {
        QueryWrapper<TestHistoryQuestionReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("history_id", historyId);
        return testHistoryQuestionReplyMapper.selectList(queryWrapper);
    }
}
