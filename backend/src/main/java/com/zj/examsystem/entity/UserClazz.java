package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserClazz implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "student_id")
    private Integer studentId;

    private Integer clazzId;
}
