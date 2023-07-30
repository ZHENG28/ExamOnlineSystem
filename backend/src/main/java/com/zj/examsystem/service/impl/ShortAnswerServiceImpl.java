package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.entity.ShortAnswer;
import com.zj.examsystem.mapper.ShortAnswerMapper;
import com.zj.examsystem.service.ShortAnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShortAnswerServiceImpl extends ServiceImpl<ShortAnswerMapper, ShortAnswer> implements ShortAnswerService {
    @Resource
    private ShortAnswerMapper shortAnswerMapper;

    @Override
    public List<Question> findAllQuestion(Integer testId) {
        return shortAnswerMapper.findQuestionsByTestId(testId);
    }
}
