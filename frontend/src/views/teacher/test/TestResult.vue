<template>
  <div>
    <el-page-header
      :content="testForm.testName"
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
                data.studentName.toLowerCase().includes(search.toLowerCase())
            )
          "
          border
          height="540px"
        >
          <el-table-column type="index" label="序号" width="80">
          </el-table-column>
          <el-table-column prop="studentName" label="学生姓名">
          </el-table-column>
          <el-table-column prop="correct" label="成绩" sortable>
          </el-table-column>
          <el-table-column prop="takeTime" label="完成时间" sortable>
          </el-table-column>
          <el-table-column width="300">
            <template #header>
              <el-input v-model="search" placeholder="输入学生姓名进行搜索" />
            </template>
            <template #default="scope">
              <el-button type="primary" @click="loadReply(scope.row)"
                >查看作答情况</el-button
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
        <div ref="objectChart" style="width: 1285px; height: 540px"></div>
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
                data.comparedUsername
                  .toLowerCase()
                  .includes(compareSearch.toLowerCase())
            )
          "
          border
          height="540px"
        >
          <el-table-column type="index" label="序号" width="80">
          </el-table-column>
          <el-table-column prop="username" label="学生1"> </el-table-column>
          <el-table-column prop="comparedUsername" label="学生2">
          </el-table-column>
          <el-table-column prop="compareThreshold" label="相似度" sortable>
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
            :page-sizes="[5, 10, 20, 50]"
            :page-size="compareSize"
            layout="total, sizes, ->, pager, next, jumper"
            :total="compareTotalItems"
          >
          </el-pagination>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      charts: "",
      opinionData: [],

      testForm: {
        testId: 0,
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

      activeName: "first",

      search: "",
      tableData: [],
      pageno: 1,
      size: 10,
      totalItems: 0,

      compareSearch: "",
      compareTableData: [],
      comparePageno: 1,
      compareSize: 10,
      compareTotalItems: 0,
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    // 初始化页面
    loadData() {
      this.testForm.testId = this.$route.params.testId;
      this.loadTest();
      this.findAll();
      this.findCompareText();
      this.drawObjectChart();
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

    loadTest() {
      this.$axios
        .post(
          "/test/findById",
          this.$qs.stringify({ testId: this.testForm.testId }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.testForm = response.data;
          this.testForm.date = [
            this.formatDate(this.testForm.beginDate),
            this.formatDate(this.testForm.endDate),
          ];
          // console.log(this.testForm);
          //   this.majorclazzName = [this.testForm.major, this.testForm.clazzId];
          //   // 简答题阈值（quesList为空 异步问题）
          //   this.findQuesBySubId(this.testForm.subjectId);
          //   console.log(this.quesList);
          //   let str = this.testForm.shortAnswer.split(",");
          //   str.forEach((item) => {
          //     let s = item.split(" ");
          //     this.shortAnswerList.push({
          //       questionId: s[0],
          //       // questionTitle: item.label,
          //       threshold: parseInt(s[1]),
          //     });
          //   });
        });
    },
    findAll() {
      this.$axios
        .post(
          "/testHistory/findAllByTestId",
          this.$qs.stringify({
            testId: this.testForm.testId,
            pageno: this.pageno,
            size: this.size,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.tableData = response.data.records;
          this.totalItems = response.data.total;
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

    findCompareText() {
      this.$axios
        .post(
          "/compareShortAnswer/loadCompareText",
          this.$qs.stringify({
            testId: this.testForm.testId,
            pageno: this.comparePageno,
            size: this.compareSize,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.compareTableData = response.data.records;
          this.compareTotalItems = response.data.total;
        });
    },
    handleCompareSizeChange(size) {
      this.compareSize = size;
      this.comparePageno = 1;
      this.findCompareText();
    },
    handleCompareCurrentChange(pageno) {
      this.comparePageno = pageno;
      this.findCompareText();
    },

    findScoreByTestId() {
      // this.$axios
      //   .post(
      //     "/testhistory/findScoreByTestId",
      //     this.$qs.stringify({ testId: this.testForm.testId }),
      //     // { headers: authHeader() }
      //   )
      //   .then((res) => {
      //     this.opinionData = res.data;
      //   });
    },
    drawObjectChart() {
      // 加载HTML元素
      setTimeout(() => {
        this.charts = this.$echarts.init(this.$refs.objectChart);
        this.charts.setOption({
          xAxis: {
            type: "category",
            data: [
              "Mon",
              "Tue",
              "Wed",
              "Thu",
              "Fri",
              "Sat",
              "Sun",
              "Fri",
              "Sat",
              "Sun",
              "Sat",
              "Sun",
              "Fri",
              "Sat",
              "Sun",
              "Sat",
              "Sun",
              "Fri",
              "Sat",
              "Sun",
            ],
          },
          yAxis: {
            type: "value",
          },
          series: [
            {
              data: [
                120, 200, 150, 80, 70, 110, 130, 70, 110, 130, 110, 130, 70,
                110, 130, 110, 130, 110, 130, 70,
              ],
              type: "bar",
            },
          ],
          // tooltip: {
          //   trigger: "item",
          // },
          // legend: {
          //   orient: "vertical",
          //   x: "left",
          //   data: this.opinion,
          // },
        });
      }, 0);
    },
  },
};
</script>
<style scoped>
</style>