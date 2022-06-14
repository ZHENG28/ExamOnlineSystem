package com.zj.examsystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * view
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
    private Integer examTime;
}
