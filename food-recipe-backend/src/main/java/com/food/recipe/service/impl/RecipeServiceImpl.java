package com.food.recipe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.recipe.common.Constants;
import com.food.recipe.dto.RecipeDTO;
import com.food.recipe.entity.*;
import com.food.recipe.exception.BusinessException;
import com.food.recipe.mapper.*;
import com.food.recipe.service.RecipeService;
import com.food.recipe.service.UserService;
import com.food.recipe.vo.RecipeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 食谱Service实现类
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private RecipeIngredientMapper ingredientMapper;

    @Autowired
    private RecipeStepMapper stepMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRecipe(RecipeDTO recipeDTO, Long userId) {
        // 创建食谱基本信息
        Recipe recipe = new Recipe();
        BeanUtils.copyProperties(recipeDTO, recipe);
        recipe.setUserId(userId);
        recipe.setStatus(Constants.RECIPE_STATUS_DRAFT);
        recipe.setViewCount(0);
        recipe.setFavoriteCount(0);
        recipe.setCommentCount(0);

        recipeMapper.insert(recipe);

        // 保存食材
        saveIngredients(recipe.getId(), recipeDTO.getIngredients());

        // 保存步骤
        saveSteps(recipe.getId(), recipeDTO.getSteps());

        log.info("用户{}创建食谱成功，食谱ID:{}", userId, recipe.getId());
        return recipe.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRecipe(RecipeDTO recipeDTO, Long userId) {
        // 检查食谱是否存在
        Recipe recipe = recipeMapper.selectById(recipeDTO.getId());
        if (recipe == null) {
            throw new BusinessException("食谱不存在");
        }

        // 检查权限（只能修改自己的食谱）
        if (!recipe.getUserId().equals(userId)) {
            throw new BusinessException("无权限修改此食谱");
        }

        // 更新食谱基本信息
        Recipe updateRecipe = new Recipe();
        BeanUtils.copyProperties(recipeDTO, updateRecipe);
        recipeMapper.updateById(updateRecipe);

        // 删除旧的食材和步骤
        ingredientMapper.delete(new LambdaQueryWrapper<RecipeIngredient>()
                .eq(RecipeIngredient::getRecipeId, recipe.getId()));
        stepMapper.delete(new LambdaQueryWrapper<RecipeStep>()
                .eq(RecipeStep::getRecipeId, recipe.getId()));

        // 保存新的食材和步骤
        saveIngredients(recipe.getId(), recipeDTO.getIngredients());
        saveSteps(recipe.getId(), recipeDTO.getSteps());

        log.info("用户{}更新食谱成功，食谱ID:{}", userId, recipe.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRecipe(Long id, Long userId) {
        Recipe recipe = recipeMapper.selectById(id);
        if (recipe == null) {
            throw new BusinessException("食谱不存在");
        }

        if (!recipe.getUserId().equals(userId)) {
            throw new BusinessException("无权限删除此食谱");
        }

        recipeMapper.deleteById(id);
        log.info("用户{}删除食谱成功，食谱ID:{}", userId, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishRecipe(Long id, Long userId) {
        Recipe recipe = recipeMapper.selectById(id);
        if (recipe == null) {
            throw new BusinessException("食谱不存在");
        }

        if (!recipe.getUserId().equals(userId)) {
            throw new BusinessException("无权限发布此食谱");
        }

        // 直接设为已发布状态（如需审核可改为 RECIPE_STATUS_PENDING）
        recipe.setStatus(Constants.RECIPE_STATUS_PUBLISHED);
        recipeMapper.updateById(recipe);

        log.info("用户{}发布食谱成功，食谱ID:{}", userId, id);
    }

    @Override
    public RecipeVO getRecipeDetail(Long id) {
        Recipe recipe = recipeMapper.selectById(id);
        if (recipe == null) {
            throw new BusinessException("食谱不存在");
        }

        RecipeVO recipeVO = buildRecipeVO(recipe);
        return recipeVO;
    }

    @Override
    public Page<RecipeVO> getRecipeList(Integer current, Integer size, String keyword,
                                         String cuisineType, String difficulty, Long tagId) {
        Page<Recipe> page = new Page<>(current, size);
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        
        // 只显示已发布的食谱
        wrapper.eq(Recipe::getStatus, Constants.RECIPE_STATUS_PUBLISHED);

        // 关键词搜索（标题或简介）
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Recipe::getTitle, keyword)
                    .or()
                    .like(Recipe::getDescription, keyword));
        }

        // 菜系筛选
        if (StringUtils.hasText(cuisineType)) {
            wrapper.eq(Recipe::getCuisineType, cuisineType);
        }

        // 难度筛选
        if (StringUtils.hasText(difficulty)) {
            wrapper.eq(Recipe::getDifficulty, difficulty);
        }

        // 按创建时间降序
        wrapper.orderByDesc(Recipe::getCreateTime);

        Page<Recipe> recipePage = recipeMapper.selectPage(page, wrapper);

        // 转换为VO
        Page<RecipeVO> voPage = new Page<>(recipePage.getCurrent(), recipePage.getSize(), recipePage.getTotal());
        List<RecipeVO> voList = recipePage.getRecords().stream()
                .map(this::buildRecipeVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public Page<RecipeVO> getMyRecipes(Integer current, Integer size, Long userId) {
        Page<Recipe> page = new Page<>(current, size);
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Recipe::getUserId, userId);
        wrapper.orderByDesc(Recipe::getCreateTime);

        Page<Recipe> recipePage = recipeMapper.selectPage(page, wrapper);

        // 转换为VO
        Page<RecipeVO> voPage = new Page<>(recipePage.getCurrent(), recipePage.getSize(), recipePage.getTotal());
        List<RecipeVO> voList = recipePage.getRecords().stream()
                .map(this::buildRecipeVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseViewCount(Long id) {
        Recipe recipe = recipeMapper.selectById(id);
        if (recipe != null) {
            recipe.setViewCount(recipe.getViewCount() + 1);
            recipeMapper.updateById(recipe);
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.selectList(null);
    }

    @Override
    public List<Tag> getTagsByType(String type) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getType, type);
        return tagMapper.selectList(wrapper);
    }

    /**
     * 保存食材
     */
    private void saveIngredients(Long recipeId, List<RecipeDTO.IngredientDTO> ingredientDTOs) {
        if (CollectionUtils.isEmpty(ingredientDTOs)) {
            return;
        }

        for (int i = 0; i < ingredientDTOs.size(); i++) {
            RecipeDTO.IngredientDTO dto = ingredientDTOs.get(i);
            RecipeIngredient ingredient = new RecipeIngredient();
            ingredient.setRecipeId(recipeId);
            ingredient.setName(dto.getName());
            ingredient.setAmount(dto.getAmount());
            ingredient.setSortOrder(i + 1);
            ingredientMapper.insert(ingredient);
        }
    }

    /**
     * 保存步骤
     */
    private void saveSteps(Long recipeId, List<RecipeDTO.StepDTO> stepDTOs) {
        if (CollectionUtils.isEmpty(stepDTOs)) {
            return;
        }

        for (int i = 0; i < stepDTOs.size(); i++) {
            RecipeDTO.StepDTO dto = stepDTOs.get(i);
            RecipeStep step = new RecipeStep();
            step.setRecipeId(recipeId);
            step.setStepNumber(i + 1);
            step.setContent(dto.getContent());
            step.setImageUrl(dto.getImageUrl());
            stepMapper.insert(step);
        }
    }

    /**
     * 构建RecipeVO
     */
    private RecipeVO buildRecipeVO(Recipe recipe) {
        RecipeVO vo = new RecipeVO();
        BeanUtils.copyProperties(recipe, vo);

        // 查询用户信息
        User user = userService.getUserById(recipe.getUserId());
        if (user != null) {
            vo.setUserNickname(user.getNickname());
            vo.setUserAvatar(user.getAvatar());
        }

        // 查询食材
        List<RecipeIngredient> ingredients = ingredientMapper.selectList(
                new LambdaQueryWrapper<RecipeIngredient>()
                        .eq(RecipeIngredient::getRecipeId, recipe.getId())
                        .orderByAsc(RecipeIngredient::getSortOrder)
        );
        vo.setIngredients(ingredients.stream().map(ing -> {
            RecipeVO.IngredientVO ingVO = new RecipeVO.IngredientVO();
            ingVO.setId(ing.getId());
            ingVO.setName(ing.getName());
            ingVO.setAmount(ing.getAmount());
            return ingVO;
        }).collect(Collectors.toList()));

        // 查询步骤
        List<RecipeStep> steps = stepMapper.selectList(
                new LambdaQueryWrapper<RecipeStep>()
                        .eq(RecipeStep::getRecipeId, recipe.getId())
                        .orderByAsc(RecipeStep::getStepNumber)
        );
        vo.setSteps(steps.stream().map(step -> {
            RecipeVO.StepVO stepVO = new RecipeVO.StepVO();
            stepVO.setId(step.getId());
            stepVO.setStepNumber(step.getStepNumber());
            stepVO.setContent(step.getContent());
            stepVO.setImageUrl(step.getImageUrl());
            return stepVO;
        }).collect(Collectors.toList()));

        // TODO: 查询标签（需要recipe_tag关联表支持）
        vo.setTags(new ArrayList<>());

        return vo;
    }

}
