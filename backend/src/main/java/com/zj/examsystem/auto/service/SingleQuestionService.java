package com.zj.examsystem.auto.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.auto.entity.SingleQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.examsystem.repository.SingleQuesRepository;
import org.springframework.data.domain.Page;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface SingleQuestionService extends IService<SingleQuestion> {

    public IPage<SingleQuestion> findAll(Integer pageno, Integer size);


}
