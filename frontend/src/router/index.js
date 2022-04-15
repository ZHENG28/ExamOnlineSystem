import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('../views/Login.vue'),
  },
  {
    path: '/',
    component: () => import('@/layout/SystemLayout.vue'),
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: '首页',
        component: () => import('../views/Home.vue'),
      },
      // admin
      {
        path: '/admin/clazz',
        name: '班级管理',
        component: () => import('../views/admin/Clazz.vue'),
      },
      {
        path: '/admin/user',
        name: '用户管理',
        component: () => import('../views/admin/User.vue'),
      },
      {
        path: '/admin/subject',
        name: '科目管理',
        component: () => import('../views/admin/Subject.vue'),
      },
      {
        path: '/admin/questionType',
        name: '问题类型管理',
        component: () => import('../views/admin/QuestionType.vue'),
      },

      // teacher
      {
        path: '/teacher/subjectInfo',
        name: '管理科目信息',
        component: () => import('../views/teacher/SubjectInfo.vue'),
      },
      {
        path: '/teacher/question',
        name: '管理题目信息',
        component: () => import('../views/teacher/Question.vue'),
      },
      {
        path: '/teacher/testBuild/:testId',
        name: '发布测验',
        component: () => import('../views/teacher/test/TestBuild.vue'),
      },
      {
        path: '/teacher/testInfo',
        name: '管理测验信息',
        component: () => import('../views/teacher/test/TestInfo.vue'),
      },

      // student
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
