import { createRouter, createWebHistory } from 'vue-router'
import SystemLayout from '@/layout/SystemLayout.vue'

import Login from '../views/Login.vue'
import log from '../views/log.vue'
import Home from '../views/Home.vue'

import Clazz from '../views/admin/Clazz.vue'
import Subject from '../views/admin/Subject.vue'
import Teacher from '../views/admin/Teacher.vue'

import Student from '../views/teacher/Student.vue'
import Question from '../views/teacher/Question.vue'
import TestInfo from '../views/teacher/test/TestInfo.vue'
import TestBuild from '../views/teacher/test/TestBuild.vue'

import Test from '../views/student/Test.vue'
import TestHistory from '../views/student/TestHistory.vue'
import TestPaper from '../views/student/TestPaper.vue'

const routes = [
  {
    path: '/',
    component: Login,
  },
  {
    path: '/log',
    component: log,
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
        path: '/admin/teacher',
        name: '教师管理',
        component: Teacher,
      },
      {
        path: '/admin/clazz',
        name: '班级管理',
        component: Clazz,
      },
      {
        path: '/admin/subject',
        name: '科目管理',
        component: Subject,
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
        component: Student,
      },
      {
        path: '/teacher/question',
        name: '题目信息',
        component: Question,
      },
      {
        path: '/teacher/testInfo',
        name: '测验信息',
        component: TestInfo,
      },
      {
        path: '/teacher/testBuild/:testId',
        name: '发布测验',
        component: TestBuild,
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
        component: Test,
      },
      {
        path: '/student/testHistory',
        name: '测验记录',
        component: TestHistory,
      },
    ],
  },
  {
    path: '/student/testPaper/:testId',
    // name: "测验记录",
    component: TestPaper,
  },

  // { path: '*', component: NotFound },
  // { path: '/noAuth', component: NoAuth },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
