package com.food.recipe.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.recipe.common.PageResult;
import com.food.recipe.common.Result;
import com.food.recipe.service.FavoriteService;
import com.food.recipe.utils.JwtUtil;
import com.food.recipe.vo.RecipeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 收藏控制器
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "收藏管理")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/recipes/{recipeId}/favorite")
    @ApiOperation("添加收藏")
    public Result<Void> addFavorite(@PathVariable Long recipeId,
                                     @RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromToken(authorization);
        favoriteService.addFavorite(recipeId, userId);
        return Result.success("收藏成功", null);
    }

    @DeleteMapping("/recipes/{recipeId}/favorite")
    @ApiOperation("取消收藏")
    public Result<Void> removeFavorite(@PathVariable Long recipeId,
                                        @RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromToken(authorization);
        favoriteService.removeFavorite(recipeId, userId);
        return Result.success("已取消收藏", null);
    }

    @GetMapping("/recipes/{recipeId}/favorite/status")
    @ApiOperation("检查收藏状态")
    public Result<Boolean> checkFavoriteStatus(@PathVariable Long recipeId,
                                                @RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromToken(authorization);
        boolean isFavorited = favoriteService.isFavorited(recipeId, userId);
        return Result.success(isFavorited);
    }

    @GetMapping("/favorites")
    @ApiOperation("查询我的收藏")
    public Result<PageResult<RecipeVO>> getMyFavorites(
            @ApiParam("当前页") @RequestParam(defaultValue = "1") Integer current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size,
            @RequestHeader("Authorization") String authorization) {
        
        Long userId = getUserIdFromToken(authorization);
        Page<RecipeVO> page = favoriteService.getMyFavorites(userId, current, size);
        PageResult<RecipeVO> pageResult = new PageResult<>(
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
