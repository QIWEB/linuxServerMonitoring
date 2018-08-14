// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
import echarts from 'echarts'
import {post, get} from './axios/axios'
import axiosURL from './axios/axiosUrl' // 引用全局url地址对象
import overall from './assets/js/overall_js' // 引用全局函数
// 定义全局请求方式变量
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.use(ElementUI)
Vue.config.productionTip = false
Vue.prototype.$echarts = echarts
Vue.prototype.$axiosURL = axiosURL
Vue.prototype.$overall = overall
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
