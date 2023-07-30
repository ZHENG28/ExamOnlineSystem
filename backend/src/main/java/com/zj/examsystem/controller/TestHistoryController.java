package com.zj.examsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.TestHistory;
import com.zj.examsystem.entity.TestHistoryWithUserTestSubject;
import com.zj.examsystem.service.CompareShortAnswerService;
import com.zj.examsystem.service.QuestionService;
import com.zj.examsystem.service.TestHistoryService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/testHistory")
public class TestHistoryController {
    @Resource
    private TestHistoryService testHistoryService;

    @Resource
    private QuestionService questionService;

    @Resource
    private CompareShortAnswerService compareShortAnswerService;

    @PostMapping("/commit")
    @ResponseBody
    public BaseResponseEntity<String> commit(TestHistory testHistory, String[] reply) {
        if (testHistoryService.commit(testHistory, reply, questionService.findQuestionListByTestId(testHistory.getTestId()))) {
            return compareShortAnswerService.compareReplyWithOther(testHistory) ?
                    BaseResponseEntity.ok("提交成功", "") : BaseResponseEntity.error(ResponseCode.FAIL,
                    "提交失败");
        } else {
            return BaseResponseEntity.error(ResponseCode.FAIL, "提交失败");
        }
    }

    @GetMapping("/loadObjectChartData")
    @ResponseBody
    public BaseResponseEntity<List<Map<String, Object>>> loadObjectChartData(Integer testId) {
        return BaseResponseEntity.ok("", testHistoryService.loadObjectChartData(testId));
    }

    @GetMapping("/findAllByUserId")
    @ResponseBody
    public BaseResponseEntity<IPage<TestHistoryWithUserTestSubject>> findAllByUserId(Integer pageno, Integer size, Integer userId) {
        return BaseResponseEntity.ok("", testHistoryService.findAllByUserId(pageno, size, userId));
    }

    @GetMapping("/findAllByTestId")
    @ResponseBody
    public BaseResponseEntity<IPage<TestHistoryWithUserTestSubject>> findAllByTestId(Integer pageno, Integer size, Integer testId) {
        return BaseResponseEntity.ok("", testHistoryService.findAllByTestId(pageno, size, testId));
    }

    @GetMapping("/findTestInfoByCompoundId")
    @ResponseBody
    public BaseResponseEntity<TestHistoryWithUserTestSubject> findTestInfoByCompoundId(Integer testId, Integer studentId) {
        return BaseResponseEntity.ok("", testHistoryService.findTestInfoByCompoundId(testId, studentId));
    }

    @GetMapping("/findHistoryByCompoundId")
    @ResponseBody
    public BaseResponseEntity<TestHistory> findHistoryByCompoundId(Integer testId, Integer studentId, Integer order) {
        return BaseResponseEntity.ok("", testHistoryService.findHistoryByCompoundId(testId, studentId, order));
    }
}

