<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">班级信息</h2>
      <div style="float: right">
        <el-button
          @click="
            clearFormFields();
            this.status = '添加';
            dialogFormVisible = true;
          "
          >添加</el-button
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
          <el-form-item label="专业" prop="major">
            <el-input v-model="clazzForm.major"></el-input>
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
            data.major.toLowerCase().includes(search.toLowerCase()) ||
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
        prop="major"
        label="专业"
        :filters="majorFilterData"
        :filter-method="majorFilter"
      >
        <template #default="scope">
          {{ scope.row.major }}
        </template>
      </el-table-column>
      <el-table-column prop="clazzName" label="班级"> </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入专业或班级进行搜索" />
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
              loadUserByClazzId(scope.row.clazzId);
            "
            >查看用户</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="'查看' + dialogTableTitle + '班的用户信息'"
      v-model="dialogTableVisible"
      width="1000px"
      top="70px"
    >
      <el-table :data="dialogTableData" border height="540px">
        <el-table-column type="index" label="序号" width="80">
        </el-table-column>
        <el-table-column
          prop="role"
          label="角色"
          :filters="roleFilterData"
          :filter-method="roleFilter"
        >
          <template #default="scope">
            <el-tag :type="scope.row.role == '学生' ? 'success' : 'danger'">{{
              scope.row.role
            }}</el-tag>
          </template>
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
import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      multiSelection: [],
      status: "",
      dialogFormVisible: false,
      dialogTableTitle: "",
      dialogTableVisible: false,
      roleFilterData: [],
      dialogTableData: [],
      clazzForm: {
        clazzId: "",
        major: "",
        clazzName: "",
      },
      formRules: {
        major: [{ required: true, message: "请填写专业名称", trigger: "blur" }],
        clazzName: [
          { required: true, message: "请填写班级名称", trigger: "blur" },
        ],
      },
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
    loadData() {
      this.findAll();
      this.findDistinctMajor();
    },
    clearFormFields() {
      this.clazzForm = {};
      this.$nextTick(() => {
        this.$refs.clazzForm.clearValidate();
      });
    },

    findAll() {
      this.$axios
        .post(
          "/clazz/findAll",
          this.$qs.stringify({
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

    findDistinctMajor() {
      this.$axios
        .get("/clazz/getDistinctMajor", { headers: authHeader() })
        .then((response) => {
          this.majorFilterData = response.data;
        });
    },
    majorFilter(value, row) {
      return row.major === value;
    },

    loadInfo(id) {
      this.$axios
        .post("/clazz/findById", this.$qs.stringify({ clazzId: id }), {
          headers: authHeader(),
        })
        .then((response) => {
          this.clazzForm = response.data;
        });
    },
    save() {
      this.$refs.clazzForm.validate((valid) => {
        if (valid) {
          this.$axios
            .get("/clazz/save", {
              headers: authHeader(),
              params: {
                clazzId: this.clazzForm.clazzId,
                major: this.clazzForm.major,
                clazzName: this.clazzForm.clazzName,
              },
            })
            .then((response) => {
              this.dialogFormVisible = false;
              if (response.data) {
                this.$message.success(this.status + "成功");
                this.loadData();
              } else {
                this.$message.error(this.status + "失败");
              }
            })
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
            });
        }
      });
    },

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
                "/clazz/del",
                this.$qs.stringify(
                  {
                    clazzId: params,
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

    loadUserByClazzId(id) {
      this.$axios
        .get(
          "/userClazz/findUserByClazzId",
          this.$qs.stringify({ clazzId: id }),
          {
            headers: authHeader(),
          }
        )
        .then((response) => {
          this.dialogTableData = response.data.records;
        });
      this.findDistinctRole();
    },
    findDistinctRole() {
      this.$axios
        .get("/role/getDistinctRole", { headers: authHeader() })
        .then((response) => {
          response.data.forEach((item) => {
            this.roleFilterData.push({
              text: item.description,
              value: item.description,
            });
          });
        });
    },
    roleFilter(value, row) {
      return row.role === value;
    },
  },
};
</script>
<style scoped>
.el-input {
  width: 250px;
}
</style>