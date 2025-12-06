package com.food.recipe.controller;

import com.food.recipe.common.Result;
import com.food.recipe.dto.LoginDTO;
import com.food.recipe.dto.RegisterDTO;
import com.food.recipe.service.UserService;
import com.food.recipe.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@Api(tags = "用户认证")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<Void> register(@Validated @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return Result.success("注册成功", null);
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = userService.login(loginDTO);
        return Result.success("登录成功", loginVO);
    }

}
