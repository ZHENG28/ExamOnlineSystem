package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.entity.TestHistory;
import com.zj.examsystem.mapper.TestHistoryMapper;
import com.zj.examsystem.service.TestHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@SuppressWarnings("all")
public class TestHistoryServiceImpl extends ServiceImpl<TestHistoryMapper, TestHistory> implements TestHistoryService {
    @Autowired
    private TestHistoryMapper testHistoryMapper;

    public List<Map<String, Object>> findScoreByTestId(Integer testId) {
        //        Test test = testRepo.findById(testId).orElseThrow();
        //        List<Integer> histories = testHistoryMapper.findByTestId(test);
        List<Map<String, Object>> scoreList = new ArrayList<>();
        int[] scoreArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        //        for (Integer score : histories) {
        //            if (score == 100) {
        //                scoreArr[9]++;
        //                continue;
        //            }
        //            scoreArr[score / 10]++;
        //        }
        int[] arr = {scoreArr[0] + scoreArr[1] + scoreArr[2] + scoreArr[3] + scoreArr[4] + scoreArr[5], scoreArr[6], scoreArr[7], scoreArr[8],
                scoreArr[9]};
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", arr[i]);
            String str = "";
            switch (i) {
                case 0:
                    str = "60以下";
                    break;
                case 1:
                    str = "60-69";
                    break;
                case 2:
                    str = "70-79";
                    break;
                case 3:
                    str = "80-89";
                    break;
                case 4:
                    str = "90-100";
                    break;
            }
            map.put("name", str);
            scoreList.add(map);
        }
        return scoreList;
    }

    public IPage<TestHistory> findAllByAccount(Integer pageno, Integer size, String account) {
        QueryWrapper<Map<String, Object>> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        Page<Map<String, Object>> page = new Page<>(pageno, size);
        return testHistoryMapper.selectPageWithTest(page, queryWrapper);
    }
}
