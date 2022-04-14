<template>
  <div class="common-layout">
    <el-header>
      <top-navbar></top-navbar>
    </el-header>
    <el-container direction="horizontal">
      <el-aside width="260px">
        <el-menu
          router
          class="el-menu-vertical-demo"
          background-color="#00BFA6"
          text-color="#fff"
          active-text-color="#000"
          default-active="home"
        >
          <el-menu-item index="home" route="/home" class="nav-font">
            <template #title>
              <el-icon><home-filled /></el-icon>
              首页
            </template>
          </el-menu-item>
          <el-menu-item
            index="student"
            route="/teacher/student"
            class="nav-font"
            v-if="showTeacher"
          >
            <el-icon><user-filled /></el-icon>
            学生信息
          </el-menu-item>
          <el-menu-item
            index="question"
            route="/teacher/question"
            class="nav-font"
            v-if="showTeacher"
          >
            <template #title>
              <el-icon><document /></el-icon>
              题目信息
            </template>
          </el-menu-item>
          <el-sub-menu index="tch-test" v-if="showTeacher">
            <template #title>
              <el-icon><edit /></el-icon>
              <span class="nav-font">测验</span>
            </template>
            <el-menu-item-group>
              <el-menu-item
                index="test-build"
                class="nav-font"
                route="/teacher/testBuild/0"
              >
                <el-icon><document-checked /></el-icon>
                发布测验
              </el-menu-item>
              <el-menu-item
                index="test-find"
                route="/teacher/testInfo"
                class="nav-font"
              >
                <el-icon><document /></el-icon>
                查看测验信息
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-menu-item
            index="user"
            route="/admin/user"
            class="nav-font"
            v-if="showAdmin"
          >
            <template #title>
              <el-icon><user-filled /></el-icon>
              用户管理
            </template>
          </el-menu-item>
          <el-menu-item
            index="clazz"
            route="/admin/clazz"
            class="nav-font"
            v-if="showAdmin"
          >
            <template #title>
              <el-icon><files /></el-icon>
              班级管理
            </template>
          </el-menu-item>
          <el-menu-item
            index="subject"
            route="/admin/subject"
            class="nav-font"
            v-if="showAdmin"
          >
            <template #title>
              <el-icon><help-filled /></el-icon>
              科目管理
            </template>
          </el-menu-item>
          <el-menu-item
            index="stu-test"
            route="/student/test"
            class="nav-font"
            v-if="showStudent"
          >
            <template #title>
              <el-icon><edit /></el-icon>
              测验
            </template>
          </el-menu-item>
          <el-menu-item
            index="test-history"
            route="/student/testHistory"
            class="nav-font"
            v-if="showStudent"
          >
            <template #title>
              <el-icon><document /></el-icon>
              测验记录
            </template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <dashboard-content> </dashboard-content>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import TopNavbar from "./TopNavbar.vue";
import DashboardContent from "./Content.vue";
export default {
  components: {
    TopNavbar,
    DashboardContent,
  },
  computed: {
    currentUser() {
      // return this.$store.state.auth.initialState.user;
    },
    showTeacher() {
      // if (this.currentUser && this.currentUser.roles) {
      //   return this.currentUser.roles.includes("ROLE_TEACHER");
      // }

      return true;
    },
    showAdmin() {
      // if (this.currentUser && this.currentUser.roles) {
      //   return this.currentUser.roles.includes("ROLE_ADMIN");
      // }

      return true;
    },
    showStudent() {
      // if (this.currentUser && this.currentUser.roles) {
      //   return this.currentUser.roles.includes("ROLE_STUDENT");
      // }

      return true;
    },
  },
};
</script>
<style scoped>
.el-header {
  padding-right: 0px !important;
}
.el-menu {
  margin-left: 20px;
  margin-top: 20px;
  padding: 10px;
  border-radius: 24px;
}
.nav-font {
  font-weight: bold;
}
.el-icon {
  margin-right: 15px !important;
}
</style>