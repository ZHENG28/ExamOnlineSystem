package com.zj.examsystem.service;

import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.ShortAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ShortAnswerService extends IService<ShortAnswer> {

    public List<Question> findAllQuestion(Integer testId);
}
