package com.food.recipe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.food.recipe.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论Mapper
 * @author prxd0527
 * @date 2025-12-07
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
