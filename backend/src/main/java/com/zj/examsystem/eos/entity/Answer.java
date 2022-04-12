package com.zj.examsystem.eos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;


public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "answer_id", type = IdType.AUTO)
    private Integer answerId;

    private String answerSign;

    private String content;

    /**
     * 是否是正确答案，1-是
     */
    private Integer isCorrent;

    private Integer questionId;


    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswerSign() {
        return answerSign;
    }

    public void setAnswerSign(String answerSign) {
        this.answerSign = answerSign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsCorrent() {
        return isCorrent;
    }

    public void setIsCorrent(Integer isCorrent) {
        this.isCorrent = isCorrent;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Answer{" +
        "answerId=" + answerId +
        ", answerSign=" + answerSign +
        ", content=" + content +
        ", isCorrent=" + isCorrent +
        ", questionId=" + questionId +
        "}";
    }
}
