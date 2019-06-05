import Vue from 'vue'
import Router from 'vue-router'

import Login from '../views/Login'
import Main from '../views/Main'
import NotFound from '../views/NotFound'

// 用于嵌套的路由组件
import UserProfile from '../views/user/Profile'
import UserList from '../views/user/List'

Vue.use(Router);

export default new Router({
 //路由模式
 //hash：路径带 # 符号，如 http://localhost/#/login
 // history：路径不带 # 符号，如 http://localhost/login
  mode: 'history',
  routes: [
    {   
        //跳转到登录页
        path:'/',
        redirect:'/login'
    },
    {
      // 登录页
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      // 首页
      path: '/main',
      name: 'Main',
      component: Main,
       // 配置嵌套路由
       children: [
        {path: '/user/profile', component: UserProfile},
        {path: '/user/list', component: UserList},
      ]
    },
    {
        path: '*',
        component: NotFound
      }
  ]
});