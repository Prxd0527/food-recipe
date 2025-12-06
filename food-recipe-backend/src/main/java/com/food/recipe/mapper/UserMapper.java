package com.food.recipe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.food.recipe.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 * @author prxd0527
 * @date 2025-12-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
