# 美食食谱分享系统（轻量版）

## 项目概括
本项目旨在开发一个面向美食爱好者的内容分享平台，聚焦于食谱发布、评论互动和收藏功能。系统采用前后端分离架构，前端使用Vue3生态，后端基于Spring Boot，提供RESTful API服务。

## 技术选型

### 前端技术栈
- **核心框架**: Vue 3.x
- **状态管理**: Pinia
- **路由管理**: Vue Router 4.x
- **开发语言**: TypeScript
- **UI框架**: Element Plus (可选 Ant Design Vue)
- **富文本编辑器**: Quill / TinyMCE
- **HTTP客户端**: Axios
- **构建工具**: Vite
- **图片处理**: vue-cropper (图片裁剪), element-plus (图片预览)

### 后端技术栈
- **核心框架**: Spring Boot 2.7.x / 3.x
- **ORM框架**: MyBatis-Plus (推荐) / Spring Data JPA
- **数据库**: MySQL 8.0+
- **缓存**: Redis (可选)
- **认证授权**: Spring Security + JWT
- **API文档**: Knife4j / Springdoc OpenAPI
- **文件存储**: 本地存储 / 阿里云OSS (可扩展)
- **日志管理**: Logback
- **数据校验**: Hibernate Validator

### 开发工具
- **版本控制**: Git
- **接口测试**: Postman / Apifox
- **数据库管理**: Navicat / DBeaver
- **开发IDE**: IntelliJ IDEA (后端), VSCode (前端)

## 项目结构 / 模块划分

### 前端项目结构
```
food-recipe-frontend/
├── public/                      # 静态资源
├── src/
│   ├── api/                     # API接口定义
│   │   ├── recipe.ts           # 食谱API
│   │   ├── comment.ts          # 评论API
│   │   ├── favorite.ts         # 收藏API
│   │   └── user.ts             # 用户API
│   ├── assets/                 # 资源文件
│   │   ├── images/
│   │   └── styles/
│   ├── components/             # 公共组件
│   │   ├── RecipeCard.vue     # 食谱卡片
│   │   ├── CommentList.vue    # 评论列表
│   │   ├── RichTextEditor.vue # 富文本编辑器
│   │   └── ImageUpload.vue    # 图片上传
│   ├── views/                  # 页面视图
│   │   ├── Home.vue           # 首页
│   │   ├── recipe/            # 食谱相关页面
│   │   │   ├── RecipeList.vue
│   │   │   ├── RecipeDetail.vue
│   │   │   ├── RecipeEdit.vue
│   │   │   └── RecipeCreate.vue
│   │   ├── user/              # 用户相关页面
│   │   │   ├── Profile.vue
│   │   │   ├── MyRecipes.vue
│   │   │   └── MyFavorites.vue
│   │   └── admin/             # 管理员页面
│   │       ├── RecipeManage.vue
│   │       └── CommentManage.vue
│   ├── router/                 # 路由配置
│   │   └── index.ts
│   ├── store/                  # Pinia状态管理
│   │   ├── user.ts            # 用户状态
│   │   ├── recipe.ts          # 食谱状态
│   │   └── app.ts             # 应用全局状态
│   ├── utils/                  # 工具函数
│   │   ├── request.ts         # HTTP请求封装
│   │   ├── auth.ts            # 认证工具
│   │   └── validate.ts        # 表单验证
│   ├── types/                  # TypeScript类型定义
│   │   ├── recipe.ts
│   │   ├── comment.ts
│   │   ├── user.ts
│   │   └── common.ts
│   ├── App.vue
│   └── main.ts
├── package.json
├── tsconfig.json
└── vite.config.ts
```

### 后端项目结构
```
food-recipe-backend/
├── src/main/java/com/food/recipe/
│   ├── FoodRecipeApplication.java
│   ├── config/                  # 配置类
│   │   ├── SecurityConfig.java # 安全配置
│   │   ├── MybatisConfig.java  # MyBatis配置
│   │   ├── CorsConfig.java     # 跨域配置
│   │   └── SwaggerConfig.java  # API文档配置
│   ├── controller/              # 控制器层
│   │   ├── RecipeController.java
│   │   ├── CommentController.java
│   │   ├── FavoriteController.java
│   │   └── UserController.java
│   ├── service/                 # 业务逻辑层
│   │   ├── RecipeService.java
│   │   ├── CommentService.java
│   │   ├── FavoriteService.java
│   │   └── UserService.java
│   ├── mapper/                  # 数据访问层
│   │   ├── RecipeMapper.java
│   │   ├── CommentMapper.java
│   │   ├── FavoriteMapper.java
│   │   └── UserMapper.java
│   ├── entity/                  # 实体类
│   │   ├── Recipe.java         # 食谱
│   │   ├── RecipeStep.java     # 食谱步骤
│   │   ├── RecipeIngredient.java # 食材
│   │   ├── Comment.java        # 评论
│   │   ├── Favorite.java       # 收藏
│   │   ├── User.java           # 用户
│   │   └── Tag.java            # 标签
│   ├── dto/                     # 数据传输对象
│   │   ├── RecipeDTO.java
│   │   ├── CommentDTO.java
│   │   └── UserDTO.java
│   ├── vo/                      # 视图对象
│   │   ├── RecipeVO.java
│   │   └── CommentVO.java
│   ├── common/                  # 公共类
│   │   ├── Result.java         # 统一返回结果
│   │   ├── PageResult.java     # 分页结果
│   │   └── Constants.java      # 常量定义
│   ├── enums/                   # 枚举类
│   │   ├── RecipeStatus.java  # 食谱状态
│   │   ├── UserRole.java      # 用户角色
│   │   └── TagType.java       # 标签类型
│   ├── exception/               # 异常处理
│   │   ├── GlobalExceptionHandler.java
│   │   └── BusinessException.java
│   └── interceptor/             # 拦截器
│       └── AuthInterceptor.java
├── src/main/resources/
│   ├── mapper/                  # MyBatis XML映射文件
│   ├── application.yml          # 应用配置
│   └── application-dev.yml      # 开发环境配置
└── pom.xml
```

## 核心功能 / 模块详解

### 1. 食谱发布管理模块

#### 普通用户功能：
- **新增食谱**：
  - 填写食谱基本信息（名称、简介、菜系、口味标签）
  - 添加食材列表（名称、用量）
  - 编辑步骤说明（支持富文本，插入图片）
  - 上传封面图和步骤配图
  - 保存草稿/发布
  
- **修改食谱**：
  - 编辑自己发布的食谱
  - 更新食材、步骤、图片
  
- **删除食谱**：
  - 下架自己的食谱（软删除）
  
- **查询食谱**：
  - 查看自己的食谱列表
  - 浏览全站食谱（支持按标签、菜系筛选）
  - 搜索食谱（按名称、食材）

#### 管理员功能：
- **查询所有食谱**：分页查看所有用户的食谱
- **删除违规食谱**：删除不符合规范的内容
- **修改审核状态**：审核通过/拒绝

### 2. 食谱评论管理模块

#### 普通用户功能：
- **新增评论**：
  - 对食谱发表评论
  - 支持评分（1-5星）
  - 上传评论图片（可选）
  
- **删除评论**：
  - 删除自己的评论
  
- **查询评论**：
  - 查看自己的评论历史
  - 查看食谱下的所有评论

#### 管理员功能：
- **删除违规评论**：删除不当言论
- **查询所有评论**：分页查看所有评论

### 3. 食谱收藏管理模块

#### 普通用户功能：
- **收藏食谱**：
  - 点击收藏按钮添加到收藏夹
  - 支持分类收藏（可选功能）
  
- **取消收藏**：
  - 从收藏列表移除
  
- **查询收藏列表**：
  - 查看自己收藏的所有食谱
  - 支持筛选和排序

#### 管理员功能：
- 无操作权限

## 数据模型

### 用户表 (user)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| username | VARCHAR(50) | 用户名（唯一） |
| password | VARCHAR(255) | 密码（加密） |
| nickname | VARCHAR(50) | 昵称 |
| avatar | VARCHAR(255) | 头像URL |
| email | VARCHAR(100) | 邮箱 |
| role | VARCHAR(20) | 角色（USER/ADMIN） |
| status | TINYINT | 状态（0禁用/1正常） |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### 食谱表 (recipe)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 发布者ID |
| title | VARCHAR(100) | 食谱标题 |
| description | TEXT | 简介 |
| cover_image | VARCHAR(255) | 封面图 |
| cuisine_type | VARCHAR(50) | 菜系（川菜/粤菜等） |
| difficulty | VARCHAR(20) | 难度（简单/中等/困难） |
| cooking_time | INT | 烹饪时长（分钟） |
| servings | INT | 份量 |
| view_count | INT | 浏览次数 |
| favorite_count | INT | 收藏次数 |
| comment_count | INT | 评论次数 |
| status | VARCHAR(20) | 状态（DRAFT/PUBLISHED/REJECTED） |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| is_deleted | TINYINT | 是否删除（0否/1是） |

### 食材表 (recipe_ingredient)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| recipe_id | BIGINT | 食谱ID |
| name | VARCHAR(50) | 食材名称 |
| amount | VARCHAR(50) | 用量 |
| sort_order | INT | 排序 |

### 步骤表 (recipe_step)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| recipe_id | BIGINT | 食谱ID |
| step_number | INT | 步骤序号 |
| content | TEXT | 步骤内容（富文本） |
| image_url | VARCHAR(255) | 步骤配图 |

### 标签表 (tag)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(50) | 标签名称 |
| type | VARCHAR(20) | 类型（CUISINE/FLAVOR） |
| create_time | DATETIME | 创建时间 |

### 食谱标签关联表 (recipe_tag)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| recipe_id | BIGINT | 食谱ID |
| tag_id | BIGINT | 标签ID |

### 评论表 (comment)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| recipe_id | BIGINT | 食谱ID |
| user_id | BIGINT | 评论者ID |
| content | TEXT | 评论内容 |
| rating | TINYINT | 评分（1-5星） |
| image_urls | VARCHAR(500) | 评论图片（JSON数组） |
| create_time | DATETIME | 创建时间 |
| is_deleted | TINYINT | 是否删除（0否/1是） |

### 收藏表 (favorite)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| recipe_id | BIGINT | 食谱ID |
| create_time | DATETIME | 收藏时间 |

## RESTful API 设计规范

### 食谱相关接口
```
POST   /api/recipes              创建食谱
GET    /api/recipes              查询食谱列表（分页、筛选）
GET    /api/recipes/{id}         查询食谱详情
PUT    /api/recipes/{id}         更新食谱
DELETE /api/recipes/{id}         删除食谱
GET    /api/recipes/my           查询我的食谱
POST   /api/recipes/{id}/publish 发布食谱
```

### 评论相关接口
```
POST   /api/recipes/{recipeId}/comments   创建评论
GET    /api/recipes/{recipeId}/comments   查询食谱评论列表
DELETE /api/comments/{id}                 删除评论
GET    /api/comments/my                   查询我的评论
```

### 收藏相关接口
```
POST   /api/favorites/{recipeId}   收藏食谱
DELETE /api/favorites/{recipeId}   取消收藏
GET    /api/favorites              查询我的收藏列表
GET    /api/favorites/check/{recipeId}  检查是否已收藏
```

### 用户相关接口
```
POST   /api/auth/register   用户注册
POST   /api/auth/login      用户登录
POST   /api/auth/logout     用户登出
GET    /api/user/info       获取当前用户信息
PUT    /api/user/profile    更新用户资料
```

### 管理员接口
```
GET    /api/admin/recipes           查询所有食谱（含审核状态）
PUT    /api/admin/recipes/{id}/audit 审核食谱
DELETE /api/admin/recipes/{id}      删除违规食谱
GET    /api/admin/comments          查询所有评论
DELETE /api/admin/comments/{id}     删除违规评论
```

## 技术实现细节

### ✅ 已确认的技术选型
- **UI框架**：Element Plus
- **富文本编辑器**：Quill.js（推荐理由：轻量、API简洁、中文文档完善、社区活跃）
- **数据库ORM**：MyBatis-Plus（提供强大的CRUD功能和代码生成器）
- **图片存储**：本地存储（上传到服务器的upload目录）
- **Spring Boot版本**：2.7.18（推荐理由：稳定、生态成熟、兼容性好）
- **Git提交规范**：每完成一个功能模块提交一次，使用中文描述

### 富文本编辑器实现（Quill.js）
- 使用 `vue-quill` 或 `@vueup/vue-quill` Vue3封装
- 支持图片上传和粘贴
- 自定义工具栏：加粗、斜体、下划线、列表、图片、链接等
- 配置图片处理器，上传到服务器
- 内容以HTML格式存储到数据库

### 图片上传与预览
- 前端：Element Plus 的 `el-upload` 组件
- 支持多图上传（封面图、步骤配图、评论图片）
- 图片格式限制：jpg/png/gif，大小限制：5MB
- 预览功能：`el-image` + `el-image-viewer`
- 后端：MultipartFile接收，保存到本地目录
- 返回图片访问URL

### 标签分类系统
- **菜系标签**：川菜、粤菜、湘菜、鲁菜、浙菜、闽菜、徽菜、苏菜、西餐、日韩料理等
- **口味标签**：麻辣、清淡、香甜、酸辣、咸鲜、微辣、重辣等
- **难度标签**：简单、中等、困难
- 支持多标签筛选和组合查询
- 标签管理：管理员可添加/编辑标签

### 权限控制实现
- **JWT Token 认证**：
  - 登录成功后返回token
  - 前端存储在localStorage
  - 每次请求在header中携带token
- **前端路由守卫**：
  - beforeEach检查token有效性
  - 未登录跳转登录页
  - 管理员页面额外检查角色
- **后端接口权限注解**：
  - 使用Spring Security + JWT
  - 自定义注解 `@RequireRole`
  - 拦截器验证token和角色权限

### 本地图片存储方案
- 上传目录：`/upload/images/`（按日期分文件夹）
- 文件命名：UUID + 原始文件扩展名
- 静态资源映射：Spring Boot配置静态资源访问路径
- 访问URL：`http://localhost:8080/upload/images/2025/12/07/xxx.jpg`

[后续开发过程中会持续补充各模块的具体实现细节]

## 开发状态跟踪

| 模块/功能 | 状态 | 负责人 | 计划完成日期 | 实际完成日期 | 备注 |
|----------|------|--------|-------------|-------------|------|
| 项目初始化 | 未开始 | AI | 2025-12-07 | | 创建前后端项目骨架 |
| 数据库设计 | 未开始 | AI | 2025-12-07 | | 设计并创建数据库表 |
| 用户认证模块 | 未开始 | AI | 2025-12-07 | | 注册、登录、权限控制 |
| 食谱发布模块（后端） | 未开始 | AI | 2025-12-08 | | CRUD接口 |
| 食谱发布模块（前端） | 未开始 | AI | 2025-12-08 | | 发布、编辑页面 |
| 食谱评论模块（后端） | 未开始 | AI | 2025-12-09 | | 评论CRUD接口 |
| 食谱评论模块（前端） | 未开始 | AI | 2025-12-09 | | 评论列表、发表评论 |
| 食谱收藏模块（后端） | 未开始 | AI | 2025-12-09 | | 收藏接口 |
| 食谱收藏模块（前端） | 未开始 | AI | 2025-12-09 | | 收藏列表、收藏按钮 |
| 管理员功能模块 | 未开始 | AI | 2025-12-10 | | 审核、管理界面 |
| 富文本编辑器集成 | 未开始 | AI | 2025-12-10 | | Quill/TinyMCE |
| 图片上传功能 | 未开始 | AI | 2025-12-10 | | 上传、预览、裁剪 |
| 标签分类系统 | 未开始 | AI | 2025-12-10 | | 标签管理、筛选 |
| 系统测试 | 未开始 | AI | 2025-12-11 | | 功能测试、接口测试 |
| 文档完善 | 未开始 | AI | 2025-12-11 | | API文档、部署文档 |

## 代码检查与问题记录
[本部分用于记录代码检查结果和开发过程中遇到的问题及其解决方案]

## 部署指南
[待开发完成后补充]

### 开发环境要求
- Node.js 16+
- JDK 8+ / JDK 11+
- MySQL 8.0+
- Maven 3.6+

### 本地开发启动步骤
[待补充]

## 项目亮点总结
1. ✅ 完整的前后端分离架构
2. ✅ TypeScript 提供类型安全
3. ✅ RESTful API 设计规范
4. ✅ 完善的权限控制体系
5. ✅ 富文本编辑器支持
6. ✅ 图片上传与预览功能
7. ✅ 灵活的标签分类系统
8. ✅ 响应式UI设计

## 后续扩展方向
- 🔄 引入 Redis 缓存提升性能
- 🔄 添加搜索引擎（Elasticsearch）
- 🔄 实现社交功能（关注、点赞）
- 🔄 集成第三方登录（微信、QQ）
- 🔄 移动端适配（响应式/小程序）
- 🔄 推荐算法（基于标签的协同过滤）
