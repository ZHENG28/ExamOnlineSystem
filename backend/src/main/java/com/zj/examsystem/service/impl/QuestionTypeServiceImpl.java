package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.QuestionType;
import com.zj.examsystem.mapper.QuestionTypeMapper;
import com.zj.examsystem.service.QuestionTypeService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionTypeServiceImpl extends ServiceImpl<QuestionTypeMapper, QuestionType> implements QuestionTypeService {
    @Resource
    private QuestionTypeMapper questionTypeMapper;

    @Override
    public IPage<QuestionType> findAll(Integer pageno, Integer size) {
        QueryWrapper<QuestionType> queryWrapper = new QueryWrapper<>();
        Page<QuestionType> page = new Page<>(pageno, size);
        return questionTypeMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<QuestionType> findAll() {
        return questionTypeMapper.selectList(null);
    }

    @Override
    public QuestionType findById(Integer questionTypeId) {
        return questionTypeMapper.selectById(questionTypeId);
    }

    @Override
    public Integer saveQuestionType(QuestionType questionType) {
        try {
            return questionType.getTypeId() != null ? questionTypeMapper.updateById(questionType) : questionTypeMapper.insert(questionType);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                return 0;
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public Integer deleteQuestionType(Integer[] id) {
        List<Integer> ids = new ArrayList<>(Arrays.asList(id));
        return questionTypeMapper.deleteBatchIds(ids);
    }
}
