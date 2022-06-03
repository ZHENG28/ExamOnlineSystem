package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TestQuestionList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "test_id")
    private Integer testId;

    private Integer questionId;
}
