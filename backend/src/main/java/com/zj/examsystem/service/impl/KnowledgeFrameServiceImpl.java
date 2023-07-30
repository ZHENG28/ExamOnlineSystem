package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.KnowledgeFrame;
import com.zj.examsystem.mapper.KnowledgeFrameMapper;
import com.zj.examsystem.service.KnowledgeFrameService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zj.examsystem.utils.Const.NEW_CHAPTER_ID;

@Service
public class KnowledgeFrameServiceImpl extends ServiceImpl<KnowledgeFrameMapper, KnowledgeFrame> implements KnowledgeFrameService {
    @Resource
    private KnowledgeFrameMapper knowledgeFrameMapper;

    @Override
    public List<KnowledgeFrame> loadKnowledgeBySubjectId(Integer subjectId) {
        QueryWrapper<KnowledgeFrame> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id", subjectId);
        queryWrapper.eq("is_knowledge", 1);
        return knowledgeFrameMapper.selectList(queryWrapper);
    }

    public List<Map<String, Object>> getTreeList(List<KnowledgeFrame> knowledgeFrameList, Integer parentId) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (KnowledgeFrame knowledgeFrame : knowledgeFrameList) {
            if (parentId.equals(knowledgeFrame.getChapterParentId())) {
                Map<String, Object> map = new HashMap<>();
                map.put("chapterId", knowledgeFrame.getChapterId());
                map.put("chapterParentId", knowledgeFrame.getChapterParentId());
                map.put("label", knowledgeFrame.getContent());
                map.put("isKnowledge", 1 == knowledgeFrame.getIsKnowledge());
                map.put("children", getTreeList(knowledgeFrameList, knowledgeFrame.getChapterId()));
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> loadKnowledgeFrameBySubjectId(Integer subjectId) {
        QueryWrapper<KnowledgeFrame> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id", subjectId);
        return getTreeList(knowledgeFrameMapper.selectList(queryWrapper), 0);
    }

    public Integer insertKnowledgeList(List<KnowledgeFrame> knowledgeFrameList) {
        int result = 0;
        Map<Integer, Integer> replaceIds = new HashMap<>();
        for (KnowledgeFrame knowledgeFrame : knowledgeFrameList) {
            if (knowledgeFrame.getChapterParentId() >= NEW_CHAPTER_ID && !replaceIds.isEmpty()) {
                knowledgeFrame.setChapterParentId(replaceIds.get(knowledgeFrame.getChapterParentId()));
            }
            //            if ((knowledgeFrame.getChapterId() >= NEW_CHAPTER_ID && 0 == knowledgeFrame.getChapterParentId())) {
            // new chapter or new section or knowledge in exist node
            Integer oldId = knowledgeFrame.getChapterId();
            knowledgeFrame.setChapterId(null);
            // insert后，会自动填充插入后的ID
            result += knowledgeFrameMapper.insert(knowledgeFrame);
            replaceIds.put(oldId, knowledgeFrame.getChapterId());
            //            } else { // new section or knowledge in new node
            //            }
        }
        return result;
    }

    @Override
    public Integer saveKnowledgeFrameList(List<KnowledgeFrame> knowledgeFrameList, Integer subjectId) {
        int result = 0;
        List<Integer> chapterIds = knowledgeFrameMapper.findIdBySubjectId(subjectId);
        List<KnowledgeFrame> newKnowledgeList = new ArrayList<>();
        try {
            for (KnowledgeFrame knowledgeFrame : knowledgeFrameList) {
                if (knowledgeFrame.getChapterId() >= NEW_CHAPTER_ID) {
                    newKnowledgeList.add(knowledgeFrame);
                } else {
                    result += insertKnowledgeList(newKnowledgeList);
                    newKnowledgeList.clear();
                    result += knowledgeFrame.getChapterId() != null ? knowledgeFrameMapper.updateById(knowledgeFrame) :
                            knowledgeFrameMapper.insert(knowledgeFrame);
                    chapterIds.remove(knowledgeFrame.getChapterId());
                }
            }
            if (!newKnowledgeList.isEmpty()) {
                result += insertKnowledgeList(newKnowledgeList);
                newKnowledgeList.clear();
            }
            if (chapterIds.size() != 0) { // something need to delete
                knowledgeFrameMapper.deleteBatchIds(chapterIds);
            }
            return result;
        } catch (
                Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                return 0;
            }
        }
        return 0;
    }
}
