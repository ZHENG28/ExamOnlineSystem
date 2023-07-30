package com.zj.examsystem.controller;

import com.zj.examsystem.entity.KnowledgeFrame;
import com.zj.examsystem.service.KnowledgeFrameService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/knowledgeFrame")
public class KnowledgeFrameController {
    @Autowired
    private KnowledgeFrameService knowledgeFrameService;

    @GetMapping("/loadKnowledgeBySubjectId")
    @ResponseBody
    public BaseResponseEntity loadKnowledgeBySubjectId(Integer subjectId) {
        return BaseResponseEntity.ok("", knowledgeFrameService.loadKnowledgeBySubjectId(subjectId));
    }

    @GetMapping("/loadKnowledgeFrameBySubjectId")
    @ResponseBody
    public BaseResponseEntity loadKnowledgeFrameBySubjectId(Integer subjectId) {
        return BaseResponseEntity.ok("", knowledgeFrameService.loadKnowledgeFrameBySubjectId(subjectId));
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public BaseResponseEntity save(String[] knowledgeFrames, Integer subjectId) {
        List<KnowledgeFrame> knowledgeFrameList = new ArrayList<>();
        for (String s : knowledgeFrames) {
            String[] str = s.split(",");
            knowledgeFrameList.add(new KnowledgeFrame("null".equals(str[0]) ? null : Integer.parseInt(str[0]), Integer.parseInt(str[1]), str[2],
                    Boolean.parseBoolean(str[3]) ? 1 : 0,
                    Integer.parseInt(str[4])));
        }
        int result = knowledgeFrameService.saveKnowledgeFrameList(knowledgeFrameList, subjectId);
        return result != 0 ? BaseResponseEntity.ok("修改成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "修改失败");
    }
}

