/**
 * Axios请求封装
 * @author prxd0527
 * @date 2025-12-07
 */
import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'
import { getToken, removeToken } from './auth'
import type { Result } from '@/types/common'

// 创建axios实例
const service: AxiosInstance = axios.create({
    baseURL: '/api', // API基础路径
    timeout: 10000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        // 添加token到请求头
        const token = getToken()
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
    (error) => {
        console.error('请求错误:', error)
        return Promise.reject(error)
    }
)

// 响应拦截器
service.interceptors.response.use(
    (response: AxiosResponse<Result>) => {
        const res = response.data

        // 如果返回的状态码不是200，则认为是错误
        if (res.code !== 200) {
            ElMessage.error(res.message || '请求失败')

            // 401: 未授权，跳转到登录页
            if (res.code === 401) {
                removeToken()
                window.location.href = '/login'
            }

            return Promise.reject(new Error(res.message || '请求失败'))
        } else {
            return res
        }
    },
    (error) => {
        console.error('响应错误:', error)

        if (error.response) {
            const { status, data } = error.response

            if (status === 401) {
                ElMessage.error('登录已过期，请重新登录')
                removeToken()
                window.location.href = '/login'
            } else if (status === 403) {
                ElMessage.error('没有权限访问')
            } else if (status === 404) {
                ElMessage.error('请求的资源不存在')
            } else if (status === 500) {
                ElMessage.error(data.message || '服务器错误')
            } else {
                ElMessage.error(data.message || '请求失败')
            }
        } else {
            ElMessage.error('网络错误，请检查网络连接')
        }

        return Promise.reject(error)
    }
)

// 封装请求方法
export default {
    get<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
        return service.get(url, config).then((res) => res.data)
    },

    post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
        return service.post(url, data, config).then((res) => res.data)
    },

    put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
        return service.put(url, data, config).then((res) => res.data)
    },

    delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
        return service.delete(url, config).then((res) => res.data)
    }
}
