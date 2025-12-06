-- ============================
-- 美食食谱分享系统 - 初始化数据
-- 创建时间: 2025-12-07
-- 创建者: prxd0527
-- ============================

USE `food_recipe`;

-- ============================
-- 1. 插入管理员账号
-- ============================
-- 默认密码：123456
-- BCrypt加密后：$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe
INSERT INTO `user` (`username`, `password`, `nickname`, `role`, `status`) VALUES
('admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe', '系统管理员', 'ADMIN', 1);

-- ============================
-- 2. 插入基础标签 - 菜系分类
-- ============================
INSERT INTO `tag` (`name`, `type`) VALUES
('川菜', 'CUISINE'),
('粤菜', 'CUISINE'),
('湘菜', 'CUISINE'),
('鲁菜', 'CUISINE'),
('浙菜', 'CUISINE'),
('闽菜', 'CUISINE'),
('徽菜', 'CUISINE'),
('苏菜', 'CUISINE'),
('西餐', 'CUISINE'),
('日韩料理', 'CUISINE'),
('东南亚菜', 'CUISINE'),
('西北菜', 'CUISINE'),
('家常菜', 'CUISINE'),
('快手菜', 'CUISINE'),
('烘焙', 'CUISINE'),
('甜品', 'CUISINE'),
('素菜', 'CUISINE'),
('海鲜', 'CUISINE');

-- ============================
-- 3. 插入基础标签 - 口味分类
-- ============================
INSERT INTO `tag` (`name`, `type`) VALUES
('麻辣', 'FLAVOR'),
('清淡', 'FLAVOR'),
('香甜', 'FLAVOR'),
('酸辣', 'FLAVOR'),
('咸鲜', 'FLAVOR'),
('微辣', 'FLAVOR'),
('重辣', 'FLAVOR'),
('酸甜', 'FLAVOR'),
('咸香', 'FLAVOR'),
('蒜香', 'FLAVOR'),
('五香', 'FLAVOR'),
('孜然', 'FLAVOR'),
('椒盐', 'FLAVOR'),
('酱香', 'FLAVOR'),
('奶香', 'FLAVOR'),
('果香', 'FLAVOR');

-- ============================
-- 4. 插入基础标签 - 难度分类
-- ============================
INSERT INTO `tag` (`name`, `type`) VALUES
('新手', 'DIFFICULTY'),
('简单', 'DIFFICULTY'),
('中等', 'DIFFICULTY'),
('较难', 'DIFFICULTY'),
('困难', 'DIFFICULTY'),
('大师级', 'DIFFICULTY');

-- ============================
-- 5. 插入测试用普通用户（可选）
-- ============================
-- 测试用户密码均为：123456
-- BCrypt加密后：$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe
INSERT INTO `user` (`username`, `password`, `nickname`, `role`, `status`) VALUES
('user1', '$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe', '美食达人001', 'USER', 1),
('user2', '$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe', '厨艺新星002', 'USER', 1);

-- ============================
-- 6. 插入示例食谱（可选，用于测试）
-- ============================
INSERT INTO `recipe` (`user_id`, `title`, `description`, `cuisine_type`, `difficulty`, `cooking_time`, `servings`, `status`) VALUES
(2, '家常红烧肉', '色泽红润，软糯香甜，肥而不腻的经典家常菜', '家常菜', 'MEDIUM', 60, 4, 'PUBLISHED');

-- 获取刚插入的食谱ID
SET @recipe_id = LAST_INSERT_ID();

-- 插入示例食材
INSERT INTO `recipe_ingredient` (`recipe_id`, `name`, `amount`, `sort_order`) VALUES
(@recipe_id, '五花肉', '500克', 1),
(@recipe_id, '冰糖', '30克', 2),
(@recipe_id, '生姜', '3片', 3),
(@recipe_id, '大葱', '2段', 4),
(@recipe_id, '八角', '2个', 5),
(@recipe_id, '桂皮', '1小块', 6),
(@recipe_id, '料酒', '2勺', 7),
(@recipe_id, '生抽', '3勺', 8),
(@recipe_id, '老抽', '1勺', 9);

-- 插入示例步骤
INSERT INTO `recipe_step` (`recipe_id`, `step_number`, `content`) VALUES
(@recipe_id, 1, '<p>五花肉洗净，切成2cm见方的块状，冷水下锅，加入姜片和料酒焯水去腥。</p>'),
(@recipe_id, 2, '<p>焯好的肉块捞出，用温水冲洗干净，沥干水分备用。</p>'),
(@recipe_id, 3, '<p>锅中不放油，直接放入肉块，小火慢煎至表面微黄出油。</p>'),
(@recipe_id, 4, '<p>加入冰糖，小火炒至冰糖融化，肉块上色。</p>'),
(@recipe_id, 5, '<p>加入热水没过肉块，放入葱段、姜片、八角、桂皮，大火烧开。</p>'),
(@recipe_id, 6, '<p>转小火炖煮40分钟，加入生抽、老抽调味，继续炖煮15分钟。</p>'),
(@recipe_id, 7, '<p>大火收汁，汤汁浓稠即可出锅装盘，撒上葱花点缀。</p>');

-- 关联标签
INSERT INTO `recipe_tag` (`recipe_id`, `tag_id`) VALUES
(@recipe_id, (SELECT id FROM `tag` WHERE `name` = '家常菜' AND `type` = 'CUISINE')),
(@recipe_id, (SELECT id FROM `tag` WHERE `name` = '咸甜' AND `type` = 'FLAVOR')),
(@recipe_id, (SELECT id FROM `tag` WHERE `name` = '中等' AND `type` = 'DIFFICULTY'));

-- ============================
-- 数据初始化完成
-- ============================
-- 所有账号密码统一为：123456
-- 管理员账号：admin / 123456
-- 测试用户：user1 / 123456, user2 / 123456
-- 注意：生产环境请修改默认密码！
-- ============================
