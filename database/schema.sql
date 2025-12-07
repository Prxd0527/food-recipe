-- ============================
-- 美食食谱分享系统 - 数据库表结构
-- 创建时间: 2025-12-07
-- 创建者: prxd0527
-- ============================

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS `food_recipe` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE `food_recipe`;

-- ============================
-- 1. 用户表
-- ============================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码（加密存储）',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `bio` VARCHAR(200) DEFAULT NULL COMMENT '个人简介',
  `role` VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '角色：USER-普通用户，ADMIN-管理员',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ============================
-- 2. 标签表
-- ============================
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` VARCHAR(50) NOT NULL COMMENT '标签名称',
  `type` VARCHAR(20) NOT NULL COMMENT '标签类型：CUISINE-菜系，FLAVOR-口味，DIFFICULTY-难度',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_type` (`name`, `type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- ============================
-- 3. 食谱表
-- ============================
DROP TABLE IF EXISTS `recipe`;
CREATE TABLE `recipe` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '食谱ID',
  `user_id` BIGINT NOT NULL COMMENT '发布者ID',
  `title` VARCHAR(100) NOT NULL COMMENT '食谱标题',
  `description` TEXT COMMENT '简介',
  `cover_image` VARCHAR(255) DEFAULT NULL COMMENT '封面图URL',
  `cuisine_type` VARCHAR(50) DEFAULT NULL COMMENT '菜系',
  `difficulty` VARCHAR(20) DEFAULT 'EASY' COMMENT '难度：EASY-简单，MEDIUM-中等，HARD-困难',
  `cooking_time` INT DEFAULT NULL COMMENT '烹饪时长（分钟）',
  `servings` INT DEFAULT NULL COMMENT '份量（人数）',
  `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `favorite_count` INT NOT NULL DEFAULT 0 COMMENT '收藏次数',
  `comment_count` INT NOT NULL DEFAULT 0 COMMENT '评论次数',
  `status` VARCHAR(20) NOT NULL DEFAULT 'DRAFT' COMMENT '状态：DRAFT-草稿，PENDING-待审核，PUBLISHED-已发布，REJECTED-已拒绝',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0-否，1-是',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_view_count` (`view_count`),
  KEY `idx_favorite_count` (`favorite_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='食谱表';

-- ============================
-- 4. 食材表
-- ============================
DROP TABLE IF EXISTS `recipe_ingredient`;
CREATE TABLE `recipe_ingredient` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '食材ID',
  `recipe_id` BIGINT NOT NULL COMMENT '食谱ID',
  `name` VARCHAR(50) NOT NULL COMMENT '食材名称',
  `amount` VARCHAR(50) DEFAULT NULL COMMENT '用量',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='食材表';

-- ============================
-- 5. 步骤表
-- ============================
DROP TABLE IF EXISTS `recipe_step`;
CREATE TABLE `recipe_step` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '步骤ID',
  `recipe_id` BIGINT NOT NULL COMMENT '食谱ID',
  `step_number` INT NOT NULL COMMENT '步骤序号',
  `content` TEXT NOT NULL COMMENT '步骤内容（富文本HTML）',
  `image_url` VARCHAR(255) DEFAULT NULL COMMENT '步骤配图URL',
  PRIMARY KEY (`id`),
  KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='步骤表';

-- ============================
-- 6. 食谱标签关联表
-- ============================
DROP TABLE IF EXISTS `recipe_tag`;
CREATE TABLE `recipe_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `recipe_id` BIGINT NOT NULL COMMENT '食谱ID',
  `tag_id` BIGINT NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_recipe_tag` (`recipe_id`, `tag_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='食谱标签关联表';

-- ============================
-- 7. 评论表
-- ============================
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `recipe_id` BIGINT NOT NULL COMMENT '食谱ID',
  `user_id` BIGINT NOT NULL COMMENT '评论者ID',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `rating` TINYINT DEFAULT NULL COMMENT '评分：1-5星',
  `image_urls` VARCHAR(500) DEFAULT NULL COMMENT '评论图片URL（JSON数组）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0-否，1-是',
  PRIMARY KEY (`id`),
  KEY `idx_recipe_id` (`recipe_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ============================
-- 8. 收藏表
-- ============================
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `recipe_id` BIGINT NOT NULL COMMENT '食谱ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_recipe` (`user_id`, `recipe_id`),
  KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';

-- ============================
-- 外键约束（可选）
-- ============================
-- 如果需要严格的外键约束，可以添加以下语句
-- 注意：外键会影响性能，建议在应用层处理关联关系

-- ALTER TABLE `recipe` ADD CONSTRAINT `fk_recipe_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;
-- ALTER TABLE `recipe_ingredient` ADD CONSTRAINT `fk_ingredient_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE;
-- ALTER TABLE `recipe_step` ADD CONSTRAINT `fk_step_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE;
-- ALTER TABLE `recipe_tag` ADD CONSTRAINT `fk_recipe_tag_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE;
-- ALTER TABLE `recipe_tag` ADD CONSTRAINT `fk_recipe_tag_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE;
-- ALTER TABLE `comment` ADD CONSTRAINT `fk_comment_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE;
-- ALTER TABLE `comment` ADD CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;
-- ALTER TABLE `favorite` ADD CONSTRAINT `fk_favorite_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;
-- ALTER TABLE `favorite` ADD CONSTRAINT `fk_favorite_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE;
