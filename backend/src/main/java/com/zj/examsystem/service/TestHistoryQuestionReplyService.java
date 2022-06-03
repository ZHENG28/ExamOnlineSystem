package com.zj.examsystem.service;

import com.zj.examsystem.entity.TestHistoryQuestionReply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface TestHistoryQuestionReplyService extends IService<TestHistoryQuestionReply> {
    public List<TestHistoryQuestionReply> findTestHistoryReplyByHistoryId(Integer historyId);
}
