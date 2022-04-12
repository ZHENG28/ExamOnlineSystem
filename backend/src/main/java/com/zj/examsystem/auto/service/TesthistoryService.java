package com.zj.examsystem.auto.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.auto.entity.Testhistory;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface TesthistoryService extends IService<Testhistory> {
    public List<Map<String, Object>> findScoreByTestId(Integer testId);

    public IPage<Testhistory> findAllByAccount(Integer pageno, Integer size, String account);
}
