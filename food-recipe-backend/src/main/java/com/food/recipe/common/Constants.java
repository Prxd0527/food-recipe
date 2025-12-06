package com.food.recipe.common;

/**
 * 常量定义
 * @author prxd0527
 * @date 2025-12-07
 */
public class Constants {

    /**
     * JWT相关常量
     */
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";
    public static final String JWT_USER_ID = "userId";
    public static final String JWT_USERNAME = "username";
    public static final String JWT_ROLE = "role";

    /**
     * 用户角色
     */
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    /**
     * 食谱状态
     */
    public static final String RECIPE_STATUS_DRAFT = "DRAFT";           // 草稿
    public static final String RECIPE_STATUS_PENDING = "PENDING";       // 待审核
    public static final String RECIPE_STATUS_PUBLISHED = "PUBLISHED";   // 已发布
    public static final String RECIPE_STATUS_REJECTED = "REJECTED";     // 已拒绝

    /**
     * 难度级别
     */
    public static final String DIFFICULTY_EASY = "EASY";       // 简单
    public static final String DIFFICULTY_MEDIUM = "MEDIUM";   // 中等
    public static final String DIFFICULTY_HARD = "HARD";       // 困难

    /**
     * 标签类型
     */
    public static final String TAG_TYPE_CUISINE = "CUISINE";       // 菜系
    public static final String TAG_TYPE_FLAVOR = "FLAVOR";         // 口味
    public static final String TAG_TYPE_DIFFICULTY = "DIFFICULTY"; // 难度

    /**
     * 图片相关
     */
    public static final String IMAGE_DIR = "images";
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif"};

}
