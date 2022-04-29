package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "history_id", type = IdType.AUTO)
    private Integer historyId;

    private Integer correct;

    private LocalDateTime finishTime;

    private Integer takeTime;

    private String reply;

    private Integer studentId;

    @TableField(select = false)
    private String studentName;

    private Integer testId;

    @TableField(select = false)
    private String testName;

    @TableField(select = false)
    private String description;

    @TableField(select = false)
    private String subjectName;
}
