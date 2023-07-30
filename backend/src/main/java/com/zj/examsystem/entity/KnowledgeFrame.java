package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class KnowledgeFrame implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "chapter_id", type = IdType.AUTO)
    private Integer chapterId;

    /**
     * 0-父节点
     */
    private Integer chapterParentId;

    private String content;

    /**
     * 是否是知识点，1-是
     */
    private Integer isKnowledge;

    private Integer subjectId;

    @TableField(select = false)
    private String subjectName;

    public KnowledgeFrame(Integer chapterId, Integer chapterParentId, String content, Integer isKnowledge, Integer subjectId) {
        this.chapterId = chapterId;
        this.chapterParentId = chapterParentId;
        this.content = content;
        this.isKnowledge = isKnowledge;
        this.subjectId = subjectId;
    }
}
