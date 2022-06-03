package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CompareShortAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "compare_id", type = IdType.AUTO)
    private Integer compareId;

    private Float compareThreshold;

    private Integer shortAnswerId;

    @TableField(select = false)
    private Integer questionId;

    @TableField(select = false)
    private String questionTitle;

    @TableField(select = false)
    private Integer testId;


    private Integer reply1Id;

    @TableField(select = false)
    private String reply1Text;

    @TableField(select = false)
    private String reply1Ranking;

    @TableField(select = false)
    private int[] reply1CompareList;

    @TableField(select = false)
    private Integer userId;

    @TableField(select = false)
    private String username;


    private Integer reply2Id;

    @TableField(select = false)
    private String reply2Text;

    @TableField(select = false)
    private String reply2Ranking;

    @TableField(select = false)
    private int[] reply2CompareList;

    @TableField(select = false)
    private Integer compareUserId;

    @TableField(select = false)
    private String compareUsername;

    public CompareShortAnswer(Integer shortAnswerId, Integer reply1Id, Integer reply2Id, Float compareThreshold) {
        this.compareThreshold = compareThreshold;
        this.shortAnswerId = shortAnswerId;
        this.reply1Id = reply1Id;
        this.reply2Id = reply2Id;
    }
}
