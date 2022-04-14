import { createRouter, createWebHistory } from 'vue-router'
import SystemLayout from '@/layout/SystemLayout.vue'
import Home from '../views/Home.vue'

const routes = [
  {
    path: '/',
    component: () => import('../views/Login.vue'),
  },
  {
    path: '/',
    component: SystemLayout,
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: '首页',
        component: Home,
      },
      {
        path: '/admin/user',
        name: '用户管理',
        component: () => import('../views/admin/User.vue'),
      },
      {
        path: '/admin/clazz',
        name: '班级管理',
        component: () => import('../views/admin/Clazz.vue'),
      },
      {
        path: '/admin/subject',
        name: '科目管理',
        component: () => import('../views/admin/Subject.vue'),
      },
    ],
  },
  {
    path: '/',
    component: SystemLayout,
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: '首页',
        component: Home,
      },
      {
        path: '/teacher/student',
        name: '学生信息',
        component: () => import('../views/teacher/Student.vue'),
      },
      {
        path: '/teacher/question',
        name: '题目信息',
        component: () => import('../views/teacher/Question.vue'),
      },
      {
        path: '/teacher/testInfo',
        name: '测验信息',
        component: () => import('../views/teacher/test/TestInfo.vue'),
      },
      {
        path: '/teacher/testBuild/:testId',
        name: '发布测验',
        component: () => import('../views/teacher/test/TestBuild.vue'),
      },
    ],
  },
  {
    path: '/',
    component: SystemLayout,
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: '首页',
        component: Home,
      },
      {
        path: '/student/test',
        name: '测验',
        component: () => import('../views/student/Test.vue'),
      },
      {
        path: '/student/testHistory',
        name: '测验记录',
        component: () => import('../views/student/TestHistory.vue'),
      },
    ],
  },
  {
    path: '/student/testPaper/:testId',
    // name: "测验记录",
    component: () => import('../views/student/TestPaper.vue'),
  },

  // { path: '*', component: NotFound },
  // { path: '/noAuth', component: NoAuth },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
