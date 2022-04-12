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
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "subject_id", type = IdType.AUTO)
    private Integer subjectId;

    private String subjectName;

    private Integer teacherId;


    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
        "subjectId=" + subjectId +
        ", subjectName=" + subjectName +
        ", teacherId=" + teacherId +
        "}";
    }
}
