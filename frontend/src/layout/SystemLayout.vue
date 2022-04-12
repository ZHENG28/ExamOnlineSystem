<template>
  <div class="common-layout">
    <el-header>
      <top-navbar></top-navbar>
    </el-header>
    <el-container direction="horizontal">
      <el-aside width="260px">
        <!-- 菜单栏减少时，高度会变 -->
        <el-menu
          router
          class="el-menu-vertical-demo"
          background-color="#8c9885"
          text-color="#292929"
          active-text-color="#fff"
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
            index="teacher"
            route="/admin/teacher"
            class="nav-font"
            v-if="showMgr"
          >
            <template #title>
              <el-icon><user-filled /></el-icon>
              教师管理
            </template>
          </el-menu-item>
          <el-menu-item
            index="clazz"
            route="/admin/clazz"
            class="nav-font"
            v-if="showMgr"
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
            v-if="showMgr"
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
      // return this.$store.state.auth.user;
    },
    showTeacher() {
      // if (this.currentUser && this.currentUser.roles) {
      //   return this.currentUser.roles.includes("ROLE_TEACHER");
      // }

      return true;
    },
    showMgr() {
      // if (this.currentUser && this.currentUser.roles) {
      //   return this.currentUser.roles.includes("ROLE_MANAGER");
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