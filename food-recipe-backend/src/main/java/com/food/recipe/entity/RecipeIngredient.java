package com.food.recipe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 食材实体类
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@TableName("recipe_ingredient")
public class RecipeIngredient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食材ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 食谱ID
     */
    private Long recipeId;

    /**
     * 食材名称
     */
    private String name;

    /**
     * 用量
     */
    private String amount;

    /**
     * 排序
     */
    private Integer sortOrder;

}
