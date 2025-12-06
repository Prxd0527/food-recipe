package com.food.recipe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 步骤实体类
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@TableName("recipe_step")
public class RecipeStep implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 步骤ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 食谱ID
     */
    private Long recipeId;

    /**
     * 步骤序号
     */
    private Integer stepNumber;

    /**
     * 步骤内容（富文本HTML）
     */
    private String content;

    /**
     * 步骤配图URL
     */
    private String imageUrl;

}
