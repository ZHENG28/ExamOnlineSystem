<!-- 考生答题界面 -->
<template>
  <el-container direction="horizontal">
    <!-- 左侧-题目索引 -->
    <el-aside width="260px" style="margin-top: 20px; margin-left: 20px">
      <el-card shadow="never" style="margin-bottom: 20px">
        <template #header>
          <span> {{ testForm.testName }} </span>
        </template>
        <el-row>
          <el-col
            :span="8"
            v-for="(item, i) in ['当前', '未答', '已答']"
            :key="i"
          >
            <div style="text-align: center">
              <el-button
                size="small"
                circle
                disabled
                :id="'logo-' + i"
                style="cursor: auto; margin-bottom: 5px"
              ></el-button>
              <span
                style="
                  flex-direction: column;
                  display: flex;
                  align-items: center;
                "
                >{{ item }}</span
              >
            </div>
          </el-col>
        </el-row>
      </el-card>
      <el-card
        shadow="hover"
        style="margin-bottom: 10px"
        v-for="(type, index) in testForm.questionList"
        :key="index"
      >
        <template #header>
          <span style="font-weight: bold">{{ type.name }}</span>
        </template>
        <el-row
          v-for="(list, i) in type.list"
          :key="i"
          :style="{ marginBottom: i + 1 == list.length ? '' : '5px' }"
        >
          <el-col
            :span="Math.floor(24 / CARD_COL_NUMBER)"
            v-for="(item, j) in list"
            :key="j"
          >
            <div style="text-align: center">
              <el-button
                circle
                size="large"
                :class="{
                  'index-btn-border':
                    sequence ==
                    j +
                      1 +
                      i * CARD_COL_NUMBER +
                      (0 +
                        (index > 0 ? testForm.questionList[0].length : 0) +
                        (index > 1 ? testForm.questionList[1].length : 0)),
                  'index-btn-style':
                    reply[
                      j +
                        i * CARD_COL_NUMBER +
                        (0 +
                          (index > 0 ? testForm.questionList[0].length : 0) +
                          (index > 1 ? testForm.questionList[1].length : 0))
                    ] != undefined,
                }"
                style="
                  cursor: auto;
                  margin-bottom: 5px;
                  font-weight: bold;
                  font-size: 16px;
                "
                @click="
                  jumpTo(
                    j +
                      1 +
                      i * CARD_COL_NUMBER +
                      (0 +
                        (index > 0 ? testForm.questionList[0].length : 0) +
                        (index > 1 ? testForm.questionList[1].length : 0))
                  )
                "
              >
                {{
                  j +
                  1 +
                  i * CARD_COL_NUMBER +
                  (0 +
                    (index > 0 ? testForm.questionList[0].length : 0) +
                    (index > 1 ? testForm.questionList[1].length : 0))
                }}
              </el-button>
            </div>
          </el-col>
        </el-row>
      </el-card>
      <div style="display: block; text-align: center">
        <el-button type="primary" @click="finish()">结束考试</el-button>
      </div>
    </el-aside>
    <!-- 右侧-答题区域 -->
    <el-main>
      <div
        style="display: flex; line-height: 50px; margin: 0 20px"
        v-if="testInfoFlag"
      >
        <span> 全卷共{{ testForm.questionTotal }}题 </span>
        <span style="margin-left: auto">
          <count-down
            :key="countDownKey"
            :flag="timekeep"
            :remainTime="remainSecond"
            @returnTakeTime="getTime"
          ></count-down>
        </span>
      </div>
      <div style="padding: 0px 20px" v-if="testPaperFlag">
        <choice-question
          :key="questionRefreshKey"
          v-if="
            sequence > 0 && sequence <= testForm.questionList[CHOICE_NO].length
          "
          :number="sequence"
          :title="
            testForm.questionList[CHOICE_NO].list[
              questionRowCol[CHOICE_NO * 2]
            ][questionRowCol[CHOICE_NO * 2 + 1]].questionTitle
          "
          :answers="
            testForm.questionList[CHOICE_NO].list[
              questionRowCol[CHOICE_NO * 2]
            ][questionRowCol[CHOICE_NO * 2 + 1]].answer
          "
          :choose="reply[sequence - 1]"
          @returnAnswer="getReply"
        ></choice-question>
        <judge-question
          :key="questionRefreshKey"
          v-if="
            sequence > testForm.questionList[CHOICE_NO].length &&
            sequence <=
              testForm.questionList[CHOICE_NO].length +
                testForm.questionList[JUDGE_NO].length
          "
          :number="sequence"
          :questionId="
            testForm.questionList[JUDGE_NO].list[questionRowCol[JUDGE_NO * 2]][
              questionRowCol[JUDGE_NO * 2 + 1]
            ].questionId
          "
          :title="
            testForm.questionList[JUDGE_NO].list[questionRowCol[JUDGE_NO * 2]][
              questionRowCol[JUDGE_NO * 2 + 1]
            ].questionTitle
          "
          :choose="reply[sequence - 1]"
          @returnAnswer="getReply"
        ></judge-question>
        <short-answer-question
          :key="questionRefreshKey"
          v-if="
            sequence >
              testForm.questionList[CHOICE_NO].length +
                testForm.questionList[JUDGE_NO].length &&
            sequence <= testForm.questionTotal
          "
          :number="sequence"
          :questionId="
            testForm.questionList[SHORT_ANSWER_NO].list[
              questionRowCol[SHORT_ANSWER_NO * 2]
            ][questionRowCol[SHORT_ANSWER_NO * 2 + 1]].questionId
          "
          :title="
            testForm.questionList[SHORT_ANSWER_NO].list[
              questionRowCol[SHORT_ANSWER_NO * 2]
            ][questionRowCol[SHORT_ANSWER_NO * 2 + 1]].questionTitle
          "
          :content="reply[sequence - 1]"
          @returnAnswer="getReply"
        ></short-answer-question>
      </div>
      <div class="operation">
        <ul class="end">
          <li>
            <el-button
              :disabled="sequence == 1"
              @click="this.jumpTo(--this.sequence)"
            >
              <el-icon style="margin-right: 10px"><arrow-left-bold /></el-icon>
              <span>上一题</span>
            </el-button>
          </li>
          <li>
            <el-button
              :disabled="sequence == testForm.questionTotal"
              @click="this.jumpTo(++this.sequence)"
            >
              <span>下一题</span>
              <el-icon style="margin-left: 10px"><arrow-right-bold /></el-icon>
            </el-button>
          </li>
        </ul>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";
import countDown from "@/components/CountDown.vue";
import choiceQuestion from "@/components/ChoiceQuestion.vue";
import judgeQuestion from "@/components/JudgeQuestion.vue";
import shortAnswerQuestion from "@/components/ShortAnswerQuestion.vue";
import moment from "moment";
export default {
  components: {
    countDown,
    choiceQuestion,
    judgeQuestion,
    shortAnswerQuestion,
  },
  data() {
    return {
      // 常量
      CARD_COL_NUMBER: 4,
      CHOICE_NO: 0, // index = typeId - 1
      JUDGE_NO: 1,
      SHORT_ANSWER_NO: 2,

      // 解决axios获取数据的异步问题
      testInfoFlag: false,
      testPaperFlag: false,

      // 倒计时
      remainSecond: 0,
      countDownKey: 0,
      takeTime: 0,
      timekeep: false,

      userId: "",
      // 考试信息
      testForm: {
        testId: "",
        testName: "",
        questionTotal: 0,
        questionList: [],
      },

      sequence: 1, // 全局
      questionRowCol: [0, 0, 0, 0, 0, 0],
      questionRefreshKey: 0, // 给自定义component加上key -> 刷新component
      reply: [],
    };
  },
  created() {
    this.userId = this.$storage.getStorageSync("user").id;
    this.testForm.testId = this.$route.params.testId;
    this.loadData();
  },
  methods: {
    loadData() {
      this.loadTestInfo().then((response) => {
        this.testInfoFlag = true;
        this.countDownKey = new Date();
        this.timekeep = true;
      });
      this.loadTestPaper().then((response) => {
        this.testPaperFlag = true;
      });
    },
    loadTestInfo() {
      return this.$axios
        .get("/test/findById", {
          headers: { Authorization: userToken() },
          params: { testId: this.testForm.testId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.testForm = res.test;
            this.remainSecond = res.test.testDuration * 60;
          }
        });
    },
    loadTestPaper() {
      return this.$axios
        .get("/question/findQuestionListByTestId", {
          headers: { Authorization: userToken() },
          params: { testId: this.testForm.testId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.testForm.questionTotal = res.length;
            this.testForm.questionList = [
              {
                name: "选择题",
                length: 0,
                list: [],
              },
              {
                name: "判断题",
                length: 0,
                list: [],
              },
              {
                name: "简答题",
                length: 0,
                list: [],
              },
            ];
            let quesList = [[], [], []];
            let quesNum = [0, 0, 0];
            res.forEach((item, index) => {
              quesList[item.typeId - 1].push(item);
              quesNum[item.typeId - 1]++;
              if (quesNum[item.typeId - 1] % this.CARD_COL_NUMBER == 0) {
                this.testForm.questionList[item.typeId - 1].list.push(
                  quesList[item.typeId - 1]
                );
                quesList[item.typeId - 1] = [];
              }
              if (index + 1 == this.testForm.questionTotal) {
                for (let i = 0; i < this.testForm.questionList.length; i++) {
                  this.testForm.questionList[i].list.push(quesList[i]);
                  this.testForm.questionList[i].length = quesNum[i];
                }
              }
            });
          }
        });
    },

    jumpTo(i) {
      this.sequence = i;
      if (i > 0 && i <= this.testForm.questionList[this.CHOICE_NO].length) {
        let order = this.sequence;
        this.questionRowCol[this.CHOICE_NO * 2] = Math.floor(
          (order - 1) / this.CARD_COL_NUMBER
        );
        this.questionRowCol[this.CHOICE_NO * 2 + 1] =
          (order - 1) % this.CARD_COL_NUMBER;
      } else if (
        i <=
        this.testForm.questionList[this.CHOICE_NO].length +
          this.testForm.questionList[this.JUDGE_NO].length
      ) {
        let order =
          this.sequence - this.testForm.questionList[this.CHOICE_NO].length;
        this.questionRowCol[this.JUDGE_NO * 2] = Math.floor(
          (order - 1) / this.CARD_COL_NUMBER
        );
        this.questionRowCol[this.JUDGE_NO * 2 + 1] =
          (order - 1) % this.CARD_COL_NUMBER;
      } else if (i <= this.testForm.questionTotal) {
        let order =
          this.sequence -
          (this.testForm.questionTotal -
            this.testForm.questionList[this.SHORT_ANSWER_NO].length);
        this.questionRowCol[this.SHORT_ANSWER_NO * 2] = Math.floor(
          (order - 1) / this.CARD_COL_NUMBER
        );
        this.questionRowCol[this.SHORT_ANSWER_NO * 2 + 1] =
          (order - 1) % this.CARD_COL_NUMBER;
      } else {
        console.log("function jumpTo(i):something error, please check out!");
      }
      this.questionRefreshKey = new Date();
    },
    getReply(data) {
      this.reply[this.sequence - 1] = data;
    },

    getTime(data) {
      this.takeTime = data;
    },
    finish() {
      let time = this.takeTime;
      this.timekeep = false;
      this.remainSecond -= time;
      this.countDownKey = new Date();
      let notEmptyReply = 0;
      this.reply.forEach((item) => {
        notEmptyReply += item != undefined;
      });
      let isCommit = false;
      if (notEmptyReply < this.testForm.questionTotal) {
        this.$confirm("你还有题目未完成，是否继续？", "提示", {
          distinguishCancelAndClose: true,
          confirmButtonText: "提交",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            isCommit = true;
            this.commit(time);
          })
          .catch(() => {
            // 点击提交后会先then()再catch()，设置isCommit防止提交后计时仍继续
            if (!isCommit) {
              // 计时继续
              this.countDownKey = new Date();
              this.timekeep = true;
            }
          });
      } else {
        this.commit(time);
      }
    },
    commit(time) {
      let arr = "";
      for (let i = 0; i < this.testForm.questionTotal; i++) {
        arr += (this.reply[i] == undefined ? " " : this.reply[i]) + ",";
      }
      this.$axios
        .post(
          "/testHistory/commit",
          this.$qs.stringify(
            {
              studentId: this.userId,
              testId: this.testForm.testId,
              finishTime: moment().format("YYYY-MM-DD HH:mm:ss"),
              takeTime: time,
              reply: this.reply,
            },
            { indices: false }
          ),
          { headers: { Authorization: userToken() } }
        )
        .then((response) => {
          this.$message({
            type: response.data.success ? "success" : "error",
            message: response.data.message,
          });
          if (response.data.success) {
            this.$router.push("/student/testHistory");
          }
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
/* 左侧索引指示信息 */
#logo-0 {
  border: 2px solid red;
}
#logo-1 {
  border: 1px solid #eee;
}
#logo-2 {
  background-color: #38d39f;
}
/* 题目是否已答背景 */
.index-btn-style {
  background-color: #38d39f !important;
  color: #fff;
}
.index-btn-border {
  position: relative;
  border: 2px solid red !important;
}
/* 上下页按钮 */
.operation .end li button {
  border: none;
  font-size: 17px;
  background-color: #fff;
}
.operation .end li {
  margin: 0 75px;
}
.operation .end {
  list-style: none;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>