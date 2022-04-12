package com.zj.examsystem.auto.controller;


import com.zj.examsystem.auto.service.TesthistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Controller
@RequestMapping("/auto/testhistory")
public class TesthistoryController {
    @Autowired
    private TesthistoryService testHistoryService;

    @PostMapping("/findScoreByTestId")
    @ResponseBody
    public Object findScoreByTestId(Integer testId) {
        return testHistoryService.findScoreByTestId(testId);
    }

    @PostMapping("/findAllByAccount")
    @ResponseBody
    public Object findAllByAccount(Integer pageno, Integer size, String account) {
        return testHistoryService.findAllByAccount(pageno, size, account);
    }
}

