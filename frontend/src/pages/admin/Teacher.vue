<template>
  <div class="row">
    <div class="col-12">
      <div>
        <h3 style="display: inline">教师信息</h3>
        <div style="float: right">
          <el-button
            @click="
              clearFormFields();
              dialogFormVisible = true;
            "
            >添加</el-button
          >
          <el-dialog title="添加教师信息" :visible.sync="dialogFormVisible">
            <el-form :model="tchForm" :rules="FormRules" ref="tchForm">
              <el-form-item
                label="工号"
                :label-width="formLabelWidth"
                prop="account"
              >
                <el-input
                  v-model="tchForm.account"
                  style="width: 250px"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="姓名"
                :label-width="formLabelWidth"
                prop="tchName"
              >
                <el-input
                  v-model="tchForm.tchName"
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
                  v-model="tchForm.password"
                  style="width: 250px"
                  readonly="readonly"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="性别"
                :label-width="formLabelWidth"
                prop="sex"
              >
                <el-select v-model="tchForm.sex" placeholder="请选择性别">
                  <el-option label="男" value="1"></el-option>
                  <el-option label="女" value="0"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                label="专业"
                :label-width="formLabelWidth"
                prop="major"
              >
                <el-select
                  v-model="tchForm.major"
                  filterable
                  placeholder="请选择专业"
                >
                  <el-option
                    v-for="major in majorArr"
                    :key="major.value"
                    :label="major.text"
                    :value="major.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                label="电话"
                :label-width="formLabelWidth"
                prop="tel"
              >
                <el-input v-model="tchForm.tel" style="width: 250px"></el-input>
              </el-form-item>
              <el-form-item
                label="邮箱"
                :label-width="formLabelWidth"
                prop="email"
              >
                <el-input
                  v-model="tchForm.email"
                  style="width: 250px"
                ></el-input>
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
              data.tchName.toLowerCase().includes(search.toLowerCase()) ||
              data.account.toLowerCase().includes(search.toLowerCase())
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
        <el-table-column prop="account" label="工号"> </el-table-column>
        <el-table-column prop="tchName" label="姓名"> </el-table-column>
        <el-table-column
          prop="major"
          label="专业"
          :filters="majorArr"
          :filter-method="filterMajor"
          filter-placement="bottom-end"
        >
        </el-table-column>
        <el-table-column prop="tel" label="电话"> </el-table-column>
        <el-table-column label="搜索">
          <!-- eslint-disable-next-line -->
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入教师工号或姓名进行搜索"
            />
          </template>
          <template slot-scope="scope">
            <el-button
              @click="
                loadModify(scope.row);
                modifyFormVisible = true;
              "
              >编辑</el-button
            >
            <el-dialog title="修改教师信息" :visible.sync="modifyFormVisible">
              <el-form :model="tchForm" :rules="FormRules">
                <el-form-item
                  label="工号"
                  :label-width="formLabelWidth"
                  prop="account"
                >
                  <el-input
                    v-model="tchForm.account"
                    style="width: 250px"
                    readonly="readonly"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="姓名"
                  :label-width="formLabelWidth"
                  prop="tchName"
                >
                  <el-input
                    v-model="tchForm.tchName"
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
                    v-model="tchForm.password"
                    style="width: 250px"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="性别"
                  :label-width="formLabelWidth"
                  prop="sex"
                >
                  <el-select v-model="tchForm.sex" placeholder="请选择性别">
                    <el-option label="男" value="1"></el-option>
                    <el-option label="女" value="0"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  label="专业"
                  :label-width="formLabelWidth"
                  prop="major"
                >
                  <el-select
                    v-model="tchForm.major"
                    filterable
                    placeholder="请选择专业"
                  >
                    <el-option
                      v-for="major in majorArr"
                      :key="major.value"
                      :label="major.text"
                      :value="major.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  label="电话"
                  :label-width="formLabelWidth"
                  prop="tel"
                >
                  <el-input
                    v-model="tchForm.tel"
                    style="width: 250px"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="邮箱"
                  :label-width="formLabelWidth"
                  prop="email"
                >
                  <el-input
                    v-model="tchForm.email"
                    style="width: 250px"
                  ></el-input>
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
import Teacher from "@/models/teacher";
import UserService from "@/services/user.service";
export default {
  data() {
    return {
      tableData: [],
      majorArr: [],
      search: "",
      multipleSelection: [],
      multipleSelectionFlag: true,
      dialogTableVisible: false,
      modifyFormVisible: false,
      dialogFormVisible: false,
      tchId: "",
      tchPwd: "",
      tchForm: new Teacher("", "", "123", "", "", "", ""),
      formLabelWidth: "200px",
      FormRules: {
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
      return value == row.major;
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
      this.tchForm = new Teacher("", "", "123", "", "", "", "");
    },

    loadData() {
      this.findAll();
      this.$ajax
        .post("/tch/findAllMajor", {}, { headers: authHeader() })
        .then((response) => {
          this.majorArr = response.data;
        });
    },
    findAll() {
      this.$ajax
        .post(
          "/tch/findAll",
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

    add() {
      this.$refs["tchForm"].validate((valid) => {
        if (!valid) {
          console.log("error submit!!");
          return;
        } else {
          UserService.addTch(this.tchForm)
            .then(
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
                this.$message.error("工号重复，请重新填写");
              }
            )
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
              this.clearFormFields();
            });
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
              "/tch/del",
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
              "/tch/del",
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
          "/tch/findByAccount",
          this.$qs.stringify({ account: row.account }),
          {
            headers: authHeader(),
          }
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
    modify() {
      if (this.tchForm.tchName == "") {
        this.$message.warning("请填写姓名");
        return;
      }
      if (this.tchForm.password == "") {
        this.$message.warning("请填写密码");
        return;
      }
      if (this.tchForm.password == "$2a$10$") {
        this.tchForm.password = this.tchPwd;
      }
      let gender = this.tchForm.sex;
      this.tchForm.sex = gender == "" ? null : gender == "男" ? 1 : 0;
      this.$ajax
        .post(
          "/tch/modify",
          {
            id: this.tchId,
            account: this.tchForm.account,
            tchName: this.tchForm.tchName,
            password: this.tchForm.password,
            sex: this.tchForm.sex,
            major: this.tchForm.major,
            tel: this.tchForm.tel,
            email: this.tchForm.email,
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