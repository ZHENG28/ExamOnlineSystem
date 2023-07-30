package com.zj.examsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.service.QuestionService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.zj.examsystem.utils.Const.INITIAL_POPULATION_SIZE;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private QuestionService questionService;

    @GetMapping("/findAllByTeacherId")
    @ResponseBody
    public BaseResponseEntity<IPage<Question>> findAllByTeacherId(Integer pageno, Integer size, Integer userId) {
        return BaseResponseEntity.ok("", questionService.findAllByTeacherId(pageno, size, userId));
    }

    @GetMapping("/findById")
    @ResponseBody
    public BaseResponseEntity<Question> findById(Integer questionId) {
        return BaseResponseEntity.ok("", questionService.findById(questionId));
    }

    @GetMapping("/findQuestionBySubjectId")
    @ResponseBody
    public BaseResponseEntity<List<Question>> findQuestionBySubjectId(Integer subjectId) {
        return BaseResponseEntity.ok("", questionService.findQuestionBySubjectId(subjectId));
    }

    @GetMapping("/findQuestionListByTestId")
    @ResponseBody
    public BaseResponseEntity<List<Question>> findQuestionListByTestId(Integer testId) {
        return BaseResponseEntity.ok("", questionService.findQuestionListByTestId(testId));
    }

    @PostMapping("/findQuestionListByQuestionIds")
    @ResponseBody
    public BaseResponseEntity<List<Question>> findQuestionListByQuestionIds(Integer[] questionIds) {
        return BaseResponseEntity.ok("", questionService.findQuestionListByQuestionIds(Arrays.asList(questionIds)));
    }

    @PostMapping("/calculateActualDifficulty")
    @ResponseBody
    public BaseResponseEntity<Double> calculateActualDifficulty(Integer[] questionIds) {
        return BaseResponseEntity.ok("", questionService.calculateActualDifficulty(questionIds));
    }

    @GetMapping("/intelligentGenerate")
    @ResponseBody
    public BaseResponseEntity<List<Question>> intelligentGenerate(Integer subjectId, Integer presetQuestionTotal, Float presetTestDifficulty,
                                                                  Float difficultyWeight,
                                                                  Float knowledgeWeight) {
        List<Question> questionList = questionService.findQuestionBySubjectId(subjectId);
        // 1. 初始化种群
        List<List<Question>> initialPopulation = new ArrayList<>();
        for (int i = 0; i < INITIAL_POPULATION_SIZE; i++) {
            List<Question> chromosome = new ArrayList<>();
            for (int j = 0; j < presetQuestionTotal; j++) {
                int rand = new Random().nextInt(questionList.size());
                Question question = questionList.get(rand);
                while (chromosome.contains(question)) {
                    rand = new Random().nextInt(questionList.size());
                    question = questionList.get(rand);
                }
                chromosome.add(question);
            }
            initialPopulation.add(chromosome);
        }
        return BaseResponseEntity.ok("生成成功", questionService.intelligentGenerate(initialPopulation,
                knowledgeWeight,
                presetTestDifficulty,
                difficultyWeight, 1));
    }

    @PostMapping("/save")
    @ResponseBody
    public BaseResponseEntity<Integer> save(Question question, String status) {
        Boolean result = question.getQuestionId() == null ? questionService.saveQuestion(question) : questionService.updateQuestion(question);
        return result ? BaseResponseEntity.ok(status + "成功", null) : BaseResponseEntity.error(ResponseCode.FAIL,
                status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public BaseResponseEntity<Integer> delete(Integer[] questionId) {
        int result = questionService.deleteQuestion(questionId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }
}

