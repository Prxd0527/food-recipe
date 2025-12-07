/**
 * 路由配置
 * @author prxd0527
 * @date 2025-12-07
 */
import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { isAuthenticated } from '@/utils/auth'
import { ElMessage } from 'element-plus'

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
        meta: { title: '首页' }
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/auth/Login.vue'),
        meta: { title: '登录', requiresGuest: true }
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/views/auth/Register.vue'),
        meta: { title: '注册', requiresGuest: true }
    },
    {
        path: '/recipes',
        name: 'RecipeList',
        component: () => import('@/views/recipe/RecipeList.vue'),
        meta: { title: '食谱列表' }
    },
    {
        path: '/recipe/:id',
        name: 'RecipeDetail',
        component: () => import('@/views/recipe/RecipeDetail.vue'),
        meta: { title: '食谱详情' }
    },
    {
        path: '/recipe/create',
        name: 'RecipeCreate',
        component: () => import('@/views/recipe/RecipeCreate.vue'),
        meta: { title: '发布食谱', requiresAuth: true }
    },
    {
        path: '/my-recipes',
        name: 'MyRecipes',
        component: () => import('@/views/user/MyRecipes.vue'),
        meta: { title: '我的食谱', requiresAuth: true }
    },
    {
        path: '/my-favorites',
        name: 'MyFavorites',
        component: () => import('@/views/user/MyFavorites.vue'),
        meta: { title: '我的收藏', requiresAuth: true }
    },
    {
        path: '/profile',
        name: 'Profile',
        component: () => import('@/views/user/Profile.vue'),
        meta: { title: '个人中心', requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    // 设置页面标题
    document.title = `${to.meta.title || '美食食谱分享系统'} - 美食食谱`

    const isAuth = isAuthenticated()

    // 需要登录的页面
    if (to.meta.requiresAuth && !isAuth) {
        ElMessage.warning('请先登录')
        next('/login')
        return
    }

    // 已登录用户访问登录/注册页面，跳转到首页
    if (to.meta.requiresGuest && isAuth) {
        next('/')
        return
    }

    next()
})

export default router
