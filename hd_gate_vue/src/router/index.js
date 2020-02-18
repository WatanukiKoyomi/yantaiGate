import Vue from 'vue'
import Router from 'vue-router'

import Layout from '@/layout/container-main'
import Dashboard from '@/views/dashboard/index'

import axios from 'axios'
Vue.prototype.$axios = axios

Vue.use(Router)

const page = name => () => import('@/views/errorPages/' + name)
const login = name => () => import('@/views/login/' + name)
const views = name => () => import('@/views/system/' + name + '/' + name)
const service = name => () => import('@/views/service/' + name)
/* const baseData = name => () => import('@/views/baseData/' + name + '/' + name) */
const lane = name => () => import('@/views/laneManagement/' + name + '/' + name)

export default new Router({
  routes: [
    {
      path: '',
      component: Layout,
      children: [
        {path: '/', name: 'dashboard', component: Dashboard},
        {path: '/404', name: '404', component: page('404')},
        {path: '/500', name: '500', component: page('500')},
        {path: '/user', name: 'user', component: views('user')},
        {path: '/dept', name: 'dept', component: views('dept')},
        {path: '/role', name: 'role', component: views('role')},
        {path: '/userRole', name: 'userRole', component: views('userRole')},
        {path: '/resource', name: 'resource', component: views('resource')},
        {path: '/monitor', name: 'monitor', component: service('monitor')},
        {path: '/history', name: 'history', component: service('history')},
        {path: '/task', name: 'task', component: service('task')},
        {path: '/lane', name: 'lane', component: lane('lane')},
        {path: '/equipment', name: 'equipment', component: lane('equipment')}
      ]
    },
    {path: '/login', name: 'login', component: login('login')},
    {path: '*', redirect: {name: '404'}}
  ]
})
