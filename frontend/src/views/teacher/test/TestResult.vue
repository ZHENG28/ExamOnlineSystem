<template>
  <div>
    <el-page-header
      :content="testName"
      @back="toTestInfo"
      style="margin-bottom: 20px"
    />
    <el-tabs v-model="activeName" type="card" :stretch="true">
      <el-tab-pane label="总览" name="first">
        <el-table
          :data="
            tableData.filter(
              (data) =>
                !search ||
                data.username.toLowerCase().includes(search.toLowerCase())
            )
          "
          id="history-table"
          border
          height="540px"
        >
          <el-table-column type="index" label="序号" width="60">
          </el-table-column>
          <el-table-column prop="username" label="学生姓名"> </el-table-column>
          <el-table-column prop="maxCorrect" label="最高成绩 / 总分" sortable>
            <template #default="scope">
              {{ scope.row.maxCorrect }}
              /
              {{ scope.row.questionTotal }}
            </template>
          </el-table-column>
          <el-table-column
            prop="completeTime"
            label="已完成次数 / 测验总次数"
            sortable
          >
            <template #default="scope">
              {{ scope.row.completeTime }}
              /
              {{ scope.row.testTime }}
            </template>
          </el-table-column>
          <el-table-column width="300">
            <template #header>
              <el-input v-model="search" placeholder="输入学生姓名进行搜索" />
            </template>
            <template #default="scope">
              <el-button
                type="primary"
                @click="toTestDetail(scope.row.testId, scope.row.studentId)"
                >查看历史作答</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageno"
            :page-sizes="[5, 10, 20, 50]"
            :page-size="size"
            layout="total, sizes, ->, pager, next, jumper"
            :total="totalItems"
          >
          </el-pagination>
        </div>
      </el-tab-pane>
      <el-tab-pane label="详情-客观题" name="second">
        <div
          id="object-chart"
          ref="objectChart"
          style="width: 1285px; height: 540px"
        ></div>
      </el-tab-pane>
      <el-tab-pane label="详情-主观题" name="third">
        <el-table
          :data="
            compareTableData.filter(
              (data) =>
                !compareSearch ||
                data.username
                  .toLowerCase()
                  .includes(compareSearch.toLowerCase()) ||
                data.compareUsername
                  .toLowerCase()
                  .includes(compareSearch.toLowerCase())
            )
          "
          id="compare-table"
          border
          height="540px"
        >
          <el-table-column type="index" label="序号" width="60">
          </el-table-column>
          <el-table-column
            prop="questionTitle"
            label="问题"
            :filters="questionFilterData"
            :filter-method="questionFilter"
            width="500"
          >
          </el-table-column>
          <el-table-column prop="username" label="学生1">
            <template #default="scope">
              <span
                >{{ scope.row.username }}（第{{
                  scope.row.reply1Ranking
                }}次）</span
              >
            </template>
          </el-table-column>
          <el-table-column prop="compareUsername" label="学生2">
            <template #default="scope">
              <span
                >{{ scope.row.compareUsername }}（第{{
                  scope.row.reply2Ranking
                }}次）</span
              >
            </template>
          </el-table-column>
          <el-table-column
            prop="compareThreshold"
            label="相似度"
            sortable
            width="100"
          >
          </el-table-column>
          <el-table-column width="300">
            <template #header>
              <el-input
                v-model="compareSearch"
                placeholder="输入学生姓名进行搜索"
              />
            </template>
            <template #default="scope">
              <el-button
                type="primary"
                @click="loadCompareText(scope.row.compareId)"
              >
                查看文本比对情况
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
            @size-change="handleCompareSizeChange"
            @current-change="handleCompareCurrentChange"
            :current-page="comparePageno"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="compareSize"
            layout="total, sizes, ->, pager, next, jumper"
            :total="compareTotalItems"
          >
          </el-pagination>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-dialog
      :title="
        compareData.username + '和' + compareData.compareUsername + '的文本对比'
      "
      id="compare-text-dialog"
      v-model="dialogVisible"
      width="1000px"
      top="80px"
    >
      <div style="margin-bottom: 10px">
        调整下方进度条，即可查看该相似度值时的文本相似情况
      </div>
      <div style="display: flex; align-items: center; margin-bottom: 20px">
        <span style="width: 110px">相似度：{{ textProgress }}%</span>
        <el-slider
          v-model="textProgress"
          :step="5"
          show-stops
          @change="
            getCompareList(compareData.reply1Text, compareData.reply2Text)
          "
        />
      </div>
      <div id="compare-div" style="display: flex; align-items: center">
        <el-card style="width: 600px; margin-right: 30px">
          <template #header>
            <div>
              <span>{{ compareData.username }}</span>
            </div>
          </template>
          <div style="line-height: 25px">
            <span
              :style="{
                color:
                  compareData.reply1CompareList.indexOf(index) != -1
                    ? 'red'
                    : '',
              }"
              v-for="(str, index) in compareData.reply1Text.split('。')"
              :key="index"
            >
              {{ str }}。
            </span>
          </div>
        </el-card>
        <el-card style="width: 600px">
          <template #header>
            <div>
              <span>{{ compareData.compareUsername }}</span>
            </div>
          </template>
          <div style="line-height: 25px">
            <span
              :style="{
                color:
                  compareData.reply2CompareList.indexOf(index) != -1
                    ? 'red'
                    : '',
              }"
              v-for="(str, index) in compareData.reply2Text.split('。')"
              :key="index"
            >
              {{ str }}。
            </span>
          </div>
        </el-card>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关 闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script>
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";
export default {
  data() {
    return {
      testId: 0,
      testName: "",

      activeName: "first",

      search: "",
      tableData: [],
      pageno: 1,
      size: 10,
      totalItems: 0,

      chart: "",

      compareSearch: "",
      compareTableData: [],
      comparePageno: 1,
      compareSize: 10,
      compareTotalItems: 0,
      questionFilterData: [],

      dialogVisible: false,
      textProgress: 50,
      compareData: {
        username: "",
        reply1Text: "",
        comparedUsername: "",
        reply2Text: "",
      },
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    // 初始化页面
    loadData() {
      this.testId = this.$route.params.testId;
      this.loadTest();
      this.findCompareTextData();
      this.findAll();
      this.loadObjectChartData();
      this.loadQuestionList();
    },
    toTestInfo() {
      this.$router.push("/teacher/testInfo");
    },
    toTestDetail(testId, studentId) {
      this.$router.push(`/student/testHistoryDetail/${testId}/${studentId}`);
    },
    formatDate(date) {
      return date
        .toLocaleString()
        .replace(/T/g, " ")
        .replace(/\.[\d]{3}Z/, "");
    },

    loadTest() {
      this.$axios
        .get("/test/findById", {
          headers: { Authorization: userToken(), showLoading: false },
          params: { testId: this.testId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.testId = res.test.testId;
            this.testName = res.test.testName;
          }
        });
    },
    findAll() {
      this.$axios
        .get("/testHistory/findAllByTestId", {
          headers: {
            Authorization: userToken(),
            // loadingTarget: "#history-table",
            showLoading: false,
          },
          params: {
            testId: this.testId,
            pageno: this.pageno,
            size: this.size,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.tableData = res.records;
            this.totalItems = res.total;
          }
        });
    },
    handleSizeChange(size) {
      this.size = size;
      this.pageno = 1;
      this.findAll();
    },
    handleCurrentChange(pageno) {
      this.pageno = pageno;
      this.findAll();
    },

    loadObjectChartData() {
      this.$axios
        .get("/testHistory/loadObjectChartData", {
          headers: {
            Authorization: userToken(),
            loadingTarget: "#object-chart",
          },
          params: { testId: this.testId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.drawObjectChart(res);
          }
        });
    },
    drawObjectChart(data) {
      let xAxisData = [],
        xData = [];
      data.forEach((item, index) => {
        xData.push({ title: item.questionTitle, value: item.correctNumber });
        xAxisData.push(index + 1);
      });
      // 加载HTML元素
      setTimeout(() => {
        this.chart = this.$echarts.init(this.$refs.objectChart);
        this.chart.setOption({
          color: ["#38d39f"],
          title: {
            text: "本次测验客观题答对人数统计",
            subtext: "统计数据为所有学生所有的测验记录",
            left: "center",
            padding: 10,
            textStyle: {
              fontSize: 20,
            },
            subtextStyle: {
              fontSize: 13,
            },
          },
          xAxis: {
            type: "category",
            data: xAxisData,
            axisTick: {
              alignWithLabel: true,
            },
          },
          yAxis: {
            type: "value",
          },
          series: [
            {
              name: "客观题作答情况统计",
              type: "bar",
              data: xData,
              itemStyle: {
                normal: {
                  label: {
                    show: true,
                    formatter(params) {
                      return params.data.value;
                    },
                    position: "top",
                  },
                },
              },
            },
          ],
          tooltip: {
            formatter(params) {
              return `${params.marker}第${params.name}题答对人数：<font style="font-weight: bold">${params.value}</font>人 <br/>
              问题：${params.data.title}`;
            },
            borderColor: "#fff",
          },
        });
      }, 0);
    },

    findCompareTextData() {
      this.$axios
        .get("/compareShortAnswer/loadCompareTextData", {
          headers: {
            Authorization: userToken(),
            loadingTarget: "#compare-table",
          },
          params: {
            testId: this.testId,
            pageno: this.comparePageno,
            size: this.compareSize,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.compareTableData = res.records;
            this.compareTotalItems = res.total;
          }
        });
    },
    handleCompareSizeChange(size) {
      this.compareSize = size;
      this.comparePageno = 1;
      this.findCompareTextData();
    },
    handleCompareCurrentChange(pageno) {
      this.comparePageno = pageno;
      this.findCompareTextData();
    },

    loadQuestionList() {
      this.$axios
        .get("/shortAnswer/findAllQuestion", {
          headers: { Authorization: userToken(), showLoading: false },
          params: {
            testId: this.testId,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.questionFilterData = [];
            res.forEach((item) => {
              this.questionFilterData.push({
                text: item.questionTitle,
                value: item.questionId,
              });
            });
          }
        });
    },
    questionFilter(value, row) {
      return row.questionId === value;
    },

    loadCompareText(compareId) {
      // this.compareData = {};
      this.textProgress = 50;
      return this.$axios
        .get("/compareShortAnswer/findById", {
          headers: {
            Authorization: userToken(),
            loadingTarget: "#compare-div",
          },
          params: {
            compareId: compareId,
            threshold: this.textProgress,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.compareData = res;
            this.dialogVisible = true;
          }
        });
    },
    getCompareList(reply1Text, reply2Text) {
      return this.$axios
        .get("/compareShortAnswer/getCompareList", {
          headers: {
            Authorization: userToken(),
            loadingTarget: "#compare-div",
          },
          params: {
            reply1Text: reply1Text,
            reply2Text: reply2Text,
            threshold: this.textProgress,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.compareData.reply1CompareList = res.reply1CompareList;
            this.compareData.reply2CompareList = res.reply2CompareList;
          }
        });
    },
  },
};
</script>
<style scoped>
</style>