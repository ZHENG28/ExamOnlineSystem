package com.zj.examsystem.eos.service.impl;

import com.zj.examsystem.eos.entity.Post;
import com.zj.examsystem.eos.mapper.PostMapper;
import com.zj.examsystem.eos.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

}
