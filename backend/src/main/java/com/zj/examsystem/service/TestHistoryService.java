package com.zj.examsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.TestHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface TestHistoryService extends IService<TestHistory> {
    public List<Map<String, Object>> findScoreByTestId(Integer testId);

    public IPage<TestHistory> findAllByAccount(Integer pageno, Integer size, String account);

}
