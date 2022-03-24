// 引入页面布局
import SystemLayout from "@/layout/SystemLayout.vue";
// GeneralViews
import NotFound from "@/pages/NotFoundPage.vue";
import NoAuth from "@/pages/NoAuthentication.vue";

const Login = () => import(/* webpackChunkName: "home" */"@/pages/login/login.vue");
const Home = () => import(/* webpackChunkName: "home" */"@/pages/Home.vue");
// teacher
const Student = () => import(/* webpackChunkName: "student" */ "@/pages/teacher/Student.vue");
const Question = () => import(/* webpackChunkName: "question" */ "@/pages/teacher/Question.vue");
const TestInfo = () => import(/* webpackChunkName: "testInfo" */ "@/pages/teacher/test/TestInfo.vue");
const TestBuild = () => import(/* webpackChunkName: "testBuild" */ "@/pages/teacher/test/TestBuild.vue");

// admin
const Teacher = () => import(/* webpackChunkName: "teacher" */ "@/pages/admin/Teacher.vue");
const Clazz = () => import(/* webpackChunkName: "clazz" */ "@/pages/admin/Clazz.vue");
const Subject = () => import(/* webpackChunkName: "subject" */ "@/pages/admin/Subject.vue");

// student
const Test = () => import(/* webpackChunkName: "test" */ "@/pages/student/Test.vue");
const TestHistory = () => import(/* webpackChunkName: "testHistory" */ "@/pages/student/TestHistory.vue");
const TestPaper = () => import(/* webpackChunkName: "testPaper" */ "@/pages/student/TestPaper.vue");

const routes = [
  {
    path: "/",
    name: "login",
    component: Login
  },
  {
    path: "/",
    component: SystemLayout,
    redirect: "/home",
    children: [
      {
        path: "/home",
        name: "首页",
        component: Home
      },
      {
        path: "/admin/teacher",
        name: "教师管理",
        component: Teacher
      },
      {
        path: "/admin/clazz",
        name: "班级管理",
        component: Clazz
      },
      {
        path: "/admin/subject",
        name: "科目管理",
        component: Subject
      },
    ]
  },
  {
    path: "/", // 页面布局
    component: SystemLayout,
    // name: "teacher",
    redirect: "/home",
    children: [
      {
        path: "/home",
        name: "首页",
        component: Home
      },
      {
        path: "/teacher/student",
        name: "学生信息",
        component: Student
      },
      {
        path: "/teacher/question",
        name: "题目信息",
        component: Question
      },
      {
        path: "/teacher/testInfo",
        name: "测验信息",
        component: TestInfo
      },
      {
        path: "/teacher/testBuild/:testId",
        name: "测验信息",
        component: TestBuild
      },
    ]
  },
  {
    path: "/",
    component: SystemLayout,
    redirect: "/home",
    children: [
      {
        path: "/home",
        name: "首页",
        component: Home
      },
      {
        path: "/student/test",
        name: "测验",
        component: Test
      },
      {
        path: "/student/testHistory",
        name: "测验记录",
        component: TestHistory
      },
    ]
  },
  {
    path: "/student/testPaper/:testId",
    // name: "测验记录",
    component: TestPaper
  },
  { path: "*", component: NotFound },
  { path: "/noAuth", component: NoAuth },
];

/**
 * Asynchronously load view (Webpack Lazy loading compatible)
 * The specified component must be inside the Views folder
 * @param  {string} name  the filename (basename) of the view to load.
function view(name) {
   var res= require('../components/Dashboard/Views/' + name + '.vue');
   return res;
};**/

export default routes;