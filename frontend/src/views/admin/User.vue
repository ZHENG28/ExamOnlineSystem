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
            findAllMajorAndClazz();
          "
          >新增</el-button
        >
        <el-button type="danger" @click="del(this.multiSelection)"
          >删除</el-button
        >
      </div>
      <el-dialog
        :title="(isAdd ? '新增' : '修改') + '用户信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="user"
          :rules="formRules"
          ref="user"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="角色">
            <!-- eslint-disable-next-line -->
            <template #default="scope">
              <div v-if="!isAdd">
                <el-tag :type="user.roleName == '学生' ? 'success' : 'danger'">
                  {{ user.roleName }}
                </el-tag>
              </div>
              <el-select
                filterable
                placeholder="请选择用户角色"
                @change="valueToRoleId"
                v-model="user.roleId"
                v-if="isAdd"
              >
                <el-option
                  v-for="roleData in roleListData"
                  :key="roleData.roleId"
                  :label="roleData.description"
                  :value="roleData.roleId"
                >
                </el-option>
              </el-select>
            </template>
          </el-form-item>
          <el-form-item
            label="账号"
            prop="account"
            :rules="{
              required: isAdd,
              message: '请填写账号',
              trigger: 'blur',
            }"
          >
            <el-input v-model="user.account" :readonly="!isAdd"></el-input>
          </el-form-item>
          <el-form-item label="名字" prop="username">
            <el-input v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="user.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="专业班级">
            <!-- eslint-disable-next-line -->
            <template #default="scope">
              <span
                :style="{
                  color: 'red',
                  display: user.roleName == '' ? '' : 'none',
                }"
                >请先选择用户角色</span
              >
              <el-cascader
                @change="valueToCascade"
                v-model="majorclazzName"
                placeholder="请选择专业班级"
                :options="majorclazzArr"
                filterable1
                :show-all-levels="false"
                :props="{ expandTrigger: 'hover' }"
                v-if="
                  user.roleName != '' &&
                  user.roleName != '1' &&
                  user.roleName != '管理员'
                "
              ></el-cascader>
            </template>
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
        prop="roleName"
        label="角色"
        :filters="roleFilterData"
        :filter-method="roleFilter"
      >
        <template #default="scope">
          <el-tag :type="scope.row.roleName == '学生' ? 'success' : 'danger'">{{
            scope.row.roleName
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
              findAllMajorAndClazz();
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
export default {
  data() {
    return {
      isAdd: true,
      dialogFormVisible: false,
      userId: "",
      clazzId: "",
      user: new User("", "", "", ""),
      formRules: {
        username: [{ required: true, message: "请填写名字", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
      },

      majorclazzArr: [],
      majorclazzName: [],
      roleListData: [],
      roleFilterData: [],

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
      this.findDistinctRole();
    },
    clearFormFields() {
      this.user = new User("", "", "", "");
      this.$nextTick(() => {
        this.$refs.user.clearValidate();
      });
      this.majorclazzName = [];
      this.userId = "";
      this.clazzId = "";
    },

    findAll() {
      this.$axios
        .post(
          "/user/findAll",
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
      return row.roleName === value;
    },

    findDistinctRole() {
      this.$axios
        .get("/role/getDistinctRole", { headers: authHeader() })
        .then((response) => {
          this.roleListData = response.data;
          response.data.forEach((item) => {
            this.roleFilterData.push({
              text: item.description,
              value: item.description,
            });
          });
        });
    },
    valueToRoleId(val) {
      this.user.roleName = val;
      this.findAllMajorAndClazz();
    },
    findAllMajorAndClazz() {
      this.$axios
        .get("/clazz/findAllMajorAndClazz", { headers: authHeader() })
        .then((response) => {
          this.majorclazzArr = response.data;
        });
    },
    valueToCascade(row) {
      this.clazzId = row[1];
    },

    // 新增&编辑
    loadInfo(account) {
      this.$axios
        .post("/user/findByAccount", this.$qs.stringify({ account: account }), {
          headers: authHeader(),
        })
        .then((response) => {
          this.user = response.data;
          this.majorclazzName = [response.data.major, response.data.clazzId];
          this.userId = response.data.userId;
          this.clazzId = response.data.clazzId;
        });
    },
    save() {
      this.$refs.user.validate((valid) => {
        if (valid) {
          this.$axios
            .post(
              "/user/save",
              this.$qs.stringify({
                userId: this.userId,
                account: this.user.account,
                password: this.user.password,
                username: this.user.username,
                roleId: this.user.roleId,
                clazzId: this.clazzId,
              }),
              { headers: authHeader() }
            )
            .then(
              (response) => {
                this.dialogFormVisible = false;
                if (response.data) {
                  this.$message.success(
                    (this.isAdd ? "添加" : "修改") + "成功"
                  );
                  this.loadData();
                } else {
                  this.$message.error((this.isAdd ? "添加" : "修改") + "失败");
                }
              },
              (error) => {
                this.$message.error("账号已存在，请重新填写");
                console.log(error);
              }
            );
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
              params.push(item.account);
            });
            this.$axios
              .post(
                "/user/del",
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
      } else {
        this.$message.info("请选择要删除的信息");
      }
    },
  },
};
</script>
<style scoped>
.el-input {
  width: 250px;
}
</style>