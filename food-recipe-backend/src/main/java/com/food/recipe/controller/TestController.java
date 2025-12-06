package com.food.recipe.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试工具控制器（仅用于开发调试）
 * @author prxd0527
 * @date 2025-12-07
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 生成BCrypt密码哈希
     */
    @GetMapping("/bcrypt")
    public Map<String, String> generateBcrypt(@RequestParam String password) {
        Map<String, String> result = new HashMap<>();
        result.put("password", password);
        result.put("bcrypt", passwordEncoder.encode(password));
        return result;
    }

    /**
     * 验证密码
     */
    @GetMapping("/verify")
    public Map<String, Object> verifyPassword(@RequestParam String password, @RequestParam String hash) {
        Map<String, Object> result = new HashMap<>();
        result.put("password", password);
        result.put("hash", hash);
        result.put("matches", passwordEncoder.matches(password, hash));
        return result;
    }

}
