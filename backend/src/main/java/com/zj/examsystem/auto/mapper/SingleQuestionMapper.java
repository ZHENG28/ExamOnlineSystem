package com.zj.examsystem.auto.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.auto.entity.SingleQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.examsystem.model.Subject;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface SingleQuestionMapper extends BaseMapper<SingleQuestion> {

    IPage<Map<String, Object>> selectPageWithSubject(Page<Map<String, Object>> page, QueryWrapper<Map<String, Object>> queryWrapper);

    SingleQuestion selectById(Integer quesId);
}
