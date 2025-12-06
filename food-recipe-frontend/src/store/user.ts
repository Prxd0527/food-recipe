/**
 * 用户状态管理
 * @author prxd0527
 * @date 2025-12-07
 */
import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { User, LoginRequest, RegisterRequest } from '@/types/user'
import { login as loginApi, register as registerApi, getUserInfo } from '@/api/user'
import { setToken, removeToken, getToken } from '@/utils/auth'
import { ElMessage } from 'element-plus'

export const useUserStore = defineStore('user', () => {
    // 当前用户信息
    const userInfo = ref<User | null>(null)
    // Token
    const token = ref<string>(getToken() || '')

    /**
     * 用户登录
     */
    async function login(loginData: LoginRequest) {
        try {
            const res = await loginApi(loginData)
            token.value = res.token
            userInfo.value = res.user
            setToken(res.token)
            ElMessage.success('登录成功')
            return true
        } catch (error) {
            return false
        }
    }

    /**
     * 用户注册
     */
    async function register(registerData: RegisterRequest) {
        try {
            await registerApi(registerData)
            ElMessage.success('注册成功，请登录')
            return true
        } catch (error) {
            return false
        }
    }

    /**
     * 获取用户信息
     */
    async function fetchUserInfo() {
        try {
            const user = await getUserInfo()
            userInfo.value = user
        } catch (error) {
            console.error('获取用户信息失败:', error)
        }
    }

    /**
     * 用户登出
     */
    function logout() {
        token.value = ''
        userInfo.value = null
        removeToken()
        ElMessage.success('退出成功')
    }

    /**
     * 检查是否为管理员
     */
    function isAdmin() {
        return userInfo.value?.role === 'ADMIN'
    }

    return {
        userInfo,
        token,
        login,
        register,
        fetchUserInfo,
        logout,
        isAdmin
    }
})
