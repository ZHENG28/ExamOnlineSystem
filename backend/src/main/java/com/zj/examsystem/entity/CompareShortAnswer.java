package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CompareShortAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "compare_id", type = IdType.AUTO)
    private Integer compareId;

    private Float compareThreshold;

    private Integer shortAnswerId;

    private Integer userId;

    @TableField(select = false)
    private String username;

    private Integer comparedUserId;

    @TableField(select = false)
    private String comparedUsername;
}
