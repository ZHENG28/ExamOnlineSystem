package com.zj.examsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.TestHistoryQuestionReply;

import java.util.List;

public interface TestHistoryQuestionReplyService extends IService<TestHistoryQuestionReply> {
    List<TestHistoryQuestionReply> findTestHistoryReplyByHistoryId(Integer historyId);
}
