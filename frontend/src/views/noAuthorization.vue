<template>
  <div>
    <div class="background">
      <img src="../assets/noAuth/icon.svg" alt="401" style="width: 100%" />
    </div>
    <div class="content">
      <div style="margin: 0 auto">
        <p
          style="
            font-size: 70px;
            font-weight: bolder;
            letter-spacing: 5px;
            line-height: 120px;
            margin-bottom: 20px;
            text-align: center;
          "
        >
          无权限访问该网页<br />
          请联系管理员
        </p>
      </div>
      <div style="margin: 0 auto">
        <el-button
          type="primary"
          round
          style="letter-spacing: 3px; padding: 17px"
          @click="toLogin()"
        >
          返回登录页面
        </el-button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  created() {},
  methods: {
    toLogin() {
      this.$router.push("/");
    },
  },
};
</script>
<style scoped>
.background {
  width: 100%;
  height: 100%;
  z-index: -1;
  position: absolute;
  overflow: hidden;
}
.content {
  z-index: 1;
  display: flex;
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  justify-content: center;
  flex-direction: column;
  margin-left: 600px;
}
</style>
