<template>
  <div>
    <img
      src="@/assets/login/admin_login_bg.png"
      alt=""
      style="width: 100%; position: fixed; z-index: -1"
    />
    <div
      style="
        width: 50%;
        text-align: center;
        padding: 0 2rem;
        box-sizing: border-box;
      "
    >
      <img
        src="@/assets/login/user_login_icon.png"
        alt=""
        style="width: 120px; margin-top: 15%"
      />
      <h2
        style="
          letter-spacing: 10px;
          font-size: 2.9rem;
          text-transform: uppercase;
          margin: 15px 0;
          color: #999;
        "
      >
        登录系统
      </h2>
      <el-form :model="user" ref="user" label-position="right">
        <el-form-item prop="account">
          <div class="input-group">
            <div class="icon">
              <i class="fa fa-user"></i>
            </div>
            <div>
              <input type="text" class="input" v-model="user.account" />
            </div>
          </div>
        </el-form-item>
        <el-form-item prop="password">
          <div class="input-group">
            <div class="icon">
              <i class="fa fa-lock"></i>
            </div>
            <div>
              <input class="input" type="password" v-model="user.password" />
            </div>
          </div>
        </el-form-item>
      </el-form>
      <button @click="login()">登录</button>
    </div>
  </div>
</template>
<script>
import User from "@/services/user";
export default {
  data() {
    return {
      user: new User("", "", "", "", ""),
    };
  },
  created() {
    if (this.$storage.getStorageSync("user")) {
      this.$router.push("/home");
    }
  },
  methods: {
    isEmptyFields() {
      if (this.user.account == "") {
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
            roleId: "1",
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
            console.log("response: " + response);
            this.$message.error("存在错误，请检查！");
          });
      }
    },
  },
};
</script>
<style scoped src="@/assets/login/admin_login_style.css"></style>
<style scoped>
@import url("https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css");
</style>