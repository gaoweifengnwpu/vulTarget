import {createRouter, createWebHistory} from 'vue-router'
import Success from './components/login/Success.vue'
import Login from './components/login/Login.vue'
import File from './components/login/File.vue'
import Register from './components/login/Register.vue'
import Index from './components/login/Index.vue'
import User from './components/login/User.vue'
import Role from './components/login/Role.vue'
import Blog from './components/login/Blog.vue'
import blog1 from './components/login/blog1.vue'
import Cve from './components/login/Cve.vue'

const routes = [{path: '/', redirect: '/lo'}, {
    path: '/lo', name: 'login', component: Login
}, {
    path: '/reg', name: 'register',  // 路由元信息 meta
    component: Register
}, {
    path: '/index', name: 'index',  // 路由元信息 meta
    component: Index, meta: {requiresAuth: true}, children: [{
        path: 'success', name: 'success', component: Success, meta: {requiresAuth: true}
    }, {
        path: 'file', name: 'file', component: File, meta: {requiresAuth: true}
    }, {
        path: 'user', name: 'user', component: User, meta: {requiresAuth: true}
    }, {
        path: 'role', name: 'role', component: Role, meta: {requiresAuth: true}
    }, {
        path: 'blog', name: 'blog', component: Blog, meta: {requiresAuth: true}
    }, {
        path: 'gb', name: 'getblog', component: blog1, meta: {requiresAuth: true}
    }, {
        path: 'cve', name: 'cve', component: Cve, meta: {requiresAuth: true}
    }]
}]


const route = createRouter({
    history: createWebHistory(), routes
})
// 路由守卫
route.beforeEach((to, from, next) => {
    // to要跳转到的路径
    // from从哪个路径来
    // next往下执行的回调
    // 在localStorage中获取token
    let token = localStorage.getItem('sessionid')
    // 判断该页面是否需要登录
    if (to.meta.requiresAuth) {
        // 如果token存在直接跳转
        if (token) {
            next()
        } else {
            // 否则跳转到login登录页面
            next({
                path: '/lo', // 跳转时传递参数到登录页面，以便登录后可以跳转到对应页面
                query: {
                    redirect: to.fullPath
                }
            })
        }
    } else {
        // 如果不需要登录，则直接跳转到对应页面
        next()
    }
})
export default route