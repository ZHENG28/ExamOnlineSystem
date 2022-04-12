package com.zj.examsystem.auto.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Testhistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDateTime finishDate;

    private Integer score;

    private Integer stuId;

    private Integer testId;

    @TableField(select = false)
    private String testName;

    @TableField(select = false)
    private String description;

    @TableField(select = false)
    private String subName;
}
