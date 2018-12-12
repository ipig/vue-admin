import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/appNews',
    component: Layout,
    redirect: '/appNews/indexNews',
    name: 'appNews',
    meta: { title: '应用信息', icon: 'example' },
    children: [
      {
        path: 'indexNews',
        name: 'indexNews',
        meta: { title: '主页', icon: 'table',pageType:1,productType:0 },
        component: () => import('@/views/app/posts')
      },
      {
        path: 'productIntro',
        name: 'productIntro',
        meta: { title: '产品介绍', icon: 'table',pageType:2,productType:1 },
        component: () => import('@/views/app/posts'),
        children: [
          {
            path: 'DBA',
            name: 'DBA',
            meta: { title: '审计', icon: 'table',pageType:2,productType:1 },
            component: () => import('@/views/app/posts')
          },
          {
            path: 'DBF',
            name: 'DBF',
            meta: { title: '防火墙', icon: 'table',pageType:2,productType:2 },
            component: () => import('@/views/app/posts')
          },
          {
            path: 'DBCtrl',
            name: 'DBCtrl',
            meta: { title: '安全运维', icon: 'table',pageType:2,productType:3 },
            component: () => import('@/views/app/posts')
          },
          {
            path: 'DBMasker',
            name: 'DBMasker',
            meta: { title: '静态脱敏', icon: 'table',pageType:2,productType:4 },
            component: () => import('@/views/app/posts')
          }
        ]
      }
    ]
  },
  {
    path: '/cs',
    component: Layout,
    name: 'cs',
    meta: { title: '客户服务', icon: 'example' },
    children: [
      {
        path: 'faq',
        name: 'faq',
        meta: { title: '常见问题', icon: 'table',pageType:3,productType:1 },
        component: () => import('@/views/app/posts'),
        children: [
          {
            path: 'DBA',
            name: 'DBA',
            meta: { title: '审计', icon: 'table',pageType:3,productType:1 },
            component: () => import('@/views/app/posts')
          },
          {
            path: 'DBF',
            name: 'DBF',
            meta: { title: '防火墙', icon: 'table',pageType:3,productType:2 },
            component: () => import('@/views/app/posts')
          },
          {
            path: 'DBCtrl',
            name: 'DBCtrl',
            meta: { title: '安全运维', icon: 'table',pageType:3,productType:3 },
            component: () => import('@/views/app/posts')
          },
          {
            path: 'DBMasker',
            name: 'DBMasker',
            meta: { title: '静态脱敏', icon: 'table',pageType:3,productType:4 },
            component: () => import('@/views/app/posts')
          }
        ]
      },
      {
        path: 'online',
        name: 'online',
        meta: { title: '在线客服', icon: 'table' },
        component: () => import('@/views/app/online')
      }
    ]
  },
  {
    path: '/cl',
    component: Layout,
    name: 'cl',
    meta: { title: '客户管理', icon: 'example' },
    children: [
      {
        path: 'list',
        name: 'cllist',
        meta: { title: '客户列表', icon: 'table' },
        component: () => import('@/views/app/sales')
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
