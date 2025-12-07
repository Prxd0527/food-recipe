package com.food.recipe.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * 用户信息更新DTO
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@ApiModel("用户信息更新请求")
public class UserUpdateDTO {

    @Size(min = 2, max = 20, message = "昵称长度在2-20个字符")
    @ApiModelProperty("昵称")
    private String nickname;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像URL")
    private String avatar;

    @Size(max = 200, message = "个人简介不能超过200个字符")
    @ApiModelProperty("个人简介")
    private String bio;

}
