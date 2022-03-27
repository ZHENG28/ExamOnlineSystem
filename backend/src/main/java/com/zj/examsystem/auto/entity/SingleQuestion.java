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
public class SingleQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ques_id", type = IdType.AUTO)
    private Integer quesId;

    private String answer;

    private String opta;

    private String optb;

    private String optc;

    private String optd;

    private String quesTitle;

    private Integer subId;


    public Integer getQuesId() {
        return quesId;
    }

    public void setQuesId(Integer quesId) {
        this.quesId = quesId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOpta() {
        return opta;
    }

    public void setOpta(String opta) {
        this.opta = opta;
    }

    public String getOptb() {
        return optb;
    }

    public void setOptb(String optb) {
        this.optb = optb;
    }

    public String getOptc() {
        return optc;
    }

    public void setOptc(String optc) {
        this.optc = optc;
    }

    public String getOptd() {
        return optd;
    }

    public void setOptd(String optd) {
        this.optd = optd;
    }

    public String getQuesTitle() {
        return quesTitle;
    }

    public void setQuesTitle(String quesTitle) {
        this.quesTitle = quesTitle;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    @Override
    public String toString() {
        return "SingleQuestion{" +
        "quesId=" + quesId +
        ", answer=" + answer +
        ", opta=" + opta +
        ", optb=" + optb +
        ", optc=" + optc +
        ", optd=" + optd +
        ", quesTitle=" + quesTitle +
        ", subId=" + subId +
        "}";
    }
}
