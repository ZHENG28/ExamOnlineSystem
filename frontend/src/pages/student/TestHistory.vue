<template>
  <div class="row">
    <div class="col-12">
      <div>
        <h3>测验记录</h3>
      </div>
      <el-table
        :data="
          tableData.filter(
            (data) =>
              !search ||
              data.test.testName.toLowerCase().includes(search.toLowerCase())
          )
        "
        height="480px"
        border
        style="width: 100%"
      >
        <el-table-column type="index" label="序号" width="50">
        </el-table-column>
        <el-table-column prop="testName" label="测验标题">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p style="width: 200px">简介: {{ scope.row.test.description }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag>{{ scope.row.test.testName }}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column
          prop="test.sub.subName"
          label="所属科目"
          :filters="subArr"
          :filter-method="filterSubId"
          filter-placement="bottom-end"
        >
        </el-table-column>
        <el-table-column prop="score" label="成绩"> </el-table-column>
        <el-table-column prop="finishDate" label="完成时间"> </el-table-column>
        <el-table-column label="搜索">
          <!-- eslint-disable-next-line -->
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入测验标题进行搜索"
            />
          </template>
          <!-- <template slot-scope="scope">
            <el-button @click="toTestDetail(scope.row.test.testId, scope.row.stu.stuId)"
              >查看详细</el-button
            >
          </template> -->
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageno + 1"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="size"
          layout="total, sizes, ->, pager, next, jumper"
          :total="totalItems"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      tableData: [],
      subArr: [],
      search: "",
      // 测验信息
      // 测验记录信息
      testHistoryForm: {
        id: "",
        stu: {
          stuId: "",
        },
        test: {
          testId: "",
          testName: "",
          description: "",
        },
        score: "",
        finishDate: "",
      },
      pageno: 0,
      size: 10,
      totalItems: 0,
    };
  },
  created() {
    this.loadData();
    this.findAll();
  },
  methods: {
    filterSubId(value, row) {
      return value == row.subId.subName;
    },
    handleSizeChange(size) {
      this.size = size;
      this.findAll();
    },
    handleCurrentChange(pageno) {
      this.pageno = pageno - 1;
      this.findAll();
    },

    loadData() {
      this.findAll();
      this.$ajax
        .post("/testHistory/findAllSubId", {}, { headers: authHeader() })
        .then((response) => {
          this.subArr = response.data;
        });
    },
    findAll() {
      this.$ajax
        .post(
          "/testHistory/findAllByAccount",
          this.$qs.stringify({
            account: this.$store.state.auth.user.account,
            pageno: this.pageno,
            size: this.size,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.tableData = response.data.content;
          this.totalItems = response.data.totalElements;
        });
    },
  },
};
</script>
<style>
</style>