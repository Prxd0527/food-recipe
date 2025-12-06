package com.food.recipe.controller;

import com.food.recipe.common.Result;
import com.food.recipe.utils.JwtUtil;
import com.food.recipe.vo.UserVO;
import com.food.recipe.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        // 从Token中获取用户ID
        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        UserVO userVO = userService.getUserVO(userId);
        return Result.success(userVO);
    }

}
