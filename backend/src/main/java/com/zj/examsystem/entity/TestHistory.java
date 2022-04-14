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

    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId;

    private Integer testId;

    private LocalDateTime finishTime;

    private Integer score;

    @TableField(select = false)
    private String testName;

    @TableField(select = false)
    private String description;

    @TableField(select = false)
    private String subName;

    @Override
    public String toString() {
        return "TestHistory{" +
        "studentId=" + studentId +
        ", testId=" + testId +
        ", finishTime=" + finishTime +
        ", score=" + score +
        "}";
    }
}
