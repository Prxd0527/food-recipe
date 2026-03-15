package com.food.recipe.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 小说生成请求
 * 用于生成第一人称叙事的小说片段
 */
@Data
@ApiModel("小说生成请求")
public class NovelGenerateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "故事背景或世界观", required = true, example = "废墟中的未来城市")
    @NotBlank(message = "故事背景不能为空")
    private String background;

    @ApiModelProperty(value = "主角姓名", required = true, example = "阿黎")
    @NotBlank(message = "主角姓名不能为空")
    private String protagonistName;

    @ApiModelProperty(value = "主角性格/经历", required = true, example = "倔强、善于倾听")
    @NotBlank(message = "主角性格或经历不能为空")
    private String protagonistTrait;

    @ApiModelProperty(value = "小说类型，如爱情、悬疑、奇幻", required = true, example = "悬疑")
    @NotBlank(message = "小说类型不能为空")
    private String genre;

    @ApiModelProperty(value = "关键词列表（JSON数组），用于情节或意象提示", example = "[\"旧日记\",\"雾气\",\"远方的车站\"]")
    private List<String> keywords;

    @ApiModelProperty(value = "情节提示，可选", example = "我在寻找失踪的朋友")
    private String plotHint;
}
