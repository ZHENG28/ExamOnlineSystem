package com.zj.examsystem.service.impl;

import com.zj.examsystem.entity.QuestionType;
import com.zj.examsystem.mapper.QuestionTypeMapper;
import com.zj.examsystem.service.QuestionTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class QuestionTypeServiceImpl extends ServiceImpl<QuestionTypeMapper, QuestionType> implements QuestionTypeService {

}
