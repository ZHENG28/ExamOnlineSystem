<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">用户信息</h2>
      <div style="float: right">
        <el-button
          @click="
            clearFormFields();
            isAdd = true;
            dialogFormVisible = true;
            findDistinctRole();
            findAllMajorAndClazz();
          "
          >添加</el-button
        >
        <el-button
          type="danger"
          @click="del(this.multiSelection)"
          :disabled="false"
          >删除</el-button
        >
      </div>
      <el-dialog
        :title="(isAdd ? '添加' : '修改') + '用户信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="userForm"
          :rules="formRules"
          ref="userForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="角色" prop="roleName">
            <template #default="scope">
              <div v-if="!isAdd">
                <!-- <el-tag :type="scope.row.role == '学生' ? 'success' : 'danger'"> -->
                <!-- {{ scope.row.roleName }} -->
                <el-tag type="success"> 学生 </el-tag>
              </div>
              <el-select
                filterable
                placeholder="请选择用户角色"
                @change="valueToRoleId"
                v-model="userForm.role"
                v-if="isAdd"
              >
                <el-option
                  v-for="role in roleListData"
                  :key="role.roleId"
                  :label="role.description"
                  :value="role.roleId"
                >
                </el-option>
              </el-select>
            </template>
          </el-form-item>
          <el-form-item
            label="账号"
            prop="account"
            :rules="{
              required: this.isAdd,
              message: '请填写账号',
              trigger: 'blur',
            }"
          >
            <el-input
              v-model="userForm.account"
              v-bind:class="{ readonly: !isAdd }"
            ></el-input>
          </el-form-item>
          <el-form-item label="名字" prop="username">
            <el-input v-model="userForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="userForm.password"
              v-bind:class="{ readonly: !isAdd }"
            ></el-input>
          </el-form-item>
          <el-form-item label="专业班级" prop="clazz.clazzId">
            <el-cascader
              @change="valueToCascade"
              v-model="majorclazzName"
              placeholder="请选择专业班级"
              :options="majorclazzArr"
              filterable
              :show-all-levels="false"
              :props="{ expandTrigger: 'hover' }"
            ></el-cascader>
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
            data.account.toLowerCase().includes(search.toLowerCase()) ||
            data.username.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
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
      <el-table-column prop="account" label="账号"> </el-table-column>
      <el-table-column prop="username" label="名字"> </el-table-column>
      <el-table-column prop="clazzName" label="专业班级"> </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入账号或名字进行搜索" />
        </template>
        <template #default="scope">
          <el-button
            @click="
              clearFormFields();
              isAdd = false;
              dialogFormVisible = true;
              loadInfo(scope.row.account);
            "
            >编辑</el-button
          >
          <el-button type="danger" @click="del([scope.row])">删除</el-button>
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
import authHeader from "@/services/auth-header";
import User from "@/services/user.js";
// import UserService from "@/services/user.service";
export default {
  data() {
    return {
      multiSelection: [],
      isAdd: true,
      dialogFormVisible: false,
      tchId: "",
      tchPwd: "",
      // userForm: new Teacher("", "", "123", "", "", "", ""),
      userForm: new User("", "", "", ""),
      majorclazzArr: [],
      majorclazzName: [],
      roleFilterData: [],
      formRules: {
        roleName: [{ required: true, message: "请选择角色", trigger: "blur" }],
        username: [{ required: true, message: "请填写姓名", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
        major: [{ required: false, message: "请选择专业", trigger: "blur" }],
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
      // this.userForm = new Teacher("", "", "123", "", "", "", "");
      this.userForm = "";
    },

    findAll() {
      this.$axios
        .post(
          "/teacher/findAll",
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

    roleFilter(value, row) {
      return row.role === value;
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

    findAllMajorAndClazz() {
      this.$axios
        .get(
          "/clazz/findAllMajorAndClazz"
          // { headers: authHeader(), }
        )
        .then((response) => {
          this.majorclazzArr = response.data;
        });
    },
    valueToCascade(row) {
      this.stuForm.clazz.clazzId = row[1];
    },
    findDistinctRole() {
      this.$axios
        .get("/role/getDistinctRole", { headers: authHeader() })
        .then((response) => {
          this.roleListData = response.data;
        });
    },
    valueToRoleId(val) {
      this.quesForm.subId = val;
    },

    loadInfo(account) {
      this.$axios
        .post(
          "/teacher/findByAccount",
          this.$qs.stringify({ account: account }),
          { headers: authHeader() }
        )
        .then((res) => {
          let tch = res.data;
          let form = this.userForm;
          this.tchId = tch.id;
          this.tchPwd = tch.password;
          form.account = tch.account;
          form.tchName = tch.tchName;
          form.password = "$2a$10$";
          form.sex = tch.sex == null ? "" : tch.sex == 1 ? "男" : "女";
          form.major = tch.major;
          form.tel = tch.tel;
          form.email = tch.email;
        });
    },
    save() {
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          UserService.addTch(this.userForm)
            .then(
              (response) => {
                this.dialogFormVisible = false;
                if (response.data.message == "OK") {
                  this.$message.success(
                    (this.isAdd ? "添加" : "修改") + "成功"
                  );
                  this.loadData();
                } else {
                  this.$message.error((this.isAdd ? "添加" : "修改") + "失败");
                }
              },
              (error) => {
                this.$message.error("账号重复，请重新填写");
                console.log("error:" + error.message);
              }
            )
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
            });
        }
      });
    },
    modify() {
      //   if (this.userForm.tchName == "") {
      //     this.$message.warning("请填写姓名");
      //     return;
      //   }
      //   if (this.userForm.password == "") {
      //     this.$message.warning("请填写密码");
      //     return;
      //   }
      //   if (this.userForm.password == "$2a$10$") {
      //     this.userForm.password = this.tchPwd;
      //   }
      //   let gender = this.userForm.sex;
      //   this.userForm.sex = gender == "" ? null : gender == "男" ? 1 : 0;
      //   this.$axios
      //     .post(
      //       "/tch/modify",
      //       {
      //         id: this.tchId,
      //         account: this.userForm.account,
      //         tchName: this.userForm.tchName,
      //         password: this.userForm.password,
      //         sex: this.userForm.sex,
      //         major: this.userForm.major,
      //         tel: this.userForm.tel,
      //         email: this.userForm.email,
      //       },
      //       { headers: authHeader() }
      //     )
      //     .then(
      //       (res) => {
      //         if (res.data.message == "OK") {
      //           this.$message.success("修改成功");
      //           this.loadData();
      //         } else {
      //           this.$message.error("修改失败");
      //         }
      //         this.clearFormFields();
      //         this.modifyFormVisible = false;
      //       },
      //       (error) => {
      //         console.log("error:" + error.message);
      //         this.$message.info("数据出错");
      //       }
      //     )
      //     .catch(function (error) {
      //       this.$message.info("数据出错");
      //       console.log(error);
      //       this.clearFormFields();
      //     });
    },

    handleSelectionChange(val) {
      this.multiSelection = val;
    },
    del(arr) {
      this.$confirm("此操作将永久删除信息, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let params = [];
          arr.forEach(function (item) {
            params.push(item.id);
          });
          this.$axios
            .post(
              "/teacher/del",
              this.$qs.stringify(
                {
                  teacherId: params,
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
              this.$message.error("删除失败，请检查");
            });
        })
        .catch(() => {
          this.$message.info("已取消删除");
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