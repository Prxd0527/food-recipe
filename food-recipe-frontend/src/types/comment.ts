/**
 * 评论相关类型定义
 * @author prxd0527
 * @date 2025-12-07
 */

/**
 * 评论接口
 */
export interface Comment {
    id: number
    recipeId: number
    userId: number
    userNickname: string
    userAvatar?: string
    content: string
    rating?: number
    createTime: string
}

/**
 * 创建评论请求
 */
export interface CommentRequest {
    content: string
    rating?: number
}
