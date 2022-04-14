<template>
  <div>
    <div v-for="testArr in tableData" :key="testArr.testForm">
      <el-row :gutter="30">
        <el-col
          :span="12"
          class="cust-col"
          v-for="testForm in testArr"
          :key="testForm.testId"
        >
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
                <span style="font-size: larger; font-weight: bold">{{
                  testForm.testName
                }}</span>
                <el-button
                  style="float: right; padding: 3px 0"
                  type="text"
                  @click="toTestPaper(testForm.testId)"
                  >开始测验</el-button
                >
              </div>
            </template>
            <div style="line-height: 25px">
              <span>简介：{{ testForm.description }}</span>
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
              <span>测验时长：{{ testForm.examDure }} 分钟</span>
              <br />
              <span>重测次数：{{ testForm.examTime }} 次</span>
              <br />
              <span>测验总分：{{ testForm.totalScore }} 分</span>
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
// import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      tableData: [],
      pageno: 1,
      size: 4,
      totalItems: 0,
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.findAll();
    },

    findAll() {
      this.$axios
        .post(
          "/test/findAllByStuIdOrNot",
          this.$qs.stringify({
            // identity: this.$store.state.auth.initialState.user.identity,
            pageno: this.pageno,
            size: this.size,
          })
          // { headers: authHeader() }
        )
        .then((response) => {
          this.totalItems = response.data.total;
          let tableArr = [];
          let arr = [];
          response.data.records.forEach((elem, index) => {
            arr.push(elem);
            if ((index + 1) % 2 == 0) {
              tableArr.push(arr);
              arr = [];
            } else if (index + 1 == this.totalItems) {
              tableArr.push(arr);
            }
          });
          this.tableData = tableArr;
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
        .post(
          "/test/findExamTimeByTestId",
          this.$qs.stringify({
            // account: this.$store.state.auth.initialState.user.account,
            account: "111",
            testId: testId,
          })
          // { headers: authHeader() }
        )
        .then((response) => {
          if (response.data) {
            this.$message.error("重测次数已达上限");
            return;
          } else {
            this.$router.push({
              path: `/student/testPaper/${testId}`,
            });
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