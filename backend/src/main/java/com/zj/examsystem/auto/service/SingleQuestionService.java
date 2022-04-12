package com.zj.examsystem.auto.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.auto.entity.SingleQuestion;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface SingleQuestionService extends IService<SingleQuestion> {

    public IPage<Map<String, Object>> findAll(Integer pageno, Integer size);

    public int saveSingleQuestion(SingleQuestion singleQuestion);

    public int deleteSingleQuestion(Integer[] id);

    public SingleQuestion findById(Integer quesId);

    public List<SingleQuestion> findQuesBySubId(Integer subId);
}
