package com.zj.examsystem.auto.mapper;

import com.zj.examsystem.auto.entity.SingleQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.examsystem.model.Subject;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface SingleQuestionMapper extends BaseMapper<SingleQuestion> {

    List<SingleQuestion> findBySub(Subject sub);
}
