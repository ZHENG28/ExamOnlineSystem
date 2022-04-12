package com.zj.examsystem.eos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2022-04-12
 */
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "test_id", type = IdType.AUTO)
    private Integer testId;

    private LocalDateTime beginDate;

    private String description;

    private LocalDateTime endDate;

    private Integer examDure;

    private Integer examTime;

    private String quesArr;

    private String testName;

    private Integer totalScore;

    private Integer subjectId;

    private Integer clazzId;


    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getExamDure() {
        return examDure;
    }

    public void setExamDure(Integer examDure) {
        this.examDure = examDure;
    }

    public Integer getExamTime() {
        return examTime;
    }

    public void setExamTime(Integer examTime) {
        this.examTime = examTime;
    }

    public String getQuesArr() {
        return quesArr;
    }

    public void setQuesArr(String quesArr) {
        this.quesArr = quesArr;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getClazzId() {
        return clazzId;
    }

    public void setClazzId(Integer clazzId) {
        this.clazzId = clazzId;
    }

    @Override
    public String toString() {
        return "Test{" +
        "testId=" + testId +
        ", beginDate=" + beginDate +
        ", description=" + description +
        ", endDate=" + endDate +
        ", examDure=" + examDure +
        ", examTime=" + examTime +
        ", quesArr=" + quesArr +
        ", testName=" + testName +
        ", totalScore=" + totalScore +
        ", subjectId=" + subjectId +
        ", clazzId=" + clazzId +
        "}";
    }
}
