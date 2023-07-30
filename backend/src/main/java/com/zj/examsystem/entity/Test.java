package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "test_id", type = IdType.AUTO)
    private Integer testId;

    private String testName;

    private String description;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    /**
     * 测验时长
     */
    private Integer testDuration;

    /**
     * 测验次数
     */
    private Integer testTime;

    /**
     * 题目数量
     */
    private Integer questionTotal;

    /**
     * 实际难度系数
     */
    private Float testDifficulty;

    /**
     * 组卷方式
     * 1-手动；2-智能
     */
    private Integer generateWay;

    /**
     * 预设题目数量
     */
    private Integer presetQuestionTotal;

    /**
     * 预设难度系数
     */
    private Float presetTestDifficulty;

    /**
     * 难度权重
     */
    private Float difficultyWeight;

    /**
     * 知识点覆盖率权重
     */
    private Float knowledgeWeight;

    private Integer subjectId;

    @TableField(select = false)
    private String subjectName;

    @TableField(select = false)
    private Integer clazzId;

    @TableField(select = false)
    private String clazzName;
}
