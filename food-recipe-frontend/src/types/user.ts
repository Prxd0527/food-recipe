/**
 * 用户相关类型定义
 * @author prxd0527
 * @date 2025-12-07
 */

/**
 * 用户角色枚举
 */
export enum UserRole {
    USER = 'USER',
    ADMIN = 'ADMIN'
}

/**
 * 用户信息接口
 */
export interface User {
    id: number
    username: string
    nickname: string
    avatar?: string
    email?: string
    role: UserRole
    status: number
    createTime: string
    updateTime: string
}

/**
 * 登录请求参数
 */
export interface LoginRequest {
    username: string
    password: string
}

/**
 * 注册请求参数
 */
export interface RegisterRequest {
    username: string
    password: string
    nickname: string
    email?: string
}

/**
 * 登录响应数据
 */
export interface LoginResponse {
    token: string
    user: User
}
