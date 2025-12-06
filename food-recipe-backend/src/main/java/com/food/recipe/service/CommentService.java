package com.food.recipe.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.recipe.dto.CommentDTO;
import com.food.recipe.vo.CommentVO;

/**
 * 评论Service接口
 * @author prxd0527
 * @date 2025-12-07
 */
public interface CommentService {

    /**
     * 创建评论
     */
    void createComment(Long recipeId, CommentDTO commentDTO, Long userId);

    /**
     * 删除评论
     */
    void deleteComment(Long id, Long userId);

    /**
     * 分页查询食谱的评论列表
     */
    Page<CommentVO> getRecipeComments(Long recipeId, Integer current, Integer size);

    /**
     * 查询我的评论
     */
    Page<CommentVO> getMyComments(Long userId, Integer current, Integer size);

}
