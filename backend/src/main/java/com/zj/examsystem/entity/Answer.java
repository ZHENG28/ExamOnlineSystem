package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "answer_id", type = IdType.AUTO)
    private Integer answerId;

    /**
     * ABC或者1-对
     */
    private String answerSign;

    private String content;

    /**
     * 是否是正确答案，1-是
     */
    private Integer isCorrect;

    private Integer questionId;

    // choice question
    public Answer(String answerSign, String content, Integer isCorrect, Integer questionId) {
        this.answerSign = answerSign;
        this.content = content;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    // judge question
    public Answer(String answerSign, Integer isCorrect, Integer questionId) {
        this.answerSign = answerSign;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }
}
