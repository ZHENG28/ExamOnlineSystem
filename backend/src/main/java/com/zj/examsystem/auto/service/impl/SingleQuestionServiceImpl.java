package com.zj.examsystem.auto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.auto.entity.*;
import com.zj.examsystem.auto.entity.SingleQuestion;
import com.zj.examsystem.auto.mapper.SingleQuestionMapper;
import com.zj.examsystem.auto.mapper.SubjectMapper;
import com.zj.examsystem.auto.service.SingleQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.repository.SingleQuesRepository;
import com.zj.examsystem.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Service
@SuppressWarnings("all")
public class SingleQuestionServiceImpl extends ServiceImpl<SingleQuestionMapper, SingleQuestion> implements SingleQuestionService {

    @Autowired
    private SingleQuestionMapper singleQuestionMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size) {
        QueryWrapper<Map<String, Object>> queryWrapper = new QueryWrapper<>();
        Page<Map<String, Object>> page = new Page<>(pageno, size);

        return singleQuestionMapper.selectPageWithSubject(page, queryWrapper);
    }

    public int saveSingleQuestion(SingleQuestion singleQuestion) {
        return singleQuestion.getQuesId() != null ? singleQuestionMapper.updateById(singleQuestion) : singleQuestionMapper.insert(singleQuestion);
    }

    @Transactional
    public int deleteSingleQuestion(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return singleQuestionMapper.deleteBatchIds(ids);
    }

    public SingleQuestion findById(Integer quesId) {
        return singleQuestionMapper.selectById(quesId);
    }

    @Override
    public List<SingleQuestion> findQuesBySubId(Integer subId) {
        QueryWrapper<SingleQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sub_id", subId);

        return singleQuestionMapper.selectList(queryWrapper);
    }
}
