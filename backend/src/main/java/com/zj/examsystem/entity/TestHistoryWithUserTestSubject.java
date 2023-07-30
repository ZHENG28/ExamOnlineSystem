package com.zj.examsystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 视图
 */
@Data
public class TestHistoryWithUserTestSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer maxCorrect;

    private Long completeTime;

    private Integer testId;

    private Integer studentId;

    private String username;

    private Integer clazzId;

    private String clazzName;

    private Integer majorId;

    private String majorName;

    private String testName;

    private String description;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    private Integer subjectId;

    private String subjectName;

    /**
     * 题目数量
     */
    private Integer questionTotal;

    /**
     * 测验次数
     */
    private Integer testTime;

    /**
     * 测验时长
     */
    private Integer testDuration;

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
}
