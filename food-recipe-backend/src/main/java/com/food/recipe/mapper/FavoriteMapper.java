package com.food.recipe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.food.recipe.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * 收藏Mapper
 * @author prxd0527
 * @date 2025-12-07
 */
@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

}
