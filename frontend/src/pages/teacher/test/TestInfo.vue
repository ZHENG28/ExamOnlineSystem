<template>
  <div class="row">
    <div class="col-12">
      <div>
        <h3 style="display: inline">测验信息</h3>
        <div style="float: right">
          <el-button @click="toTestBuild(0)">添加</el-button>
          <el-button type="danger" @click="multidel()" :disabled="false"
            >删除</el-button
          >
        </div>
      </div>
      <el-table
        :data="
          tableData.filter(
            (data) =>
              !search ||
              data.testId.toLowerCase().includes(search.toLowerCase())
          )
        "
        height="480px"
        border
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="40"> </el-table-column>
        <el-table-column type="index" label="序号" width="50">
        </el-table-column>
        <el-table-column prop="testName" label="测验标题">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p style="width: 200px">简介: {{ scope.row.description }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag>{{ scope.row.testName }}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column
          prop="sub.subName"
          label="所属科目"
          :filters="subArr"
          :filter-method="filterSubId"
          filter-placement="bottom-end"
        >
        </el-table-column>
        <el-table-column
          prop="testClazz.clazzName"
          label="测验班级"
          :filters="clazzArr"
          :filter-method="filterClazz"
          filter-placement="bottom-end"
        >
        </el-table-column>
        <el-table-column prop="examTime" label="重测次数" width="90">
        </el-table-column>
        <el-table-column prop="endDate" label="结束时间"> </el-table-column>
        <el-table-column label="搜索">
          <!-- eslint-disable-next-line -->
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入测验标题进行搜索"
            />
          </template>
          <template slot-scope="scope">
            <el-button @click="toTestBuild(scope.row.testId)">编辑</el-button>
            <el-button type="danger" @click="del(scope.row)">删除</el-button>
          </template>
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
      clazzArr: [],
      search: "",
      test: [],
      multipleSelection: [],
      multipleSelectionFlag: true,
      dialogTableVisible: false,
      modifyFormVisible: false,
      dialogFormVisible: false,
      testForm: {
        testId: "",
        testName: "",
        description: "",
        sub: {
          subId: "",
          subName: "",
        },
        quesArr: "",
        beginDate: "",
        endDate: "",
        date: [],
        examDure: "",
        testClazz: {
          clazzId: "",
          clazzName: "",
        },
        totalScore: "",
        examTime: "",
      },
      formLabelWidth: "200px",
      FormRules: {
        description: [
          { required: false, message: "请填写本次测验简介", trigger: "blur" },
        ],
        testSize: [
          { required: true, message: "请填写测验题数", trigger: "blur" },
        ],
        examTime: [
          { required: false, message: "请填写测验时长", trigger: "blur" },
        ],
        endDate: [
          { required: true, message: "请填写开始-结束时间", trigger: "blur" },
        ],
        totalScore: [
          { required: false, message: "请填写测验总分", trigger: "blur" },
        ],
        subName: [
          { required: false, message: "请选择所属科目", trigger: "blur" },
        ],
        clazzName: [
          { required: false, message: "请选择测验班级", trigger: "blur" },
        ],
      },
      pageno: 0,
      size: 10,
      totalItems: 0,
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    filterClazz(value, row) {
      return value == row.testClazz.clazzName;
    },
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
    toTestBuild(testId) {
      this.$router.push({
        path: `/teacher/testBuild/${testId}`,
      });
    },
    clearFormFields() {
      this.testForm = {
        testId: "",
        description: "",
        testSize: "",
        examTime: "",
        buildDate: "",
        endDate: "",
        testClazz: {
          clazzId: "",
          clazzName: "",
        },
        totalScore: "",
        subId: {
          subId: "",
          subName: "",
        },
      };
    },

    loadData() {
      this.findAll();
      this.$ajax
        .post("/test/findAllSubId", {}, { headers: authHeader() })
        .then((response) => {
          this.subArr = response.data;
        });
      this.$ajax
        .post("/test/findAllClazz", {}, { headers: authHeader() })
        .then((response) => {
          this.clazzArr = response.data;
        });
    },
    findAll() {
      this.$ajax
        .post(
          "/test/findAll",
          this.$qs.stringify({
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

    del(row) {
      this.$confirm("此操作将永久删除信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$ajax
            .post(
              "/test/del",
              this.$qs.stringify({
                testId: row.testId,
                pageno: this.pageno,
                size: this.size,
              }),
              { headers: authHeader() }
            )
            .then((response) => {
              this.tableData = response.data.content;
              this.totalItems = response.data.totalElements;
            });
          this.$message.success("删除成功!");
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if (this.multipleSelection.length == 0) {
        // this.multipleSelectionFlag = true;
      } else {
        // this.multipleSelectionFlag = false;
      }
    },
    multidel() {
      this.$confirm("此操作将永久删除信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let checkArr = this.multipleSelection;
          let params = [];
          checkArr.forEach(function (item) {
            params.push(item.testId);
          });
          this.$ajax
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
              this.tableData = response.data.content;
              this.totalItems = response.data.totalElements;
            });
          this.$message.success("删除成功!");
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
  },
};
</script>
<style>
</style>