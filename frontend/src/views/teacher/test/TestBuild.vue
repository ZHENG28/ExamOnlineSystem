<template>
  <div>
    <h2>{{ status }}测验信息</h2>
    <el-form
      :model="testForm"
      :rules="formRules"
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
              @change="valueToCascade"
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
          <el-form-item label="所属科目" prop="subName">
            <el-select
              filterable
              placeholder="请选择科目"
              @change="valueTosubId"
              v-model="testForm.subName"
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
        <el-col :span="18">
          <el-form-item prop="quesArr">
            <el-button
              type="primary"
              :disabled="testForm.subId == ''"
              @click="
                findQuesBySubId(testForm.subId);
                dialogFormVisible = true;
              "
              style="margin-right: 30px"
              >选择考题</el-button
            >
            <p
              :style="{
                display: 'inline',
                color: 'red',
                visibility: testForm.subId != '' ? 'hidden' : 'visible',
              }"
            >
              请先选择所属科目
            </p>
            <div class="dialog-container">
              <el-dialog
                title="添加考试题目"
                v-model="dialogFormVisible"
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
                      <span>{{ option.label }}</span>
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
                      <el-button @click="dialogFormVisible = false"
                        >取 消</el-button
                      >
                      <el-button type="primary" @click="testQuesToQuesArr()"
                        >确 定</el-button
                      >
                    </div>
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
          <el-form-item label="测验时长" prop="examDure">
            <el-input-number
              v-model="testForm.examDure"
              :step="30"
            ></el-input-number>
            分钟
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="测验总分" prop="totalScore">
            <el-input v-model="testForm.totalScore"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="测验次数" prop="examTime">
            <el-input-number
              v-model="testForm.examTime"
              :step="1"
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
// import authHeader from "@/services/auth-header";
// import echarts from "echarts";
export default {
  data() {
    return {
      status: "",
      randomCheck: [],
      dialogFormVisible: false,
      quesList: [],
      testQues: [],
      subIdFilterData: [],
      clazzArr: [],
      majorclazzArr: [],
      majorclazzName: [],
      testForm: {
        testId: "",
        testName: "",
        description: "",
        subId: "",
        subName: "",
        quesArr: "",
        beginDate: "",
        endDate: "",
        date: [],
        examDure: 0,
        testClazz: "",
        clazzName: "",
        major: "",
        totalScore: "",
        examTime: 0,
      },
      formRules: {
        testName: [
          { required: true, message: "请填写本次测验标题", trigger: "blur" },
        ],
        description: [
          { required: false, message: "请填写本次测验简介", trigger: "blur" },
        ],
        subName: [
          { required: true, message: "请选择所属科目", trigger: "change" },
        ],
        quesArr: [{ required: true, message: "请选择题库", trigger: "blur" }],
        date: [
          { required: true, message: "请填写开始-结束时间", trigger: "blur" },
        ],
        examDure: [
          {
            required: true,
            message: "请填写测验时长",
            trigger: "blur",
          },
        ],
        testClazz: [
          { required: true, message: "请选择测验班级", trigger: "blur" },
        ],
        totalScore: [
          { required: true, message: "请填写测验总分", trigger: "blur" },
        ],
        examTime: [
          {
            // type: "number",
            required: true,
            message: "请填写测验次数",
            trigger: "blur",
            // inputPattern: /^[1-9]\d*$/,
            // inputErrorMessage: "请填写测验次数",
          },
        ],
      },
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
          "/auto/subject/findAllSubId"
          //  { headers: authHeader() }
        )
        .then((response) => {
          this.subIdFilterData = response.data;
        });
    },
    findAllMajorAndClazz() {
      this.$axios
        .get(
          "/auto/clazz/findAllMajorAndClazz"
          // { headers: authHeader(), }
        )
        .then((response) => {
          this.majorclazzArr = response.data;
        });
    },
    valueTosubId(val) {
      this.testForm.subId = val;
      this.testQues = [];
    },
    valueToCascade(row) {
      this.testForm.clazzId = row[1];
    },

    findQuesBySubId(id) {
      this.$axios
        .post(
          "/auto/singleQuestion/findQuesBySubId",
          this.$qs.stringify({
            subId: id,
          })
          // { headers: authHeader() }
        )
        .then((response) => {
          this.quesList = [];
          response.data.forEach((elem) => {
            this.quesList.push({
              key: elem.quesId,
              label: elem.quesTitle,
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
    testQuesToQuesArr() {
      let arr = [];
      this.testQues.forEach((elem) => {
        arr += elem + ",";
      });
      this.testForm.quesArr = arr.substring(0, arr.length - 1);
      this.dialogFormVisible = false;
    },

    loadInfo(id) {
      this.$axios
        .post(
          "/auto/test/findById",
          this.$qs.stringify({ testId: id })
          // { headers: authHeader(), }
        )
        .then((response) => {
          this.testForm = response.data;
          this.testForm.date = [
            this.formatDate(this.testForm.beginDate),
            this.formatDate(this.testForm.endDate),
          ];
          this.majorclazzName = [this.testForm.major, this.testForm.testClazz];
        });
    },
    save() {
      this.$refs.testForm.validate((valid) => {
        if (valid) {
          this.$axios
            .get("/auto/test/save", {
              // headers: authHeader(),
              params: {
                testId: this.testForm.testId,
                testName: this.testForm.testName,
                description: this.testForm.description,
                subId: this.testForm.subId,
                quesArr: this.testForm.quesArr,
                beginDate: this.testForm.date[0],
                endDate: this.testForm.date[1],
                examDure: this.testForm.examDure,
                testClazz: this.testForm.clazzId,
                totalScore: this.testForm.totalScore,
                examTime: this.testForm.examTime,
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
.dialog-container >>> .el-dialog {
  margin-bottom: 0px;
}
.transfer-container >>> .el-transfer-panel {
  width: 488px;
}
.transfer-container >>> .el-transfer-panel__body {
  height: 500px;
}
/* .transfer-container >>> .el-transfer-panel__list.is-filterable {
  height: 389px;
} */
</style>
