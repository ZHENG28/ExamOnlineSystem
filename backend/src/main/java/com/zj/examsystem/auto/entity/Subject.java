package com.zj.examsystem.auto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sub_id", type = IdType.AUTO)
    private Integer subId;

    private String subName;

    private Integer teacherId;

    @TableField(select = false)
    private String tchName;
}
