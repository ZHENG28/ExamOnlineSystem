<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">测验信息</h2>
      <div style="float: right">
        <el-button @click="toTestBuild(0)">新增</el-button>
        <el-button type="danger" @click="del(this.multiSelection)"
          >删除</el-button
        >
      </div>
    </div>
    <el-table
      :data="
        tableData.filter(
          (data) =>
            !search ||
            data.testName.toLowerCase().includes(search.toLowerCase())
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
            <p style="width: 150px">简介: {{ scope.row.description }}</p>
            <template #reference class="name-wrapper">
              <span>{{ scope.row.testName }}</span>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="subjectName"
        label="所属科目"
        :filters="subjectFilterData"
        :filter-method="subjectFilter"
        width="150"
      >
        <template #default="scope">
          <el-popover trigger="hover" placement="right">
            <p style="width: 150px">测验班级: {{ scope.row.clazzName }}</p>
            <template #reference class="name-wrapper">
              <span>{{ scope.row.subjectName }}</span>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="questionTotal" label="测验题数" width="90">
      </el-table-column>
      <el-table-column prop="examTime" label="测验次数" width="90">
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
          <el-button
            :disabled="Date.parse(scope.row.beginDate) < new Date()"
            @click="toTestBuild(scope.row.testId)"
            >编辑</el-button
          >
          <el-button type="danger" @click="del([scope.row])">删除</el-button>
          <el-button type="primary" @click="toTestResult(scope.row.testId)"
            >查看测验结果</el-button
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
  </div>
</template>
<script>
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";
export default {
  data() {
    return {
      multiSelection: [],
      subjectFilterData: [],
      search: "",
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
    // 初始化页面
    loadData() {
      this.findAll();
      this.loadSubjectByTeacherId();
    },

    findAll() {
      this.$axios
        .get("/test/findAllByUserId", {
          headers: { Authorization: userToken() },
          params: {
            userId: this.$storage.getStorageSync("user").id,
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

    loadSubjectByTeacherId() {
      this.$axios
        .get("/subject/loadSubjectByTeacherId", {
          headers: { Authorization: userToken() },
          params: { teacherId: this.$storage.getStorageSync("user").id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.subjectFilterData = [];
            res.forEach((item) => {
              this.subjectFilterData.push({
                text: item.subjectName,
                value: item.subjectId,
              });
            });
          }
        });
    },
    subjectFilter(value, row) {
      return row.subjectId == value;
    },

    // 新增&编辑
    toTestBuild(testId) {
      this.$router.push(`/teacher/testBuild/${testId}`);
    },
    // 查看测验结果
    toTestResult(testId) {
      this.$router.push(`/teacher/testResult/${testId}`);
    },

    // 删除
    handleSelectionChange(val) {
      this.multiSelection = val;
    },
    del(arr) {
      if (arr.length) {
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
                "/test/delete",
                this.$qs.stringify({ testId: params }, { indices: false }),
                { headers: { Authorization: userToken() } }
              )
              .then((response) => {
                this.$message({
                  type: response.data.success ? "success" : "error",
                  message: response.data.message,
                });
                this.loadData();
              });
          })
          .catch(() => {
            this.$message.info("已取消删除");
          });
      } else {
        this.$message.info("请选择要删除的信息");
      }
    },
  },
};
</script>
<style scoped>
</style>