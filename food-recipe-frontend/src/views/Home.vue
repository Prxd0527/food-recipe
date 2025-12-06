<template>
  <div class="home-container">
    <el-container>
      <!-- 顶部导航栏 -->
      <el-header class="header">
        <div class="header-content">
          <div class="logo">
            <span class="logo-icon">🍳</span>
            <span class="logo-text">美食食谱</span>
          </div>
          <div class="nav-menu">
            <el-menu mode="horizontal" :ellipsis="false" router>
              <el-menu-item index="/">首页</el-menu-item>
              <el-menu-item index="/recipes">食谱</el-menu-item>
              <el-menu-item v-if="userStore.userInfo" index="/my-recipes">我的食谱</el-menu-item>
              <el-menu-item v-if="userStore.userInfo" index="/my-favorites">我的收藏</el-menu-item>
              <el-menu-item v-if="userStore.userInfo" index="/recipe/create">发布食谱</el-menu-item>
            </el-menu>
          </div>
          <div class="user-info" v-if="userStore.userInfo">
            <el-dropdown>
              <span class="username">
                <el-avatar :size="32" :src="userStore.userInfo.avatar" />
                <span style="margin-left: 8px">{{ userStore.userInfo.nickname }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>个人中心</el-dropdown-item>
                  <el-dropdown-item>我的食谱</el-dropdown-item>
                  <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
          <div class="user-actions" v-else>
            <el-button type="primary" @click="goToLogin">登录</el-button>
            <el-button @click="goToRegister">注册</el-button>
          </div>
        </div>
      </el-header>

      <!-- 主体内容 -->
      <el-main class="main-content">
        <div class="welcome-section">
          <h1>欢迎来到美食食谱分享平台</h1>
          <p>发现、分享、创作属于你的美食故事</p>
          <div class="features">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-card shadow="hover">
                  <div class="feature-item">
                    <el-icon :size="40" color="#409EFF"><Document /></el-icon>
                    <h3>发布食谱</h3>
                    <p>分享你的拿手好菜</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="8">
                <el-card shadow="hover">
                  <div class="feature-item">
                    <el-icon :size="40" color="#67C23A"><ChatDotRound /></el-icon>
                    <h3>互动评论</h3>
                    <p>与美食爱好者交流</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="8">
                <el-card shadow="hover">
                  <div class="feature-item">
                    <el-icon :size="40" color="#F56C6C"><Star /></el-icon>
                    <h3>收藏食谱</h3>
                    <p>保存喜欢的美食</p>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { Document, ChatDotRound, Star } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

onMounted(async () => {
  // 如果已登录，获取用户信息
  if (userStore.token && !userStore.userInfo) {
    await userStore.fetchUserInfo()
  }
})

const goToLogin = () => {
  router.push('/login')
}

const goToRegister = () => {
  router.push('/register')
}

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    router.push('/login')
  })
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background: #f5f7fa;
}

.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.logo {
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
  color: #303133;
}

.logo-icon {
  font-size: 32px;
  margin-right: 8px;
}

.nav-menu {
  flex: 1;
  margin: 0 40px;
}

.user-info {
  display: flex;
  align-items: center;
}

.username {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user-actions {
  display: flex;
  gap: 10px;
}

.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
}

.welcome-section {
  text-align: center;
  padding: 60px 0;
}

.welcome-section h1 {
  font-size: 36px;
  color: #303133;
  margin-bottom: 16px;
}

.welcome-section p {
  font-size: 18px;
  color: #606266;
  margin-bottom: 60px;
}

.features {
  margin-top: 40px;
}

.feature-item {
  text-align: center;
  padding: 30px 20px;
}

.feature-item h3 {
  margin: 20px 0 10px 0;
  color: #303133;
  font-size: 20px;
}

.feature-item p {
  color: #909399;
  font-size: 14px;
  margin: 0;
}
</style>
