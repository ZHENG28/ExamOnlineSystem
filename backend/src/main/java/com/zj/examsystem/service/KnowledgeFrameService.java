package com.zj.examsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.KnowledgeFrame;

import java.util.List;
import java.util.Map;

public interface KnowledgeFrameService extends IService<KnowledgeFrame> {
    List<KnowledgeFrame> loadKnowledgeBySubjectId(Integer subjectId);

    List<Map<String, Object>> loadKnowledgeFrameBySubjectId(Integer subjectId);

    Integer saveKnowledgeFrameList(List<KnowledgeFrame> knowledgeFrameList, Integer subjectId);
}
