package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShortAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "short_answer_id", type = IdType.AUTO)
    private Integer shortAnswerId;

    private Integer threshold;

    private Integer questionId;

    private Integer testId;
}
