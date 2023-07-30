package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "question_id", type = IdType.AUTO)
    private Integer questionId;

    private String questionTitle;

    private Float questionDifficulty;

    private Integer subjectId;

    private Integer typeId;

    @NotBlank
    private Integer knowledgeId;

    @TableField(select = false)
    private String subjectName;

    @TableField(select = false)
    private String typeName;

    @TableField(select = false)
    private List<Answer> answer;

    @TableField(select = false)
    private String correct;

    @TableField(select = false)
    private String knowledgeContent;
}
