package com.zj.examsystem.auto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sub_id", type = IdType.AUTO)
    private Integer subId;

    private String subName;

    private Integer teacherId;


    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Subject{" +
        "subId=" + subId +
        ", subName=" + subName +
        ", teacherId=" + teacherId +
        "}";
    }
}
