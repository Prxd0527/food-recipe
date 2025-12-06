package com.food.recipe.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论VO
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@ApiModel("评论信息")
public class CommentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论ID")
    private Long id;

    @ApiModelProperty("食谱ID")
    private Long recipeId;

    @ApiModelProperty("评论者ID")
    private Long userId;

    @ApiModelProperty("评论者昵称")
    private String userNickname;

    @ApiModelProperty("评论者头像")
    private String userAvatar;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评分")
    private Integer rating;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

}
