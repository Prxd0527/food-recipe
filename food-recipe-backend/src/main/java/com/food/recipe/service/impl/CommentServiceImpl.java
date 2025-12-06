package com.food.recipe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.recipe.dto.CommentDTO;
import com.food.recipe.entity.Comment;
import com.food.recipe.entity.Recipe;
import com.food.recipe.entity.User;
import com.food.recipe.exception.BusinessException;
import com.food.recipe.mapper.CommentMapper;
import com.food.recipe.mapper.RecipeMapper;
import com.food.recipe.service.CommentService;
import com.food.recipe.service.UserService;
import com.food.recipe.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论Service实现类
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createComment(Long recipeId, CommentDTO commentDTO, Long userId) {
        // 检查食谱是否存在
        Recipe recipe = recipeMapper.selectById(recipeId);
        if (recipe == null) {
            throw new BusinessException("食谱不存在");
        }

        // 创建评论
        Comment comment = new Comment();
        comment.setRecipeId(recipeId);
        comment.setUserId(userId);
        comment.setContent(commentDTO.getContent());
        comment.setRating(commentDTO.getRating());

        commentMapper.insert(comment);

        // 更新食谱评论数
        recipe.setCommentCount(recipe.getCommentCount() + 1);
        recipeMapper.updateById(recipe);

        log.info("用户{}评论食谱{}成功", userId, recipeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long id, Long userId) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }

        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException("无权限删除此评论");
        }

        commentMapper.deleteById(id);

        // 更新食谱评论数
        Recipe recipe = recipeMapper.selectById(comment.getRecipeId());
        if (recipe != null && recipe.getCommentCount() > 0) {
            recipe.setCommentCount(recipe.getCommentCount() - 1);
            recipeMapper.updateById(recipe);
        }

        log.info("用户{}删除评论{}成功", userId, id);
    }

    @Override
    public Page<CommentVO> getRecipeComments(Long recipeId, Integer current, Integer size) {
        Page<Comment> page = new Page<>(current, size);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRecipeId, recipeId);
        wrapper.orderByDesc(Comment::getCreateTime);

        Page<Comment> commentPage = commentMapper.selectPage(page, wrapper);

        // 转换为VO
        Page<CommentVO> voPage = new Page<>(commentPage.getCurrent(), commentPage.getSize(), commentPage.getTotal());
        List<CommentVO> voList = commentPage.getRecords().stream()
                .map(this::buildCommentVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public Page<CommentVO> getMyComments(Long userId, Integer current, Integer size) {
        Page<Comment> page = new Page<>(current, size);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getUserId, userId);
        wrapper.orderByDesc(Comment::getCreateTime);

        Page<Comment> commentPage = commentMapper.selectPage(page, wrapper);

        // 转换为VO
        Page<CommentVO> voPage = new Page<>(commentPage.getCurrent(), commentPage.getSize(), commentPage.getTotal());
        List<CommentVO> voList = commentPage.getRecords().stream()
                .map(this::buildCommentVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return voPage;
    }

    /**
     * 构建CommentVO
     */
    private CommentVO buildCommentVO(Comment comment) {
        CommentVO vo = new CommentVO();
        BeanUtils.copyProperties(comment, vo);

        // 查询用户信息
        User user = userService.getUserById(comment.getUserId());
        if (user != null) {
            vo.setUserNickname(user.getNickname());
            vo.setUserAvatar(user.getAvatar());
        }

        return vo;
    }

}
