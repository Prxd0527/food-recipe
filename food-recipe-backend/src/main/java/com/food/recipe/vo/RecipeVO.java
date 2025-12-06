package com.food.recipe.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 食谱VO
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@ApiModel("食谱信息")
public class RecipeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("食谱ID")
    private Long id;

    @ApiModelProperty("发布者ID")
    private Long userId;

    @ApiModelProperty("发布者昵称")
    private String userNickname;

    @ApiModelProperty("发布者头像")
    private String userAvatar;

    @ApiModelProperty("食谱标题")
    private String title;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("封面图URL")
    private String coverImage;

    @ApiModelProperty("菜系")
    private String cuisineType;

    @ApiModelProperty("难度")
    private String difficulty;

    @ApiModelProperty("烹饪时长（分钟）")
    private Integer cookingTime;

    @ApiModelProperty("份量（人数）")
    private Integer servings;

    @ApiModelProperty("浏览次数")
    private Integer viewCount;

    @ApiModelProperty("收藏次数")
    private Integer favoriteCount;

    @ApiModelProperty("评论次数")
    private Integer commentCount;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("食材列表")
    private List<IngredientVO> ingredients;

    @ApiModelProperty("步骤列表")
    private List<StepVO> steps;

    @ApiModelProperty("标签列表")
    private List<TagVO> tags;

    /**
     * 食材VO
     */
    @Data
    @ApiModel("食材信息")
    public static class IngredientVO implements Serializable {
        @ApiModelProperty("食材ID")
        private Long id;

        @ApiModelProperty("食材名称")
        private String name;

        @ApiModelProperty("用量")
        private String amount;
    }

    /**
     * 步骤VO
     */
    @Data
    @ApiModel("步骤信息")
    public static class StepVO implements Serializable {
        @ApiModelProperty("步骤ID")
        private Long id;

        @ApiModelProperty("步骤序号")
        private Integer stepNumber;

        @ApiModelProperty("步骤内容")
        private String content;

        @ApiModelProperty("步骤配图URL")
        private String imageUrl;
    }

    /**
     * 标签VO
     */
    @Data
    @ApiModel("标签信息")
    public static class TagVO implements Serializable {
        @ApiModelProperty("标签ID")
        private Long id;

        @ApiModelProperty("标签名称")
        private String name;

        @ApiModelProperty("标签类型")
        private String type;
    }

}
