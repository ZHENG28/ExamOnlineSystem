<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">教师信息</h2>
      <div style="float: right">
        <el-button
          @click="
            clearFormFields();
            this.status = '添加';
            dialogFormVisible = true;
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
        :title="status + '教师信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="tchForm"
          :rules="formRules"
          ref="tchForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="工号" prop="account">
            <el-input v-model="tchForm.account"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="tchName">
            <el-input
              v-model="tchForm.tchName"
              autofocus="autofocus"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="tchForm.password" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-select v-model="tchForm.sex" placeholder="请选择性别">
              <el-option label="男" value="1"></el-option>
              <el-option label="女" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="专业" prop="major">
            <el-select
              v-model="tchForm.major"
              filterable
              placeholder="请选择专业"
            >
              <el-option
                v-for="major in majorFilterData"
                :key="major.value"
                :label="major.text"
                :value="major.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="电话" prop="tel">
            <el-input v-model="tchForm.tel"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="tchForm.email"></el-input>
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
            data.tchName.toLowerCase().includes(search.toLowerCase()) ||
            data.account.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
      <el-table-column prop="account" label="工号"> </el-table-column>
      <el-table-column prop="tchName" label="姓名"> </el-table-column>
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
      <el-table-column prop="tel" label="电话"> </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入教师工号或姓名进行搜索" />
        </template>
        <template #default="scope">
          <el-button
            @click="
              clearFormFields();
              this.status = '修改';
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
// import authHeader from "@/services/auth-header";
import Teacher from "@/models/teacher";
// import UserService from "@/services/user.service";
export default {
  data() {
    return {
      multiSelection: [],
      status: "",
      dialogFormVisible: false,
      tchId: "",
      tchPwd: "",
      tchForm: new Teacher("", "", "123", "", "", "", ""),
      formRules: {
        account: [{ required: true, message: "请填写工号", trigger: "blur" }],
        tchName: [{ required: true, message: "请填写姓名", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
        major: [{ required: false, message: "请选择专业", trigger: "blur" }],
        tel: [
          {
            required: false,
            message: "请填写电话",
            trigger: "blur",
            inputPattern: /^1[3|4|5|8][0-9]\d{4,8}$/,
            inputErrorMessage: "电话格式不正确",
          },
        ],
        email: [
          {
            required: false,
            message: "请填写邮箱",
            trigger: "blur",
            inputPattern:
              /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
            inputErrorMessage: "邮箱格式不正确",
          },
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
      this.tchForm = new Teacher("", "", "123", "", "", "", "");
    },

    findAll() {
      this.$axios
        .post(
          "/auto/teacher/findAll",
          this.$qs.stringify({
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

    findDistinctMajor() {
      this.$axios
        .get(
          "/auto/clazz/getDistinctMajor"
          // { headers: authHeader() }
        )
        .then((response) => {
          this.majorFilterData = response.data;
        });
    },
    majorFilter(value, row) {
      return row.major === value;
    },

    loadInfo(account) {
      this.$axios
        .post(
          "/auto/teacher/findByAccount",
          this.$qs.stringify({ account: account })
          // { headers: authHeader(), }
        )
        .then((res) => {
          let tch = res.data;
          let form = this.tchForm;
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
      this.$refs.tchForm.validate((valid) => {
        if (valid) {
          UserService.addTch(this.tchForm)
            .then(
              (response) => {
                this.dialogFormVisible = false;
                if (response.data.message == "OK") {
                  this.$message.success(this.status + "成功");
                  this.loadData();
                } else {
                  this.$message.error(this.status + "失败");
                }
              },
              (error) => {
                this.$message.error("工号重复，请重新填写");
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
      //   if (this.tchForm.tchName == "") {
      //     this.$message.warning("请填写姓名");
      //     return;
      //   }
      //   if (this.tchForm.password == "") {
      //     this.$message.warning("请填写密码");
      //     return;
      //   }
      //   if (this.tchForm.password == "$2a$10$") {
      //     this.tchForm.password = this.tchPwd;
      //   }
      //   let gender = this.tchForm.sex;
      //   this.tchForm.sex = gender == "" ? null : gender == "男" ? 1 : 0;
      //   this.$axios
      //     .post(
      //       "/tch/modify",
      //       {
      //         id: this.tchId,
      //         account: this.tchForm.account,
      //         tchName: this.tchForm.tchName,
      //         password: this.tchForm.password,
      //         sex: this.tchForm.sex,
      //         major: this.tchForm.major,
      //         tel: this.tchForm.tel,
      //         email: this.tchForm.email,
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
              "/auto/teacher/del",
              this.$qs.stringify(
                {
                  teacherId: params,
                  pageno: this.pageno,
                  size: this.size,
                },
                { indices: false }
              )
              // { headers: authHeader() }
            )
            .then((response) => {
              this.tableData = response.data.records;
              this.totalItems = response.data.total;
              this.$message.success("删除成功！");
            })
            .catch(() => {
              this.$message.error("删除失败，请检查待删除教师的相关内容。");
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