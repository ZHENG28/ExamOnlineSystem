<!-- 考生答题界面 -->
<template>
  <div id="answer">
    <div class="flexarea">
      <!-- 左边题目编号区 -->
      <transition>
        <div class="left">
          <div class="l-top">
            {{ testForm.testName }}
          </div>
          <ul class="l-top">
            <li>
              <a href="javascript:;"></a>
              <span>当前</span>
            </li>
            <li>
              <a href="javascript:;"></a>
              <span>未答</span>
            </li>
            <li>
              <a href="javascript:;"></a>
              <span>已答</span>
            </li>
          </ul>
          <div class="l-bottom">
            <div class="item">
              <p>选择题部分</p>
              <ul class="cust-ul">
                <li v-for="(list, i) in testPaper.quesNum" :key="i">
                  <a
                    style="text-decoration: none"
                    href="javascript:;"
                    @click="change(i)"
                    :class="{
                      border: index == i,
                      bg: bg_flag && radio[i] != undefined,
                    }"
                  >
                    {{ i + 1 }}
                  </a>
                </li>
              </ul>
            </div>
            <div style="display: block; text-align: center">
              <el-button type="primary" @click="finish()">结束考试</el-button>
            </div>
          </div>
        </div>
      </transition>
      <!--右边选择答题区-->
      <transition>
        <div class="right">
          <div class="title">
            <p style="color: red">请选择正确的选项</p>
            <span class="auto-right">
              <i class="iconf ont el-icon-s-claim icon20"></i>
              全卷共{{ testPaper.quesNum }}题
              <i
                class="iconfont el-icon-time icon20"
                style="margin-left: 20px"
              ></i>
              <span>
                倒计时：{{
                  hour
                    ? formatNum(hour) +
                      " : " +
                      formatNum(minute) +
                      " : " +
                      formatNum(second)
                    : formatNum(minute) + " : " + formatNum(second)
                }}</span
              >
            </span>
          </div>
          <div class="content">
            <p class="topic">
              <span class="number">{{ number }}</span>
              {{ currQues.quesTitle }}
            </p>
            <div>
              <el-radio-group v-model="radio[index]" @change="getChangeLabel">
                <el-radio :label="1">{{ currQues.optA }}</el-radio>
                <el-radio :label="2">{{ currQues.optB }}</el-radio>
                <el-radio :label="3">{{ currQues.optC }}</el-radio>
                <el-radio :label="4">{{ currQues.optD }}</el-radio>
              </el-radio-group>
            </div>
          </div>
          <div class="operation">
            <ul class="end">
              <li>
                <button
                  :disabled="index == 0"
                  v-bind:class="{ 'btn-hover': isabled }"
                  @click="previous()"
                >
                  <i class="iconfont el-icon-arrow-left"></i><span>上一题</span>
                </button>
              </li>
              <li>
                <button
                  :disabled="index + 1 == testPaper.quesNum"
                  v-bind:class="{ 'btn-hover': isabled }"
                  @click="next()"
                >
                  <span>下一题</span
                  ><i class="iconfont el-icon-arrow-right"></i>
                </button>
              </li>
            </ul>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
// import authHeader from "@/services/auth-header";
// import moment from "moment";
export default {
  data() {
    return {
      startTime: null, // 考试开始时间
      endTime: null, // 考试结束时间
      // 倒计时
      hour: "",
      minute: "",
      second: "",
      promiseTimer: "",
      remainSecond: "",

      index: 0, // 全局index
      number: 1, // 题号
      radio: [], // 保存所有选择题的所选选项
      bg_flag: false, // 是否已答题目，已答则改变背景色
      isabled: true, // 按钮可用时的样式

      score: [], //每种类型分数的总数
      topic: {
        //试卷信息
      },
      rightAnswer: "",

      // 考生信息
      stuForm: {
        stuId: "",
        account: "",
      },
      // 考试信息
      testForm: {
        testId: "",
        testName: "",
        totalScore: "",
      },
      // 试卷题目信息集合
      testPaper: {
        quesNum: "",
        quesArr: [],
      },
      // 当前题目信息
      currQues: {
        quesId: "",
        quesTitle: "",
        optA: "",
        optB: "",
        optC: "",
        optD: "",
        answer: "",
      },
    };
  },
  created() {
    this.testForm.testId = this.$route.params.testId;
    this.loadTestInfo(this.testForm.testId);
    this.loadTestPaper(this.testForm.testId);
    // this.loadStuInfo(this.$store.state.auth.initialState.user.account);
    this.loadStuInfo("111");
  },
  methods: {
    countDowm() {
      var self = this;
      clearInterval(this.promiseTimer);
      this.promiseTimer = setInterval(function () {
        if (self.hour === 0) {
          if (self.minute !== 0 && self.second === 0) {
            self.second = 59;
            self.minute -= 1;
          } else if (self.minute === 0 && self.second === 0) {
            self.second = 0;
            self.$emit("countDowmEnd", true);
            clearInterval(self.promiseTimer);
          } else {
            self.second -= 1;
          }
        } else {
          if (self.minute !== 0 && self.second === 0) {
            self.second = 59;
            self.minute -= 1;
          } else if (self.minute === 0 && self.second === 0) {
            self.hour -= 1;
            self.minute = 59;
            self.second = 59;
          } else {
            self.second -= 1;
          }
        }
      }, 1000);
    },
    formatNum(num) {
      return num < 10 ? "0" + num : "" + num;
    },
    loadTestInfo(testId) {
      //   this.$axios
      //     .post("/test/findById", this.$qs.stringify({ testId: testId }), {
      //       headers: authHeader(),
      //     })
      //     .then((res) => {
      //       let test = res.data;
      //       let testForm = this.testForm;
      //       testForm.testId = test.testId;
      //       testForm.testName = test.testName;
      //       testForm.totalScore = test.totalScore;
      //       this.remainSecond = test.examDure * 60;
      //       if (this.remainSecond > 0) {
      //         this.hour = Math.floor((this.remainSecond / 3600) % 24);
      //         this.minute = Math.floor((this.remainSecond / 60) % 60);
      //         this.second = Math.floor(this.remainSecond % 60);
      //         this.countDowm();
      //       }
      //     });
    },
    loadTestPaper(testId) {
      //   this.$axios
      //     .post(
      //       "/test/findQuesArrByTestId",
      //       this.$qs.stringify({ testId: testId }),
      //       {
      //         headers: authHeader(),
      //       }
      //     )
      //     .then((res) => {
      //       this.testPaper.quesNum = res.data.length;
      //       this.testPaper.quesArr = res.data;
      //       this.currQues = this.testPaper.quesArr[this.number - 1];
      //     });
    },
    loadStuInfo(account) {
      //   this.$axios
      //     .post("/stu/findByAccount", this.$qs.stringify({ account: account }), {
      //       headers: authHeader(),
      //     })
      //     .then((res) => {
      //       this.stuForm.stuId = res.data.id;
      //       this.stuForm.account = account;
      //     });
    },

    change(index) {
      this.index = index;
      this.number = this.index + 1;
      this.currQues = this.testPaper.quesArr[this.index];
    },
    // 获取选择题作答选项
    getChangeLabel(val) {
      this.radio[this.number - 1] = val;
      this.bg_flag = val != "";
    },
    // 上一题
    previous() {
      this.index--;
      this.isabled = this.index != 0;
      this.change(this.index);
    },
    // 下一题
    next() {
      this.index++;
      this.isabled = this.index + 1 != this.testPaper.quesNum;
      this.change(this.index);
    },

    // 结束考试
    finish() {
      // let radioSize = 0;
      // let rightSize = 0;
      // for (let i = 0; i < this.radio.length; i++) {
      //   let answer = this.radio[i];
      //   let right = this.testPaper.quesArr[i].answer;
      //   if (answer != undefined) {
      //     radioSize++;
      //     switch (answer) {
      //       case 1: // A
      //         if (right == "A") {
      //           rightSize++;
      //         }
      //         break;
      //       case 2: // B
      //         if (right == "B") {
      //           rightSize++;
      //         }
      //         break;
      //       case 3: // C
      //         if (right == "C") {
      //           rightSize++;
      //         }
      //         break;
      //       case 4: // D
      //         if (right == "D") {
      //           rightSize++;
      //         }
      //         break;
      //     }
      //   }
      // }
      // if (radioSize < this.testPaper.quesNum) {
      //   this.$confirm("你还有题目未完成，是否继续提交？", "提示", {
      //     confirmButtonText: "确定",
      //     cancelButtonText: "取消",
      //     type: "warning",
      //   })
      //     .then(() => {
      //       this.commit(rightSize);
      //     })
      //     .catch(() => {
      //       return;
      //     });
      // } else {
      //   this.commit(rightSize);
      // }
    },
    // 提交试卷
    commit(rightSize) {
      //   this.$axios.post(
      //     "/testHistory/commit",
      //     this.$qs.stringify({
      //       stu: this.stuForm.stuId,
      //       test: this.testForm.testId,
      //       score:
      //         rightSize *
      //         Math.floor(this.testForm.totalScore / this.testPaper.quesNum),
      //       finishDate: moment(new Date()).format("yyyy-MM-D HH:mm:ss"),
      //     }),
      //     {
      //       headers: authHeader(),
      //     }
      //   );
      //   this.$router.push("/student/testHistory");
      //   // let radio = this.radio;
      //   // let finalScore = 0;
      //   // if (this.testForm.examDure != 0) {
      //   //   this.$confirm("考试结束时间未到,是否提前交卷", "友情提示", {
      //   //     confirmButtonText: "立即交卷",
      //   //     cancelButtonText: "再检查一下",
      //   //     type: "warning",
      //   //   })
      //   //     .then(() => {
      //   //       let date = new Date();
      //   //       this.endTime = this.getTime(date);
      //   //       let answerDate = this.endTime.substr(0, 10);
      //   //       //提交成绩信息
      //   //       this.$axios({
      //   //         url: "/api/score",
      //   //         method: "post",
      //   //         data: {
      //   //           examCode: this.examData.examCode, //考试编号
      //   //           subject: this.examData.source, //课程名称
      //   //           etScore: finalScore, //答题成绩
      //   //           answerDate: answerDate, //答题日期
      //   //         },
      //   //       }).then((res) => {
      //   //         if (res.data.code == 200) {
      //   //           this.$router.push({
      //   //             path: "/studentScore",
      //   //             query: {
      //   //               score: finalScore,
      //   //               startTime: this.startTime,
      //   //               endTime: this.endTime,
      //   //             },
      //   //           });
      //   //         }
      //   //       });
      //   //     })
      //   //     .catch(() => {
      //   //       console.log("继续答题");
      //   //     });
      //   // }
    },
  },
};
</script>

<style lang="scss" scoped>
// 页面容器
#answer {
  padding-bottom: 30px;
}
#answer .item {
  color: #fff;
  display: flex;
  padding: 20px;
  font-size: 20px;
}
// 右侧答题区布局
.flexarea {
  display: flex;
}
.flexarea .right {
  flex: 1;
}
// 题目索引
#answer .left .item {
  padding: 0px;
  font-size: 16px;
}
// 左侧索引指示信息
.left {
  width: 260px;
  height: 100%;
  margin: 10px 10px 0px 10px;
}
.left .l-top {
  display: flex;
  justify-content: space-around;
  padding: 16px 0px;
  border: 1px solid #eee;
  border-radius: 4px;
  margin-bottom: 10px;
  background-color: #fff;
}
.left .l-top li a {
  display: inline-block;
  padding: 10px;
  border-radius: 50%;
  background-color: #fff;
  border: 1.5px solid red;
}
.left .l-top li:nth-child(2) a {
  border: 1px solid #eee;
}
.left .l-top li:nth-child(3) a {
  background-color: #409eff;
  border: none;
}
.left .l-top li {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
// 题目是否已答背景
.bg {
  background-color: #409eff !important;
}
.border {
  position: relative;
  border: 1.5px solid red !important;
}
// 题目索引
.cust-ul {
  padding-left: 0px !important;
  margin-bottom: 10px !important;
  list-style: none;
}
.l-bottom {
  border-radius: 4px;
  background-color: #fff;
}
.l-bottom .item p {
  margin-bottom: 15px;
  margin-top: 10px;
  color: #000;
  margin-left: 10px;
  letter-spacing: 2px;
}
.l-bottom .item li {
  width: 15%;
  margin-left: 5px;
  margin-bottom: 10px;
}
.l-bottom .item {
  display: flex;
  flex-direction: column;
}
.l-bottom .item ul {
  width: 100%;
  margin-bottom: -8px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
.l-bottom .item ul li a {
  position: relative;
  justify-content: center;
  display: inline-flex;
  align-items: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #eee;
  text-align: center;
  color: #000;
  font-size: 16px;
}
// 上下页按钮
.operation .end li button {
  border: none;
  font-size: 17px;
  background-color: #fff;
}
.btn-hover:hover {
  color: #409eff;
}
.operation .end li {
  margin: 0 75px;
}
.operation {
  background-color: #fff;
  border-radius: 4px;
  padding: 10px 0px;
  margin-right: 10px;
}
.operation .end {
  margin-bottom: 0px;
  list-style: none;
  display: flex;
  justify-content: center;
  align-items: center;
  // color: rgb(39, 118, 223);
}
// 右侧上方
.auto-right {
  margin-left: auto;
  margin-right: 10px;
}
.icon20 {
  font-size: 20px;
  color: #409eff;
}
.right .title p {
  margin-left: 20px;
}
.right .title {
  margin-right: 10px;
  padding-right: 30px;
  display: flex;
  margin-top: 10px;
  background-color: #fff;
  height: 50px;
  line-height: 50px;
}
// 题目区域
.right .content {
  background-color: #fff;
  margin: 10px;
  margin-left: 0px;
  height: 470px;
}
.content {
  padding: 0px 20px;
}
.content .topic {
  padding: 20px 0px;
  padding-top: 30px;
  font-size: 17px;
  font-weight: normal;
}
// 题号
.content .number {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 25px;
  height: 25px;
  background-color: #409eff;
  color: #fff;
  border-radius: 4px;
  margin-right: 4px;
  font-weight: bold;
}
// 题目选项
.content .el-radio-group label {
  color: #000;
  margin: 7px 0px;
  font-size: 15px;
}
.content .el-radio-group {
  display: flex;
  flex-direction: column;
}
</style>