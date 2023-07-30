package com.zj.examsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.ShortAnswer;

import java.util.List;

public interface ShortAnswerService extends IService<ShortAnswer> {
    List<Question> findAllQuestion(Integer testId);
}
