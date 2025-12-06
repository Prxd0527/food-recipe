package com.food.recipe.service;

import com.food.recipe.dto.LoginDTO;
import com.food.recipe.dto.RegisterDTO;
import com.food.recipe.entity.User;
import com.food.recipe.vo.LoginVO;
import com.food.recipe.vo.UserVO;

/**
 * 用户Service接口
 * @author prxd0527
 * @date 2025-12-07
 */
public interface UserService {

    /**
     * 用户注册
     */
    void register(RegisterDTO registerDTO);

    /**
     * 用户登录
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);

    /**
     * 根据ID查询用户
     */
    User getUserById(Long id);

    /**
     * 获取用户信息VO
     */
    UserVO getUserVO(Long userId);

    /**
     * 更新用户信息
     */
    void updateUser(User user);

}
