<template>
  <div>
    <img src="../assets/login/user_login_bg.png" alt="" class="wave" />
    <div class="container">
      <div class="img"></div>
      <div class="login-box">
        <form action="#" style="width: 360px">
          <img
            src="../assets/login/user_login_icon.png"
            alt=""
            class="avatar"
          />
          <h2 style="letter-spacing: 10px">登录系统</h2>
          <div class="radio-group">
            <div>
              <el-button
                icon="checked"
                round
                size="large"
                :type="isTch"
                @click="changeIdentity(2)"
                >教师</el-button
              >
            </div>
            <div>
              <el-button
                icon="briefcase"
                round
                size="large"
                :type="isStu"
                @click="changeIdentity(3)"
                >学生</el-button
              >
            </div>
          </div>
          <div class="input-group">
            <div class="icon">
              <i class="fa fa-user"></i>
            </div>
            <div>
              <input type="text" class="input" v-model="user.account" />
            </div>
          </div>
          <div class="input-group">
            <div class="icon">
              <i class="fa fa-lock"></i>
            </div>
            <div>
              <input class="input" type="password" v-model="user.password" />
            </div>
          </div>
          <input type="button" class="btn" value="登录" @click="login()" />
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import User from "@/services/user";
export default {
  data() {
    return {
      user: new User("", "", "", "", ""),
      isTch: "",
      isStu: "",
    };
  },
  created() {
    if (this.$storage.getStorageSync("user")) {
      this.$router.push("/home");
    }
  },
  methods: {
    changeIdentity(id) {
      this.user.roleId = id;
      this.isTch = id == 2 ? "primary" : "";
      this.isStu = id == 3 ? "primary" : "";
    },

    isEmptyFields() {
      if (this.user.roleId == "") {
        this.$message.warning("请选择身份");
      } else if (this.user.account == "") {
        this.$message.warning("用户名不能为空");
      } else if (this.user.password == "") {
        this.$message.warning("密码不能为空");
      } else {
        return true;
      }
      return false;
    },
    login() {
      if (this.isEmptyFields()) {
        this.$axios
          .post("/user/login", {
            roleId: this.user.roleId,
            account: this.user.account,
            password: this.user.password,
          })
          .then((response) => {
            if (response.data.token) {
              // 将该登录用户的令牌移入store
              this.$storage.setStorageSync("user", response.data, 10800000);
              this.$router.push("/home");
            }
          })
          .catch((response) => {
            console.log(response);
            this.$message.error("存在错误，请检查！");
          });
      }
    },
  },
};
</script>

<style scoped>
@import url("../assets/login/user_login_style.css");
@import url("https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css");
</style>