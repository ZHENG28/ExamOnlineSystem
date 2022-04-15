<template>
  <el-row>
    <el-col :span="4">
      <h2 class="route-name">{{ routeName }}</h2>
    </el-col>
    <el-col :span="20">
      <el-dropdown>
        <el-avatar :style="{ backgroundColor: color }">{{
          form.username.substr(0, 1).toUpperCase()
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
        :rules="FormRules"
        ref="form"
        label-width="200px"
        :label-position="right"
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
export default {
  data() {
    return {
      dialogFormVisible: false,
      pswd: "",
      form: {
        account: "",
        username: "",
        password: "",
      },
      FormRules: {
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
    let state = this.$store.state.initialState;
    if (!state.status.isLogin) {
      this.$router.replace("/");
    } else {
      this.color = this.getColor();
      this.findInfoById(state.user.id);
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

    findInfoById(id) {
      this.$axios
        .get("/user/findInfoById", {
          headers: authHeader(),
          params: {
            userId: id,
          },
        })
        .then((response) => {
          // this.pswd = response.data.password;
          this.form = response.data;
          // switch (role) {
          //   case 3:
          //     this.form.password = "$2a$10$";
          //     break;
          //   case 2:
          //     this.form.username = res.data.tchName;
          //     this.form.password = "$2a$10$";
          //     break;
          //   case 1:
          //     this.form.username = res.data.stuName;
          //     this.form.password = "$2a$10$";
          //     break;
          // }
        });
    },
    updateUser() {
      //   if (this.form.username == "") {
      //     this.$message.warning("请填写姓名");
      //     return;
      //   }
      //   if (this.form.password == "") {
      //     this.$message.warning("请填写密码");
      //     return;
      //   }
      //   if (this.form.password == "$2a$10$") {
      //     this.form.password = this.pswd;
      //   }
      //   this.$axios
      //     .post(
      //       "/sys/updateUser",
      //       {
      //         role: this.form.role,
      //         account: this.form.account,
      //         username: this.form.username,
      //         password: this.form.password,
      //       },
      //       { headers: authHeader() }
      //     )
      //     .then(
      //       (res) => {
      //         if (res.data.message == "OK") {
      //           this.$message.success("修改成功");
      //         } else {
      //           this.$message.error("修改失败");
      //         }
      //         this.dialogFormVisible = false;
      //       },
      //       (error) => {
      //         console.log("error:" + error.message);
      //         this.$message.info("数据出错");
      //       }
      //     )
      //     .catch(function (error) {
      //       this.$message.info("数据出错");
      //       console.log(error);
      //     });
    },

    logOut() {
      localStorage.removeItem("user");
      localStorage.setItem("isLogin", false);
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
