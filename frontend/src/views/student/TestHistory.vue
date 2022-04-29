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
        :filters="subNameFilterData"
        :filter-method="subNameFilter"
        filter-placement="bottom-end"
      >
      </el-table-column>
      <el-table-column prop="correct" label="成绩"> </el-table-column>
      <el-table-column prop="finishDate" label="完成时间">
        <template #default="scope">
          {{
            scope.row.finishDate
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
        <!-- <template #default="scope">
          <el-button
            @click="toTestDetail(scope.row.testId, scope.row.stuId)"
            >查看详细</el-button
          >
        </template> -->
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
// import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      testHistoryForm: {
        id: "",
        stuId: "",
        testId: "",
        testName: "",
        description: "",
        right: "",
        finishDate: "",
      },
      subNameFilterData: [],
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
    loadData() {
      this.findAll();
      this.findAllSubId();
    },

    findAll() {
      this.$axios
        .post(
          "/testhistory/findAllByAccount",
          this.$qs.stringify({
            userId: this.$storage.getStorageSync("user").id,
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

    findAllSubId() {
      this.$axios
        .post(
          "/subject/findAllSubId"
          // { headers: authHeader() }
        )
        .then((response) => {
          this.subNameFilterData = response.data;
        });
    },
    subNameFilter(value, row) {
      return row.subName == value;
    },
  },
};
</script>
<style>
</style>