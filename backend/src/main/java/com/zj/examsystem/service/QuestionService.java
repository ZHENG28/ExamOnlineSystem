package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface QuestionService extends IService<Question> {
    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size);

    public int saveQuestion(Question question);

    public int deleteQuestion(Integer[] id);

    public Question findById(Integer quesId);

    public List<Question> findQuesBySubId(Integer subId);
}
