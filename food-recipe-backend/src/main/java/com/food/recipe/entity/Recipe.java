package com.food.recipe.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 食谱实体类
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@TableName("recipe")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食谱ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发布者ID
     */
    private Long userId;

    /**
     * 食谱标题
     */
    private String title;

    /**
     * 简介
     */
    private String description;

    /**
     * 封面图URL
     */
    private String coverImage;

    /**
     * 菜系
     */
    private String cuisineType;

    /**
     * 难度：EASY-简单，MEDIUM-中等，HARD-困难
     */
    private String difficulty;

    /**
     * 烹饪时长（分钟）
     */
    private Integer cookingTime;

    /**
     * 份量（人数）
     */
    private Integer servings;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 收藏次数
     */
    private Integer favoriteCount;

    /**
     * 评论次数
     */
    private Integer commentCount;

    /**
     * 状态：DRAFT-草稿，PENDING-待审核，PUBLISHED-已发布，REJECTED-已拒绝
     */
    private String status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除：0-否，1-是
     */
    @TableLogic
    private Integer isDeleted;

}
