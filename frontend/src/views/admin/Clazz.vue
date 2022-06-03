<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">班级信息</h2>
      <div style="float: right">
        <el-button
          @click="
            clearFormFields();
            this.status = '新增';
            dialogFormVisible = true;
          "
          >新增</el-button
        >
        <el-button type="danger" @click="del(this.multiSelection)"
          >删除</el-button
        >
      </div>
      <el-dialog
        :title="status + '班级信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="clazzForm"
          :rules="formRules"
          ref="clazzForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="专业" prop="majorId">
            <el-select
              filterable
              placeholder="请选择专业"
              @change="valueToMajorId"
              v-model="clazzForm.majorId"
            >
              <el-option
                v-for="major in majorList"
                :key="major.majorId"
                :label="major.majorName"
                :value="major.majorId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="班级" prop="clazzName">
            <el-input v-model="clazzForm.clazzName"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="save()">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
    <el-table
      :data="
        tableData.filter(
          (data) =>
            !search ||
            data.clazzName.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
      <el-table-column
        prop="majorName"
        label="专业"
        :filters="majorFilterData"
        :filter-method="majorFilter"
      >
      </el-table-column>
      <el-table-column prop="clazzName" label="班级"> </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入班级进行搜索" />
        </template>
        <template #default="scope">
          <el-button
            @click="
              clearFormFields();
              this.status = '修改';
              dialogFormVisible = true;
              loadInfo(scope.row.clazzId);
            "
            >编辑</el-button
          >
          <el-button type="danger" @click="del([scope.row])">删除</el-button>
          <el-button
            type="primary"
            @click="
              dialogTableVisible = true;
              dialogTableTitle = scope.row.clazzName;
              loadStudentByClazzId(scope.row.clazzId);
            "
            >查看所有学生</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="dialogTableTitle + '班级所有学生信息'"
      v-model="dialogTableVisible"
      width="1000px"
      top="70px"
    >
      <el-table :data="dialogTableData" border height="540px">
        <el-table-column type="index" label="序号" width="80">
        </el-table-column>
        <el-table-column prop="username" label="名字"> </el-table-column>
      </el-table>
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
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";
export default {
  data() {
    return {
      status: "",
      dialogFormVisible: false,
      clazzForm: {
        clazzId: "",
        clazzName: "",
        majorId: "",
        majorName: "",
      },
      formRules: {
        clazzName: [
          { required: true, message: "请填写班级名称", trigger: "blur" },
        ],
        majorId: [
          { required: true, message: "请填写专业名称", trigger: "change" },
        ],
      },

      dialogTableTitle: "",
      dialogTableVisible: false,
      dialogTableData: [],
      roleFilterData: [],

      multiSelection: [],
      majorList: [],
      majorFilterData: [],
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
      this.loadMajorList();
    },
    clearFormFields() {
      this.clazzForm = {};
      this.$nextTick(() => {
        this.$refs.clazzForm.clearValidate();
      });
    },

    findAll() {
      this.$axios
        .get("/clazz/findAll", {
          headers: { Authorization: userToken() },
          params: {
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

    loadMajorList() {
      this.$axios
        .get("/major/findAll", { headers: { Authorization: userToken() } })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.majorList = res;
            this.majorFilterData = [];
            res.forEach((item) => {
              this.majorFilterData.push({
                text: item.majorName,
                value: item.majorId,
              });
            });
          }
        });
    },
    majorFilter(value, row) {
      return row.majorId === value;
    },
    valueToMajorId(val) {
      this.clazzForm.majorId = val;
    },

    // 新增&编辑
    loadInfo(id) {
      this.$axios
        .get("/clazz/findById", {
          headers: { Authorization: userToken() },
          params: { clazzId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.clazzForm = res;
          }
        });
    },
    save() {
      this.$refs.clazzForm.validate((valid) => {
        if (valid) {
          this.$axios
            .post(
              "/clazz/save",
              this.$qs.stringify({
                clazzId: this.clazzForm.clazzId,
                clazzName: this.clazzForm.clazzName,
                majorId: this.clazzForm.majorId,
                status: this.status,
              }),
              { headers: { Authorization: userToken() } }
            )
            .then((response) => {
              this.dialogFormVisible = false;
              this.$message({
                type: response.data.success ? "success" : "error",
                message: response.data.message,
              });
              this.loadData();
            })
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
            });
        }
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
              params.push(item.clazzId);
            });
            this.$axios
              .post(
                "/clazz/delete",
                this.$qs.stringify({ clazzId: params }, { indices: false }),
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

    // 查看用户
    loadStudentByClazzId(id) {
      this.$axios
        .get("/user/loadStudentByClazzId", {
          headers: { Authorization: userToken() },
          params: { clazzId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.dialogTableData = res;
          }
        });
    },
  },
};
</script>
<style scoped>
.el-input {
  width: 250px;
}
</style>