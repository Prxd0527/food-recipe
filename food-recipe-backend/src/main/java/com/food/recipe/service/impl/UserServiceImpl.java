package com.food.recipe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.food.recipe.common.Constants;
import com.food.recipe.dto.LoginDTO;
import com.food.recipe.dto.RegisterDTO;
import com.food.recipe.entity.User;
import com.food.recipe.exception.BusinessException;
import com.food.recipe.mapper.UserMapper;
import com.food.recipe.service.UserService;
import com.food.recipe.utils.JwtUtil;
import com.food.recipe.vo.LoginVO;
import com.food.recipe.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户Service实现类
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        User existUser = getUserByUsername(registerDTO.getUsername());
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setNickname(registerDTO.getNickname());
        user.setEmail(registerDTO.getEmail());
        user.setRole(Constants.ROLE_USER);
        user.setStatus(1);

        int result = userMapper.insert(user);
        if (result == 0) {
            throw new BusinessException("注册失败");
        }

        log.info("用户注册成功：{}", user.getUsername());
    }

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 查询用户
        User user = getUserByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 验证密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }

        // 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

        // 构建返回数据
        UserVO userVO = getUserVO(user.getId());
        LoginVO loginVO = new LoginVO(token, userVO);

        log.info("用户登录成功：{}", user.getUsername());
        return loginVO;
    }

    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public UserVO getUserVO(Long userId) {
        User user = getUserById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        int result = userMapper.updateById(user);
        if (result == 0) {
            throw new BusinessException("更新失败");
        }
    }

}
