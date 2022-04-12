package com.zj.examsystem.eos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "question_id", type = IdType.AUTO)
    private Integer questionId;

    private String questionTitle;

    private Integer subjectId;

    private Integer typeId;

    @TableField(select = false)
    private String subName;

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
