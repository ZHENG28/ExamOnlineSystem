package com.zj.examsystem.service.impl;

import com.zj.examsystem.entity.Post;
import com.zj.examsystem.mapper.PostMapper;
import com.zj.examsystem.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

}
