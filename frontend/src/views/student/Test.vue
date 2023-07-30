<template>
  <div>
    <div v-for="testArr in tableData" :key="testArr.testForm">
      <el-row :gutter="30">
        <el-col :span="12" v-for="testForm in testArr" :key="testForm.testId">
          <el-card
            :header="testForm.testName"
            shadow="hover"
            style="height: 250px"
          >
            <template #header>
              <div
                style="
                  display: flex;
                  justify-content: space-between;
                  align-items: center;
                "
              >
                <span style="font-size: larger; font-weight: bold">
                  {{ testForm.testName }}
                </span>
                <el-button
                  style="float: right; padding: 3px 0"
                  type="text"
                  @click="toTestPaper(testForm.testId)"
                  :disabled="Date.parse(testForm.endDate) < new Date()"
                  >开始测验</el-button
                >
              </div>
            </template>
            <div style="line-height: 25px">
              <el-scrollbar :height="150">
                <span>简介：{{ testForm.description }}</span>
                <br />
                <span>
                  所属科目：
                  <el-tag>{{ testForm.subjectName }}</el-tag>
                </span>
                <br />
                <span
                  >结束时间：{{
                    testForm.endDate
                      .toLocaleString()
                      .replace(/T/g, " ")
                      .replace(/\.[\d]{3}Z/, "")
                  }}</span
                >
                <br />
                <span>测验时长：{{ testForm.testDuration }} 分钟</span>
                <br />
                <span>测验次数：{{ testForm.testTime }} 次</span>
                <br />
                <span>测验题数：{{ testForm.questionTotal }} 道</span>
              </el-scrollbar>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div style="margin-top: 10px">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageno"
        :page-sizes="[4, 8, 12]"
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
      tableData: [],
      userId: "",
      pageno: 1,
      size: 4,
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
    },

    findAll() {
      this.$axios
        .get("/test/findAllByUserId", {
          headers: { Authorization: userToken() },
          params: {
            userId: this.userId,
            pageno: this.pageno,
            size: this.size,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.totalItems = res.total;
            this.tableData = [];
            let arr = [];
            res.records.forEach((elem, index) => {
              arr.push(elem);
              if ((index + 1) % 2 == 0) {
                this.tableData.push(arr);
                arr = [];
              } else if (index + 1 == this.totalItems) {
                this.tableData.push(arr);
              }
            });
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

    toTestPaper(testId) {
      this.$axios
        .get("/test/findExamTimeByTestId", {
          headers: { Authorization: userToken() },
          params: {
            userId: this.userId,
            testId: testId,
          },
        })
        .then((response) => {
          if (response.data.success) {
            this.$router.push(`/student/testPaper/${testId}`);
          } else {
            this.$message.error(response.data.message);
            return;
          }
        });
    },
  },
};
</script>
<style scoped>
.el-row {
  margin-bottom: 20px;
}
</style>