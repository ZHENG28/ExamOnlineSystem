<template>
  <div class="row">
    <div
      class="col-md-8 testBuild"
      style="width: 100% !important; height: 500px"
    >
      <card>
        <h5 slot="header" class="title">{{ this.status }}测验信息</h5>
        <el-form :model="testForm" :rules="FormRules" ref="testForm">
          <el-form-item
            label="测验标题"
            :label-width="formLabelWidth"
            prop="testName"
            class="cust-form-item"
            style="margin-right: 50px"
          >
            <el-input
              v-model="testForm.testName"
              style="width: 250px"
              placeholder="请填写测验标题"
            ></el-input>
          </el-form-item>
          <el-form-item class="cust-form-item" prop="quesArr">
            <!--  -->
            <el-button
              type="primary"
              :hidden="status != '修改'"
              style="margin-right: 30px"
              @click="
                testResultVisible = true;
                openChart();
              "
              >查看本次测验结果</el-button
            >
            <el-dialog title="本次测验结果" :visible.sync="testResultVisible">
              <div id="main" style="width: 600px; height: 400px"></div>
            </el-dialog>
          </el-form-item>
          <el-form-item
            label="测验简介"
            :label-width="formLabelWidth"
            prop="description"
            class="cust-form-item"
          >
            <el-input
              type="textarea"
              :autosize="{ minRows: 2 }"
              v-model="testForm.description"
              style="width: 700px"
              maxlength="50"
              show-word-limit
            ></el-input>
          </el-form-item>
          <el-form-item
            label="所属科目"
            :label-width="formLabelWidth"
            prop="sub.subName"
            class="cust-form-item"
            style="margin-right: 50px"
          >
            <el-select
              filterable
              placeholder="请选择科目"
              @change="valueTosubId"
              v-model="testForm.sub.subName"
            >
              <el-option
                v-for="sub in subArr"
                :key="sub.subId"
                :label="sub.text"
                :value="sub.subId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            class="cust-form-item"
            prop="quesArr"
            style="margin-right: 50px"
          >
            <!--  -->
            <el-button
              type="primary"
              :disabled="this.testForm.sub.subId == ''"
              @click="
                findQuesBySubId(testForm.sub.subId);
                dialogFormVisible = true;
              "
              style="margin-right: 30px"
              >选择考题</el-button
            >
            <p
              :hidden="this.testForm.sub.subId != ''"
              style="display: inline; color: red"
            >
              请先选择所属科目
            </p>
            <el-dialog
              class="cust-dialog"
              title="添加考试题目"
              :visible.sync="dialogFormVisible"
            >
              <el-transfer
                filterable
                filter-placeholder="输入题目信息进行搜索"
                :titles="['题库', '考题']"
                :left-default-checked="randomCheck"
                v-model="testQues"
                :data="quesList"
              >
                <span slot-scope="{ option }"
                  >{{ option.key }} - {{ option.label }}</span
                >
              </el-transfer>
              <div class="btncontainer">
                <el-button type="primary" hidden @click="randomSelect()"
                  >随机选题</el-button
                >
                <el-button
                  style="float: right"
                  @click="dialogFormVisible = false"
                  >取 消</el-button
                >
                <el-button
                  type="primary"
                  style="float: right"
                  @click="testQuesToQuesArr()"
                  >确 定</el-button
                >
              </div>
            </el-dialog>
          </el-form-item>
          <el-form-item
            label="测验日期"
            :label-width="formLabelWidth"
            prop="date"
            class="cust-form-item"
          >
            <el-date-picker
              v-model="testForm.date"
              type="datetimerange"
              value-format="yyyy-MM-dd HH:mm:ss"
              format="yyyy-MM-dd HH:mm:ss"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item
            label="测验时长"
            :label-width="formLabelWidth"
            prop="examDure"
            class="cust-form-item"
          >
            <el-input-number
              v-model="testForm.examDure"
              :step="30"
            ></el-input-number>
            分钟
          </el-form-item>
          <el-form-item
            label="测验班级"
            :label-width="formLabelWidth"
            prop="clazzId"
            class="cust-form-item"
          >
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
          <el-form-item
            label="测验总分"
            :label-width="formLabelWidth"
            prop="totalScore"
            class="cust-form-item"
          >
            <el-input
              v-model="testForm.totalScore"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="重测次数"
            :label-width="formLabelWidth"
            prop="examTime"
            class="cust-form-item"
          >
            <el-input-number
              v-model="testForm.examTime"
              :step="1"
            ></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="toTestInfo()">返回列表</el-button>
          <el-button type="primary" @click="status == '发布' ? add() : modify()"
            >确 定</el-button
          >
        </div>
      </card>
    </div>
  </div>
</template>
<script>
import authHeader from "@/services/auth-header";
import echarts from "echarts";
export default {
  data() {
    return {
      isShowDialog: false,
      status: "",
      search: "",
      randomCheck: [],
      dialogFormVisible: false,
      testResultVisible: false,

      charts: "",
      opinion: ["90-100", "80-89", "70-79", "60-69", "60以下"],
      opinionData: [],

      quesList: [],
      testQues: [],
      subArr: [],
      clazzArr: [],
      majorclazzArr: [],
      majorclazzName: [],
      testForm: {
        testId: "",
        testName: "",
        description: "",
        sub: {
          subId: "",
          subName: "",
        },
        quesArr: "",
        beginDate: "",
        endDate: "",
        date: [],
        examDure: "",
        testClazz: {
          clazzId: "",
          clazzName: "",
          major: "",
        },
        totalScore: "",
        examTime: "",
      },
      formLabelWidth: "100px",
      FormRules: {
        testName: [
          { required: true, message: "请填写本次测验标题", trigger: "blur" },
        ],
        description: [
          { required: false, message: "请填写本次测验简介", trigger: "blur" },
        ],
        subName: [
          { required: true, message: "请选择所属科目", trigger: "blur" },
        ],
        date: [
          { required: true, message: "请填写开始-结束时间", trigger: "blur" },
        ],
        examDure: [
          { required: false, message: "请填写测验时长", trigger: "blur" },
        ],
        clazzName: [
          { required: true, message: "请选择测验班级", trigger: "blur" },
        ],
        totalScore: [
          { required: false, message: "请填写测验总分", trigger: "blur" },
        ],
        examTime: [
          { required: true, message: "请填写重测次数", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    if (this.$route.params.testId == 0) {
      this.status = "发布";
    } else {
      this.testForm.testId = this.$route.params.testId;
      this.loadModify(this.$route.params.testId);
      this.findScoreByTestId();
      this.status = "修改";
    }
    this.loadData();
  },
  methods: {
    valueTosubId(val) {
      this.testForm.sub.subId = val;
      this.testQues = [];
    },
    valueToCascade(row) {
      this.testForm.testClazz.clazzId = row[1];
    },
    toTestInfo() {
      this.$router.push("/teacher/testInfo");
    },
    loadData() {
      this.findAllSub();
      this.findAllMajorAndClazz();
    },
    findScoreByTestId() {
      this.$ajax
        .post(
          "/test/findScoreByTestId",
          this.$qs.stringify({ testId: this.testForm.testId }),
          { headers: authHeader() }
        )
        .then((res) => {
          this.opinionData = res.data;
        });
    },
    findQuesBySubId(subId) {
      this.$ajax
        .post(
          "/test/findQuesBySubId",
          this.$qs.stringify({
            subId: subId,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.quesList = [];
          response.data.forEach((elem) => {
            this.quesList.push({
              label: elem.quesTitle,
              key: elem.quesId,
            });
          });
        });
    },
    testQuesToQuesArr() {
      let arr = this.testForm.quesArr;
      this.testQues.forEach((elem) => {
        arr += elem + ",";
      });
      this.testForm.quesArr = arr.substring(0, arr.length - 1);
      this.dialogFormVisible = false;
    },
    // getRandom(begin, end) {
    //   return parseInt(Math.random() * (end - begin + 1) + begin);
    // },
    // randomSelect() {
    //   let num = this.getRandom(0, this.quesList.length);
    //   for (let i = 0; i < num; i++) {
    //     this.randomCheck.push(
    //       this.getRandom(1, this.quesList[this.quesList.length - 1].key)
    //     );
    //   }
    //   console.log(this.randomCheck);
    // },

    findAllMajorAndClazz() {
      this.$ajax
        .post(
          "/test/findAllMajorAndClazz",
          {},
          {
            headers: authHeader(),
          }
        )
        .then((response) => {
          this.majorclazzArr = response.data;
        });
    },
    findAllSub() {
      this.$ajax
        .post("/test/findAllSubId", {}, { headers: authHeader() })
        .then((response) => {
          this.subArr = response.data;
        });
    },
    add() {
      this.$refs["testForm"].validate((valid) => {
        if (!valid) {
          console.log("error submit!!");
          return;
        } else {
          this.$ajax
            .get("/test/add", {
              headers: authHeader(),
              params: {
                testId: this.testForm.testId,
                testName: this.testForm.testName,
                description: this.testForm.description,
                sub: this.testForm.sub.subId,
                quesArr: this.testForm.quesArr,
                beginDate: this.testForm.date[0],
                endDate: this.testForm.date[1],
                examDure: this.testForm.examDure,
                testClazz: this.testForm.testClazz.clazzId,
                totalScore: this.testForm.totalScore,
                examTime: this.testForm.examTime,
              },
            })
            .then((response) => {
              if (response.data == "success") {
                this.$message.success("发布成功");
                this.$router.push("/teacher/testInfo");
              } else if (response.data == "failed") {
                this.$message.error("发布失败");
              }
            })
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
            });
        }
      });
    },

    loadModify(testId) {
      this.$ajax
        .post("/test/findById", this.$qs.stringify({ testId: testId }), {
          headers: authHeader(),
        })
        .then((res) => {
          let test = res.data;
          let testForm = this.testForm;
          testForm.testId = test.testId;
          testForm.testName = test.testName;
          testForm.description = test.description;
          testForm.sub = test.sub;
          testForm.quesArr = test.quesArr;
          testForm.beginDate = test.beginDate;
          testForm.endDate = test.endDate;
          testForm.examDure = test.examDure;
          testForm.testClazz = test.testClazz;
          testForm.totalScore = test.totalScore;
          testForm.examTime = test.examTime;

          testForm.date = [test.beginDate, test.endDate];
          if (testForm.testClazz != null) {
            this.majorclazzName = [
              testForm.testClazz.major,
              testForm.testClazz.clazzId,
            ];
          } else {
            testForm.testClazz = {
              clazzId: "",
              clazzName: "",
              major: "",
            };
          }
          if (testForm.quesArr != "") {
            let strArr = testForm.quesArr.split(",");
            strArr.forEach((elem) => {
              this.testQues.push(parseInt(elem));
            });
          }
        });
    },
    modify() {
      this.$refs["testForm"].validate((valid) => {
        if (!valid) {
          console.log("error submit!!");
          return;
        } else {
          this.$ajax
            .post(
              "/test/modify",
              this.$qs.stringify({
                testId: this.testForm.testId,
                testName: this.testForm.testName,
                description: this.testForm.description,
                sub: this.testForm.sub.subId,
                quesArr: this.testForm.quesArr,
                beginDate: this.testForm.date[0],
                endDate: this.testForm.date[1],
                examDure: this.testForm.examDure,
                testClazz: this.testForm.testClazz.clazzId,
                totalScore: this.testForm.totalScore,
                examTime: this.testForm.examTime,
              }),
              { headers: authHeader() }
            )
            .then((response) => {
              if (response.data == "success") {
                this.$message.success("修改成功");
                this.$router.push("/teacher/testInfo");
              } else if (response.data == "failed") {
                this.$message.error("修改失败");
              }
            })
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
            });
        }
      });
    },

    drawPie(id) {
      this.charts = echarts.init(document.getElementById(id));
      this.charts.setOption({
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          x: "left",
          data: this.opinion,
        },
        series: [
          {
            type: "pie",
            radius: "55%",
            center: ["50%", "60%"],
            data: this.opinionData,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      });
    },
    openChart() {
      const self = this;
      setTimeout(() => {
        //  执行echarts画图方法
        self.drawPie("main");
      }, 0);
    },
  },
};
</script>
<style>
.cust-form-item {
  display: inline-block;
}
.cust-form-item .el-dialog {
  margin-top: 3vh !important;
  margin-bottom: 10px;
  width: 1200px;
  height: 650px;
}
.cust-form-item .el-form-item__content {
  margin-bottom: 10px;
}
.cust-form-item:nth-child(4) .el-form-item__content {
  margin-right: 50px;
}
.cust-form-item:nth-child(4) {
  margin-right: 50px;
}
.cust-form-item .el-form-item__label {
  font-size: 13px !important;
}
.el-transfer-panel {
  width: 488px !important;
  height: 500px;
}
.el-transfer-panel__list.is-filterable {
  height: 389px;
}
.testBuild .card-footer {
  padding-top: 0px;
}
.testBuild .card-body {
  padding-bottom: 0px;
}
.btncontainer {
  margin-top: 10px;
}
.cust-dialog .el-dialog__body {
  padding-top: 0px;
  padding-bottom: 20px;
}
</style>
