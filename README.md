# 🍳 美食食谱分享系统

> 一个面向美食爱好者的内容分享平台，聚焦于食谱发布、评论互动和收藏功能。

## 📦 技术栈

| 前端 | 后端 | 数据库 |
|------|------|--------|
| Vue 3 + TypeScript | Spring Boot 2.7.18 | MySQL 8.0+ |
| Pinia + Vue Router | MyBatis-Plus | - |
| Element Plus | Spring Security + JWT | - |
| Vite | Knife4j (OpenAPI3) | - |

---

## 🚀 快速开始

### 环境要求

- **Node.js** 16+
- **JDK** 8 或 11
- **MySQL** 8.0+
- **Maven** 3.6+

### 1. 克隆项目

```bash
git clone <repository-url>
```

### 2. 配置数据库

```bash
# 登录MySQL
mysql -u root -p

# 执行数据库脚本
source database/schema.sql
source database/data.sql
```

### 3. 配置后端

编辑 `food-recipe-backend/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/food_recipe?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username:        # MySQL用户名
    password:     # MySQL密码

file:
  upload-path:   # 上传目录
```

### 4. 启动后端

```bash
cd food-recipe-backend
mvn spring-boot:run
```

后端启动成功后访问：
- **API地址**: http://localhost:8080
- **API文档**: http://localhost:8080/doc.html

### 5. 启动前端

```bash
cd food-recipe-frontend
npm install
npm run dev
```

前端启动成功后访问：
- **应用地址**: http://localhost:5173

---

## 🔑 测试账号

| 账号 | 密码 | 角色 |
|------|------|------|
| admin | 123456 | 管理员 |
| user1 | 123456 | 普通用户 |

---

## 📁 项目结构

```
food/
├── database/                    # 数据库脚本
│   ├── schema.sql              # 表结构
│   └── data.sql                # 初始数据
├── food-recipe-backend/         # Spring Boot 后端
│   └── src/main/java/com/food/recipe/
│       ├── controller/         # 控制器
│       ├── service/            # 业务逻辑
│       ├── entity/             # 实体类
│       ├── dto/                # 数据传输对象
│       ├── vo/                 # 视图对象
│       └── config/             # 配置类
├── food-recipe-frontend/        # Vue3 前端
│   └── src/
│       ├── views/              # 页面组件
│       ├── components/         # 通用组件
│       ├── api/                # API接口
│       ├── store/              # Pinia状态
│       └── router/             # 路由配置
├── upload/                      # 文件上传目录
├── PROGRESS.md                  # 开发进度
└── README.md                    # 本文件
```

---

## 🌟 核心功能

### 用户功能
- ✅ 用户注册、登录
- ✅ 个人中心（修改昵称、邮箱、头像、简介）
- ✅ 浏览食谱列表（搜索、筛选）
- ✅ 查看食谱详情
- ✅ 发布/编辑/删除食谱
- ✅ 收藏/取消收藏食谱
- ✅ 发表/删除评论

### 管理员功能
- ✅ 删除任意评论
- ⏳ 食谱审核（待实现）
- ⏳ 用户管理（待实现）

---

## 🔌 API 接口概览

### 认证接口
```
POST /api/auth/register     用户注册
POST /api/auth/login        用户登录
```

### 用户接口
```
GET  /api/user/info         获取用户信息
PUT  /api/user/info         更新用户信息
```

### 食谱接口
```
POST   /api/recipes              创建食谱
GET    /api/recipes              获取食谱列表
GET    /api/recipes/{id}         获取食谱详情
PUT    /api/recipes/{id}         更新食谱
DELETE /api/recipes/{id}         删除食谱
POST   /api/recipes/{id}/publish 发布食谱
GET    /api/recipes/my           我的食谱
```

### 评论接口
```
POST   /api/recipes/{id}/comments  发表评论
GET    /api/recipes/{id}/comments  获取评论列表
DELETE /api/comments/{id}          删除评论
```

### 收藏接口
```
POST   /api/favorites/{recipeId}       收藏食谱
DELETE /api/favorites/{recipeId}       取消收藏
GET    /api/favorites                  我的收藏
GET    /api/favorites/check/{recipeId} 检查是否已收藏
```

### 文件上传
```
POST /api/upload/image    上传图片
```

### 小说生成器（第一人称）
```
POST /api/novels/generate  生成第一人称小说片段
```
**请求示例**
```json
{
  "background": "废墟中的未来城市",
  "protagonistName": "阿黎",
  "protagonistTrait": "顽强且敏感",
  "genre": "悬疑",
  "keywords": ["旧日记", "雾气", "远方的车站"],
  "plotHint": "我在追踪失踪朋友留下的线索"
}
```

---

## 🛠️ 生产环境部署

### 1. 构建前端

```bash
cd food-recipe-frontend
npm run build
```

生成的 `dist` 目录可部署到 Nginx 或其他静态服务器。

### 2. 构建后端

```bash
cd food-recipe-backend
mvn clean package -DskipTests
```

生成的 `target/food-recipe-backend-1.0.0.jar` 可直接运行：

```bash
java -jar food-recipe-backend-1.0.0.jar --spring.profiles.active=prod
```

### 3. Nginx 配置示例

```nginx
server {
    listen 80;
    server_name your-domain.com;

    # 前端静态文件
    location / {
        root /var/www/food-recipe-frontend/dist;
        try_files $uri $uri/ /index.html;
    }

    # API 反向代理
    location /api/ {
        proxy_pass http://localhost:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    # 图片访问
    location /upload/ {
        alias /var/www/upload/;
    }
}
```

---

## ⚠️ 注意事项

1. **安全配置**：生产环境请修改 `SecurityConfig.java`，启用接口权限验证
2. **图片存储**：生产环境建议使用云存储（如阿里云OSS）
3. **密码安全**：请修改默认密码和JWT密钥
4. **CORS配置**：生产环境请限制允许的域名

---

## 📝 更新日志

### v1.0.0 (2025-12-07)
- ✅ 完成用户认证模块
- ✅ 完成食谱管理模块
- ✅ 完成评论系统
- ✅ 完成收藏功能
- ✅ 完成个人中心
- ✅ 完成UI美化
- ✅ 完成搜索功能

---
