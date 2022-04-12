package com.zj.examsystem.auto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class SingleQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ques_id", type = IdType.AUTO)
    private Integer quesId;

    private String answer;

    private String opta;

    private String optb;

    private String optc;

    private String optd;

    private String quesTitle;

    private Integer subId;

    @TableField(select = false)
    private String subName;
}
