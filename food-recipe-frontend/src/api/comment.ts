/**
 * 评论相关API
 * @author prxd0527
 * @date 2025-12-07
 */
import request from '@/utils/request'
import type { Comment, CommentRequest } from '@/types/comment'
import type { PageResult } from '@/types/common'

/**
 * 创建评论
 */
export function createComment(recipeId: number, data: CommentRequest) {
    return request.post(`/recipes/${recipeId}/comments`, data)
}

/**
 * 删除评论
 */
export function deleteComment(id: number) {
    return request.delete(`/comments/${id}`)
}

/**
 * 查询食谱评论列表
 */
export function getRecipeComments(recipeId: number, params: { current?: number; size?: number }) {
    return request.get<PageResult<Comment>>(`/recipes/${recipeId}/comments`, { params })
}

/**
 * 查询我的评论
 */
export function getMyComments(params: { current?: number; size?: number }) {
    return request.get<PageResult<Comment>>('/comments/my', { params })
}
