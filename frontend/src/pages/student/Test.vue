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
          <el-card class="box-card" shadow="hover" style="height: 250px">
            <div slot="header" class="clearfix">
              <span>{{ testForm.testName }}</span>
              <el-button
                style="float: right; padding: 3px 0"
                type="text"
                @click="toTestPaper(testForm.testId)"
                >开始测验</el-button
              >
            </div>
            <div class="text item cust-div">
              <span>简介：{{ testForm.description }}</span>
              <br />
              <span>结束时间：{{ testForm.endDate }}</span>
              <br />
              <span>测验时长：{{ testForm.examDure }}</span>
              <br />
              <span>重测次数：{{ testForm.examTime }}</span>
              <br />
              <span>测验总分：{{ testForm.totalScore }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageno + 1"
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
import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      // 放在findAll后的数据
      tableData: [],

      // 分页查询相关参数
      pageno: 0,
      size: 4,
      totalItems: 0,
    };
  },
  created() {
    this.findAll();
  },
  methods: {
    // 分页查询
    handleSizeChange(size) {
      this.size = size;
      this.findAll();
    },
    handleCurrentChange(pageno) {
      this.pageno = pageno - 1;
      this.findAll();
    },
    // 跳转（将router放在systemLayout框架的外面，即可不受影响）
    toTestPaper(testId) {
      this.$ajax
        .post(
          "/test/findExamTimeByTestId",
          this.$qs.stringify({
            account: this.$store.state.auth.user.account,
            testId: testId,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          if (response.data == "OK") {
            this.$router.push({
              path: `/student/testPaper/${testId}`,
            });
          } else if (response.data == "No") {
            this.$message.error("重测次数已达上限");
            return;
          }
        });
    },

    findAll() {
      this.$ajax
        .post(
          "/test/findAllByAccount",
          this.$qs.stringify({
            account: this.$store.state.auth.user.account,
            pageno: this.pageno,
            size: this.size,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.totalItems = response.data.totalElements;
          let tableArr = [];
          let arr = [];
          response.data.content.forEach((elem, index) => {
            arr.push(elem);
            if ((index + 1) % 2 == 0) {
              tableArr.push(arr);
              arr = [];
            } else if (index + 1 == this.totalItems) {
              tableArr.push(arr);
            }
          });
          this.tableData = tableArr;
          // console.log(this.tableData);
        });
    },
  },
};
</script>
<style>
.el-row {
  margin-bottom: 20px;
}
</style>