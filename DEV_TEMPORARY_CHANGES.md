# 开发阶段临时修改清单

**创建时间**: 2025-12-07
**目的**: 记录开发/测试阶段暂时禁用的功能，生产环境部署前需要恢复

---

## 1. Spring Security 安全配置已放开

**文件位置**: `food-recipe-backend/src/main/java/com/food/recipe/config/SecurityConfig.java`

**修改内容**: 
- 原本有严格的权限控制，现在改为 `.anyRequest().permitAll()` 允许所有请求

**恢复方法**:
将 `filterChain` 方法中的配置恢复为：
```java
.authorizeRequests()
.antMatchers("/api/auth/**").permitAll()
.antMatchers("/api/recipes/**").permitAll()  // 食谱列表公开
.antMatchers("/doc.html", "/webjars/**", "/swagger-resources/**", 
             "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
.antMatchers("/upload/**").permitAll()
.anyRequest().authenticated()  // 其他接口需要认证
```

---

## 2. 食谱列表状态过滤已注释

**文件位置**: `food-recipe-backend/src/main/java/com/food/recipe/service/impl/RecipeServiceImpl.java`

**修改行数**: 约156-157行

**修改内容**:
```java
// 开发阶段：暂时注释掉状态过滤，显示所有食谱
// wrapper.eq(Recipe::getStatus, Constants.RECIPE_STATUS_PUBLISHED);
```

**恢复方法**:
取消注释，只显示已发布的食谱：
```java
// 只查询已发布的食谱
wrapper.eq(Recipe::getStatus, Constants.RECIPE_STATUS_PUBLISHED);
```

---

## 3. 测试工具Controller

**文件位置**: `food-recipe-backend/src/main/java/com/food/recipe/controller/TestController.java`

**说明**: 这是为了调试BCrypt密码而创建的测试接口

**恢复方法**: 
生产环境部署前应**删除此文件**，或在SecurityConfig中移除其白名单：
```java
.antMatchers("/api/test/**").permitAll()  // 删除这行
```

---

## 恢复检查清单

部署生产环境前，请完成以下检查：

- [ ] 恢复 SecurityConfig 权限控制
- [ ] 取消注释 RecipeServiceImpl 中的状态过滤
- [ ] 删除或禁用 TestController
- [ ] 更新 application.yml 中的数据库密码为生产环境密码
- [ ] 更新 JWT 密钥为更强的随机字符串
- [ ] 关闭 MyBatis SQL 日志输出
