package com.food.recipe.controller;

import com.food.recipe.common.Result;
import com.food.recipe.dto.UserUpdateDTO;
import com.food.recipe.entity.User;
import com.food.recipe.utils.JwtUtil;
import com.food.recipe.vo.UserVO;
import com.food.recipe.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户控制器
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/info")
    @ApiOperation("获取当前用户信息")
    public Result<UserVO> getUserInfo(@RequestHeader("Authorization") String authorization) {
        Long userId = getUserIdFromHeader(authorization);
        UserVO userVO = userService.getUserVO(userId);
        return Result.success(userVO);
    }

    @PutMapping("/info")
    @ApiOperation("更新用户信息")
    public Result<UserVO> updateUserInfo(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody UserUpdateDTO updateDTO) {
        Long userId = getUserIdFromHeader(authorization);
        
        // 获取当前用户
        User user = userService.getUserById(userId);
        
        // 更新用户信息
        if (updateDTO.getNickname() != null) {
            user.setNickname(updateDTO.getNickname());
        }
        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getAvatar() != null) {
            user.setAvatar(updateDTO.getAvatar());
        }
        if (updateDTO.getBio() != null) {
            user.setBio(updateDTO.getBio());
        }
        
        userService.updateUser(user);
        
        // 返回更新后的用户信息
        UserVO userVO = userService.getUserVO(userId);
        return Result.success(userVO);
    }

    /**
     * 从请求头中获取用户ID
     */
    private Long getUserIdFromHeader(String authorization) {
        String token = authorization.replace("Bearer ", "");
        return jwtUtil.getUserIdFromToken(token);
    }

}
