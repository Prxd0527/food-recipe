/**
 * 用户相关API
 * @author prxd0527
 * @date 2025-12-07
 */
import request from '@/utils/request'
import type { LoginRequest, RegisterRequest, LoginResponse, User } from '@/types/user'

/**
 * 用户注册
 */
export function register(data: RegisterRequest) {
    return request.post('/auth/register', data)
}

/**
 * 用户登录
 */
export function login(data: LoginRequest) {
    return request.post<LoginResponse>('/auth/login', data)
}

/**
 * 获取当前用户信息
 */
export function getUserInfo() {
    return request.get<User>('/user/info')
}
