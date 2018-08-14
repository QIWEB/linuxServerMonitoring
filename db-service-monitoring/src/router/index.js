import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login.vue'
import Index from '../components/Index.vue'
import Serverdetails from '../components/serverRelease/ServerDetails.vue'
import ServerStatus from '../components/serverRelease/ServerStatus.vue'
import ProcessList from '../components/processList/ProcessList.vue'
Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/Index',
      name: 'Index',
      component: Index,
      meta: {
        login: true /* 需要登录可查看的页面 */
      },
      children: [/* 子路由 */
        {
          path: '/Serverdetails',
          component: Serverdetails,
          meta: {
            login: true /* 需要登录可查看的页面 */
          }
        },
        {
          path: '/ServerStatus',
          component: ServerStatus,
          meta: {
            login: true /* 需要登录可查看的页面 */
          }
        },
        {
          path: '/ProcessList',
          component: ProcessList,
          meta: {
            login: true /* 需要登录可查看的页面 */
          }
        }
      ]
    }
  ]
})

/**
 *  全局拦截路由 访问要求验证登录的页面 未登录 直接跳转到登录页面
 * to:表示目标路由
 * from:表示来源路由
 * next:表示执行下一步操作
 */
router.beforeEach((to, from, next) => {
  // console.info(22, window.location.href)
  // console.info(to,from,next)
  // 对路由变化作出响应...
  // console.log(router,to)
  // console.log(router,to.query, from)
  // console.log(to,$.param( to.query ),window.location.href)
  // console.log('login:' + JSON.stringify(to))
  // 全局拦截器的
  if (to.meta.login) { /* 判断该路由是否需要登录权限 */
    // console.log('accessToken1:' + localStorage.getItem('accessToken'))
    if (localStorage.getItem('accessToken') !== null) { /* 通过store获取当前的token是否存在 */
    // console.log('accessToken2:' + localStorage.getItem('accessToken'))
      next()
    } else {
      next({path: '/'}) // 跳转至登录页面
    }
  } else {
    next()
  }
})
export default router
