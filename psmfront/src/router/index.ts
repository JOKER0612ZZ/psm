import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
// import { intercept } from './intercept'
const routes: Array<RouteRecordRaw> = [
  {
    //路由初始指向
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/components/login.vue'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/components/register.vue')
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/components/main-views/home.vue'),
    redirect: '/home/index',
    children: [
      {
        path: 'index',
        name: 'index',
        component: () => import('@/components/pages/index.vue')
      },
      {
        path: 'project',
        name: 'project',
        component: () => import('@/components/pages/project.vue'),
        children: [
          {
            path: 'details/:mark',
            name: 'details',
            component: () => import('@/components/pages/project/details.vue'),
            redirect: to => {
              const { params } = to
              return { name: 'view', params }
            },
            children: [
              {
                path: 'view',
                name: 'view',
                component: () => import('@/components/details/view.vue')
              },
              {
                path: 'demand',
                name: 'demand',
                component: () => import('@/components/details/demand.vue')
              }
            ]
          },
        ]
      },
      {
        path: 'team',
        name: 'team',
        component: () => import('@/components/pages/team.vue')
      },
      {
        path: 'task',
        name: 'task',
        component: () => import('@/components/pages/task.vue')
      },
      {
        path: 'file',
        name: 'file',
        component: () => import('@/components/pages/file.vue')
      },
      {
        path: 'personal',
        name: 'personal',
        component: () => import('@/components/pages/personal.vue')
      }
    ]
  }
]
const router = createRouter({
  history: createWebHistory(),
  routes
})
// intercept(router)
export default router
