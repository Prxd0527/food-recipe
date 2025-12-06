/**
 * 食谱相关API
 * @author prxd0527
 * @date 2025-12-07
 */
import request from '@/utils/request'
import type { Recipe, RecipeRequest, Tag } from '@/types/recipe'
import type { PageResult } from '@/types/common'

/**
 * 创建食谱
 */
export function createRecipe(data: RecipeRequest) {
    return request.post<number>('/recipes', data)
}

/**
 * 更新食谱
 */
export function updateRecipe(id: number, data: RecipeRequest) {
    return request.put(`/recipes/${id}`, data)
}

/**
 * 删除食谱
 */
export function deleteRecipe(id: number) {
    return request.delete(`/recipes/${id}`)
}

/**
 * 发布食谱
 */
export function publishRecipe(id: number) {
    return request.post(`/recipes/${id}/publish`)
}

/**
 * 获取食谱详情
 */
export function getRecipeDetail(id: number) {
    return request.get<Recipe>(`/recipes/${id}`)
}

/**
 * 分页查询食谱列表
 */
export function getRecipeList(params: {
    current?: number
    size?: number
    keyword?: string
    cuisineType?: string
    difficulty?: string
    tagId?: number
}) {
    return request.get<PageResult<Recipe>>('/recipes', { params })
}

/**
 * 查询我的食谱
 */
export function getMyRecipes(params: { current?: number; size?: number }) {
    return request.get<PageResult<Recipe>>('/recipes/my', { params })
}

/**
 * 获取所有标签
 */
export function getAllTags() {
    return request.get<Tag[]>('/recipes/tags')
}

/**
 * 根据类型获取标签
 */
export function getTagsByType(type: string) {
    return request.get<Tag[]>(`/recipes/tags/${type}`)
}
