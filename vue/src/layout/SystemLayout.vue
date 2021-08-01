<template>
  <div class="wrapper">
    <div class="sidebar">
      <div
        class="sidebar-wrapper"
        id="style-3"
        style="background-color: #97e7ff; border-radius: 5px"
      >
        <el-menu
          router
          default-active="2"
          class="el-menu-vertical-demo"
          background-color="#97e7ff"
          text-color="#fff"
          active-text-color="#000"
        >
          <!-- text-color="#07bbf1" -->
          <el-menu-item index="home" route="/home" class="nav-font">
            <template slot="title">
              <i class="nav-icons el-icon-news"></i>首页
            </template>
          </el-menu-item>
          <el-menu-item
            index="student"
            route="/teacher/student"
            class="nav-font"
            v-if="showTeacher"
          >
            <i class="nav-icons el-icon-user"></i>
            学生信息
          </el-menu-item>
          <el-menu-item
            index="question"
            route="/teacher/question"
            class="nav-font"
            v-if="showTeacher"
          >
            <template slot="title">
              <i class="nav-icons el-icon-document"></i>
              题目信息
            </template>
          </el-menu-item>
          <el-submenu index="test" v-if="showTeacher">
            <template slot="title">
              <i class="nav-icons el-icon-edit-outline"></i>
              <span class="nav-font">测验</span>
            </template>
            <el-menu-item-group>
              <el-menu-item
                index="test-build"
                class="nav-font"
                route="/teacher/testBuild/0"
                >发布测验</el-menu-item
              >
              <el-menu-item
                index="test-find"
                route="/teacher/testInfo"
                class="nav-font"
                >查看测验信息</el-menu-item
              >
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item
            index="teacher"
            route="/admin/teacher"
            class="nav-font"
            v-if="showMgr"
          >
            <template slot="title">
              <i class="nav-icons el-icon-s-check"></i>教师管理
            </template>
          </el-menu-item>
          <el-menu-item
            index="clazz"
            route="/admin/clazz"
            class="nav-font"
            v-if="showMgr"
          >
            <template slot="title">
              <i class="nav-icons el-icon-files"></i>班级管理
            </template>
          </el-menu-item>
          <el-menu-item
            index="subject"
            route="/admin/subject"
            class="nav-font"
            v-if="showMgr"
          >
            <template slot="title">
              <i class="nav-icons el-icon-help"></i>科目管理
            </template>
          </el-menu-item>
          <el-menu-item
            index="subject"
            route="/student/test"
            class="nav-font"
            v-if="showStudent"
          >
            <template slot="title">
              <i class="nav-icons el-icon-edit-outline"></i>测验
            </template>
          </el-menu-item>
          <el-menu-item
            index="subject"
            route="/student/testHistory"
            class="nav-font"
            v-if="showStudent"
          >
            <template slot="title">
              <i class="nav-icons el-icon-document"></i>测验记录
            </template>
          </el-menu-item>
        </el-menu>
      </div>
    </div>
    <div class="main-panel">
      <top-navbar></top-navbar>
      <dashboard-content @click.native="toggleSidebar"> </dashboard-content>
      <content-footer></content-footer>
    </div>
  </div>
</template>
<style scoped>
/* 子菜单的背景色 */
.el-menu-item {
  background-color: #97e7ff !important;
}
.nav-font {
  font-weight: bold;
  font-size: 14px;
}
.nav-icons {
  font-size: 20px;
  color: #fff !important;
  margin-right: 13px !important;
  margin-left: 9px !important;
}
/* 鼠标悬浮时，子菜单的样式 */
.el-menu-item:hover {
  outline: 0 !important;
  color: #fff !important;
}
/* 鼠标悬浮时，图标的样式 */
.el-menu-item:hover {
  border-color: #97e7ff;
}
.nav-font:hover {
  font-weight: 500;
}
.el-submenu:hover {
  background-color: #97e7ff !important;
}
.el-menu-item:hover::before,
.el-menu-item.is-active::before {
  content: "\00BB";
  font-size: 22px;
}
</style>
<style>
.el-submenu__title:hover {
  outline: 0 !important;
  color: #97e7ff;
  background: none !important;
}
</style>
<script>
import TopNavbar from "./TopNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import MobileMenu from "./MobileMenu";
export default {
  components: {
    TopNavbar,
    ContentFooter,
    DashboardContent,
    MobileMenu,
  },
  data() {
    return {
      isShow: true,
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showTeacher() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes("ROLE_TEACHER");
      }

      return false;
    },
    showMgr() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes("ROLE_MANAGER");
      }

      return false;
    },
    showStudent() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes("ROLE_STUDENT");
      }

      return false;
    },
  },
  methods: {
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }
    },
  },
};
</script>
