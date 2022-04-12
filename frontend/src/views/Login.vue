<!-- 用户登录界面 -->
<template>
  <div class="body">
    <div
      ref="container"
      class="loginContainer"
      v-bind:class="{ 'right-panel-active': isActive }"
    >
      <!-- 管理员入口 -->
      <div class="container__form container--signup">
        <el-form action="#" class="form" ref="mgrForm">
          <div class="login">
            <h2>管理员登录</h2>
            <div class="login_box">
              <!-- required不能为空 必须在css效果中有很大的作用 -->
              <input type="text" required v-model="user.account" /><label
                >账户</label
              >
            </div>
            <div class="login_box">
              <input
                type="password"
                required="required"
                v-model="user.password"
              /><label>密码</label>
            </div>
            <div class="login_box">
              <input
                type="text"
                required="required"
                v-model="user.captchaCode"
                placeholder="请输入验证码"
                style="width: 150px; float: left"
                @blur="checkKaptcha()"
              />
              <el-image
                :src="imgUrl"
                width="80"
                height="34"
                alt="验证码"
                @click="getKaptcha()"
              >
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </div>
          </div>
          <span style="color: red">{{ errMsg }}</span>
          <button class="button" @click.prevent="login(3)">登录系统</button>
        </el-form>
      </div>

      <!-- 用户入口 -->
      <div class="container__form container--signin">
        <el-form action="#" class="form" ref="userForm">
          <div class="login">
            <h2>欢迎登录</h2>
            <div class="identity" style="font-size: 18px">
              身份：
              <label style="margin: 0 20px 0 10px">
                <input type="radio" v-model="user.identity" :value="2" />
                教师
              </label>
              <label>
                <input type="radio" v-model="user.identity" :value="1" />
                学生
              </label>
            </div>
            <div class="login_box">
              <input type="text" required v-model="user.account" /><label
                >账户</label
              >
            </div>
            <div class="login_box">
              <input
                type="password"
                required="required"
                v-model="user.password"
              /><label>密码</label>
            </div>
            <div class="login_box">
              <input
                type="text"
                required="required"
                v-model="user.captchaCode"
                placeholder="请输入验证码"
                style="width: 150px; float: left"
                @blur="checkKaptcha()"
              />
              <el-image
                :src="imgUrl"
                width="80"
                height="34"
                alt="验证码"
                @click="getKaptcha()"
              >
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </div>
          </div>
          <span style="color: red">{{ errMsg }}</span>
          <button class="button" @click.prevent="login(user.identity)">
            点击登录
          </button>
        </el-form>
      </div>

      <!-- 叠加部分 -->
      <div class="container__overlay">
        <div class="overlay">
          <div class="overlay__panel overlay--left">
            <button
              class="button"
              ref="user"
              @click="
                isActive = false;
                getKaptcha();
                clearFormFields();
              "
            >
              用户登录入口
            </button>
          </div>
          <div class="overlay__panel overlay--right">
            <button
              class="button"
              ref="manager"
              @click="
                isActive = true;
                getKaptcha();
                clearFormFields();
              "
            >
              管理员登录入口
            </button>
          </div>
        </div>
      </div>
    </div>
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
  },
  methods: {
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
@import url("../assets/login/formstyle.css");
@import url("../assets/login/pagestyle.css");
.body {
  font-size: 16px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}
</style>