package com.zj.examsystem.service.impl;

import com.zj.examsystem.entity.TestQuestionList;
import com.zj.examsystem.mapper.TestQuestionListMapper;
import com.zj.examsystem.service.TestQuestionListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@SuppressWarnings("all")
public class TestQuestionListServiceImpl extends ServiceImpl<TestQuestionListMapper, TestQuestionList> implements TestQuestionListService {
    @Autowired
    private TestQuestionListMapper testQuestionListMapper;
}
