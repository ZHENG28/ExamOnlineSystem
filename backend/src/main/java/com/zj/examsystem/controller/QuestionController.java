package com.zj.examsystem.controller;


import com.zj.examsystem.entity.Question;
import com.zj.examsystem.service.QuestionService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/findAllByTeacherId")
    @ResponseBody
    public Object findAllByTeacherId(Integer pageno, Integer size, Integer userId) {
        return BaseResponseEntity.ok("", questionService.findAllByTeacherId(pageno, size, userId));
    }

    @GetMapping("/findById")
    @ResponseBody
    public Object findById(Integer questionId) {
        return BaseResponseEntity.ok("", questionService.findById(questionId));
    }

    @GetMapping("/findQuestionBySubjectId")
    @ResponseBody
    public Object findQuestionBySubjectId(Integer subjectId) {
        return BaseResponseEntity.ok("", questionService.findQuestionBySubjectId(subjectId));
    }

    @GetMapping("/findQuestionListByTestId")
    @ResponseBody
    public Object findQuestionListByTestId(Integer testId) {
        return BaseResponseEntity.ok("", questionService.findQuestionListByTestId(testId));
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(Question question, String status) {
        Boolean result = question.getQuestionId() == null ? questionService.saveQuestion(question) : questionService.updateQuestion(question);
        return result ? BaseResponseEntity.ok(status + "成功", "") : BaseResponseEntity.error(ResponseCode.FAIL,
                status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Integer[] questionId) {
        int result = questionService.deleteQuestion(questionId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }
}

