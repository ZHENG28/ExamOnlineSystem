<template>
  <div class="row">
    <div class="col-12">
      <div>
        <h3 style="display: inline">学生信息</h3>
        <div style="float: right">
          <el-button
            @click="
              clearFormFields();
              findAllMajorAndClazz();
              dialogFormVisible = true;
            "
            >添加</el-button
          >
          <el-dialog title="添加学生信息" :visible.sync="dialogFormVisible">
            <el-form :model="stuForm" :rules="FormRules" ref="stuForm">
              <el-form-item
                label="学号"
                :label-width="formLabelWidth"
                prop="account"
              >
                <el-input
                  v-model="stuForm.account"
                  style="width: 250px"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="姓名"
                :label-width="formLabelWidth"
                prop="stuName"
              >
                <el-input
                  v-model="stuForm.stuName"
                  style="width: 250px"
                  autofocus="autofocus"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="密码"
                :label-width="formLabelWidth"
                prop="password"
              >
                <el-input
                  v-model="stuForm.password"
                  style="width: 250px"
                  readonly="readonly"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="性别"
                :label-width="formLabelWidth"
                prop="sex"
              >
                <el-select v-model="stuForm.sex" placeholder="请选择性别">
                  <el-option label="男" value="1"></el-option>
                  <el-option label="女" value="0"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                label="专业班级"
                :label-width="formLabelWidth"
                prop="clazz.clazzId"
              >
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
            <div slot="footer" class="dialog-footer">
              <el-button
                @click="
                  clearFormFields();
                  dialogFormVisible = false;
                "
                >取 消</el-button
              >
              <el-button type="primary" @click="add()">确 定</el-button>
            </div>
          </el-dialog>
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
              data.stuName.toLowerCase().includes(search.toLowerCase()) ||
              data.account.toLowerCase().includes(search.toLowerCase())
          )
        "
        height="480px"
        border
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="40"> </el-table-column>
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="account" label="学号"> </el-table-column>
        <el-table-column prop="stuName" label="姓名"> </el-table-column>
        <el-table-column
          prop="clazz.major"
          label="专业"
          :filters="majorArr"
          :filter-method="filterMajor"
          filter-placement="bottom-end"
        >
        </el-table-column>
        <el-table-column
          prop="clazz.clazzName"
          label="班级"
          :filters="clazzArr"
          :filter-method="filterClazz"
          filter-placement="bottom-end"
        >
        </el-table-column>
        <el-table-column label="搜索">
          <!-- eslint-disable-next-line -->
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入学号或姓名进行搜索"
            />
          </template>
          <template slot-scope="scope">
            <el-button
              @click="
                loadModify(scope.row);
                findAllMajorAndClazz();
                modifyFormVisible = true;
              "
              >编辑</el-button
            >
            <el-dialog title="修改学生信息" :visible.sync="modifyFormVisible">
              <el-form :model="stuForm" :rules="FormRules">
                <el-form-item
                  label="学号"
                  :label-width="formLabelWidth"
                  prop="account"
                >
                  <el-input
                    v-model="stuForm.account"
                    style="width: 250px"
                    readonly="readonly"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="姓名"
                  :label-width="formLabelWidth"
                  prop="stuName"
                >
                  <el-input
                    v-model="stuForm.stuName"
                    style="width: 250px"
                    autofocus="autofocus"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="密码"
                  :label-width="formLabelWidth"
                  prop="password"
                >
                  <el-input
                    type="password"
                    v-model="stuForm.password"
                    style="width: 250px"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="性别"
                  :label-width="formLabelWidth"
                  prop="sex"
                >
                  <el-select v-model="stuForm.sex" placeholder="请选择性别">
                    <el-option label="男" value="1"></el-option>
                    <el-option label="女" value="0"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  label="专业班级"
                  :label-width="formLabelWidth"
                  prop="clazz.clazzId"
                >
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
              <div slot="footer" class="dialog-footer">
                <el-button
                  @click="
                    clearFormFields();
                    modifyFormVisible = false;
                  "
                  >取 消</el-button
                >
                <el-button type="primary" @click="modify()">确 定</el-button>
              </div>
            </el-dialog>
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
import Student from "@/models/student";
import UserService from "@/services/user.service";
export default {
  data() {
    return {
      tableData: [],
      majorArr: [],
      clazzArr: [],
      majorclazzArr: [],
      search: "",
      multipleSelection: [],
      multipleSelectionFlag: true,
      dialogTableVisible: false,
      modifyFormVisible: false,
      dialogFormVisible: false,
      stuId: "",
      stuPwd: "",
      stuForm: new Student("", "", "123", "", "", "", ""),
      majorclazzName: [],
      formLabelWidth: "200px",
      FormRules: {
        account: [{ required: true, message: "请填写学号", trigger: "blur" }],
        stuName: [{ required: true, message: "请填写姓名", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
        clazz: [
          { required: false, message: "请选择专业班级", trigger: "blur" },
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
    filterMajor(value, row) {
      if (row.clazz != null) {
        return value == row.clazz.major;
      } else {
        return false;
      }
    },
    filterClazz(value, row) {
      if (row.clazz != null) {
        return value == row.clazz.clazzName;
      } else {
        return false;
      }
    },
    valueToCascade(row) {
      this.stuForm.clazz.clazzId = row[1];
    },
    handleSizeChange(size) {
      this.size = size;
      this.findAll();
    },
    handleCurrentChange(pageno) {
      this.pageno = pageno - 1;
      this.findAll();
    },
    clearFormFields() {
      this.stuForm = new Student("", "", "123", "", "", "", "");
      this.majorclazzName = [];
    },

    loadData() {
      this.findAll();
      this.$ajax
        .post(
          "/stu/findAllMajor",
          {},
          {
            headers: authHeader(),
          }
        )
        .then((response) => {
          this.majorArr = response.data;
        });
      this.$ajax
        .post(
          "/stu/findAllClazz",
          {},
          {
            headers: authHeader(),
          }
        )
        .then((response) => {
          this.clazzArr = response.data;
        });
    },
    findAll() {
      this.$ajax
        .post(
          "/stu/findAll",
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

    findAllMajorAndClazz() {
      this.$ajax
        .post(
          "/stu/findAllMajorAndClazz",
          {},
          {
            headers: authHeader(),
          }
        )
        .then((response) => {
          this.majorclazzArr = response.data;
        });
    },
    add() {
      this.$refs["stuForm"].validate((valid) => {
        if (!valid) {
          console.log("error submit!!");
          return;
        } else {
          UserService.addStu(this.stuForm).then(
            (res) => {
              if (res.data.message == "OK") {
                this.$message.success("添加成功");
                this.loadData();
              } else {
                this.$message.error("添加失败");
              }
              this.clearFormFields();
              this.dialogFormVisible = false;
            },
            (error) => {
              console.log("error:" + error.message);
              this.$message.error("数据出错: " + error.message);
            }
          );
        }
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
              "/stu/del",
              this.$qs.stringify({
                account: row.account,
                pageno: this.pageno,
                size: this.size,
              }),
              { headers: authHeader() }
            )
            .then(
              (response) => {
                this.tableData = response.data.content;
                this.totalItems = response.data.totalElements;
                this.$message.success("删除成功!");
              },
              (error) => {
                console.log("error:" + error);
                this.$message.error("删除失败");
              }
            );
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
            params.push(item.account);
          });
          this.$ajax
            .post(
              "/stu/del",
              this.$qs.stringify(
                {
                  account: params,
                  pageno: this.pageno,
                  size: this.size,
                },
                { indices: false }
              ),
              { headers: authHeader() }
            )
            .then(
              (response) => {
                this.tableData = response.data.content;
                this.totalItems = response.data.totalElements;
                this.$message.success("删除成功!");
              },
              (error) => {
                console.log("error:" + error);
                this.$message.error("删除失败");
              }
            );
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },

    loadModify(row) {
      this.$ajax
        .post(
          "/stu/findByAccount",
          this.$qs.stringify({ account: row.account }),
          {
            headers: authHeader(),
          }
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
    modify() {
      if (this.stuForm.stuName == "") {
        this.$message.warning("请填写姓名");
        return;
      }
      if (this.stuForm.password == "") {
        this.$message.warning("请填写密码");
        return;
      }
      if (this.stuForm.password == "$2a$10$") {
        this.stuForm.password = this.stuPwd;
      }
      let gender = this.stuForm.sex;
      this.stuForm.sex = gender == "" ? null : gender;
      this.$ajax
        .post(
          "/stu/modify",
          {
            id: this.stuId,
            account: this.stuForm.account,
            stuName: this.stuForm.stuName,
            password: this.stuForm.password,
            sex: this.stuForm.sex,
            clazz: this.stuForm.clazz,
          },
          { headers: authHeader() }
        )
        .then(
          (res) => {
            if (res.data.message == "OK") {
              this.$message.success("修改成功");
              this.loadData();
            } else {
              this.$message.error("修改失败");
            }
            this.clearFormFields();
            this.modifyFormVisible = false;
          },
          (error) => {
            console.log("error:" + error.message);
            this.$message.info("数据出错");
          }
        )
        .catch(function (error) {
          this.$message.info("数据出错");
          console.log(error);
          this.clearFormFields();
        });
    },
  },
};
</script>
<style>
</style>