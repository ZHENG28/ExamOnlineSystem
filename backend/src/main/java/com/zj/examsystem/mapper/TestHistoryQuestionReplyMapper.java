package com.zj.examsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.examsystem.entity.TestHistoryQuestionReply;

import java.util.List;
import java.util.Map;

public interface TestHistoryQuestionReplyMapper extends BaseMapper<TestHistoryQuestionReply> {
    List<Map<String, Object>> findReplyByHistoryIdWithCorrect(Integer historyId);
}
