package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "subject_id", type = IdType.AUTO)
    private Integer subjectId;

    private String subjectName;

    private Integer teacherId;

    @TableField(select = false)
    private String teacherName;

    private Integer clazzId;

    @TableField(select = false)
    private String clazzName;

    @TableField(select = false)
    private Integer majorId;

    @TableField(select = false)
    private String majorName;
}
