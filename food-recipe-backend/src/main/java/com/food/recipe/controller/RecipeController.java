package com.food.recipe.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.recipe.common.PageResult;
import com.food.recipe.common.Result;
import com.food.recipe.dto.RecipeDTO;
import com.food.recipe.entity.Tag;
import com.food.recipe.service.RecipeService;
import com.food.recipe.utils.JwtUtil;
import com.food.recipe.vo.RecipeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 食谱控制器
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@RestController
@RequestMapping("/api/recipes")
@Api(tags = "食谱管理")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @ApiOperation("创建食谱")
    public Result<Long> createRecipe(@Validated @RequestBody RecipeDTO recipeDTO,
                                      @RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromToken(authorization);
        Long recipeId = recipeService.createRecipe(recipeDTO, userId);
        return Result.success("创建成功", recipeId);
    }

    @PutMapping("/{id}")
    @ApiOperation("更新食谱")
    public Result<Void> updateRecipe(@PathVariable Long id,
                                      @Validated @RequestBody RecipeDTO recipeDTO,
                                      @RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromToken(authorization);
        recipeDTO.setId(id);
        recipeService.updateRecipe(recipeDTO, userId);
        return Result.success("更新成功", null);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除食谱")
    public Result<Void> deleteRecipe(@PathVariable Long id,
                                      @RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromToken(authorization);
        recipeService.deleteRecipe(id, userId);
        return Result.success("删除成功", null);
    }

    @PostMapping("/{id}/publish")
    @ApiOperation("发布食谱")
    public Result<Void> publishRecipe(@PathVariable Long id,
                                       @RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromToken(authorization);
        recipeService.publishRecipe(id, userId);
        return Result.success("发布成功，等待审核", null);
    }

    @GetMapping("/{id}")
    @ApiOperation("查询食谱详情")
    public Result<RecipeVO> getRecipeDetail(@PathVariable Long id) {
        // 增加浏览次数
        recipeService.increaseViewCount(id);
        
        RecipeVO recipeVO = recipeService.getRecipeDetail(id);
        return Result.success(recipeVO);
    }

    @GetMapping
    @ApiOperation("分页查询食谱列表")
    public Result<PageResult<RecipeVO>> getRecipeList(
            @ApiParam("当前页") @RequestParam(defaultValue = "1") Integer current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size,
            @ApiParam("关键词") @RequestParam(required = false) String keyword,
            @ApiParam("菜系") @RequestParam(required = false) String cuisineType,
            @ApiParam("难度") @RequestParam(required = false) String difficulty,
            @ApiParam("标签ID") @RequestParam(required = false) Long tagId) {
        
        Page<RecipeVO> page = recipeService.getRecipeList(current, size, keyword, cuisineType, difficulty, tagId);
        PageResult<RecipeVO> pageResult = new PageResult<>(
                page.getTotal(),
                page.getRecords(),
                page.getCurrent(),
                page.getSize()
        );
        return Result.success(pageResult);
    }

    @GetMapping("/my")
    @ApiOperation("查询我的食谱")
    public Result<PageResult<RecipeVO>> getMyRecipes(
            @ApiParam("当前页") @RequestParam(defaultValue = "1") Integer current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size,
            @RequestHeader("Authorization") String authorization) {
        
        Long userId = getUserIdFromToken(authorization);
        Page<RecipeVO> page = recipeService.getMyRecipes(current, size, userId);
        PageResult<RecipeVO> pageResult = new PageResult<>(
                page.getTotal(),
                page.getRecords(),
                page.getCurrent(),
                page.getSize()
        );
        return Result.success(pageResult);
    }

    @GetMapping("/tags")
    @ApiOperation("获取所有标签")
    public Result<List<Tag>> getAllTags() {
        List<Tag> tags = recipeService.getAllTags();
        return Result.success(tags);
    }

    @GetMapping("/tags/{type}")
    @ApiOperation("根据类型获取标签")
    public Result<List<Tag>> getTagsByType(@PathVariable String type) {
        List<Tag> tags = recipeService.getTagsByType(type);
        return Result.success(tags);
    }

    /**
     * 从Token中获取用户ID
     */
    private Long getUserIdFromToken(String authorization) {
        String token = authorization.replace("Bearer ", "");
        return jwtUtil.getUserIdFromToken(token);
    }

}
