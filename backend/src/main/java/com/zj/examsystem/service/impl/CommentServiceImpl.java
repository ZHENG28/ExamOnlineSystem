package com.zj.examsystem.service.impl;

import com.zj.examsystem.entity.Comment;
import com.zj.examsystem.mapper.CommentMapper;
import com.zj.examsystem.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
