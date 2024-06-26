import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { intercept } from './intercept'
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
        redirect: '/home/index/indexOverView',
        component: () => import('@/components/pages/index.vue'),
        children: [
          {
            path: 'indexOverView',
            name: 'indexOverView',
            component: () => import('@/components/pages/index/indexOverview.vue')
          },
          {
            path: 'indexAgent',
            name: 'indexAgent',
            component: () => import('@/components/pages/index/indexAgent.vue')
          }
        ]
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
              },
              {
                path: 'projectFile',
                name: 'projectFile',
                component: () => import('@/components/details/projectFile.vue')
              },
              {
                path:'projectMember',
                name:'projectMember',
                component:() => import('@/components/details/projectMember.vue')
              },
              {
                path:'verify',
                name:'verify',
                component:()=> import('@/components/details/verify.vue')
              }
            ]
          },
        ]
      },
      {
        path: 'team',
        name: 'team',
        component: () => import('@/components/pages/team.vue'),
      },
      {
        path: 'task',
        name: 'task',
        component: () => import('@/components/pages/task.vue')
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
intercept(router)
export default router
