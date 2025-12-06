package com.food.recipe.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.recipe.vo.RecipeVO;

/**
 * 收藏Service接口
 * @author prxd0527
 * @date 2025-12-07
 */
public interface FavoriteService {

    /**
     * 添加收藏
     */
    void addFavorite(Long recipeId, Long userId);

    /**
     * 取消收藏
     */
    void removeFavorite(Long recipeId, Long userId);

    /**
     * 检查是否已收藏
     */
    boolean isFavorited(Long recipeId, Long userId);

    /**
     * 查询我的收藏列表
     */
    Page<RecipeVO> getMyFavorites(Long userId, Integer current, Integer size);

}
