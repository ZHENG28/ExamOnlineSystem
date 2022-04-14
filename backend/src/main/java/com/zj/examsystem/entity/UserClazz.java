package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserClazz implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id")
    private Integer userId;

    private Integer clazzId;
}
