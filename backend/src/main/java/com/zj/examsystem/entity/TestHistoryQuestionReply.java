package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TestHistoryQuestionReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "reply_id", type = IdType.AUTO)
    private Integer replyId;

    private Integer historyId;

    private Integer questionId;

    private String reply;

    public TestHistoryQuestionReply(Integer historyId, Integer questionId, String reply) {
        this.historyId = historyId;
        this.questionId = questionId;
        this.reply = reply;
    }
}
