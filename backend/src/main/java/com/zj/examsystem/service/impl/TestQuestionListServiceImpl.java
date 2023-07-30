package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.TestQuestionList;
import com.zj.examsystem.mapper.TestQuestionListMapper;
import com.zj.examsystem.service.TestQuestionListService;
import org.springframework.stereotype.Service;

@Service
public class TestQuestionListServiceImpl extends ServiceImpl<TestQuestionListMapper, TestQuestionList> implements TestQuestionListService {
}
