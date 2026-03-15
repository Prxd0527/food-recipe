package com.food.recipe.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 小说生成结果
 */
@Data
@ApiModel("小说生成结果")
public class NovelGenerateResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("章节标题")
    private String title;

    @ApiModelProperty("完整小说内容，按段落拼接")
    private String content;

    @ApiModelProperty("分段内容")
    private List<String> paragraphs;
}
