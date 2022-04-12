package com.zj.examsystem.eos.service.impl;

import com.zj.examsystem.eos.entity.Comment;
import com.zj.examsystem.eos.mapper.CommentMapper;
import com.zj.examsystem.eos.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
