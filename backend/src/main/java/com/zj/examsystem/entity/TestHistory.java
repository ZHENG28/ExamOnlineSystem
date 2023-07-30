package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TestHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "history_id", type = IdType.AUTO)
    private Integer historyId;

    private Integer correct;

    /**
     * 完成测验的结束时间
     */
    private LocalDateTime finishTime;

    /**
     * 完成测验的时长（秒）
     */
    private Integer takeTime;

    private Integer testId;

    private Integer studentId;

    @TableField(select = false)
    private String studentName;

    @TableField(select = false)
    private String testName;

    @TableField(select = false)
    private String description;

    @TableField(select = false)
    private String subjectName;
}
