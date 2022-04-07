package com.zj.examsystem.auto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.auto.entity.SingleQuestion;
import com.zj.examsystem.auto.mapper.SingleQuestionMapper;
import com.zj.examsystem.auto.service.SingleQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.repository.SingleQuesRepository;
import com.zj.examsystem.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Service
public class SingleQuestionServiceImpl extends ServiceImpl<SingleQuestionMapper, SingleQuestion> implements SingleQuestionService {

    @Autowired
    private SingleQuestionMapper singleQuestionMapper;

    @Autowired
    private SubjectRepository subjectRepo;

    public IPage<SingleQuestion> findAll(Integer pageno, Integer size)
    {
        QueryWrapper<SingleQuestion> queryWrapper = new QueryWrapper<>();
        IPage<SingleQuestion> page = new Page<>();

        return singleQuestionMapper.selectPage(page,queryWrapper);
    }
}
