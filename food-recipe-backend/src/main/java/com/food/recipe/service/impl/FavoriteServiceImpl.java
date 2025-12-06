package com.food.recipe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.recipe.entity.Favorite;
import com.food.recipe.entity.Recipe;
import com.food.recipe.exception.BusinessException;
import com.food.recipe.mapper.FavoriteMapper;
import com.food.recipe.mapper.RecipeMapper;
import com.food.recipe.service.FavoriteService;
import com.food.recipe.service.RecipeService;
import com.food.recipe.vo.RecipeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏Service实现类
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private RecipeService recipeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFavorite(Long recipeId, Long userId) {
        // 检查食谱是否存在
        Recipe recipe = recipeMapper.selectById(recipeId);
        if (recipe == null) {
            throw new BusinessException("食谱不存在");
        }

        // 检查是否已收藏
        if (isFavorited(recipeId, userId)) {
            throw new BusinessException("已经收藏过了");
        }

        // 创建收藏
        Favorite favorite = new Favorite();
        favorite.setRecipeId(recipeId);
        favorite.setUserId(userId);
        favoriteMapper.insert(favorite);

        // 更新食谱收藏数
        recipe.setFavoriteCount(recipe.getFavoriteCount() + 1);
        recipeMapper.updateById(recipe);

        log.info("用户{}收藏食谱{}成功", userId, recipeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFavorite(Long recipeId, Long userId) {
        // 查询收藏记录
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getRecipeId, recipeId);
        wrapper.eq(Favorite::getUserId, userId);
        Favorite favorite = favoriteMapper.selectOne(wrapper);

        if (favorite == null) {
            throw new BusinessException("未收藏此食谱");
        }

        // 删除收藏
        favoriteMapper.deleteById(favorite.getId());

        // 更新食谱收藏数
        Recipe recipe = recipeMapper.selectById(recipeId);
        if (recipe != null && recipe.getFavoriteCount() > 0) {
            recipe.setFavoriteCount(recipe.getFavoriteCount() - 1);
            recipeMapper.updateById(recipe);
        }

        log.info("用户{}取消收藏食谱{}成功", userId, recipeId);
    }

    @Override
    public boolean isFavorited(Long recipeId, Long userId) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getRecipeId, recipeId);
        wrapper.eq(Favorite::getUserId, userId);
        return favoriteMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Page<RecipeVO> getMyFavorites(Long userId, Integer current, Integer size) {
        // 查询收藏的食谱ID列表
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId);
        wrapper.orderByDesc(Favorite::getCreateTime);

        Page<Favorite> page = new Page<>(current, size);
        Page<Favorite> favoritePage = favoriteMapper.selectPage(page, wrapper);

        // 查询食谱详情
        List<Long> recipeIds = favoritePage.getRecords().stream()
                .map(Favorite::getRecipeId)
                .collect(Collectors.toList());

        Page<RecipeVO> voPage = new Page<>(favoritePage.getCurrent(), favoritePage.getSize(), favoritePage.getTotal());
        
        if (!recipeIds.isEmpty()) {
            List<RecipeVO> recipeVOs = recipeIds.stream()
                    .map(recipeService::getRecipeDetail)
                    .collect(Collectors.toList());
            voPage.setRecords(recipeVOs);
        }

        return voPage;
    }

}
