package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.mapper.SubjectMapper;
import com.zj.examsystem.mapper.QuestionMapper;
import com.zj.examsystem.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@SuppressWarnings("all")
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size) {
        QueryWrapper<Map<String, Object>> queryWrapper = new QueryWrapper<>();
        Page<Map<String, Object>> page = new Page<>(pageno, size);

        return questionMapper.selectPageWithSubject(page, queryWrapper);
    }

    public int saveQuestion(Question question) {
        return question.getQuestionId() != null ? questionMapper.updateById(question) : questionMapper.insert(question);
    }

    @Transactional
    public int deleteQuestion(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return questionMapper.deleteBatchIds(ids);
    }

    public Question findById(Integer quesId) {
        return questionMapper.selectById(quesId);
    }

    @Override
    public List<Question> findQuesBySubId(Integer subId) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sub_id", subId);

        return questionMapper.selectList(queryWrapper);
    }
}
