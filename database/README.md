# 数据库初始化指南

## 创建者
prxd0527

## 数据库信息
- 数据库名称：`food_recipe`
- 字符集：`utf8mb4`
- 排序规则：`utf8mb4_unicode_ci`

## 初始化步骤

### 方法一：使用MySQL命令行工具

1. 打开MySQL命令行或任何MySQL客户端（如Navicat、DBeaver、MySQL Workbench等）

2. 执行表结构脚本：
```bash
# 方式1：使用命令行
mysql -u root -p < database/schema.sql

# 方式2：在MySQL命令行中执行
mysql> source D:/akaifa/food/database/schema.sql;
```

3. 执行初始化数据脚本：
```bash
# 方式1：使用命令行
mysql -u root -p < database/data.sql

# 方式2：在MySQL命令行中执行
mysql> source D:/akaifa/food/database/data.sql;
```

###方法二：使用MySQL客户端工具（推荐）

1. 打开Navicat/DBeaver/MySQL Workbench等工具

2. 连接到MySQL服务器（localhost:3306）
   - 用户名：root
   - 密码：123456

3. 打开并执行 `schema.sql` 文件
   - 这将创建数据库和所有表结构

4. 打开并执行 `data.sql` 文件
   - 这将插入基础数据和测试数据

### 方法三：手动复制粘贴

1. 打开 `database/schema.sql` 文件

2. 复制全部内容到MySQL客户端查询窗口

3. 执行SQL

4. 重复以上步骤执行 `database/data.sql`

## 初始化数据说明

### 默认账号

#### 管理员账号
- 用户名：`admin`
- 密码：`admin123`
- 角色：ADMIN

#### 测试普通用户
- 用户1：`user1` / `user123`
- 用户2：`user2` / `user123`

### 基础标签

系统已预置以下标签：

#### 菜系标签
川菜、粤菜、湘菜、鲁菜、浙菜、闽菜、徽菜、苏菜、西餐、日韩料理、东南亚菜、西北菜、家常菜、快手菜、烘焙、甜品、素菜、海鲜

#### 口味标签
麻辣、清淡、香甜、酸辣、咸鲜、微辣、重辣、酸甜、咸香、蒜香、五香、孜然、椒盐、酱香、奶香、果香

#### 难度标签
新手、简单、中等、较难、困难、大师级

### 示例食谱

系统已预置一个示例食谱：**家常红烧肉**，包含完整的食材和步骤，可用于测试功能。

## 验证数据库初始化

执行以下SQL验证数据库是否正确初始化：

```sql
-- 1. 验证数据库是否创建
SHOW DATABASES LIKE 'food_recipe';

-- 2. 使用数据库
USE food_recipe;

-- 3. 查看所有表
SHOW TABLES;

-- 4. 验证用户表数据
SELECT * FROM user;

-- 5. 验证标签数据
SELECT * FROM tag;

-- 6. 验证示例食谱
SELECT * FROM recipe;
```

预期结果：
- 应该有 8 张表
- user 表应该有 3 条记录（1个管理员 + 2个测试用户）
- tag 表应该有 40 条标签记录
- recipe 表应该有 1 条示例食谱

## 常见问题

### 1. 密码错误
如果MySQL密码不是 `123456`，请修改初始化脚本或使用正确的密码连接。

### 2. 数据库已存在
如果数据库 `food_recipe` 已存在，脚本会先删除再创建。请注意备份重要数据！

### 3. 权限问题
确保MySQL用户有创建数据库和表的权限。

## 下一步

数据库初始化完成后，可以：
1. 启动后端服务：进入 `food-recipe-backend` 目录，运行 `mvn spring-boot:run`
2. 访问API文档：http://localhost:8080/doc.html
3. 启动前端服务：进入 `food-recipe-frontend` 目录，运行 `npm run dev`
4. 访问前端页面：http://localhost:5173
