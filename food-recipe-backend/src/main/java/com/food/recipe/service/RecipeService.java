package com.food.recipe.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.recipe.dto.RecipeDTO;
import com.food.recipe.entity.Recipe;
import com.food.recipe.entity.Tag;
import com.food.recipe.vo.RecipeVO;

import java.util.List;

/**
 * 食谱Service接口
 * @author prxd0527
 * @date 2025-12-07
 */
public interface RecipeService {

    /**
     * 创建食谱
     */
    Long createRecipe(RecipeDTO recipeDTO, Long userId);

    /**
     * 更新食谱
     */
    void updateRecipe(RecipeDTO recipeDTO, Long userId);

    /**
     * 删除食谱（软删除）
     */
    void deleteRecipe(Long id, Long userId);

    /**
     * 发布食谱（状态变更为待审核）
     */
    void publishRecipe(Long id, Long userId);

    /**
     * 查询食谱详情
     */
    RecipeVO getRecipeDetail(Long id);

    /**
     * 分页查询食谱列表
     */
    Page<RecipeVO> getRecipeList(Integer current, Integer size, String keyword, 
                                  String cuisineType, String difficulty, Long tagId);

    /**
     * 查询我的食谱
     */
    Page<RecipeVO> getMyRecipes(Integer current, Integer size, Long userId);

    /**
     * 增加浏览次数
     */
    void increaseViewCount(Long id);

    /**
     * 查询所有标签
     */
    List<Tag> getAllTags();

    /**
     * 根据类型查询标签
     */
    List<Tag> getTagsByType(String type);

}
