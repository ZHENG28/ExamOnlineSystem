<template>
  <el-row>
    <el-col :span="4">
      <h2 class="route-name">{{ routeName }}</h2>
    </el-col>
    <el-col :span="20">
      <el-dropdown>
        <el-avatar size="default" :src="avatarImg" />
        <el-icon style="margin-left: 15px; float: right; margin-top: 15px">
          <arrow-down />
        </el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="dialogFormVisible = true">
              <el-icon><setting /></el-icon>
              设置
            </el-dropdown-item>
            <el-dropdown-item @click="logOut">
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
        <el-form-item label="姓名" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="form.password"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateInfo()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </el-row>
</template>
<script>
// import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      avatarImg: "/src/assets/login/background.jpg",
      dialogFormVisible: false,
      formLabelWidth: "200px",
      pswd: "",
      form: {
        role: "",
        account: "",
        username: "",
        password: "",
      },
      FormRules: {
        account: [{ required: true, message: "请填写学号", trigger: "blur" }],
        username: [{ required: true, message: "请填写姓名", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
      },
    };
  },
  computed: {
    routeName() {
      const { name } = this.$route;
      return name;
    },
  },
  created() {
    // let user = this.$store.state.auth.user;
    // this.form.account = user.account;
    // this.form.role = user.roles[0];
    // this.findInfoByAccount();
  },
  methods: {
    findInfoByAccount() {
      //   switch (this.form.role) {
      //     case "ROLE_MANAGER":
      //       this.form.username = this.form.account;
      //       this.getInfo(3);
      //       break;
      //     case "ROLE_TEACHER":
      //       this.getInfo(2);
      //       break;
      //     case "ROLE_STUDENT":
      //       this.getInfo(1);
      //       break;
      //   }
    },
    getInfo(role) {
      //   this.$axios
      //     .get("/sys/findInfo", {
      //       headers: authHeader(),
      //       params: {
      //         role: role,
      //         account: this.form.account,
      //       },
      //     })
      //     .then((res) => {
      //       this.pswd = res.data.password;
      //       switch (role) {
      //         case 3:
      //           this.form.password = "$2a$10$";
      //           break;
      //         case 2:
      //           this.form.username = res.data.tchName;
      //           this.form.password = "$2a$10$";
      //           break;
      //         case 1:
      //           this.form.username = res.data.stuName;
      //           this.form.password = "$2a$10$";
      //           break;
      //       }
      //     });
    },
    updateInfo() {
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
      //       "/sys/updateInfo",
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
      // this.$store.dispatch("auth/logout");
      // this.$router.push("/");
      console.log("logout");
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
