package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserMajor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "teacher_id")
    private Integer teacherId;

    private Integer majorId;
}
