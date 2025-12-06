/**
 * 通用类型定义
 * @author prxd0527
 * @date 2025-12-07
 */

/**
 * 统一响应结构
 */
export interface Result<T = any> {
    code: number
    message: string
    data: T
}

/**
 * 分页请求参数
 */
export interface PageRequest {
    current: number
    size: number
}

/**
 * 分页响应数据
 */
export interface PageResult<T> {
    total: number
    records: T[]
    current: number
    size: number
    pages: number
}
