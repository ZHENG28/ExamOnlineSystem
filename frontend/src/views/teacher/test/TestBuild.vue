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
        <el-col :span="12">
          <el-form-item label="测验标题" prop="testName">
            <el-input
              v-model="testForm.testName"
              placeholder="请填写测验标题"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="测验班级" prop="clazzId">
            <el-cascader
              @change="valueToClazzId"
              v-model="majorclazzName"
              placeholder="请选择专业班级"
              :options="majorclazzArr"
              filterable
              :show-all-levels="false"
              :props="{ expandTrigger: 'hover' }"
            ></el-cascader>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="测验简介" prop="description">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2 }"
            v-model="testForm.description"
            style="width: 700px"
            maxlength="50"
            show-word-limit
          ></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="所属科目" prop="subjectId">
            <el-select
              filterable
              placeholder="请选择科目"
              @change="valueTosubId"
              v-model="testForm.subjectName"
            >
              <el-option
                v-for="sub in subIdFilterData"
                :key="sub.subId"
                :label="sub.text"
                :value="sub.subId"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item prop="questionList">
            <el-button
              type="primary"
              :disabled="testForm.subjectId == ''"
              @click="
                findQuesBySubId(testForm.subjectId);
                questionListVisible = true;
              "
              style="margin-right: 30px"
              >选择考题</el-button
            >
            <span
              :style="{
                display: 'inline',
                color: 'red',
                visibility: testForm.subjectId != '' ? 'hidden' : 'visible',
              }"
            >
              请先选择所属科目
            </span>
            <div class="dialog-container">
              <el-dialog
                title="添加考试题目"
                v-model="questionListVisible"
                width="1200px"
                top="20px"
              >
                <div class="transfer-container">
                  <el-transfer
                    :data="quesList"
                    v-model="testQues"
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
                        >随机选题</el-button
                      >
                    </div>
                    <div style="display: inline">
                      <el-button @click="questionListVisible = false"
                        >取 消</el-button
                      >
                      <el-button
                        type="primary"
                        @click="testQuesToquestionList()"
                        >确 定</el-button
                      >
                    </div>
                  </span>
                </template>
              </el-dialog>
            </div>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item>
            <el-button
              type="primary"
              @click="shortAnswerVisible = true"
              :style="{
                marginRight: '30px',
                visibility: shortAnswerBtnVisible ? 'hidden' : 'visible',
              }"
              >设置简答题阈值</el-button
            >
            <div class="dialog-container">
              <el-dialog
                title="设置简答题阈值"
                v-model="shortAnswerVisible"
                width="600px"
              >
                <span>阈值即</span>
                <el-form
                  :model="shortAnswerList"
                  :rules="shortAnswerFormRules"
                  ref="shortAnswerList"
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
      <el-row>
        <el-col :span="12">
          <el-form-item label="测验日期" prop="date">
            <el-date-picker
              v-model="testForm.date"
              type="datetimerange"
              value-format="YYYY-MM-DD hh:mm:ss"
              format="YYYY-MM-DD hh:mm:ss"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="测验时长" prop="examDuration">
            <el-input-number
              v-model="testForm.examDuration"
              :step="30"
              :min="0"
              placeholder="0"
            ></el-input-number>
            分钟
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="测验总分" prop="questionTotal">
            <el-input-number
              v-model="testForm.questionTotal"
              :step="50"
              :min="0"
              placeholder="100"
            ></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="测验次数" prop="examTime">
            <el-input-number
              v-model="testForm.examTime"
              :step="1"
              :min="0"
              placeholder="1"
            ></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div>
      <el-button @click="toTestInfo()">返回列表</el-button>
      <el-button type="primary" @click="save()">确 定</el-button>
    </div>
  </div>
</template>
<script>
import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      status: "",
      questionListVisible: false,
      testForm: {
        testId: "",
        testName: "",
        clazzId: "",
        major: "",
        clazzName: "",
        description: "",
        subjectId: "",
        subjectName: "",
        questionList: "",
        date: [],
        beginDate: "",
        endDate: "",
        examDuration: 0,
        questionTotal: 100,
        examTime: 1,
        shortAnswer: "",
      },
      testFormRules: {
        testName: [
          { required: true, message: "请填写本次测验标题", trigger: "blur" },
        ],
        clazzId: [
          { required: true, message: "请选择测验班级", trigger: "blur" },
        ],
        description: [
          { required: false, message: "请填写本次测验简介", trigger: "blur" },
        ],
        subjectId: [
          { required: true, message: "请选择所属科目", trigger: "blur" },
        ],
        questionList: [
          { required: true, message: "请选择题库", trigger: "blur" },
        ],
        date: [
          { required: true, message: "请填写开始-结束时间", trigger: "blur" },
        ],
        examDuration: [
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
        questionTotal: [
          { required: true, message: "请填写测验总分", trigger: "blur" },
          {
            type: "number",
            asyncValidator: (rule, value) => {
              return new Promise((resolve, reject) => {
                if (value == 0) {
                  reject("测验总分不能为0，请填写");
                } else {
                  resolve();
                }
              });
            },
          },
        ],
        examTime: [
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
      },

      shortAnswerVisible: false,
      shortAnswerBtnVisible: true,
      shortAnswerList: [],
      shortAnswerFormRules: {
        threshold: [{ required: true, message: "请填写阈值", trigger: "blur" }],
      },

      majorclazzArr: [],
      majorclazzName: [],
      subIdFilterData: [],
      quesList: [],
      testQues: [],
      randomCheck: [],
    };
  },
  created() {
    if (this.$route.params.testId == 0) {
      this.status = "发布";
    } else {
      this.status = "修改";
      this.testForm.testId = this.$route.params.testId;
      this.loadInfo(this.$route.params.testId);
    }
    this.loadData();
  },
  methods: {
    // 初始化页面
    loadData() {
      this.findAllSub();
      this.findAllMajorAndClazz();
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

    findAllSub() {
      this.$axios
        .post(
          "/subject/findAllSubIdByUserId",
          this.$qs.stringify({
            userId: this.$storage.getStorageSync("user").id,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.subIdFilterData = response.data;
        });
    },
    findAllMajorAndClazz() {
      this.$axios
        .get("/clazz/findAllMajorAndClazz", { headers: authHeader() })
        .then((response) => {
          this.majorclazzArr = response.data;
        });
    },
    valueTosubId(val) {
      this.testForm.subjectId = val;
      this.testQues = [];
    },
    valueToClazzId(row) {
      this.testForm.clazzId = row[1];
    },

    findQuesBySubId(id) {
      this.$axios
        .post(
          "/question/findQuesBySubId",
          this.$qs.stringify({
            subjectId: id,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.quesList = [];
          response.data.forEach((elem) => {
            this.quesList.push({
              key: elem.questionId,
              label: elem.questionTitle,
              type: elem.typeName,
            });
          });
        });
    },
    getRandom(begin, end) {
      return parseInt(Math.random() * (end - begin + 1) + begin);
    },
    randomSelect() {
      let num = this.getRandom(1, this.quesList.length);
      let keys = [],
        result = [];
      this.quesList.forEach((item) => {
        keys.push(item.key);
      });
      let i = 0;
      while (i < num) {
        let ques = this.getRandom(1, keys[this.quesList.length - 1]);
        if (keys.indexOf(ques) != -1 && result.indexOf(ques) == -1) {
          result.push(ques);
          i++;
        }
      }
      this.randomCheck = result;
    },
    testQuesToquestionList() {
      let arr = [];
      this.testQues.forEach((elem) => {
        arr += elem + ",";
      });
      this.testForm.questionList = arr.substring(0, arr.length - 1);
      this.questionListVisible = false;
      this.shortAnswerBtnVisible = this.haveShortAnswer();
    },

    haveShortAnswer() {
      this.shortAnswerList = [];
      this.testQues.forEach((elem) => {
        this.quesList.forEach((item) => {
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

    loadInfo(id) {
      this.$axios
        .post("/test/findById", this.$qs.stringify({ testId: id }), {
          headers: authHeader(),
        })
        .then((response) => {
          this.testForm = response.data;
          this.testForm.date = [
            this.formatDate(this.testForm.beginDate),
            this.formatDate(this.testForm.endDate),
          ];
          this.majorclazzName = [this.testForm.major, this.testForm.clazzId];
          // 简答题阈值（quesList为空 异步问题）
          this.findQuesBySubId(this.testForm.subjectId);
          console.log(this.quesList);
          let str = this.testForm.shortAnswer.split(",");
          str.forEach((item) => {
            let s = item.split(" ");
            this.shortAnswerList.push({
              questionId: s[0],
              // questionTitle: item.label,
              threshold: parseInt(s[1]),
            });
          });
          this.shortAnswerBtnVisible = this.shortAnswerList.length == 0;
        });
    },
    save() {
      // this.$refs.testForm.validate((valid) => {
      //   if (valid) {
      let shortAnswer = [];
      this.shortAnswerList.forEach((item) => {
        shortAnswer += item.questionId + " " + item.threshold + ",";
      });
      this.$axios
        .get("/test/save", {
          headers: authHeader(),
          params: {
            testId: this.testForm.testId,
            testName: this.testForm.testName,
            description: this.testForm.description,
            subjectId: this.testForm.subjectId,
            questionList: this.testForm.questionList,
            beginDate: this.testForm.date[0],
            endDate: this.testForm.date[1],
            examDuration: this.testForm.examDuration,
            clazzId: this.testForm.clazzId,
            questionTotal: this.testForm.questionTotal,
            examTime: this.testForm.examTime,
            shortAnswer: shortAnswer.substring(0, shortAnswer.length - 1),
          },
        })
        .then((response) => {
          if (response.data) {
            this.$message.success(this.status + "成功");
            this.$router.push("/teacher/testInfo");
          } else {
            this.$message.error(this.status + "失败");
          }
        })
        .catch(function (error) {
          this.$message.info("数据出错");
          console.log(error);
        });
      //   }
      // });
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
