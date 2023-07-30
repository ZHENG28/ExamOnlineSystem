<template>
  <div>
    <h2>{{ status }}测验信息</h2>
    <el-form
      :model="testForm"
      :rules="testFormRules"
      ref="testForm"
      label-width="100px"
      label-position="right"
      style="padding: 20px 0"
    >
      <el-row>
        <el-col :span="8">
          <el-form-item label="测验标题" prop="testName">
            <el-input
              v-model="testForm.testName"
              placeholder="请填写测验标题"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="测验日期" prop="date">
            <el-date-picker
              v-model="testForm.date"
              type="datetimerange"
              value-format="YYYY-MM-DD HH:mm:ss"
              format="YYYY-MM-DD HH:mm:ss"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="测验简介" prop="description">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2 }"
            v-model="testForm.description"
            style="width: 1100px"
            maxlength="50"
            show-word-limit
          ></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="所属科目" prop="subjectId">
            <el-select
              filterable
              placeholder="请选择科目"
              @change="changeSubjectId"
              v-model="testForm.subjectId"
            >
              <el-option
                v-for="subject in subjectList"
                :key="subject.subjectId"
                :label="subject.subjectName"
                :value="subject.subjectId"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="组卷方式" prop="generateWay">
            <el-select
              filterable
              placeholder="请选择组卷方式"
              @change="changeGeneratePaperWay"
              v-model="testForm.generateWay"
            >
              <el-option :key="1" label="手动组卷" :value="1"> </el-option>
              <el-option :key="2" label="智能组卷" :value="2"> </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="questionList">
            <el-button
              v-if="testForm.generateWay != '2'"
              type="primary"
              @click="
                findQuestionBySubjectId(testForm.subjectId);
                manualVisible = true;
              "
              :disabled="testForm.subjectId == '' || testForm.generateWay == ''"
              style="margin-right: 30px"
              >选择考题</el-button
            >
            <el-button
              v-if="testForm.generateWay == '2'"
              type="primary"
              @click="intelligentGenerate()"
              :disabled="testForm.subjectId == '' || testForm.generateWay == ''"
              style="margin-right: 30px"
              >生成试卷</el-button
            >
            <span style="display: inline; color: red">
              {{ "共" + testForm.questionTotal + "道题" }}
            </span>
            <div class="dialog-container">
              <el-dialog
                title="添加考试题目"
                v-model="manualVisible"
                width="1200px"
                top="20px"
              >
                <div class="transfer-container">
                  <el-transfer
                    :data="subjectQuestionList"
                    v-model="testForm.questionList"
                    :titles="['题库', '考题']"
                    filterable
                    filter-placeholder="输入题目信息进行搜索"
                    :left-default-checked="randomCheck"
                  >
                    <template #default="{ option }">
                      <el-tag
                        :type="
                          option.type == '判断题'
                            ? 'success'
                            : option.type == '简答题'
                            ? 'danger'
                            : ''
                        "
                        >{{ option.type }}</el-tag
                      >
                      <span style="margin-left: 10px">{{ option.label }}</span>
                    </template>
                  </el-transfer>
                </div>
                <template #footer>
                  <span class="dialog-footer">
                    <div style="float: left; display: inline">
                      <el-button type="primary" @click="randomSelect()"
                        >随机组卷</el-button
                      >
                    </div>
                    <div style="display: inline">
                      <el-button @click="manualVisible = false"
                        >取 消</el-button
                      >
                      <el-button type="primary" @click="saveQuestionList()"
                        >确 定</el-button
                      >
                    </div>
                  </span>
                </template>
              </el-dialog>
              <el-dialog
                title="查看考试题目"
                v-model="intelligentVisible"
                z-index="1000"
                width="1000px"
                top="70px"
              >
                <el-table :data="intelligentQuestionList" border height="540px">
                  <el-table-column type="index" label="序号" width="60">
                  </el-table-column>
                  <el-table-column prop="typeName" label="题目类型" width="85">
                    <template #default="scope">
                      <el-tag
                        :type="
                          scope.row.typeName == '判断题'
                            ? 'success'
                            : scope.row.typeName == '简答题'
                            ? 'danger'
                            : ''
                        "
                      >
                        {{ scope.row.typeName }}
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column prop="questionTitle" label="题目信息">
                  </el-table-column>
                  <el-table-column
                    prop="knowledgeContent"
                    label="关联知识点"
                    width="200"
                  >
                  </el-table-column>
                </el-table>
              </el-dialog>
            </div>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="testForm.subjectId != '' && testForm.generateWay == '2'">
        <el-col :span="6">
          <el-form-item label="预设题数" prop="presetQuestionTotal">
            <el-input-number
              v-model="testForm.presetQuestionTotal"
              :step="1"
              :min="1"
              :max="subjectQuestionList.length"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="预设难度" prop="presetTestDifficulty">
            <el-input-number
              v-model="testForm.presetTestDifficulty"
              :precision="2"
              :step="0.01"
              :min="0"
              :max="1"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="难度权重" prop="difficultyWeight">
            <el-input-number
              v-model="testForm.difficultyWeight"
              :precision="2"
              :step="0.01"
              :min="0"
              :max="1"
              @change="valueToKnowledgeWeight"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="知识点权重" prop="knowledgeWeight">
            <el-input-number
              v-model="testForm.knowledgeWeight"
              :precision="2"
              :step="0.01"
              :min="0"
              :max="1"
              @change="valueToDifficultyWeight"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="测验时长" prop="testDuration">
            <el-input-number
              v-model="testForm.testDuration"
              :step="30"
              :min="0"
              placeholder="0"
            />
            分钟
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="测验次数" prop="testTime">
            <el-input-number
              v-model="testForm.testTime"
              :step="1"
              :min="0"
              placeholder="1"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item>
            <el-button
              type="primary"
              @click="shortAnswerVisible = true"
              :style="{
                marginRight: '30px',
                visibility: shortAnswerBtnVisible ? 'hidden' : 'visible',
              }"
              >设置简答题相似度阈值</el-button
            >
            <div class="dialog-container">
              <el-dialog
                title="设置简答题相似度阈值"
                v-model="shortAnswerVisible"
                width="600px"
              >
                <el-form
                  :model="shortAnswerList"
                  :rules="shortAnswerFormRules"
                  label-width="200px"
                  label-position="left"
                >
                  <el-form-item
                    v-for="(shortAnswer, index) in shortAnswerList"
                    :key="index"
                    :label="shortAnswer.questionTitle"
                    prop="threshold"
                  >
                    <el-slider v-model="shortAnswer.threshold" size="small" />
                  </el-form-item>
                </el-form>
                <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="shortAnswerVisible = false"
                      >关 闭</el-button
                    >
                  </span>
                </template>
              </el-dialog>
            </div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div>
      <el-button @click="toTestInfo()">返回列表</el-button>
      <el-button type="primary" @click="save()">确 定</el-button>
      <el-button
        v-if="testForm.questionTotal != 0"
        type="primary"
        @click="loadCurrentTestPaper()"
        >预览试卷</el-button
      >
      <span style="display: inline; color: red; margin-left: 20px">
        测验开始后{{
          testForm.date[0] != undefined
            ? "（即" + testForm.date[0] + "之后）"
            : ""
        }}，将无法修改测验信息！
        {{
          testForm.subjectId != "" && testForm.generateWay == "2"
            ? "（权重参数涉及智能生成的试卷更侧重于哪个方面，请慎重设置）"
            : ""
        }}
      </span>
      <el-dialog
        title="查看当前生成的试卷"
        v-model="currentTestPaperVisible"
        width="1200px"
        top="70px"
      >
        <el-scrollbar :height="mainHeight">
          <div>
            <div
              style="padding: 0px 20px"
              v-for="(question, index) in currentQuestionList"
              :key="question.questionId"
            >
              <choice-question
                v-if="question.typeName == '选择题'"
                :number="index + 1"
                :title="question.questionTitle"
                :answers="question.answer"
                :isable="false"
              ></choice-question>
              <judge-question
                v-if="question.typeName == '判断题'"
                :number="index + 1"
                :questionId="question.questionId"
                :title="question.questionTitle"
                :isable="false"
              ></judge-question>
              <short-answer-question
                v-if="question.typeName == '简答题'"
                :number="index + 1"
                :questionId="question.questionId"
                :title="question.questionTitle"
                :isable="true"
              ></short-answer-question>
              <span style="color: red"
                >正确答案：{{
                  question.correct == "true"
                    ? "√"
                    : question.correct == "false"
                    ? "×"
                    : question.correct == null
                    ? "无"
                    : question.correct
                }}</span
              >
            </div>
          </div>
        </el-scrollbar>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";

import choiceQuestion from "@/components/ChoiceQuestion.vue";
import judgeQuestion from "@/components/JudgeQuestion.vue";
import shortAnswerQuestion from "@/components/ShortAnswerQuestion.vue";
export default {
  components: {
    choiceQuestion,
    judgeQuestion,
    shortAnswerQuestion,
  },
  data() {
    return {
      status: "",
      manualVisible: false,
      intelligentVisible: false,
      testForm: {
        testId: "",
        testName: "",
        description: "",
        date: [],
        beginDate: "",
        endDate: "",
        testDuration: 0,
        testTime: 1,
        questionTotal: 0,
        subjectId: "",
        subjectName: "",
        questionList: [],
        shortAnswer: [],
        presetQuestionTotal: 10,
        presetTestDifficulty: 0.5,
        knowledgeWeight: 0.5,
        difficultyWeight: 0.5,
        generateWay: "",
      },
      testFormRules: {
        testName: [
          { required: true, message: "请填写本次测验标题", trigger: "blur" },
        ],
        description: [
          { required: false, message: "请填写本次测验简介", trigger: "blur" },
        ],
        date: [
          { required: true, message: "请填写开始-结束时间", trigger: "blur" },
        ],
        testDuration: [
          {
            required: true,
            message: "请填写测验时长",
            trigger: "blur",
          },
          {
            type: "number",
            asyncValidator: (rule, value) => {
              return new Promise((resolve, reject) => {
                if (value == 0) {
                  reject("测验时长不能为0，请填写");
                } else {
                  resolve();
                }
              });
            },
          },
        ],
        testTime: [
          {
            required: true,
            message: "请填写测验次数",
            trigger: "blur",
          },
          {
            type: "number",
            asyncValidator: (rule, value) => {
              return new Promise((resolve, reject) => {
                if (value == 0) {
                  reject("测验次数不能为0，请填写");
                } else {
                  resolve();
                }
              });
            },
          },
        ],
        subjectId: [
          { required: true, message: "请选择所属科目", trigger: "blur" },
        ],
        generateWay: [
          { required: true, message: "请选择组卷方式", trigger: "blur" },
        ],
        questionList: [
          { required: true, message: "请选择考试题目", trigger: "blur" },
        ],
      },

      shortAnswerVisible: false,
      shortAnswerBtnVisible: true,
      shortAnswerList: [],
      shortAnswerFormRules: {
        threshold: [{ required: true, message: "请填写阈值", trigger: "blur" }],
      },

      subjectList: [],
      subjectQuestionList: [],
      randomCheck: [],
      intelligentQuestionList: [],
      testDifficulty: 0,

      currentTestPaperVisible: false,
      currentQuestionList: [],
      mainHeight: "565px",
    };
  },
  created() {
    if (this.$route.params.testId == 0) {
      this.status = "发布";
      this.loadData();
    } else {
      this.status = "修改";
      this.testForm.testId = this.$route.params.testId;
      this.subjectQuestionList.length = 100;
      this.loadInfo(this.$route.params.testId);
    }
    this.loadData();
  },
  methods: {
    // 初始化页面
    loadData() {
      this.loadSubjectByTeacherId();
    },
    toTestInfo() {
      this.$router.push("/teacher/testInfo");
    },
    formatDate(date) {
      return date
        .toLocaleString()
        .replace(/T/g, " ")
        .replace(/\.[\d]{3}Z/, "");
    },

    loadSubjectByTeacherId() {
      this.$axios
        .get("/subject/loadSubjectByTeacherId", {
          headers: { Authorization: userToken() },
          params: { teacherId: this.$storage.getStorageSync("user").id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.subjectList = res;
          }
        });
    },
    changeSubjectId() {
      this.testForm.questionList = [];
      this.testForm.questionTotal = 0;
      this.shortAnswerBtnVisible = this.haveShortAnswer();
      if (this.testForm.generateWay == "2") {
        this.findQuestionBySubjectId(this.testForm.subjectId);
      }
    },
    changeGeneratePaperWay() {
      this.testForm.questionList = [];
      this.testForm.questionTotal = 0;
      this.shortAnswerBtnVisible = this.haveShortAnswer();
      if (this.testForm.generateWay == "2" && this.testForm.subjectId != "") {
        this.findQuestionBySubjectId(this.testForm.subjectId);
      }
    },

    findQuestionBySubjectId(id) {
      return this.$axios
        .get("/question/findQuestionBySubjectId", {
          headers: { Authorization: userToken() },
          params: { subjectId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.subjectQuestionList = [];
            res.forEach((elem) => {
              this.subjectQuestionList.push({
                key: elem.questionId,
                label: elem.questionTitle,
                type: elem.typeName,
              });
            });
          }
        });
    },
    getRandom(begin, end) {
      // [begin, end]
      return Math.round(Math.random() * (end - begin) + begin);
    },
    randomSelect() {
      let num = this.getRandom(1, this.subjectQuestionList.length);
      let keys = [],
        result = [];
      this.subjectQuestionList.forEach((item) => {
        keys.push(item.key);
      });
      let i = 0;
      while (i < num) {
        let questionIndex = this.getRandom(0, keys.length - 1);
        result.push(keys[questionIndex]);
        keys.splice(questionIndex, 1);
        i++;
      }
      this.randomCheck = result;
    },
    saveQuestionList() {
      this.$axios
        .post(
          "/question/calculateActualDifficulty",
          this.$qs.stringify(
            { questionIds: this.testForm.questionList },
            { indices: false }
          ),
          { headers: { Authorization: userToken() } }
        )
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.testDifficulty = res;
            this.testForm.questionTotal = this.testForm.questionList.length;
            this.shortAnswerBtnVisible = this.haveShortAnswer();
            this.manualVisible = false;
          }
        });
    },

    valueToKnowledgeWeight() {
      this.testForm.knowledgeWeight = 1 - this.testForm.difficultyWeight;
    },
    valueToDifficultyWeight() {
      this.testForm.difficultyWeight = 1 - this.testForm.knowledgeWeight;
    },
    intelligentGenerate() {
      this.$axios
        .get("/question/intelligentGenerate", {
          headers: { Authorization: userToken() },
          params: {
            subjectId: this.testForm.subjectId,
            presetQuestionTotal: this.testForm.presetQuestionTotal,
            presetTestDifficulty: this.testForm.presetTestDifficulty,
            difficultyWeight: this.testForm.difficultyWeight,
            knowledgeWeight: this.testForm.knowledgeWeight,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          this.$message({
            type: response.data.success ? "success" : "error",
            message: response.data.message,
          });
          if (res) {
            this.testForm.questionList = [];
            this.intelligentQuestionList = res;
            let difficultySum = 0;
            this.intelligentQuestionList.forEach((elem) => {
              this.testForm.questionList.push(elem.questionId);
              difficultySum += elem.questionDifficulty;
            });
            this.testForm.questionTotal = this.testForm.questionList.length;
            this.testDifficulty = difficultySum / this.testForm.questionTotal;
            this.shortAnswerBtnVisible = this.haveShortAnswer();
            this.intelligentVisible = true;
          }
        })
        .catch(function (error) {
          this.$message.error("生成出错");
          console.log(error);
        });
    },

    haveShortAnswer() {
      this.shortAnswerList = [];
      this.testForm.questionList.forEach((elem) => {
        this.subjectQuestionList.forEach((item) => {
          if (elem == item.key && item.type == "简答题") {
            this.shortAnswerList.push({
              questionId: item.key,
              questionTitle: item.label,
              threshold: 50,
            });
          }
        });
      });
      return this.shortAnswerList.length == 0;
    },

    loadCurrentTestPaper() {
      this.$axios
        .post(
          "/question/findQuestionListByQuestionIds",
          this.$qs.stringify(
            { questionIds: this.testForm.questionList },
            { indices: false }
          ),
          { headers: { Authorization: userToken() } }
        )
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.currentQuestionList = res;
            this.currentTestPaperVisible = true;
          }
        });
    },

    loadInfo(id) {
      this.$axios
        .get("/test/findById", {
          headers: { Authorization: userToken() },
          params: { testId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.testForm = res.test;
            this.testForm.date = [
              this.formatDate(this.testForm.beginDate),
              this.formatDate(this.testForm.endDate),
            ];
            this.testForm.questionList = res.questionList;
            this.testForm.shortAnswer = res.shortAnswer;
            if (this.testForm.shortAnswer.length != 0) {
              this.findQuestionBySubjectId(this.testForm.subjectId).then(
                (response) => {
                  this.testForm.shortAnswer.forEach((item) => {
                    let s = item.split(" ");
                    this.subjectQuestionList.forEach((elem) => {
                      if (s[0] == elem.key && elem.type == "简答题") {
                        this.shortAnswerList.push({
                          questionId: s[0],
                          questionTitle: elem.label,
                          threshold: parseInt(s[1]),
                        });
                      }
                    });
                  });
                  this.shortAnswerBtnVisible = this.shortAnswerList.length == 0;
                }
              );
            }
          }
        });
    },
    save() {
      this.$refs.testForm.validate((valid) => {
        if (valid) {
          if (this.shortAnswerList.length != 0) {
            this.testForm.shortAnswer = [];
            this.shortAnswerList.forEach((item) => {
              this.testForm.shortAnswer.push(
                item.questionId + " " + item.threshold
              );
            });
          }
          this.$axios
            .post(
              "/test/save",
              this.$qs.stringify(
                {
                  testId: this.testForm.testId,
                  testName: this.testForm.testName,
                  description: this.testForm.description,
                  beginDate: this.testForm.date[0],
                  endDate: this.testForm.date[1],
                  testDuration: this.testForm.testDuration,
                  testTime: this.testForm.testTime,
                  questionTotal: this.testForm.questionTotal,
                  subjectId: this.testForm.subjectId,
                  questionList: this.testForm.questionList,
                  shortAnswer: this.testForm.shortAnswer,
                  testDifficulty: this.testDifficulty.toFixed(2),
                  status: this.status,
                },
                { indices: false }
              ),
              {
                headers: { Authorization: userToken() },
              }
            )
            .then((response) => {
              this.$message({
                type: response.data.success ? "success" : "error",
                message: response.data.message,
              });
              if (response.data.success) {
                this.$router.push("/teacher/testInfo");
              }
            })
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
            });
        }
      });
    },
  },
};
</script>
<style scoped>
.el-input {
  width: 250px;
}
.el-row {
  margin: 20px 0;
}
.el-form-item {
  margin-bottom: 20px;
}
.dialog-container >>> .el-dialog {
  margin-bottom: 0px;
}
.transfer-container >>> .el-transfer-panel {
  width: 488px;
}
.transfer-container >>> .el-transfer-panel__body {
  height: 500px;
}
</style>
