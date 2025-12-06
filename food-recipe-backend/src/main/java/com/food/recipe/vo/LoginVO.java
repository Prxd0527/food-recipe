package com.food.recipe.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录响应VO
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("登录响应")
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("JWT Token")
    private String token;

    @ApiModelProperty("用户信息")
    private UserVO user;

}
