package com.food.recipe.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回结果
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@ApiModel("分页返回结果")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("数据列表")
    private List<T> records;

    @ApiModelProperty("当前页码")
    private Long current;

    @ApiModelProperty("每页大小")
    private Long size;

    @ApiModelProperty("总页数")
    private Long pages;

    public PageResult() {
    }

    public PageResult(Long total, List<T> records, Long current, Long size) {
        this.total = total;
        this.records = records;
        this.current = current;
        this.size = size;
        this.pages = (total + size - 1) / size;
    }

}
