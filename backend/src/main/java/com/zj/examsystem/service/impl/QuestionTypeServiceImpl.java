package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.QuestionType;
import com.zj.examsystem.mapper.QuestionTypeMapper;
import com.zj.examsystem.service.QuestionTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@SuppressWarnings("all")
public class QuestionTypeServiceImpl extends ServiceImpl<QuestionTypeMapper, QuestionType> implements QuestionTypeService {

    @Autowired
    private QuestionTypeMapper questionTypeMapper;

    public IPage<QuestionType> findAll(Integer pageno, Integer size, Integer... userId) {
        QueryWrapper<QuestionType> queryWrapper = new QueryWrapper<>();
        Page<QuestionType> page = new Page<>(pageno, size);

//        if (userId == null) {
//            return questionTypeMapper.selectPageWithQuestionType(page, queryWrapper);
//        } else {
//            //            Student stu = studentMapper.selectById(id);
//            //            queryWrapper.eq("test_clazz", stu.getClazzId());
//            return questionTypeMapper.selectPageWithQuestionType(page, queryWrapper);
//        }
        return questionTypeMapper.selectPage(page, queryWrapper);
    }

    public List<Map<String, Object>> findDistinctQuestionType() {
        List<Map<String, Object>> tmp = questionTypeMapper.selectDistinctQuestionType();
        List<Map<String, Object>> List = new ArrayList<>();
        for (Map<String, Object> resMap : tmp) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", resMap.get("type_name"));
            map.put("value", resMap.get("type_name"));
            map.put("typeId", resMap.get("type_id"));
            List.add(map);
        }
        return List;
    }

    public int saveQuestionType(QuestionType questionType) {
        return questionType.getTypeId() != null ? questionTypeMapper.updateById(questionType) : questionTypeMapper.insert(questionType);
    }

    @Transactional
    public int deleteQuestionType(Integer[] id) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ids.add(id[i]);
        }
        return questionTypeMapper.deleteBatchIds(ids);
    }

    public QuestionType findById(Integer questionTypeId) {
        return questionTypeMapper.selectById(questionTypeId);
    }
}
