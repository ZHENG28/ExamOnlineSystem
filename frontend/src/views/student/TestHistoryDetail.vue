<template>
  <el-container direction="horizontal">
    <el-aside width="300px" style="margin-top: 20px; margin-left: 20px">
      <el-page-header
        :content="testForm.testName"
        @back="toTestHistory"
        style="margin-bottom: 30px"
      />
      <el-card shadow="never" :body-style="{ padding: 0 }">
        <template #header>
          <el-select
            filterable
            placeholder="请选择要查看的测验记录"
            @change="valueToChangeHistory"
            v-model="historyNumber"
          >
            <el-option
              v-for="(str, index) in completeList"
              :key="index"
              :label="str"
              :value="index"
            >
            </el-option>
          </el-select>
        </template>
        <el-table
          border
          :data="historyTableData"
          :show-header="false"
          table-layout="auto"
          :row-style="{ height: '50px' }"
          style="font-weight: bold"
        >
          <el-table-column
            v-for="(item, index) in historyTableTitle"
            :key="index"
            align="center"
          >
            <template #default="scope">
              {{ scope.row[index] }}
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <el-card
        shadow="never"
        :body-style="{ padding: 0 }"
        style="margin-top: 30px"
      >
        <template #header>
          <span>本次测验详情</span>
        </template>
        <el-table
          border
          :data="testHistoryInfoTable"
          :show-header="false"
          table-layout="auto"
          :row-style="{ height: '50px' }"
          style="font-weight: bold"
        >
          <el-table-column
            v-for="(item, index) in historyTableTitle"
            :key="index"
            align="center"
          >
            <template #default="scope">
              {{ scope.row[index] }}
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-aside>
    <el-main>
      <el-scrollbar :height="mainHeight">
        <div>
          <div
            style="padding: 0px 20px"
            v-for="(question, index) in testForm.questionList"
            :key="question.questionId"
          >
            <choice-question
              :key="questionRefreshKey"
              v-if="question.typeName == '选择题'"
              :number="index + 1"
              :title="question.questionTitle"
              :answers="question.answer"
              :choose="reply[index]"
              :isable="false"
            ></choice-question>
            <judge-question
              :key="questionRefreshKey"
              v-if="question.typeName == '判断题'"
              :number="index + 1"
              :questionId="question.questionId"
              :title="question.questionTitle"
              :choose="reply[index]"
              :isable="false"
            ></judge-question>
            <short-answer-question
              :key="questionRefreshKey"
              v-if="question.typeName == '简答题'"
              :number="index + 1"
              :questionId="question.questionId"
              :title="question.questionTitle"
              :content="reply[index]"
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
    </el-main>
  </el-container>
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
      studentId: "",
      completeList: [],
      mainHeight: "",
      // 考试信息
      testForm: {
        testId: "",
        testName: "",
        questionTotal: 0,
        questionList: [],
        testTime: 0,
        completeTime: 0,
      },

      historyId: [],
      historyTableTitle: ["key", "value"],
      historyTableData: [],
      testHistoryInfoTable: [],
      historyNumber: 0,

      questionRefreshKey: 0,
      reply: [],
    };
  },
  created() {
    this.mainHeight = document.documentElement.clientHeight - 50 + "px";
    this.studentId = this.$route.params.studentId;
    this.testForm.testId = this.$route.params.testId;
    this.loadData();
  },
  methods: {
    loadData() {
      this.findTestInfoByCompoundId(this.testForm.testId, this.studentId);
      this.findHistoryByCompoundId().then((response) => {
        this.loadTestQuestionList().then((response) => {
          this.loadTestHistoryReply();
        });
      });
    },
    toTestHistory() {
      let user = this.$storage.getStorageSync("user");
      if (user && user.roles) {
        if (user.roles.includes("ROLE_TEACHER")) {
          this.$router.push(`/teacher/testResult/${this.testForm.testId}`);
        } else if (user.roles.includes("ROLE_STUDENT")) {
          this.$router.push("/student/testHistory");
        } else {
          console.log("test history detail return error!");
        }
      }
    },
    findTestInfoByCompoundId(testId, studentId) {
      this.$axios
        .get("/testHistory/findTestInfoByCompoundId", {
          headers: { Authorization: userToken() },
          params: {
            testId: testId,
            studentId: studentId,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.testForm = res;
            for (let i = 1; i <= res.completeTime; i++) {
              this.completeList.push("第" + i + "次测验记录");
              this.testHistoryInfoTable = [
                ["所属科目", res.subjectName],
                [
                  "测验开始时间",
                  res.beginDate
                    .toLocaleString()
                    .replace(/T/g, " ")
                    .replace(/\.[\d]{3}Z/, ""),
                ],
                [
                  "测验结束时间",
                  res.endDate
                    .toLocaleString()
                    .replace(/T/g, " ")
                    .replace(/\.[\d]{3}Z/, ""),
                ],
                ["总计题数", res.questionTotal],
                ["最高成绩", res.maxCorrect],
              ];
            }
          }
        });
    },
    findHistoryByCompoundId() {
      return this.$axios
        .get("/testHistory/findHistoryByCompoundId", {
          headers: { Authorization: userToken() },
          params: {
            testId: this.testForm.testId,
            studentId: this.studentId,
            order: this.historyNumber,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.historyId = res.historyId;
            this.historyTableData = [
              [
                "完成时间",
                res.finishTime
                  .toLocaleString()
                  .replace(/T/g, " ")
                  .replace(/\.[\d]{3}Z/, ""),
              ],
              [
                "作答耗时",
                (Math.floor(res.takeTime / 3600) != 0
                  ? Math.floor(res.takeTime / 3600) + "小时"
                  : "") +
                  Math.floor(res.takeTime / 60) +
                  "分钟" +
                  (res.takeTime % 60) +
                  "秒",
              ],
              ["答对题数", res.correct],
            ];
          }
        });
    },
    loadTestQuestionList() {
      return this.$axios
        .get("/question/findQuestionListByTestId", {
          headers: { Authorization: userToken() },
          params: { testId: this.testForm.testId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.testForm.questionTotal = res.length;
            this.testForm.questionList = res;
          }
        });
    },
    loadTestHistoryReply() {
      this.$axios
        .get("/testHistoryQuestionReply/findTestHistoryReplyByHistoryId", {
          headers: { Authorization: userToken() },
          params: { historyId: this.historyId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.reply = [];
            this.testForm.questionList.forEach((item) => {
              let flag = false;
              res.forEach((elem) => {
                if (elem.questionId == item.questionId) {
                  this.reply.push(elem.questionId + " " + elem.reply);
                  flag = true;
                }
              });
              if (!flag) {
                this.reply.push("");
              }
            });
            this.questionRefreshKey = new Date();
          }
        });
    },
    valueToChangeHistory(val) {
      this.historyNumber = val;
      this.findHistoryByCompoundId().then((response) => {
        this.loadTestHistoryReply();
      });
    },
  },
};
</script>

<style scoped>
.el-card {
  height: fit-content;
  text-align: center;
}
</style>