<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">测验信息</h2>
      <div style="float: right">
        <el-button @click="toTestBuild(0)">添加</el-button>
        <el-button
          type="danger"
          @click="del(this.multiSelection)"
          :disabled="false"
          >删除</el-button
        >
      </div>
    </div>
    <el-table
      :data="
        tableData.filter(
          (data) =>
            !search || data.testId.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
      <el-table-column prop="testName" label="测验标题">
        <template #default="scope">
          <el-popover trigger="hover" placement="right">
            <p style="width: 200px">简介: {{ scope.row.description }}</p>
            <template #reference class="name-wrapper">
              <span>{{ scope.row.testName }}</span>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="subName"
        label="所属科目"
        :filters="subIdFilterData"
        :filter-method="subIdFilter"
        filter-placement="bottom-end"
      >
      </el-table-column>
      <el-table-column
        prop="clazzName"
        label="测验班级"
        :filters="clazzFilterData"
        :filter-method="clazzFilter"
        filter-placement="bottom-end"
      >
      </el-table-column>
      <el-table-column prop="examTime" label="重测次数" width="90">
      </el-table-column>
      <el-table-column prop="endDate" label="结束时间">
        <template #default="scope">
          {{
            scope.row.endDate
              .toLocaleString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "")
          }}
        </template>
      </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入测验标题进行搜索" />
        </template>
        <template #default="scope">
          <el-button @click="toTestBuild(scope.row.testId)">编辑</el-button>
          <el-button type="danger" @click="del([scope.row])">删除</el-button>
          <el-button
            type="primary"
            @click="
              testResultVisible = true;
              openChart();
            "
            >查看测验结果</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="本次测验结果" v-model="testResultVisible">
      <div id="chartBox" style="width: 600px; height: 400px"></div>
    </el-dialog>
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
  </div>
</template>
<script>
// import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      multiSelection: [],
      multiSelectionFlag: true,
      subIdFilterData: [],
      clazzFilterData: [],
      search: "",

      isShowDialog: false,
      testResultVisible: false,
      charts: "",
      opinion: ["90-100", "80-89", "70-79", "60-69", "60以下"],
      opinionData: [],

      tableData: [],
      pageno: 1,
      size: 10,
      totalItems: 0,
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.findAll();
      this.findClazzAndSubIdFilterData();
    },

    findClazzAndSubIdFilterData() {
      this.$axios
        .post(
          "/auto/subject/findAllSubId"
          // { headers: authHeader() }
        )
        .then((response) => {
          this.subIdFilterData = response.data;
        });
      this.$axios
        .get(
          "/auto/clazz/getDistinctClazz"
          //  { headers: authHeader() }
        )
        .then((response) => {
          this.clazzFilterData = response.data;
        });
    },
    clazzFilter(value, row) {
      return row.clazzName == value;
    },
    subIdFilter(value, row) {
      return row.subName == value;
    },

    findAll() {
      this.$axios
        .post(
          "/auto/test/findAllByStuIdOrNot",
          this.$qs.stringify({
            pageno: this.pageno,
            size: this.size,
          })
          // { headers: authHeader() }
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

    toTestBuild(testId) {
      this.$router.push({
        path: `/teacher/testBuild/${testId}`,
      });
    },

    handleSelectionChange(val) {
      this.multiSelection = val;
      if (this.multiSelection.length == 0) {
        // this.multiSelectionFlag = true;
      } else {
        // this.multiSelectionFlag = false;
      }
    },
    del(arr) {
      this.$confirm("此操作将永久删除信息, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let params = [];
          arr.forEach(function (item) {
            params.push(item.testId);
          });
          this.$axios
            .post(
              "/auto/test/del",
              this.$qs.stringify(
                {
                  testId: params,
                  pageno: this.pageno,
                  size: this.size,
                },
                { indices: false }
              )
              // { headers: authHeader() }
            )
            .then((response) => {
              this.tableData = response.data.records;
              this.totalItems = response.data.total;
              this.$message.success("删除成功！");
            })
            .catch(() => {
              this.$message.error("删除失败");
            });
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },

    findScoreByTestId() {
      // this.$axios
      //   .post(
      //     "/auto/testhistory/findScoreByTestId",
      //     this.$qs.stringify({ testId: this.testForm.testId }),
      //     // { headers: authHeader() }
      //   )
      //   .then((res) => {
      //     this.opinionData = res.data;
      //   });
    },
    drawPie(id) {
      console.log("12");
      //   this.charts = echarts.init(document.getElementById(id));
      //   this.charts.setOption({
      //     tooltip: {
      //       trigger: "item",
      //     },
      //     legend: {
      //       orient: "vertical",
      //       x: "left",
      //       data: this.opinion,
      //     },
      //     series: [
      //       {
      //         type: "pie",
      //         radius: "55%",
      //         center: ["50%", "60%"],
      //         data: this.opinionData,
      //         itemStyle: {
      //           emphasis: {
      //             shadowBlur: 10,
      //             shadowOffsetX: 0,
      //             shadowColor: "rgba(0, 0, 0, 0.5)",
      //           },
      //         },
      //       },
      //     ],
      //   });
    },
    openChart() {
      // setTimeout(() => {
      this.drawPie("chartBox");
      // }, 0);
    },
  },
};
</script>
<style scoped>
</style>