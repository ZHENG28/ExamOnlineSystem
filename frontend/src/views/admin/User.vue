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
                <el-tag
                  :type="user.description == '学生' ? 'success' : 'danger'"
                >
                  {{ user.description }}
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
                  v-for="roleData in roleList"
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
                  display: user.roleId == '' ? '' : 'none',
                }"
                >请先选择用户角色</span
              >
              <el-select
                filterable
                placeholder="请选择专业"
                @change="valueToMajorId"
                v-model="majorId"
                v-if="user.roleId == '2'"
              >
                <el-option
                  v-for="major in majorList"
                  :key="major.majorId"
                  :label="major.majorName"
                  :value="major.majorId"
                >
                </el-option>
              </el-select>
              <el-cascader
                @change="valueToCascade"
                v-model="majorclazzName"
                placeholder="请选择专业班级"
                :options="majorClazzList"
                filterable1
                :show-all-levels="false"
                :props="{ expandTrigger: 'hover' }"
                v-if="user.roleId == '3'"
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
        prop="description"
        label="角色"
        :filters="roleFilterData"
        :filter-method="roleFilter"
      >
        <template #default="scope">
          <el-tag
            :type="
              scope.row.description == '学生'
                ? 'success'
                : scope.row.description == '教师'
                ? 'warning'
                : 'danger'
            "
            >{{ scope.row.description }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="account" label="账号"> </el-table-column>
      <el-table-column prop="username" label="名字"> </el-table-column>
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
              loadMajorClazzCascader();
              loadInfo(scope.row.userId);
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
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";
import User from "@/services/user.js";
export default {
  data() {
    return {
      isAdd: true,
      dialogFormVisible: false,
      user: new User("", "", "", "", ""),
      oldPassword: "",
      majorId: "",
      majorList: [],
      clazzId: "",
      majorClazzList: [],
      majorclazzName: [],
      formRules: {
        username: [{ required: true, message: "请填写名字", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
      },

      multiSelection: [],
      roleList: [],
      roleFilterData: [],
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
      this.loadRoleList();
    },
    clearFormFields() {
      this.user = new User("", "", "", "", "");
      this.$nextTick(() => {
        this.$refs.user.clearValidate();
      });
      this.majorclazzName = [];
      this.majorId = "";
      this.clazzId = "";
      this.oldPassword = "";
    },

    findAll() {
      this.$axios
        .get("/user/findAll", {
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

    loadRoleList() {
      this.$axios
        .get("/role/findAll", { headers: { Authorization: userToken() } })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.roleList = res;
            this.roleFilterData = [];
            res.forEach((item) => {
              this.roleFilterData.push({
                text: item.description,
                value: item.roleId,
              });
            });
          }
        });
    },
    roleFilter(value, row) {
      return row.roleId === value;
    },
    valueToRoleId(val) {
      this.user.roleId = val;
      switch (val) {
        case 2:
          this.loadMajorList();
          break;
        case 3:
          this.loadMajorClazzCascader();
          break;
      }
    },

    loadMajorList() {
      this.$axios
        .get("/major/findAll", { headers: { Authorization: userToken() } })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.majorList = res;
          }
        });
    },
    valueToMajorId(val) {
      this.majorId = val;
    },
    loadMajorClazzCascader() {
      this.$axios
        .get("/clazz/loadMajorClazzCascader", {
          headers: { Authorization: userToken() },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.majorClazzList = res;
          }
        });
    },
    valueToCascade(row) {
      this.clazzId = row[1];
    },

    // 新增&编辑
    loadInfo(id) {
      this.$axios
        .get("/user/findInfoById", {
          headers: { Authorization: userToken() },
          params: { userId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.user = res;
            this.majorclazzName = [res.majorId, res.clazzId];
            this.clazzId = res.clazzId;
            this.majorId = res.majorId;
            this.oldPassword = res.password;
            switch (res.roleId) {
              case 2:
                this.loadMajorList();
                break;
              case 3:
                this.loadMajorClazzCascader();
                break;
            }
          }
        });
    },
    save() {
      this.$refs.user.validate((valid) => {
        if (valid) {
          let data = {
            userId: this.user.userId,
            account: this.user.account,
            username: this.user.username,
            roleId: this.user.roleId,
            status: this.isAdd ? "添加" : "修改",
          };
          if (this.user.password != this.oldPassword) {
            data["password"] = this.user.password;
          }
          switch (this.user.roleId) {
            case 2:
              data["majorId"] = this.majorId;
              break;
            case 3:
              data["clazzId"] = this.clazzId;
              break;
          }
          this.$axios
            .post("/user/save", this.$qs.stringify(data), {
              headers: { Authorization: userToken() },
            })
            .then((response) => {
              this.dialogFormVisible = false;
              this.$message({
                type: response.data.success ? "success" : "error",
                message: response.data.message,
              });
              this.loadData();
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
              params.push(item.userId);
            });
            this.$axios
              .post(
                "/user/delete",
                this.$qs.stringify({ userId: params }, { indices: false }),
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
  },
};
</script>
<style scoped>
.el-input {
  width: 250px;
}
</style>