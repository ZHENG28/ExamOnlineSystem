package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

@Data
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "test_id", type = IdType.AUTO)
    private Integer testId;

    private String testName;

    private String description;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    private Integer examDuration;

    private Integer examTime;

    private String questionList;

    private Integer questionTotal;

    private Integer subjectId;

    private Integer clazzId;

    @TableField(select = false)
    private String subjectName;

    @TableField(select = false)
    private String major;

    @TableField(select = false)
    private String clazzName;

    @TableField(select = false)
    private String shortAnswer;
}
