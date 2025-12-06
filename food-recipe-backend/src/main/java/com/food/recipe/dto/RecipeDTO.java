package com.food.recipe.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 食谱创建/更新DTO
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@ApiModel("食谱创建/更新请求")
public class RecipeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("食谱ID（更新时必填）")
    private Long id;

    @ApiModelProperty(value = "食谱标题", required = true)
    @NotBlank(message = "食谱标题不能为空")
    @Size(max = 100, message = "标题长度不能超过100个字符")
    private String title;

    @ApiModelProperty("简介")
    @Size(max = 500, message = "简介长度不能超过500个字符")
    private String description;

    @ApiModelProperty("封面图URL")
    private String coverImage;

    @ApiModelProperty("菜系")
    private String cuisineType;

    @ApiModelProperty("难度：EASY-简单，MEDIUM-中等，HARD-困难")
    private String difficulty;

    @ApiModelProperty("烹饪时长（分钟）")
    private Integer cookingTime;

    @ApiModelProperty("份量（人数）")
    private Integer servings;

    @ApiModelProperty(value = "食材列表", required = true)
    @NotNull(message = "食材列表不能为空")
    private List<IngredientDTO> ingredients;

    @ApiModelProperty(value = "步骤列表", required = true)
    @NotNull(message = "步骤列表不能为空")
    private List<StepDTO> steps;

    @ApiModelProperty("标签ID列表")
    private List<Long> tagIds;

    /**
     * 食材DTO
     */
    @Data
    @ApiModel("食材")
    public static class IngredientDTO implements Serializable {
        @ApiModelProperty(value = "食材名称", required = true)
        @NotBlank(message = "食材名称不能为空")
        private String name;

        @ApiModelProperty("用量")
        private String amount;
    }

    /**
     * 步骤DTO
     */
    @Data
    @ApiModel("步骤")
    public static class StepDTO implements Serializable {
        @ApiModelProperty(value = "步骤内容", required = true)
        @NotBlank(message = "步骤内容不能为空")
        private String content;

        @ApiModelProperty("步骤配图URL")
        private String imageUrl;
    }

}
