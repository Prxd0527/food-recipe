<template>
  <div class="home-container">
    <!-- 顶部导航栏 -->
    <header class="navbar">
      <div class="navbar-content">
        <div class="logo" @click="router.push('/')">
          <span class="logo-icon">🍳</span>
          <span class="logo-text">美食食谱</span>
        </div>

        <nav class="nav-links">
          <router-link to="/" class="nav-link" exact-active-class="active">首页</router-link>
          <router-link to="/recipes" class="nav-link" active-class="active">发现食谱</router-link>
          <template v-if="userStore.userInfo">
            <router-link to="/recipe/create" class="nav-link" active-class="active">发布食谱</router-link>
            <router-link to="/my-recipes" class="nav-link" active-class="active">我的食谱</router-link>
          </template>
        </nav>

        <div class="nav-actions">
          <template v-if="userStore.userInfo">
            <el-dropdown @command="handleCommand" trigger="click">
              <div class="user-avatar">
                <el-avatar :size="36" :src="userAvatarUrl">
                  {{ userStore.userInfo.nickname?.charAt(0) }}
                </el-avatar>
                <span class="username">{{ userStore.userInfo.nickname }}</span>
                <el-icon><ArrowDown /></el-icon>
              </div>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile">
                    <el-icon><User /></el-icon> 个人中心
                  </el-dropdown-item>
                  <el-dropdown-item command="my-recipes">
                    <el-icon><Document /></el-icon> 我的食谱
                  </el-dropdown-item>
                  <el-dropdown-item command="my-favorites">
                    <el-icon><Star /></el-icon> 我的收藏
                  </el-dropdown-item>
                  <el-dropdown-item divided command="logout">
                    <el-icon><SwitchButton /></el-icon> 退出登录
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <el-button round @click="router.push('/login')">登录</el-button>
            <el-button type="primary" round @click="router.push('/register')">注册</el-button>
          </template>
        </div>
      </div>
    </header>

    <!-- 英雄区域 -->
    <section class="hero-section">
      <div class="hero-bg"></div>
      <div class="hero-content">
        <h1 class="hero-title">
          探索美食的
          <span class="gradient-text">无限可能</span>
        </h1>
        <p class="hero-subtitle">
          发现、分享、创作属于你的美食故事<br/>
          与千万美食爱好者一起，开启味蕾之旅
        </p>
        
        <!-- 搜索框 -->
        <div class="hero-search">
          <el-input
            v-model="searchKeyword"
            size="large"
            placeholder="搜索你想要的食谱..."
            @keyup.enter="handleSearch"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
            <template #append>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
            </template>
          </el-input>
        </div>
        
        <div class="hero-actions">
          <el-button type="primary" size="large" round @click="router.push('/recipes')">
            <el-icon><Search /></el-icon>
            探索食谱
          </el-button>
          <el-button size="large" round @click="goToCreateRecipe">
            <el-icon><EditPen /></el-icon>
            发布食谱
          </el-button>
        </div>
        <div class="hero-stats">
          <div class="stat-item">
            <span class="stat-number">1000+</span>
            <span class="stat-label">精选食谱</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-number">500+</span>
            <span class="stat-label">活跃用户</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-number">10000+</span>
            <span class="stat-label">每日浏览</span>
          </div>
        </div>
      </div>
    </section>

    <!-- 特色功能区 -->
    <section class="features-section">
      <div class="section-container">
        <div class="section-header">
          <h2 class="section-title">为什么选择我们</h2>
          <p class="section-subtitle">我们致力于为您提供最优质的美食分享体验</p>
        </div>
        <div class="features-grid">
          <div class="feature-card" @click="goToCreateRecipe">
            <div class="feature-icon" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
              <el-icon :size="32"><EditPen /></el-icon>
            </div>
            <h3 class="feature-title">发布食谱</h3>
            <p class="feature-desc">记录你的拿手好菜，分享独家烹饪秘诀，让更多人品尝你的手艺</p>
          </div>
          <div class="feature-card" @click="router.push('/recipes')">
            <div class="feature-icon" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
              <el-icon :size="32"><ChatDotRound /></el-icon>
            </div>
            <h3 class="feature-title">互动交流</h3>
            <p class="feature-desc">与美食爱好者互动评论，交流烹饪心得，共同成长进步</p>
          </div>
          <div class="feature-card" @click="goToFavorites">
            <div class="feature-icon" style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);">
              <el-icon :size="32"><Star /></el-icon>
            </div>
            <h3 class="feature-title">收藏管理</h3>
            <p class="feature-desc">一键收藏喜欢的食谱，建立你的专属美食库，随时查阅</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 推荐食谱区 -->
    <section class="recipes-section">
      <div class="section-container">
        <div class="section-header">
          <h2 class="section-title">热门推荐</h2>
          <p class="section-subtitle">精选优质食谱，开启你的美食探索之旅</p>
        </div>
        <div class="recipes-grid" v-loading="recipesLoading">
          <div 
            v-for="recipe in recommendedRecipes" 
            :key="recipe.id" 
            class="recipe-card"
            @click="router.push(`/recipe/${recipe.id}`)"
          >
            <div class="recipe-image">
              <img :src="getImageUrl(recipe.coverImage)" :alt="recipe.title" />
              <div class="recipe-overlay">
                <span class="recipe-time">
                  <el-icon><Timer /></el-icon>
                  {{ recipe.cookingTime || 30 }}分钟
                </span>
              </div>
            </div>
            <div class="recipe-info">
              <h3 class="recipe-title">{{ recipe.title }}</h3>
              <p class="recipe-desc">{{ recipe.description }}</p>
              <div class="recipe-meta">
                <div class="recipe-author">
                  <el-avatar :size="24" :src="recipe.userAvatar">
                    {{ recipe.userNickname?.charAt(0) }}
                  </el-avatar>
                  <span>{{ recipe.userNickname }}</span>
                </div>
                <div class="recipe-stats">
                  <span><el-icon><View /></el-icon> {{ recipe.viewCount || 0 }}</span>
                  <span><el-icon><Star /></el-icon> {{ recipe.favoriteCount || 0 }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="recipes-more">
          <el-button type="primary" round size="large" @click="router.push('/recipes')">
            查看更多食谱
            <el-icon><ArrowRight /></el-icon>
          </el-button>
        </div>
      </div>
    </section>

    <!-- 行动号召区 -->
    <section class="cta-section">
      <div class="cta-content">
        <h2 class="cta-title">准备好开始你的美食之旅了吗？</h2>
        <p class="cta-subtitle">加入我们，与千万美食爱好者一起分享烹饪的乐趣</p>
        <el-button type="primary" size="large" round @click="router.push('/register')">
          立即加入
        </el-button>
      </div>
    </section>

    <!-- 页脚 -->
    <footer class="footer">
      <div class="footer-content">
        <div class="footer-brand">
          <span class="logo-icon">🍳</span>
          <span class="logo-text">美食食谱</span>
        </div>
        <p class="footer-copyright">© 2025 美食食谱分享平台. Created by prxd0527</p>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { getRecipeList } from '@/api/recipe'
import { 
  ArrowDown, User, Document, Star, SwitchButton, 
  Search, EditPen, ChatDotRound, Timer, View, ArrowRight 
} from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
import type { Recipe } from '@/types/recipe'

const router = useRouter()
const userStore = useUserStore()

const recommendedRecipes = ref<Recipe[]>([])
const recipesLoading = ref(false)
const searchKeyword = ref('')

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({ path: '/recipes', query: { keyword: searchKeyword.value.trim() } })
  } else {
    router.push('/recipes')
  }
}

const getImageUrl = (url?: string) => {
  if (!url) return 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400'
  if (url.startsWith('http')) return url
  return `http://localhost:8080${url}`
}

const userAvatarUrl = computed(() => {
  const avatar = userStore.userInfo?.avatar
  if (!avatar) return ''
  if (avatar.startsWith('http')) return avatar
  return `http://localhost:8080${avatar}`
})

const loadRecommendedRecipes = async () => {
  recipesLoading.value = true
  try {
    const result = await getRecipeList({ current: 1, size: 6 })
    recommendedRecipes.value = result.records
  } catch (error) {
    console.error('加载推荐食谱失败:', error)
  } finally {
    recipesLoading.value = false
  }
}

const handleCommand = (command: string) => {
  switch (command) {
    case 'profile':
      router.push('/profile')
      break
    case 'my-recipes':
      router.push('/my-recipes')
      break
    case 'my-favorites':
      router.push('/my-favorites')
      break
    case 'logout':
      ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        userStore.logout()
        router.push('/login')
      })
      break
  }
}

const goToCreateRecipe = () => {
  if (userStore.userInfo) {
    router.push('/recipe/create')
  } else {
    router.push('/login')
  }
}

const goToFavorites = () => {
  if (userStore.userInfo) {
    router.push('/my-favorites')
  } else {
    router.push('/login')
  }
}

onMounted(async () => {
  if (userStore.token && !userStore.userInfo) {
    await userStore.fetchUserInfo()
  }
  loadRecommendedRecipes()
})
</script>

<style scoped>
/* ===== 全局变量 ===== */
.home-container {
  --primary-color: #667eea;
  --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  --text-primary: #1a1a2e;
  --text-secondary: #4a5568;
  --text-muted: #718096;
  --bg-primary: #ffffff;
  --bg-secondary: #f7f8fc;
  --border-color: #e2e8f0;
  --shadow-sm: 0 2px 8px rgba(0, 0, 0, 0.06);
  --shadow-md: 0 4px 20px rgba(0, 0, 0, 0.08);
  --shadow-lg: 0 8px 40px rgba(0, 0, 0, 0.12);
  --radius-sm: 8px;
  --radius-md: 12px;
  --radius-lg: 20px;
  --transition: all 0.3s ease;

  min-height: 100vh;
  background: var(--bg-secondary);
}

/* ===== 导航栏 ===== */
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid var(--border-color);
}

.navbar-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 22px;
  font-weight: 700;
  color: var(--text-primary);
  cursor: pointer;
}

.logo-icon {
  font-size: 28px;
}

.nav-links {
  display: flex;
  gap: 8px;
}

.nav-link {
  padding: 8px 16px;
  border-radius: var(--radius-sm);
  color: var(--text-secondary);
  text-decoration: none;
  font-weight: 500;
  transition: var(--transition);
}

.nav-link:hover,
.nav-link.active {
  color: var(--primary-color);
  background: rgba(102, 126, 234, 0.1);
}

.nav-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 12px;
  border-radius: var(--radius-lg);
  cursor: pointer;
  transition: var(--transition);
}

.user-avatar:hover {
  background: var(--bg-secondary);
}

.username {
  font-weight: 500;
  color: var(--text-primary);
}

/* ===== 英雄区域 ===== */
.hero-section {
  position: relative;
  padding: 160px 24px 100px;
  overflow: hidden;
}

.hero-bg {
  position: absolute;
  inset: 0;
  background: var(--primary-gradient);
  opacity: 0.05;
}

.hero-bg::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -20%;
  width: 600px;
  height: 600px;
  background: radial-gradient(circle, rgba(102, 126, 234, 0.3) 0%, transparent 70%);
  border-radius: 50%;
}

.hero-bg::after {
  content: '';
  position: absolute;
  bottom: -30%;
  left: -10%;
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, rgba(118, 75, 162, 0.2) 0%, transparent 70%);
  border-radius: 50%;
}

.hero-content {
  position: relative;
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
}

.hero-title {
  font-size: 56px;
  font-weight: 800;
  line-height: 1.2;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.gradient-text {
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-subtitle {
  font-size: 20px;
  line-height: 1.8;
  color: var(--text-secondary);
  margin-bottom: 32px;
}

.hero-search {
  max-width: 600px;
  margin: 0 auto 32px;
}

.hero-search :deep(.el-input__wrapper) {
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.12);
  border-radius: 30px;
  padding: 8px 16px;
}

.hero-search :deep(.el-input-group__append) {
  background: var(--primary-color, #667eea);
  border: none;
  border-radius: 0 30px 30px 0;
  padding: 0 24px;
}

.hero-search :deep(.el-input-group__append .el-button) {
  color: white;
  font-weight: 500;
}

.hero-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
  margin-bottom: 60px;
}

.hero-actions .el-button {
  padding: 20px 32px;
  font-size: 16px;
}

.hero-stats {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 40px;
}

.stat-item {
  text-align: center;
}

.stat-number {
  display: block;
  font-size: 36px;
  font-weight: 700;
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.stat-label {
  font-size: 14px;
  color: var(--text-muted);
}

.stat-divider {
  width: 1px;
  height: 40px;
  background: var(--border-color);
}

/* ===== 通用区域样式 ===== */
.section-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
}

.section-header {
  text-align: center;
  margin-bottom: 48px;
}

.section-title {
  font-size: 36px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.section-subtitle {
  font-size: 16px;
  color: var(--text-muted);
}

/* ===== 特色功能区 ===== */
.features-section {
  padding: 100px 0;
  background: var(--bg-primary);
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 32px;
}

.feature-card {
  padding: 40px 32px;
  background: var(--bg-secondary);
  border-radius: var(--radius-lg);
  text-align: center;
  cursor: pointer;
  transition: var(--transition);
}

.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: var(--shadow-lg);
  background: var(--bg-primary);
}

.feature-icon {
  width: 80px;
  height: 80px;
  margin: 0 auto 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: var(--radius-md);
  color: white;
}

.feature-title {
  font-size: 22px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.feature-desc {
  font-size: 15px;
  line-height: 1.6;
  color: var(--text-muted);
}

/* ===== 推荐食谱区 ===== */
.recipes-section {
  padding: 100px 0;
}

.recipes-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 32px;
  margin-bottom: 48px;
}

.recipe-card {
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
  overflow: hidden;
  cursor: pointer;
  transition: var(--transition);
  box-shadow: var(--shadow-sm);
}

.recipe-card:hover {
  transform: translateY(-8px);
  box-shadow: var(--shadow-lg);
}

.recipe-image {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.recipe-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: var(--transition);
}

.recipe-card:hover .recipe-image img {
  transform: scale(1.1);
}

.recipe-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 12px 16px;
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
}

.recipe-time {
  display: flex;
  align-items: center;
  gap: 4px;
  color: white;
  font-size: 13px;
}

.recipe-info {
  padding: 20px;
}

.recipe-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.recipe-desc {
  font-size: 14px;
  color: var(--text-muted);
  line-height: 1.5;
  margin-bottom: 16px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.recipe-meta {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.recipe-author {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  color: var(--text-secondary);
}

.recipe-stats {
  display: flex;
  gap: 12px;
  font-size: 13px;
  color: var(--text-muted);
}

.recipe-stats span {
  display: flex;
  align-items: center;
  gap: 4px;
}

.recipes-more {
  text-align: center;
}

/* ===== CTA区域 ===== */
.cta-section {
  padding: 100px 24px;
  background: var(--primary-gradient);
}

.cta-content {
  max-width: 600px;
  margin: 0 auto;
  text-align: center;
}

.cta-title {
  font-size: 36px;
  font-weight: 700;
  color: white;
  margin-bottom: 16px;
}

.cta-subtitle {
  font-size: 18px;
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 32px;
}

.cta-section .el-button {
  background: white;
  color: var(--primary-color);
  border: none;
  padding: 20px 40px;
  font-size: 16px;
  font-weight: 600;
}

.cta-section .el-button:hover {
  background: rgba(255, 255, 255, 0.9);
}

/* ===== 页脚 ===== */
.footer {
  padding: 40px 24px;
  background: var(--text-primary);
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.footer-brand {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 20px;
  font-weight: 600;
  color: white;
}

.footer-copyright {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.6);
}

/* ===== 响应式设计 ===== */
@media (max-width: 1024px) {
  .features-grid,
  .recipes-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .navbar-content {
    padding: 0 16px;
  }

  .nav-links {
    display: none;
  }

  .hero-title {
    font-size: 36px;
  }

  .hero-subtitle {
    font-size: 16px;
  }

  .hero-stats {
    flex-direction: column;
    gap: 20px;
  }

  .stat-divider {
    display: none;
  }

  .features-grid,
  .recipes-grid {
    grid-template-columns: 1fr;
  }

  .section-title {
    font-size: 28px;
  }

  .footer-content {
    flex-direction: column;
    gap: 16px;
    text-align: center;
  }
}
</style>
