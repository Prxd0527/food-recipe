/**
 * 收藏相关API
 * @author prxd0527
 * @date 2025-12-07
 */
import request from '@/utils/request'
import type { Recipe } from '@/types/recipe'
import type { PageResult } from '@/types/common'

/**
 * 添加收藏
 */
export function addFavorite(recipeId: number) {
    return request.post(`/recipes/${recipeId}/favorite`)
}

/**
 * 取消收藏
 */
export function removeFavorite(recipeId: number) {
    return request.delete(`/recipes/${recipeId}/favorite`)
}

/**
 * 检查收藏状态
 */
export function checkFavoriteStatus(recipeId: number) {
    return request.get<boolean>(`/recipes/${recipeId}/favorite/status`)
}

/**
 * 查询我的收藏
 */
export function getMyFavorites(params: { current?: number; size?: number }) {
    return request.get<PageResult<Recipe>>('/favorites', { params })
}
