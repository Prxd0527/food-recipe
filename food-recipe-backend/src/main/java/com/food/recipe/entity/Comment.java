package com.food.recipe.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论实体类
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 食谱ID
     */
    private Long recipeId;

    /**
     * 评论者ID
     */
    private Long userId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评分：1-5星
     */
    private Integer rating;

    /**
     * 评论图片URL（JSON数组）
     */
    private String imageUrls;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 是否删除：0-否，1-是
     */
    @TableLogic
    private Integer isDeleted;

}
