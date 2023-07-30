import { createRouter, createWebHistory } from 'vue-router'
import { useStorage } from 'vue3-storage'

const routes = [
  // 用户登录入口
  {
    path: '/',
    component: () => import('../views/Login.vue'),
  },
  // 管理员登录入口
  {
    path: '/admin/login',
    component: () => import('../views/admin/Login.vue'),
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
        path: '/admin/major',
        name: '专业管理',
        component: () => import('../views/admin/Major.vue'),
      },
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
        path: '/teacher/knowledgeFrame/:subjectId',
        name: '查看知识点框架',
        component: () => import('../views/teacher/KnowledgeFrame.vue'),
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
        path: '/teacher/testResult/:testId',
        name: '查看测验结果',
        component: () => import('../views/teacher/test/TestResult.vue'),
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
    component: () => import('../views/student/TestPaper.vue'),
  },
  {
    path: '/student/testHistoryDetail/:testId/:studentId',
    component: () => import('../views/student/TestHistoryDetail.vue'),
  },

  {
    path: '/:catchAll(.*)',
    redirect: '/404',
  },
  {
    path: '/404',
    component: () => import('../views/NotFound.vue'),
  },

  {
    path: '/noAuth',
    component: () => import('../views/noAuthorization.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

function getRoutesList() {
  let list = []
  routes.forEach((item) => {
    list.push(item['path'])
    if (item['children']) {
      item['children'].forEach((i) => {
        list.push(i['path'])
      })
    }
  })
  return list
}
router.beforeEach((to, from, next) => {
  let noAuthPath = ['/', '/admin/login', '/home', '/404', '/noAuth']
  let user = useStorage().getStorageSync('user')
  let routeList = getRoutesList()
  if (!user && routeList.indexOf(to.path) != -1) {
    // in routes && no token -> to login
    if (noAuthPath.indexOf(to.path) != -1) {
      // to login -> pass
      next()
    } else {
      next('/')
    }
  } else {
    let role = user.roles[0].toLowerCase().substring(5)
    if (to.path.startsWith('/' + role) || noAuthPath.indexOf(to.path) != -1) {
      // have authorization
      next()
    } else {
      if (routeList.indexOf(to.path) != -1 && to.path != '/404') {
        next('/noAuth')
      } else {
        next()
      }
    }
  }
})

export default router
