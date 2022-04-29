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
            <p style="width: 200px">简介: {{ scope.row.description }}</p>
            <template #reference class="name-wrapper">
              <span>{{ scope.row.testName }}</span>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="subjectName"
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
import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      multiSelection: [],
      subIdFilterData: [],
      clazzFilterData: [],
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
      this.findClazzAndSubIdFilterData();
    },

    findAll() {
      this.$axios
        .post(
          "/test/findAllByUserId",
          this.$qs.stringify({
            userId: this.$storage.getStorageSync("user").id,
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

    findClazzAndSubIdFilterData() {
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
      this.$axios
        .get("/clazz/getDistinctClazz", { headers: authHeader() })
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

    // 新增&编辑
    toTestBuild(testId) {
      this.$router.push({
        path: `/teacher/testBuild/${testId}`,
      });
    },
    // 查看测验结果
    toTestResult(testId) {
      this.$router.push({
        path: `/teacher/testResult/${testId}`,
      });
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
                "/test/del",
                this.$qs.stringify(
                  {
                    testId: params,
                    pageno: this.pageno,
                    size: this.size,
                  },
                  { indices: false }
                ),
                { headers: authHeader() }
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
      } else {
        this.$message.info("请选择要删除的信息");
      }
    },
  },
};
</script>
<style scoped>
</style>