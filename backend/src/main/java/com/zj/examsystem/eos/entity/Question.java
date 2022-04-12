package com.zj.examsystem.eos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2022-04-12
 */
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "question_id", type = IdType.AUTO)
    private Integer questionId;

    private String questionTitle;

    private Integer subjectId;

    private Integer typeId;


    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Question{" +
        "questionId=" + questionId +
        ", questionTitle=" + questionTitle +
        ", subjectId=" + subjectId +
        ", typeId=" + typeId +
        "}";
    }
}
