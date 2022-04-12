<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">学生信息</h2>
      <div style="float: right">
        <el-button
          @click="
            clearFormFields();
            this.status = '添加';
            findAllMajorAndClazz();
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
        :title="status + '学生信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="stuForm"
          :rules="formRules"
          ref="stuForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="学号" prop="account">
            <el-input v-model="stuForm.account"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="stuName">
            <el-input
              v-model="stuForm.stuName"
              autofocus="autofocus"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="stuForm.password" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-select v-model="stuForm.sex" placeholder="请选择性别">
              <el-option label="男" value="1"></el-option>
              <el-option label="女" value="0"></el-option>
            </el-select>
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
            data.stuName.toLowerCase().includes(search.toLowerCase()) ||
            data.account.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"></el-table-column>
      <el-table-column prop="account" label="学号"> </el-table-column>
      <el-table-column prop="stuName" label="姓名"> </el-table-column>
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
      <el-table-column
        prop="clazzName"
        label="班级"
        :filters="clazzFilterData"
        :filter-method="clazzFilter"
        filter-placement="bottom-end"
      >
      </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入学号或姓名进行搜索" />
        </template>
        <template #default="scope">
          <el-button
            @click="
              clearFormFields();
              this.status = '修改';
              dialogFormVisible = true;
              findAllMajorAndClazz();
              loadInfo(scope.row.account);
            "
            >编辑</el-button
          >
          <el-button type="danger" @click="del([scope.row])">删除</el-button>
          <!-- <el-dialog
            title="修改学生信息"
            v-model="modifyFormVisible"
            width="600px"
          >
            <el-form
              :model="stuForm"
              :rules="formRules"
              label-width="200px"
              :label-position="right"
            >
              <el-form-item label="学号" prop="account">
                <el-input
                  v-model="stuForm.account"
                  readonly="readonly"
                ></el-input>
              </el-form-item>
              <el-form-item label="姓名" prop="stuName">
                <el-input
                  v-model="stuForm.stuName"
                  autofocus="autofocus"
                ></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="stuForm.password"></el-input>
              </el-form-item>
              <el-form-item label="性别" prop="sex">
                <el-select v-model="stuForm.sex" placeholder="请选择性别">
                  <el-option label="男" value="1"></el-option>
                  <el-option label="女" value="0"></el-option>
                </el-select>
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
            <div class="dialog-footer">
              <el-button
                @click="
                  clearFormFields();
                  modifyFormVisible = false;
                "
                >取 消</el-button
              >
              <el-button type="primary" @click="modify()">确 定</el-button>
            </div>
          </el-dialog> -->
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
import Student from "@/models/student";
// import UserService from "@/services/user.service";
export default {
  data() {
    return {
      multiSelection: [],
      status: "",
      dialogFormVisible: false,
      majorclazzArr: [],
      stuId: "",
      stuPwd: "",
      stuForm: new Student("", "", "123", "", "", "", ""),
      majorclazzName: [],
      formRules: {
        account: [{ required: true, message: "请填写学号", trigger: "blur" }],
        stuName: [{ required: true, message: "请填写姓名", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
        clazz: [
          { required: false, message: "请选择专业班级", trigger: "blur" },
        ],
      },
      majorFilterData: [],
      clazzFilterData: [],
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
      this.findMajorAndClazzFilterData();
    },
    clearFormFields() {
      this.stuForm = new Student("", "", "123", "", "", "", "");
      this.majorclazzName = [];
    },

    findAll() {
      this.$axios
        .post(
          "/auto/student/findAll",
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

    findMajorAndClazzFilterData() {
      this.$axios
        .get(
          "/auto/clazz/getDistinctMajor"
          // { headers: authHeader(), }
        )
        .then((response) => {
          this.majorFilterData = response.data;
        });
      this.$axios
        .get(
          "/auto/clazz/getDistinctClazz"
          // { headers: authHeader(), }
        )
        .then((response) => {
          this.clazzFilterData = response.data;
        });
    },
    majorFilter(value, row) {
      return value == row.major;
    },
    clazzFilter(value, row) {
      return value == row.clazzName;
    },

    findAllMajorAndClazz() {
      this.$axios
        .get(
          "/auto/clazz/findAllMajorAndClazz"
          // { headers: authHeader(), }
        )
        .then((response) => {
          this.majorclazzArr = response.data;
        });
    },
    valueToCascade(row) {
      this.stuForm.clazz.clazzId = row[1];
    },

    loadInfo(account) {
      this.$axios
        .post(
          "/auto/student/findByAccount",
          this.$qs.stringify({ account: account })
          // { headers: authHeader(), }
        )
        .then((res) => {
          let stu = res.data;
          let studentForm = this.stuForm;
          this.stuId = stu.id;
          this.stuPwd = stu.password;
          studentForm.account = stu.account;
          studentForm.stuName = stu.stuName;
          studentForm.password = "$2a$10$";
          studentForm.sex = stu.sex == null ? "" : stu.sex == 1 ? "男" : "女";
          if (stu.clazz != null) {
            studentForm.clazz.clazzId = stu.clazz.clazzId;
            studentForm.clazz.major = stu.clazz.major;
            studentForm.clazz.clazzName = stu.clazz.clazzName;
            this.majorclazzName = [stu.clazz.major, stu.clazz.clazzId];
          }
        });
    },
    save() {
      this.$refs.stuForm.validate((valid) => {
        if (valid) {
          UserService.addStu(this.stuForm)
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
                this.$message.error("学号重复，请重新填写");
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
      //   if (this.stuForm.stuName == "") {
      //     this.$message.warning("请填写姓名");
      //     return;
      //   }
      //   if (this.stuForm.password == "") {
      //     this.$message.warning("请填写密码");
      //     return;
      //   }
      //   if (this.stuForm.password == "$2a$10$") {
      //     this.stuForm.password = this.stuPwd;
      //   }
      //   let gender = this.stuForm.sex;
      //   this.stuForm.sex = gender == "" ? null : gender;
      //   this.$axios
      //     .post(
      //       "/auto/student/modify",
      //       {
      //         id: this.stuId,
      //         account: this.stuForm.account,
      //         stuName: this.stuForm.stuName,
      //         password: this.stuForm.password,
      //         sex: this.stuForm.sex,
      //         clazz: this.stuForm.clazz,
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
              "/auto/student/del",
              this.$qs.stringify(
                {
                  studentId: params,
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
              this.$message.error("删除失败，请检查待删除学生的相关内容。");
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