<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">专业信息</h2>
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
        :title="status + '专业信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="majorForm"
          :rules="formRules"
          ref="majorForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="专业" prop="majorName">
            <el-input v-model="majorForm.majorName"></el-input>
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
            data.majorName.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
      <el-table-column prop="majorName" label="专业"> </el-table-column>
      <el-table-column width="450">
        <template #header>
          <el-input v-model="search" placeholder="输入专业名称进行搜索" />
        </template>
        <template #default="scope">
          <el-button
            @click="
              clearFormFields();
              this.status = '修改';
              dialogFormVisible = true;
              loadInfo(scope.row.majorId);
            "
            >编辑</el-button
          >
          <el-button type="danger" @click="del([scope.row])">删除</el-button>
          <el-button
            type="primary"
            @click="
              dialogTableVisible = true;
              dialogTableTitle = scope.row.majorName;
              dialogTableSubTitle = '所属班级';
              loadClazzByMajorId(scope.row.majorId);
            "
          >
            查看所属班级
          </el-button>
          <el-button
            type="primary"
            @click="
              dialogTableVisible = true;
              dialogTableTitle = scope.row.majorName;
              dialogTableSubTitle = '所有教师信息';
              loadTeacherByMajorId(scope.row.majorId);
            "
          >
            查看所有教师
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="dialogTableTitle + '专业' + dialogTableSubTitle"
      v-model="dialogTableVisible"
      width="1000px"
      top="70px"
    >
      <el-table :data="dialogTableData" border height="540px">
        <el-table-column type="index" label="序号" width="80">
        </el-table-column>
        <el-table-column
          prop="clazzName"
          label="班级"
          v-if="dialogTableSubTitle == '所属班级'"
        >
        </el-table-column>
        <el-table-column
          prop="username"
          label="名字"
          v-if="dialogTableSubTitle == '所有教师信息'"
        >
        </el-table-column>
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
      majorForm: {
        majorId: "",
        majorName: "",
      },
      formRules: {
        majorName: [
          { required: true, message: "请填写专业名称", trigger: "blur" },
        ],
      },

      dialogTableTitle: "",
      dialogTableSubTitle: "",
      dialogTableVisible: false,
      dialogTableData: [],

      multiSelection: [],
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
    },
    clearFormFields() {
      this.majorForm = {};
      this.$nextTick(() => {
        this.$refs.majorForm.clearValidate();
      });
    },

    findAll() {
      this.$axios
        .get("/major/findAll", {
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

    // 新增&编辑
    loadInfo(id) {
      this.$axios
        .get("/major/findById", {
          headers: { Authorization: userToken() },
          params: { majorId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.majorForm = res;
          }
        });
    },
    save() {
      this.$refs.majorForm.validate((valid) => {
        if (valid) {
          this.$axios
            .post(
              "/major/save",
              this.$qs.stringify({
                majorId: this.majorForm.majorId,
                majorName: this.majorForm.majorName,
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
              params.push(item.majorId);
            });
            this.$axios
              .post(
                "/major/delete",
                this.$qs.stringify({ majorId: params }, { indices: false }),
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
    loadClazzByMajorId(id) {
      this.$axios
        .get("/clazz/loadClazzByMajorId", {
          headers: { Authorization: userToken() },
          params: { majorId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.dialogTableData = res;
          }
        });
    },
    loadTeacherByMajorId(id) {
      this.$axios
        .get("/user/loadTeacherByMajorId", {
          headers: { Authorization: userToken() },
          params: { majorId: id },
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