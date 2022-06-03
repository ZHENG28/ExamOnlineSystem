<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2>测验记录</h2>
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
    >
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
      >
      </el-table-column>
      <el-table-column label="最高成绩/总分">
        <template #default="scope">
          {{ scope.row.maxCorrect }}
          /
          {{ scope.row.questionTotal }}
        </template>
      </el-table-column>
      <el-table-column label="已完成次数/测验总次数">
        <template #default="scope">
          {{ scope.row.completeTime }}
          /
          {{ scope.row.examTime }}
        </template>
      </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入测验标题进行搜索" />
        </template>
        <template #default="scope">
          <el-button
            type="primary"
            @click="toTestDetail(scope.row.testId, scope.row.studentId)"
          >
            查看历史作答
          </el-button>
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
      userId: "",
      subjectFilterData: [],
      search: "",
      tableData: [],
      pageno: 1,
      size: 10,
      totalItems: 0,
    };
  },
  created() {
    this.userId = this.$storage.getStorageSync("user").id;
    this.loadData();
  },
  methods: {
    loadData() {
      this.findAll();
      this.loadSubjectByStudentId();
    },

    findAll() {
      this.$axios
        .get("/testHistory/findAllByUserId", {
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

    loadSubjectByStudentId() {
      this.$axios
        .get("/subject/loadSubjectByStudentId", {
          headers: { Authorization: userToken() },
          params: { studentId: this.userId },
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
      return row.subjectId === value;
    },

    toTestDetail(testId, studentId) {
      this.$router.push(`/student/testHistoryDetail/${testId}/${studentId}`);
    },
  },
};
</script>
<style>
</style>