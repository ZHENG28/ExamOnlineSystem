<template>
  <el-row>
    <el-col :span="4">
      <h2 class="route-name">{{ routeName }}</h2>
    </el-col>
    <el-col :span="20">
      <el-dropdown>
        <el-avatar :style="{ backgroundColor: color }">{{
          userName.substr(0, 1).toUpperCase()
        }}</el-avatar>
        <el-icon style="margin-left: 15px; float: right; margin-top: 15px">
          <arrow-down />
        </el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="dialogFormVisible = true">
              <el-icon><setting /></el-icon>
              设置
            </el-dropdown-item>
            <el-dropdown-item @click="logOut()">
              <el-icon><right /></el-icon>
              登出
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
    <el-dialog title="修改个人信息" v-model="dialogFormVisible" width="600px">
      <el-form
        :model="form"
        :rules="formRules"
        ref="form"
        label-width="200px"
        label-position="right"
      >
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" readonly="readonly"></el-input>
        </el-form-item>
        <el-form-item label="名字" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="form.password"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateUser()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </el-row>
</template>
<script>
import authHeader from "@/services/auth-header";
import User from "@/services/user";
export default {
  data() {
    return {
      dialogFormVisible: false,
      oldPassword: "",
      userName: "",
      form: new User("", "", "", "", ""),
      formRules: {
        username: [{ required: true, message: "请填写名字", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
      },
      color: "",
    };
  },
  computed: {
    routeName() {
      const { name } = this.$route;
      return name;
    },
  },
  created() {
    if (!this.$storage.getStorageSync("isLogin")) {
      this.$router.replace("/");
    } else {
      this.color = this.getColor();
      this.loadInfo(this.$storage.getStorageSync("user").id);
    }
  },
  methods: {
    getColor() {
      let result = "#";
      let arr = [
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "a",
        "b",
        "c",
        "d",
        "e",
        "f",
      ];
      for (let i = 0; i < 6; i++) {
        let num = parseInt(Math.random() * 16);
        result += arr[num];
      }
      return result;
    },

    loadInfo(id) {
      this.$axios
        .get("/user/findInfoById", {
          headers: authHeader(),
          params: {
            userId: id,
          },
        })
        .then((response) => {
          this.form = response.data;
          this.oldPassword = response.data.password;
          this.userName = response.data.username;
        });
    },
    updateUser() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          let data = {
            userId: this.form.userId,
            username: this.form.username,
          };
          if (this.form.password != this.oldPassword) {
            data.password = this.form.password;
          }
          this.$axios
            .post("/user/save", this.$qs.stringify(data), {
              headers: authHeader(),
            })
            .then(
              (response) => {
                this.dialogFormVisible = false;
                if (response.data) {
                  this.$message.success("修改成功");
                } else {
                  this.$message.error("修改失败");
                }
                this.loadInfo(this.form.userId);
              },
              (error) => {
                this.$message.info("数据出错");
                console.log(error);
              }
            );
        }
      });
    },

    logOut() {
      this.$storage.removeStorageSync("user");
      this.$storage.setStorageSync("isLogin", false);
      this.$router.replace("/");
    },
  },
};
</script>
<style scoped>
.route-name {
  text-align: center;
  margin-top: 20px;
}
.el-input {
  width: 250px;
}
.el-dropdown {
  margin-top: 15px;
  margin-right: 15px;
  float: right;
}
</style>
