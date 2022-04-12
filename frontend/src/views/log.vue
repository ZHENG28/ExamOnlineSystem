<template>
  <div>
    <img src="../assets/login/bg.png" alt="" class="wave" />
    <div class="container">
      <div class="img">
        <img src="../assets/login/img-3.svg" alt="" />
      </div>
      <div class="login-box">
        <el-form action="#" class="form" ref="userForm">
          <img src="../assets/login/avatar.svg" alt="" class="avatar" />
          <h2>Welcome</h2>
          <div class="input-group">
            <div class="icon">
              <i class="fa fa-user"></i>
            </div>
            <div>
              <h5>Username</h5>
              <input type="text" class="input" />
            </div>
          </div>
          <div class="input-group">
            <div class="icon">
              <i class="fa fa-lock"></i>
            </div>
            <div>
              <h5>Password</h5>
              <input type="password" class="input" />
            </div>
          </div>
          <input type="submit" class="btn" value="Login" />
        </el-form>
      </div>
    </div>
    <a href="https://www.ramostear.com" target="_blank" class="copyright"
      >&copy; Ramostear</a
    >
  </div>
</template>

<script>
// import User from "@/models/user";
export default {
  name: "Login",
  data() {
    return {
      // 用于叠加部分更替效果
      isActive: true,
      // 登录部分
      user: "",
      // user: new User("", "", "", ""),
      loading: false,
      errMsg: "",
      imgUrl: "",
    };
  },
  computed: {
    // loggedIn() {
    //   return this.$store.state.auth.status.loggedIn;
    // },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/home");
    }
    this.getKaptcha();

    const inputs = document.querySelectorAll(".input");
    inputs.forEach((input) => {
      input.addEventListener("focus", focusFunction);
      input.addEventListener("blur", blurFunction);
    });
  },
  methods: {
    focusFunction() {
      let parentNode = this.parentNode.parentNode;
      parentNode.classList.add("focus");
    },
    blurFunction() {
      let parentNode = this.parentNode.parentNode;
      if (this.value == "") {
        parentNode.classList.remove("focus");
      }
    },

    getKaptcha() {
      //   axios
      //     .get("/captcha/getCaptchaCode", {
      //       responseType: "arraybuffer",
      //     })
      //     .then((response) => {
      //       return (
      //         "data:image/png;base64," +
      //         btoa(
      //           new Uint8Array(response.data).reduce(
      //             (data, byte) => data + String.fromCharCode(byte),
      //             ""
      //           )
      //         )
      //       );
      //     })
      //     .then((data) => {
      //       this.imgUrl = data;
      //     });
    },
    checkKaptcha() {
      //   axios
      //     .get("/captcha/checkCaptchaCode", {
      //       params: { captchaCode: this.user.captchaCode },
      //     })
      //     .then((res) => {
      //       switch (res.data) {
      //         case "输入为空":
      //           this.errMsg = "请输入验证码";
      //           return false;
      //         case "输入错误":
      //           this.errMsg = "验证码错误，请重试";
      //           return false;
      //         case "验证成功":
      //           this.errMsg = "";
      //       }
      //     });
      //   return true;
    },

    clearFormFields() {
      this.user = new User("", "", "", "");
      this.errMsg = "";
    },
    validFormData() {
      this.errMsg = "";
      // 验证是否为空
      if (this.user.identity == "") {
        // 如果管理员传入0，则输出为true
        this.errMsg = "请选择身份";
        return false;
      }
      if (this.user.account == "" || this.user.password == "") {
        this.errMsg = "用户名或密码不能为空";
        return false;
      }
      if (this.user.captchaCode == "") {
        this.errMsg = "请输入验证码";
        return false;
      }
      return true;
    },
    //用户登录请求后台处理
    login(identity) {
      this.user.identity = identity;
      if (!this.validFormData()) {
        this.loading = false;
        return;
      }
      if (!this.checkKaptcha()) {
        this.loading = false;
        console.log("check error!");
        return;
      }

      this.$store.dispatch("auth/login", this.user).then(
        () => {
          this.$router.push("/home");
        },
        (error) => {
          this.loading = false;
          this.errMsg =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          this.getKaptcha();
        }
      );
    },
  },
};
</script>

<style scoped>
@import url("../assets/login/style.css");
</style>