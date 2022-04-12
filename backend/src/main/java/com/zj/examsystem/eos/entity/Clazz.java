package com.zj.examsystem.eos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2022-04-12
 */
public class Clazz implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "clazz_id", type = IdType.AUTO)
    private Integer clazzId;

    private String clazzName;

    private String major;


    public Integer getClazzId() {
        return clazzId;
    }

    public void setClazzId(Integer clazzId) {
        this.clazzId = clazzId;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Clazz{" +
        "clazzId=" + clazzId +
        ", clazzName=" + clazzName +
        ", major=" + major +
        "}";
    }
}
