package com.zj.examsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.examsystem.entity.Answer;
import com.zj.examsystem.entity.Question;
import com.zj.examsystem.mapper.AnswerMapper;
import com.zj.examsystem.mapper.QuestionMapper;
import com.zj.examsystem.mapper.TestQuestionListMapper;
import com.zj.examsystem.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.zj.examsystem.utils.Const.*;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private AnswerMapper answerMapper;

    @Resource
    private TestQuestionListMapper testQuestionListMapper;

    @Override
    public IPage<Question> findAllByTeacherId(Integer pageno, Integer size, Integer userId) {
        Page<Question> page = new Page<>(pageno, size);
        IPage<Question> questionIPage = questionMapper.findAllByTeacherId(userId, page);
        for (Question question : questionIPage.getRecords()) {
            QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
            answerQueryWrapper.eq("question_id", question.getQuestionId());
            question.setAnswer(answerMapper.selectList(answerQueryWrapper));
            if (((Integer) 1).equals(question.getTypeId())) {
                answerQueryWrapper.eq("is_correct", "1");
                question.setCorrect(answerMapper.selectOne(answerQueryWrapper).getAnswerSign());
            }
        }
        return questionIPage;
    }

    @Override
    public Question findById(Integer questionId) {
        Question question = questionMapper.findById(questionId);

        QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
        answerQueryWrapper.eq("question_id", question.getQuestionId());
        question.setAnswer(answerMapper.selectList(answerQueryWrapper));
        if (((Integer) 1).equals(question.getTypeId()) || ((Integer) 2).equals(question.getTypeId())) {
            answerQueryWrapper.eq("is_correct", "1");
            question.setCorrect(answerMapper.selectOne(answerQueryWrapper).getAnswerSign());
        }
        return question;
    }

    @Override
    public List<Question> findQuestionBySubjectId(Integer subjectId) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id", subjectId);
        return questionMapper.findListById(queryWrapper);
    }

    @Override
    public List<Question> findQuestionListByTestId(Integer testId) {
        List<Integer> questionIds = testQuestionListMapper.findQuestionIdsByTestId(testId);
        return findQuestionListByQuestionIds(questionIds);
    }

    @Override
    public List<Question> findQuestionListByQuestionIds(List<Integer> questionIds) {
        List<Question> questionList = new ArrayList<>();
        for (Integer questionId : questionIds) {
            Question question = questionMapper.findById(questionId);
            QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
            answerQueryWrapper.eq("question_id", questionId);
            List<Answer> answerList = answerMapper.selectList(answerQueryWrapper);
            switch (question.getTypeId()) {
                case 1:
                    for (Answer answer : answerList) {
                        if (((Integer) 1).equals(answer.getIsCorrect())) {
                            question.setCorrect(answer.getAnswerSign());
                        }
                    }
                    break;
                case 2:
                    question.setCorrect("1".equals(answerList.get(0).getAnswerSign()) ? "true" : "false");
                    break;
            }
            question.setAnswer(answerList);
            questionList.add(question);
        }
        return questionList;
    }

    @Override
    public Double calculateActualDifficulty(Integer[] questionIds) {
        Double difficulty = 0.0;
        for (Integer questionId : questionIds) {
            Question question = questionMapper.selectById(questionId);
            difficulty += question.getQuestionDifficulty();
        }
        return difficulty / questionIds.length;
    }

    public Double computeFitness(List<Question> questionList, Float knowledgeWeight, Float examDifficulty,
                                 Float difficultyWeight) {
        // 2-1 计算该套试卷的知识点覆盖率
        List<Integer> knowledgeList = new ArrayList<>();
        for (Question question : questionList) {
            if (!knowledgeList.contains(question.getKnowledgeId())) {
                knowledgeList.add(question.getKnowledgeId());
            }
        }
        double knowledgeCoverage = (double) knowledgeList.size() / questionList.size();

        // 2-2 计算该套试卷的实际难度（各题的平均难度系数，优化情况应该是各题型的分数占比）
        Double difficulty = 0.0;
        for (Question question : questionList) {
            difficulty += question.getQuestionDifficulty();
        }
        Double actualDifficulty = difficulty / questionList.size();

        // = 1 - (1 - knowledgeCoverage) * knowledgeWeight - |examDifficulty - actualDifficulty| * difficultyWeight
        return 1 - (1 - knowledgeCoverage) * knowledgeWeight - Math.abs(examDifficulty - actualDifficulty) * difficultyWeight;
    }

    private Integer getFittestIndex(List<Double> fitnessList) {
        int res = 0;
        Double fittest = Double.MIN_VALUE;
        for (int i = 0; i < fitnessList.size(); i++) {
            if (fitnessList.get(i) > fittest) {
                fittest = fitnessList.get(i);
                res = i;
            }
        }
        return res;
    }

    private List<List<Question>> selectGenerate(Double fitnessSum, List<Double> fitnessList, List<List<Question>> parent) {
        // 3-1 生成个体的选择概率和累积率
        List<Double> probabilityList = new ArrayList<>();
        List<Double> accumulationList = new ArrayList<>();
        for (Double fitness : fitnessList) {
            Double probability = fitness / fitnessSum;
            probabilityList.add(probability);
            accumulationList.add(computeAccumulation(probabilityList));
        }

        // 3-2 采用轮盘赌策略选择m个个体
        List<List<Question>> children = new ArrayList<>();
        for (int i = 0; i < INITIAL_POPULATION_SIZE; i++) {
            Double random = new Random().nextDouble();
            Integer index = selectByCompareAccumulationList(accumulationList, random);
            if (index != accumulationList.size()) {
                children.add(parent.get(index));
            } else { // 未找到，重新生成一个随机数
                i--;
            }
        }

        // 3-3 去除集合中重复的元素
        LinkedHashSet<List<Question>> set = new LinkedHashSet<>(children);
        children.clear();
        children.addAll(set);
        return children;
    }

    private Double computeAccumulation(List<Double> probabilityList) {
        Double res = 0.0;
        for (Double probability : probabilityList) {
            res += probability;
        }
        return res;
    }

    private Integer selectByCompareAccumulationList(List<Double> accumulationList, Double random) {
        for (int i = 0; i < accumulationList.size(); i++) {
            if (random <= accumulationList.get(i)) {
                return i;
            }
        }
        return accumulationList.size();
    }

    private Map<String, Object> crossoverGenerate(List<List<Question>> children) {
        Map<String, Object> ret = new HashMap<>();
        // 4-1 随机选择两个个体配对
        int random1 = new Random().nextInt(children.size());
        int random2 = new Random().nextInt(children.size());
        while (random1 == random2) {
            random1 = new Random().nextInt(children.size());
            random2 = new Random().nextInt(children.size());
        }
        ret.put("oldIndex1", random1);
        ret.put("oldIndex2", random2);
        List<Question> individual1 = children.get(random1);
        List<Question> individual2 = children.get(random2);
        // 4-2 生成[0, 1]之间的随机数，与交叉概率比较
        double random = new Random().nextDouble();
        if (random < PROBABILITY_CROSSOVER) {
            // 4-3 随机选择一个交叉点位置
            int crossoverPoint = new Random().nextInt(individual1.size());
            // 4-4 互换交叉点后的基因
            List<Question> newIndividual1 = new ArrayList<>();
            List<Question> newIndividual2 = new ArrayList<>();
            for (int j = 0; j < individual1.size(); j++) {
                if (j < crossoverPoint) {
                    newIndividual1.add(individual1.get(j));
                    newIndividual2.add(individual2.get(j));
                } else {
                    newIndividual1.add(individual2.get(j));
                    newIndividual2.add(individual1.get(j));
                }
            }
            // 4-5 检查是否存在重复元素
            ret.put("newIndividual1", checkDuplication(newIndividual1));
            ret.put("newIndividual2", checkDuplication(newIndividual2));
        } else {
            return null;
        }
        return ret;
    }

    private List<Question> checkDuplication(List<Question> individual) {
        List<Question> ret = new ArrayList<>();
        for (Question question : individual) {
            if (!ret.contains(question)) {
                ret.add(question);
            } else { // 替换同配置题目
                List<Question> sameConfigureQuestionList =
                        questionMapper.selectSameWithDifficultyAndTypeAndKnowledge(question.getQuestionId(), question.getQuestionDifficulty(),
                                question.getQuestionDifficulty() - 0.2F
                                , question.getQuestionDifficulty() + 0.2F,
                                question.getTypeId(), question.getKnowledgeId());
                if (!sameConfigureQuestionList.isEmpty()) {
                    for (Question sameConfigureQuestion : sameConfigureQuestionList) {
                        if (!individual.contains(sameConfigureQuestion)) {
                            ret.add(sameConfigureQuestion);
                            break;
                        }
                    }
                } else {
                    sameConfigureQuestionList =
                            questionMapper.selectSameWithTypeAndKnowledge(question.getQuestionId(), question.getQuestionDifficulty(),
                                    question.getTypeId(), question.getKnowledgeId());
                    for (Question sameConfigureQuestion : sameConfigureQuestionList) {
                        if (!individual.contains(sameConfigureQuestion)) {
                            ret.add(sameConfigureQuestion);
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }

    private List<Question> mutationGenerate(List<Question> questionList) {
        for (int i = 0; i < questionList.size(); i++) {
            // 5-2 决定该基因位是否发生突变
            double random = new Random().nextDouble();
            if (random < PROBABILITY_MUTATION) {
                Question question = questionList.get(i);
                List<Question> sameConfigureQuestionList =
                        questionMapper.selectSameWithDifficultyAndTypeAndKnowledge(question.getQuestionId(), question.getQuestionDifficulty(),
                                question.getQuestionDifficulty() - 0.2F
                                , question.getQuestionDifficulty() + 0.2F,
                                question.getTypeId(), question.getKnowledgeId());
                if (!sameConfigureQuestionList.isEmpty()) {
                    for (Question sameConfigureQuestion : sameConfigureQuestionList) {
                        if (!questionList.contains(sameConfigureQuestion)) {
                            questionList.set(i, sameConfigureQuestion);
                            break;
                        }
                    }
                } else {
                    sameConfigureQuestionList =
                            questionMapper.selectSameWithTypeAndKnowledge(question.getQuestionId(), question.getQuestionDifficulty(),
                                    question.getTypeId(), question.getKnowledgeId());
                    for (Question sameConfigureQuestion : sameConfigureQuestionList) {
                        if (!questionList.contains(sameConfigureQuestion)) {
                            questionList.set(i, sameConfigureQuestion);
                            break;
                        }
                    }
                }
            }
        }
        return questionList;
    }

    @Override
    public List<Question> intelligentGenerate(List<List<Question>> parent, Float knowledgeWeight,
                                              Float examDifficulty,
                                              Float difficultyWeight, Integer iteration) {
        if (iteration < GA_ITERATIONS_MAX) {
            // 2. 计算适应度值fitness
            List<Double> fitnessList = new ArrayList<>();
            double fitnessSum = 0.0;
            for (List<Question> chromosome : parent) {
                Double fitness = computeFitness(chromosome, knowledgeWeight, examDifficulty, difficultyWeight);
                if (fitness == 1) { // fitness 趋向于 1
                    return chromosome;
                } else {
                    fitnessList.add(fitness);
                    fitnessSum += fitness;
                }
            }

            // 3. 选择selection
            List<List<Question>> children = selectGenerate(fitnessSum, fitnessList, parent);

            // 4. 交叉crossover
            int childrenLength = children.size();
            List<List<Question>> newChildren = new ArrayList<>();
            while (newChildren.size() < childrenLength) {
                Map<String, Object> ret = crossoverGenerate(children);
                if (null != ret) {
                    // 4-6 计算新个体的适应度值
                    List<Question> newIndividual1 = (List<Question>) ret.get("newIndividual1");
                    Double fitness1 = computeFitness(newIndividual1, knowledgeWeight, examDifficulty,
                            difficultyWeight);
                    if (fitness1 > fitnessList.get((Integer) ret.get("oldIndex1"))) {
                        newChildren.add(newIndividual1);
                        children.remove((Integer) ret.get("oldIndex1"));
                    }
                    List<Question> newIndividual2 = (List<Question>) ret.get("newIndividual2");
                    Double fitness2 = computeFitness(newIndividual2, knowledgeWeight, examDifficulty,
                            difficultyWeight);
                    if (fitness2 > fitnessList.get((Integer) ret.get("oldIndex2"))) {
                        newChildren.add(newIndividual2);
                        children.remove((Integer) ret.get("oldIndex2"));
                    }
                }
            }
            children.clear();
            children.addAll(newChildren);

            // 5. 突变mutation
            for (int i = 0; i < children.size(); i++) {
                // 5-1 决定该个体是否发生突变
                double random = new Random().nextDouble();
                if (random < PROBABILITY_MUTATION) {
                    // add: insert; set: replace
                    children.set(i, mutationGenerate(children.get(i)));
                }
            }

            // ADDITION: 按照父代适应度值大小，补全子代
            childrenLength = children.size();
            for (int i = 0; i < INITIAL_POPULATION_SIZE - childrenLength; i++) {
                if (!fitnessList.isEmpty()) {
                    Integer index = getFittestIndex(fitnessList);
                    if (children.contains(parent.get(index))) {
                        i--;
                    } else {
                        children.add(parent.get(index));
                    }
                    parent.remove(parent.get(index));
                    fitnessList.remove(fitnessList.get(index));
                } else {
                    children.add(mutationGenerate(children.get(new Random().nextInt(children.size()))));
                }
            }
            // 6. 重复迭代
            return intelligentGenerate(children, knowledgeWeight, examDifficulty, difficultyWeight, iteration + 1);
        } else { // 达到迭代次数上限
            double fitnessMax = 0.0;
            int index = -1;
            for (int i = 0; i < parent.size(); i++) {
                Double fitness = computeFitness(parent.get(i), knowledgeWeight, examDifficulty, difficultyWeight);
                if (fitness == 1) { // fitness 趋向于 1
                    return parent.get(i);
                } else if (fitness > fitnessMax) {
                    fitnessMax = fitness;
                    index = i;
                }
            }
            return parent.get(index);
        }
    }

    @Override
    @Transactional
    public Boolean saveQuestion(Question question) {
        String[] answers = new String[]{""};
        if (null != question.getCorrect()) {
            answers = question.getCorrect().split(TEST_QUESTION_LIST_SPLIT);
            question.setCorrect(null);
        }
        int result = questionMapper.insert(question);
        if (3 != question.getTypeId() && result == 1) {
            switch (question.getTypeId()) {
                case 1: // choice
                    String correct = answers[0];
                    for (int i = 1; i < answers.length; i++) {
                        String[] arr = answers[i].split(" "); // sign content
                        Answer answer = new Answer(arr[1], arr[2], correct.equals(arr[1]) ? 1 : 0, question.getQuestionId());
                        result += answerMapper.insert(answer);
                    }
                    return result == answers.length;
                case 2: // judge
                    Answer answer = new Answer(Boolean.parseBoolean(answers[0]) ? "1" : "0", 1, question.getQuestionId());
                    return answerMapper.insert(answer) != 0;
            }
        } else {
            return 3 == question.getTypeId() && result == 1;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateQuestion(Question question) {
        String[] answers = question.getCorrect().split(TEST_QUESTION_LIST_SPLIT);
        question.setCorrect(null);
        int result = questionMapper.updateById(question);
        if (!((Integer) 3).equals(question.getTypeId()) && result == 1) {
            switch (question.getTypeId()) {
                case 1: // choice
                    String correct = answers[0];
                    List<Integer> answerIds = answerMapper.findIdByQuestionId(question.getQuestionId());
                    int insertResult = 1;
                    for (int i = 1; i < answers.length; i++) {
                        String[] arr = answers[i].split(" "); // id sign content
                        if (!arr[0].isEmpty()) { // update option
                            Integer id = Integer.valueOf(arr[0]);
                            Answer answer = new Answer(id, arr[1], arr[2], correct.equals(arr[1]) ? 1 : 0,
                                    question.getQuestionId());
                            insertResult += answerMapper.updateById(answer);
                            answerIds.remove(id);
                        } else { // insert new option
                            Answer answer = new Answer(arr[1], arr[2], correct.equals(arr[1]) ? 1 : 0,
                                    question.getQuestionId());
                            insertResult += answerMapper.insert(answer);
                        }
                    }
                    if (answerIds.size() != 0) { // something need to delete
                        answerMapper.deleteBatchIds(answerIds);
                    }
                    return insertResult == answers.length;
                case 2: // judge
                    QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
                    answerQueryWrapper.eq("question_id", question.getQuestionId());
                    Answer answer = answerMapper.selectOne(answerQueryWrapper);
                    answer.setAnswerSign(Boolean.parseBoolean(answers[0]) ? "1" : "0");
                    return answerMapper.updateById(answer) != 0;
            }
        } else {
            return ((Integer) 3).equals(question.getTypeId()) && result == 1;
        }
        return false;
    }

    @Override
    @Transactional
    public Integer deleteQuestion(Integer[] id) {
        List<Integer> ids = new ArrayList<>(Arrays.asList(id));
        return questionMapper.deleteBatchIds(ids);
    }
}
