package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;

@Data
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "test_id", type = IdType.AUTO)
    private Integer testId;

    private LocalDateTime beginDate;

    private String description;

    private LocalDateTime endDate;

    private Integer examDure;

    private Integer examTime;

    private String quesArr;

    private String testName;

    private Integer totalScore;

    private Integer subjectId;

    private Integer clazzId;

    @TableField(select = false)
    private String subjectName;

    @TableField(select = false)
    private String major;

    @TableField(select = false)
    private String clazzName;
}
