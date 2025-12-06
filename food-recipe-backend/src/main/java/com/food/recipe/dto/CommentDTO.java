package com.food.recipe.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 评论DTO
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@ApiModel("评论请求")
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论内容", required = true)
    @NotBlank(message = "评论内容不能为空")
    private String content;

    @ApiModelProperty("评分（1-5星）")
    @Min(value = 1, message = "评分最低1星")
    @Max(value = 5, message = "评分最高5星")
    private Integer rating;

}
