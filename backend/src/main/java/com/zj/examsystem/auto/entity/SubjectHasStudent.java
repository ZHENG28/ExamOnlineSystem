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
public class SubjectHasStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "subject_sub_id", type = IdType.AUTO)
    private Integer subjectSubId;

    private Integer studentId;


    public Integer getSubjectSubId() {
        return subjectSubId;
    }

    public void setSubjectSubId(Integer subjectSubId) {
        this.subjectSubId = subjectSubId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "SubjectHasStudent{" +
        "subjectSubId=" + subjectSubId +
        ", studentId=" + studentId +
        "}";
    }
}
