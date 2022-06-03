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
          background-color="#38d39f"
          text-color="#fff"
          active-text-color="#000"
          default-active="home"
          :style="{ height: menuHeight }"
        >
          <el-menu-item index="home" route="/home" class="nav-font">
            <template #title>
              <el-icon><home-filled /></el-icon>
              首页
            </template>
          </el-menu-item>

          <!-- admin -->
          <el-menu-item
            index="major"
            route="/admin/major"
            class="nav-font"
            v-if="showAdmin"
          >
            <template #title>
              <el-icon><grid /></el-icon>
              专业管理
            </template>
          </el-menu-item>
          <el-menu-item
            index="clazz"
            route="/admin/clazz"
            class="nav-font"
            v-if="showAdmin"
          >
            <template #title>
              <el-icon><expand /></el-icon>
              班级管理
            </template>
          </el-menu-item>
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
            index="questionType"
            route="/admin/questionType"
            class="nav-font"
            v-if="showAdmin"
          >
            <template #title>
              <el-icon><list /></el-icon>
              问题类型管理
            </template>
          </el-menu-item>

          <!-- teacher -->
          <el-menu-item
            index="subjectInfo"
            route="/teacher/subjectInfo"
            class="nav-font"
            v-if="showTeacher"
          >
            <template #title>
              <el-icon><help-filled /></el-icon>
              管理科目信息
            </template>
          </el-menu-item>
          <el-menu-item
            index="question"
            route="/teacher/question"
            class="nav-font"
            v-if="showTeacher"
          >
            <template #title>
              <el-icon><trend-charts /></el-icon>
              管理题目信息
            </template>
          </el-menu-item>
          <el-sub-menu index="tch-test" v-if="showTeacher">
            <template #title>
              <el-icon><promotion /></el-icon>
              <span class="nav-font">测验信息</span>
            </template>
            <el-menu-item-group>
              <el-menu-item
                index="test-build"
                class="nav-font"
                route="/teacher/testBuild/0"
              >
                <el-icon><checked /></el-icon>
                发布测验
              </el-menu-item>
              <el-menu-item
                index="test-find"
                route="/teacher/testInfo"
                class="nav-font"
              >
                <el-icon><list /></el-icon>
                管理测验信息
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>

          <!-- student -->
          <el-menu-item
            index="stu-test"
            route="/student/test"
            class="nav-font"
            v-if="showStudent"
          >
            <template #title>
              <el-icon><promotion /></el-icon>
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
              <el-icon><list /></el-icon>
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
  data() {
    return {
      showAdmin: false,
      showTeacher: false,
      showStudent: false,
      menuHeight: "",
    };
  },
  created() {
    this.menuHeight = document.documentElement.clientHeight - 180 + "px";

    let user = this.$storage.getStorageSync("user");
    if (user && user.roles) {
      this.showAdmin = user.roles.includes("ROLE_ADMIN");
      this.showTeacher = user.roles.includes("ROLE_TEACHER");
      this.showStudent = user.roles.includes("ROLE_STUDENT");
    }
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