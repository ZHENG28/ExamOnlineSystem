package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Clazz implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "clazz_id", type = IdType.AUTO)
    private Integer clazzId;

    private String clazzName;

    private String major;



    @Override
    public String toString() {
        return "Clazz{" +
        "clazzId=" + clazzId +
        ", clazzName=" + clazzName +
        ", major=" + major +
        "}";
    }
}
