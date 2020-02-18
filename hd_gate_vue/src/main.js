// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import 'babel-polyfill'
import ElementUI from 'element-ui'
import Axios from 'axios'
import NProgress from 'nprogress'

import store from './store'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/font-awesome-4.7.0/css/font-awesome.min.css'
import '@/assets/css/main.css'
import '@/assets/css/scrollbar.css'
import '@/assets/css/reset.css'
import 'nprogress/nprogress.css'
import 'animate.css'
import '@/assets/icons'

import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'

import DropdownPlugin from './components/dropdown'
import NavbarPlugin from './components/navbar'
import MBackTop from './components/back-top'

import echarts from 'echarts'
import {hasBtnPermission} from './store/modules/hasBtnPermission'

import App from './App' // 最后引入避免样式覆盖

Vue.prototype.hasBtnPerm = hasBtnPermission
Vue.prototype.$echarts = echarts

Vue.use(ElementUI)

Vue.use(Viewer)

Vue.use(DropdownPlugin)
Vue.use(NavbarPlugin)
Vue.use(MBackTop)

router.beforeEach((to, from, next) => {
  NProgress.start()
  return next()
})

router.afterEach(transition => {
  setTimeout(() => {
    NProgress.done()
  })
})

window.APP_INFO = process.env.APP_INFO

// status < 500 不会抛错误
Axios.defaults.validateStatus = status => {
  return status < 500
}
// 设置请求token(暂不加入token)
Axios.interceptors.request.use(config => {
  // var token = sessionStorage.getItem('token')
  // config.headers['Authorization'] = 'Bearer ' + token
  // console.log('aaaaaaaaaaaaaaaaaaaaa', config)
  return config
})

// 接口错误拦截
Axios.interceptors.response.use(res => {
  if (res.data.userState != null && res.data.userState.indexOf('error:') > -1) {
    app && app.$message({
      type: 'warning',
      message: res.data.userState.substr(6)
    })
    sessionStorage.removeItem('user')
    router.push({name: 'login'})
    return Promise.reject(new Error(res.data.userState.substr(6)))
  } else {
    return res.data
  }
}, err => {
  app.$notify.error({
    title: '服务错误',
    message: '服务器响应错误 ' + err.message
  })
  return Promise.reject(err)
})

Vue.prototype.$http = Axios
Vue.http = Axios

Vue.config.productionTip = false

/* eslint-disable no-new */
var app = new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: { App }
})

window.app = app

