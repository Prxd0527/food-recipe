package com.food.recipe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.food.recipe.entity.Recipe;
import org.apache.ibatis.annotations.Mapper;

/**
 * 食谱Mapper
 * @author prxd0527
 * @date 2025-12-07
 */
@Mapper
public interface RecipeMapper extends BaseMapper<Recipe> {

}
