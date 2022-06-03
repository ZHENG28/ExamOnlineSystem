package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "test_id", type = IdType.AUTO)
    private Integer testId;

    private String testName;

    private String description;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    /**
     * 测验时长
     */
    private Integer examDuration;

    /**
     * 测验次数
     */
    private Integer examTime;

    /**
     * 题目数量
     */
    private Integer questionTotal;

    private Integer subjectId;

    @TableField(select = false)
    private String subjectName;

    @TableField(select = false)
    private Integer clazzId;

    @TableField(select = false)
    private String clazzName;
}
