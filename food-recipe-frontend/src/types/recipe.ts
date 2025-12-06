/**
 * 食谱相关类型定义
 * @author prxd0527
 * @date 2025-12-07
 */

/**
 * 食谱状态枚举
 */
export enum RecipeStatus {
    DRAFT = 'DRAFT',
    PENDING = 'PENDING',
    PUBLISHED = 'PUBLISHED',
    REJECTED = 'REJECTED'
}

/**
 * 难度枚举
 */
export enum Difficulty {
    EASY = 'EASY',
    MEDIUM = 'MEDIUM',
    HARD = 'HARD'
}

/**
 * 食材接口
 */
export interface Ingredient {
    id?: number
    name: string
    amount?: string
}

/**
 * 步骤接口
 */
export interface Step {
    id?: number
    stepNumber?: number
    content: string
    imageUrl?: string
}

/**
 * 标签接口
 */
export interface Tag {
    id: number
    name: string
    type: string
}

/**
 * 食谱接口
 */
export interface Recipe {
    id: number
    userId: number
    userNickname: string
    userAvatar?: string
    title: string
    description?: string
    coverImage?: string
    cuisineType?: string
    difficulty?: string
    cookingTime?: number
    servings?: number
    viewCount: number
    favoriteCount: number
    commentCount: number
    status: RecipeStatus
    createTime: string
    updateTime: string
    ingredients: Ingredient[]
    steps: Step[]
    tags: Tag[]
}

/**
 * 创建/更新食谱请求
 */
export interface RecipeRequest {
    id?: number
    title: string
    description?: string
    coverImage?: string
    cuisineType?: string
    difficulty?: string
    cookingTime?: number
    servings?: number
    ingredients: Ingredient[]
    steps: Step[]
    tagIds?: number[]
}
