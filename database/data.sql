-- ============================
-- 美食食谱分享系统 - 初始化数据
-- 创建时间: 2025-12-07
-- 创建者: prxd0527
-- ============================

USE `food_recipe`;

-- ============================
-- 1. 插入用户数据
-- ============================
-- 所有账号密码统一为：123456
-- BCrypt加密后：$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe
INSERT INTO `user` (`username`, `password`, `nickname`, `email`, `bio`, `role`, `status`) VALUES
('admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe', '系统管理员', 'admin@food.com', '美食食谱平台管理员，致力于分享美食文化', 'ADMIN', 1),
('user1', '$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe', '美食达人小王', 'user1@food.com', '热爱烹饪，专注川菜家常菜研究', 'USER', 1),
('user2', '$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe', '厨艺新星小李', 'user2@food.com', '西点烘焙爱好者，甜品控一枚', 'USER', 1),
('user3', '$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe', '粤菜大师阿明', 'user3@food.com', '广东老饕，粤菜传承人', 'USER', 1),
('user4', '$2a$10$EixZaYVK1fsbw1ZfbX3OXe.PaVvFbtyAKs4hfVZ6g2f.lGWXXVHGe', '素食主义者小美', 'user4@food.com', '素食主义践行者，健康饮食推广者', 'USER', 1);

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
-- 5. 插入食谱数据
-- ============================

-- 食谱1：家常红烧肉
INSERT INTO `recipe` (`user_id`, `title`, `description`, `cuisine_type`, `difficulty`, `cooking_time`, `servings`, `view_count`, `favorite_count`, `comment_count`, `status`) VALUES
(2, '家常红烧肉', '色泽红润，软糯香甜，肥而不腻的经典家常菜。传统做法，入口即化，是一道老少皆宜的美味佳肴。', '家常菜', 'MEDIUM', 60, 4, 1250, 328, 45, 'PUBLISHED');
SET @recipe1_id = LAST_INSERT_ID();

INSERT INTO `recipe_ingredient` (`recipe_id`, `name`, `amount`, `sort_order`) VALUES
(@recipe1_id, '五花肉', '500克', 1),
(@recipe1_id, '冰糖', '30克', 2),
(@recipe1_id, '生姜', '3片', 3),
(@recipe1_id, '大葱', '2段', 4),
(@recipe1_id, '八角', '2个', 5),
(@recipe1_id, '桂皮', '1小块', 6),
(@recipe1_id, '料酒', '2勺', 7),
(@recipe1_id, '生抽', '3勺', 8),
(@recipe1_id, '老抽', '1勺', 9);

INSERT INTO `recipe_step` (`recipe_id`, `step_number`, `content`) VALUES
(@recipe1_id, 1, '<p>五花肉洗净，切成2cm见方的块状，冷水下锅，加入姜片和料酒焯水去腥。</p>'),
(@recipe1_id, 2, '<p>焯好的肉块捞出，用温水冲洗干净，沥干水分备用。</p>'),
(@recipe1_id, 3, '<p>锅中不放油，直接放入肉块，小火慢煎至表面微黄出油。</p>'),
(@recipe1_id, 4, '<p>加入冰糖，小火炒至冰糖融化，肉块上色。</p>'),
(@recipe1_id, 5, '<p>加入热水没过肉块，放入葱段、姜片、八角、桂皮，大火烧开。</p>'),
(@recipe1_id, 6, '<p>转小火炖煮40分钟，加入生抽、老抽调味，继续炖煮15分钟。</p>'),
(@recipe1_id, 7, '<p>大火收汁，汤汁浓稠即可出锅装盘，撒上葱花点缀。</p>');

-- 食谱2：麻婆豆腐
INSERT INTO `recipe` (`user_id`, `title`, `description`, `cuisine_type`, `difficulty`, `cooking_time`, `servings`, `view_count`, `favorite_count`, `comment_count`, `status`) VALUES
(2, '麻婆豆腐', '经典川菜代表作，麻辣鲜香，豆腐嫩滑。传承百年的四川名菜，下饭神器！', '川菜', 'EASY', 20, 2, 982, 256, 38, 'PUBLISHED');
SET @recipe2_id = LAST_INSERT_ID();

INSERT INTO `recipe_ingredient` (`recipe_id`, `name`, `amount`, `sort_order`) VALUES
(@recipe2_id, '嫩豆腐', '1块', 1),
(@recipe2_id, '猪肉末', '100克', 2),
(@recipe2_id, '郫县豆瓣酱', '2勺', 3),
(@recipe2_id, '花椒粉', '1茶匙', 4),
(@recipe2_id, '蒜末', '适量', 5),
(@recipe2_id, '葱花', '适量', 6);

INSERT INTO `recipe_step` (`recipe_id`, `step_number`, `content`) VALUES
(@recipe2_id, 1, '<p>豆腐切成2cm的方块，放入淡盐水中浸泡10分钟。</p>'),
(@recipe2_id, 2, '<p>锅中放油，爆香蒜末，加入猪肉末炒散炒熟。</p>'),
(@recipe2_id, 3, '<p>加入郫县豆瓣酱炒出红油，倒入适量清水。</p>'),
(@recipe2_id, 4, '<p>放入豆腐块，轻轻推动，中火煮5分钟。</p>'),
(@recipe2_id, 5, '<p>水淀粉勾芡，撒上花椒粉和葱花即可。</p>');

-- 食谱3：白灼虾
INSERT INTO `recipe` (`user_id`, `title`, `description`, `cuisine_type`, `difficulty`, `cooking_time`, `servings`, `view_count`, `favorite_count`, `comment_count`, `status`) VALUES
(4, '白灼虾', '粤菜经典做法，保留虾的原汁原味，鲜甜可口。配上特制酱油蘸料，简单又美味！', '粤菜', 'EASY', 15, 3, 756, 189, 22, 'PUBLISHED');
SET @recipe3_id = LAST_INSERT_ID();

INSERT INTO `recipe_ingredient` (`recipe_id`, `name`, `amount`, `sort_order`) VALUES
(@recipe3_id, '基围虾', '500克', 1),
(@recipe3_id, '姜片', '3片', 2),
(@recipe3_id, '葱段', '2根', 3),
(@recipe3_id, '料酒', '1勺', 4),
(@recipe3_id, '生抽', '3勺', 5),
(@recipe3_id, '蒜末', '适量', 6);

INSERT INTO `recipe_step` (`recipe_id`, `step_number`, `content`) VALUES
(@recipe3_id, 1, '<p>虾剪去虾须，挑去虾线，清洗干净。</p>'),
(@recipe3_id, 2, '<p>锅中水烧开，加入姜片、葱段、料酒。</p>'),
(@recipe3_id, 3, '<p>放入虾，煮至变红卷曲即可捞出。</p>'),
(@recipe3_id, 4, '<p>生抽加蒜末调成蘸料，即可上桌。</p>');

-- 食谱4：蛋炒饭
INSERT INTO `recipe` (`user_id`, `title`, `description`, `cuisine_type`, `difficulty`, `cooking_time`, `servings`, `view_count`, `favorite_count`, `comment_count`, `status`) VALUES
(3, '黄金蛋炒饭', '粒粒分明的黄金蛋炒饭，简单快手又美味。隔夜米饭的最佳归宿！', '家常菜', 'EASY', 10, 1, 2156, 567, 89, 'PUBLISHED');
SET @recipe4_id = LAST_INSERT_ID();

INSERT INTO `recipe_ingredient` (`recipe_id`, `name`, `amount`, `sort_order`) VALUES
(@recipe4_id, '隔夜米饭', '1碗', 1),
(@recipe4_id, '鸡蛋', '2个', 2),
(@recipe4_id, '葱花', '适量', 3),
(@recipe4_id, '盐', '适量', 4);

INSERT INTO `recipe_step` (`recipe_id`, `step_number`, `content`) VALUES
(@recipe4_id, 1, '<p>米饭提前打散，鸡蛋打散备用。</p>'),
(@recipe4_id, 2, '<p>热锅多油，倒入蛋液快速翻炒成碎蛋。</p>'),
(@recipe4_id, 3, '<p>加入米饭，大火快速翻炒，使米粒分开。</p>'),
(@recipe4_id, 4, '<p>加盐调味，撒上葱花即可出锅。</p>');

-- 食谱5：提拉米苏
INSERT INTO `recipe` (`user_id`, `title`, `description`, `cuisine_type`, `difficulty`, `cooking_time`, `servings`, `view_count`, `favorite_count`, `comment_count`, `status`) VALUES
(3, '经典提拉米苏', '意大利经典甜点，咖啡与奶酪的完美结合。入口即化，回味无穷的甜蜜体验。', '甜品', 'MEDIUM', 120, 6, 1567, 423, 56, 'PUBLISHED');
SET @recipe5_id = LAST_INSERT_ID();

INSERT INTO `recipe_ingredient` (`recipe_id`, `name`, `amount`, `sort_order`) VALUES
(@recipe5_id, '马斯卡彭奶酪', '250克', 1),
(@recipe5_id, '手指饼干', '200克', 2),
(@recipe5_id, '浓缩咖啡', '200ml', 3),
(@recipe5_id, '鸡蛋', '3个', 4),
(@recipe5_id, '细砂糖', '80克', 5),
(@recipe5_id, '可可粉', '适量', 6);

INSERT INTO `recipe_step` (`recipe_id`, `step_number`, `content`) VALUES
(@recipe5_id, 1, '<p>蛋黄加糖打发至浓稠发白。</p>'),
(@recipe5_id, 2, '<p>加入马斯卡彭奶酪搅拌均匀。</p>'),
(@recipe5_id, 3, '<p>蛋白打发，分次拌入蛋黄奶酪糊。</p>'),
(@recipe5_id, 4, '<p>手指饼干蘸咖啡，铺入容器底部。</p>'),
(@recipe5_id, 5, '<p>铺一层奶酪糊，再铺一层饼干，重复两次。</p>'),
(@recipe5_id, 6, '<p>冷藏4小时以上，食用前撒可可粉。</p>');

-- 食谱6：凉拌黄瓜
INSERT INTO `recipe` (`user_id`, `title`, `description`, `cuisine_type`, `difficulty`, `cooking_time`, `servings`, `view_count`, `favorite_count`, `comment_count`, `status`) VALUES
(5, '爽口凉拌黄瓜', '清脆爽口的夏日开胃菜，酸辣可口，5分钟搞定！健康低脂的素食选择。', '素菜', 'EASY', 5, 2, 1890, 445, 67, 'PUBLISHED');
SET @recipe6_id = LAST_INSERT_ID();

INSERT INTO `recipe_ingredient` (`recipe_id`, `name`, `amount`, `sort_order`) VALUES
(@recipe6_id, '黄瓜', '2根', 1),
(@recipe6_id, '蒜末', '适量', 2),
(@recipe6_id, '生抽', '2勺', 3),
(@recipe6_id, '香醋', '1勺', 4),
(@recipe6_id, '香油', '少许', 5),
(@recipe6_id, '辣椒油', '适量', 6);

INSERT INTO `recipe_step` (`recipe_id`, `step_number`, `content`) VALUES
(@recipe6_id, 1, '<p>黄瓜洗净，用刀拍散，切成小块。</p>'),
(@recipe6_id, 2, '<p>加入蒜末、生抽、香醋、香油、辣椒油。</p>'),
(@recipe6_id, 3, '<p>拌匀后腌制5分钟即可食用。</p>');

-- ============================
-- 6. 插入评论数据
-- ============================
INSERT INTO `comment` (`recipe_id`, `user_id`, `content`, `rating`) VALUES
(@recipe1_id, 3, '按照这个方法做出来真的很好吃！肉软烂入味，家人都说好！', 5),
(@recipe1_id, 4, '火候控制得好的话，确实肥而不腻，收藏了！', 5),
(@recipe1_id, 5, '第一次做红烧肉就成功了，太开心了！', 4),
(@recipe2_id, 3, '正宗川味麻婆豆腐，麻辣鲜香！', 5),
(@recipe2_id, 5, '豆瓣酱放多了有点咸，下次注意用量', 4),
(@recipe3_id, 2, '简单又美味，虾很新鲜的话这样做最好吃！', 5),
(@recipe3_id, 5, '蘸料很重要，我又加了点辣椒', 4),
(@recipe4_id, 2, '米饭一定要用隔夜饭才能炒出粒粒分明的效果！', 5),
(@recipe4_id, 4, '加点火腿丁更好吃', 5),
(@recipe4_id, 5, '简单又快手，上班族的最爱', 4),
(@recipe5_id, 2, '冷藏一夜后口感更好！和外面买的一样好吃', 5),
(@recipe5_id, 4, '第一次做甜品就成功了，太有成就感！', 5),
(@recipe6_id, 2, '夏天必备的开胃菜！', 5),
(@recipe6_id, 3, '清爽可口，减脂期的最爱', 5);

-- ============================
-- 7. 插入收藏数据
-- ============================
INSERT INTO `favorite` (`user_id`, `recipe_id`) VALUES
(2, @recipe3_id),
(2, @recipe5_id),
(3, @recipe1_id),
(3, @recipe2_id),
(3, @recipe6_id),
(4, @recipe1_id),
(4, @recipe4_id),
(4, @recipe5_id),
(5, @recipe2_id),
(5, @recipe4_id),
(5, @recipe6_id);

-- ============================
-- 8. 关联食谱标签
-- ============================
INSERT INTO `recipe_tag` (`recipe_id`, `tag_id`) VALUES
(@recipe1_id, (SELECT id FROM `tag` WHERE `name` = '家常菜' AND `type` = 'CUISINE')),
(@recipe1_id, (SELECT id FROM `tag` WHERE `name` = '五香' AND `type` = 'FLAVOR')),
(@recipe2_id, (SELECT id FROM `tag` WHERE `name` = '川菜' AND `type` = 'CUISINE')),
(@recipe2_id, (SELECT id FROM `tag` WHERE `name` = '麻辣' AND `type` = 'FLAVOR')),
(@recipe3_id, (SELECT id FROM `tag` WHERE `name` = '粤菜' AND `type` = 'CUISINE')),
(@recipe3_id, (SELECT id FROM `tag` WHERE `name` = '清淡' AND `type` = 'FLAVOR')),
(@recipe4_id, (SELECT id FROM `tag` WHERE `name` = '家常菜' AND `type` = 'CUISINE')),
(@recipe4_id, (SELECT id FROM `tag` WHERE `name` = '咸香' AND `type` = 'FLAVOR')),
(@recipe5_id, (SELECT id FROM `tag` WHERE `name` = '甜品' AND `type` = 'CUISINE')),
(@recipe5_id, (SELECT id FROM `tag` WHERE `name` = '奶香' AND `type` = 'FLAVOR')),
(@recipe6_id, (SELECT id FROM `tag` WHERE `name` = '素菜' AND `type` = 'CUISINE')),
(@recipe6_id, (SELECT id FROM `tag` WHERE `name` = '酸辣' AND `type` = 'FLAVOR'));

-- ============================
-- 数据初始化完成
-- ============================
-- 所有账号密码统一为：123456
-- 管理员账号：admin / 123456
-- 测试用户：user1~user4 / 123456
-- 注意：生产环境请修改默认密码！
-- ============================
