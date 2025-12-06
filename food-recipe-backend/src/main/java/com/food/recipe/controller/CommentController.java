package com.food.recipe.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.recipe.common.PageResult;
import com.food.recipe.common.Result;
import com.food.recipe.dto.CommentDTO;
import com.food.recipe.service.CommentService;
import com.food.recipe.utils.JwtUtil;
import com.food.recipe.vo.CommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 评论控制器
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "评论管理")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/recipes/{recipeId}/comments")
    @ApiOperation("创建评论")
    public Result<Void> createComment(@PathVariable Long recipeId,
                                       @Validated @RequestBody CommentDTO commentDTO,
                                       @RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromToken(authorization);
        commentService.createComment(recipeId, commentDTO, userId);
        return Result.success("评论成功", null);
    }

    @DeleteMapping("/comments/{id}")
    @ApiOperation("删除评论")
    public Result<Void> deleteComment(@PathVariable Long id,
                                       @RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromToken(authorization);
        commentService.deleteComment(id, userId);
        return Result.success("删除成功", null);
    }

    @GetMapping("/recipes/{recipeId}/comments")
    @ApiOperation("查询食谱评论列表")
    public Result<PageResult<CommentVO>> getRecipeComments(
            @PathVariable Long recipeId,
            @ApiParam("当前页") @RequestParam(defaultValue = "1") Integer current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size) {
        
        Page<CommentVO> page = commentService.getRecipeComments(recipeId, current, size);
        PageResult<CommentVO> pageResult = new PageResult<>(
                page.getTotal(),
                page.getRecords(),
                page.getCurrent(),
                page.getSize()
        );
        return Result.success(pageResult);
    }

    @GetMapping("/comments/my")
    @ApiOperation("查询我的评论")
    public Result<PageResult<CommentVO>> getMyComments(
            @ApiParam("当前页") @RequestParam(defaultValue = "1") Integer current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size,
            @RequestHeader("Authorization") String authorization) {
        
        Long userId = getUserIdFromToken(authorization);
        Page<CommentVO> page = commentService.getMyComments(userId, current, size);
        PageResult<CommentVO> pageResult = new PageResult<>(
                page.getTotal(),
                page.getRecords(),
                page.getCurrent(),
                page.getSize()
        );
        return Result.success(pageResult);
    }

    /**
     * 从Token中获取用户ID
     */
    private Long getUserIdFromToken(String authorization) {
        String token = authorization.replace("Bearer ", "");
        return jwtUtil.getUserIdFromToken(token);
    }

}
