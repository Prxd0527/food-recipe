<template>
  <div class="auth-page">
    <div class="auth-container">
      <!-- 左侧装饰区 -->
      <div class="auth-banner">
        <div class="banner-content">
          <div class="logo">
            <span class="logo-icon">🍳</span>
            <span class="logo-text">美食食谱</span>
          </div>
          <h1>欢迎回来</h1>
          <p>登录后即可发布食谱、收藏美食、与美食爱好者交流互动</p>
        </div>
      </div>

      <!-- 右侧表单区 -->
      <div class="auth-form-container">
        <div class="auth-form-wrapper">
          <div class="form-header">
            <h2>账号登录</h2>
            <p>使用您的账号登录</p>
          </div>

          <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginRules"
            label-width="0"
            size="large"
          >
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="用户名"
                clearable
              >
                <template #prefix>
                  <el-icon><User /></el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="密码"
                show-password
                @keyup.enter="handleLogin"
              >
                <template #prefix>
                  <el-icon><Lock /></el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                :loading="loading"
                class="submit-btn"
                @click="handleLogin"
              >
                登 录
              </el-button>
            </el-form-item>
          </el-form>

          <div class="form-footer">
            <span>还没有账号？</span>
            <router-link to="/register" class="link">立即注册</router-link>
          </div>

          <div class="back-home">
            <router-link to="/">
              <el-icon><ArrowLeft /></el-icon> 返回首页
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { User, Lock, ArrowLeft } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { LoginRequest } from '@/types/user'

const router = useRouter()
const userStore = useUserStore()

const loginFormRef = ref<FormInstance>()
const loading = ref(false)

const loginForm = reactive<LoginRequest>({
  username: '',
  password: ''
})

const loginRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 50, message: '用户名长度为3-50个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return

  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const success = await userStore.login(loginForm)
        if (success) {
          router.push('/')
        }
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  background: var(--bg-secondary, #f7f8fc);
}

.auth-container {
  display: flex;
  min-height: 100vh;
}

.auth-banner {
  flex: 1;
  background: var(--primary-gradient, linear-gradient(135deg, #667eea 0%, #764ba2 100%));
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 60px;
}

.banner-content {
  max-width: 400px;
  color: white;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 40px;
}

.logo-icon {
  font-size: 32px;
}

.banner-content h1 {
  font-size: 42px;
  font-weight: 700;
  margin: 0 0 16px;
}

.banner-content p {
  font-size: 16px;
  line-height: 1.8;
  opacity: 0.9;
}

.auth-form-container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 60px;
  background: white;
}

.auth-form-wrapper {
  width: 100%;
  max-width: 400px;
}

.form-header {
  text-align: center;
  margin-bottom: 40px;
}

.form-header h2 {
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary, #1a1a2e);
  margin: 0 0 8px;
}

.form-header p {
  font-size: 14px;
  color: var(--text-muted, #718096);
  margin: 0;
}

.submit-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
}

.form-footer {
  text-align: center;
  margin-top: 24px;
  font-size: 14px;
  color: var(--text-muted, #718096);
}

.form-footer .link {
  color: var(--primary-color, #667eea);
  font-weight: 500;
  text-decoration: none;
  margin-left: 8px;
}

.form-footer .link:hover {
  text-decoration: underline;
}

.back-home {
  text-align: center;
  margin-top: 40px;
}

.back-home a {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  color: var(--text-muted, #718096);
  text-decoration: none;
  transition: color 0.3s;
}

.back-home a:hover {
  color: var(--primary-color, #667eea);
}

/* 响应式 */
@media (max-width: 768px) {
  .auth-banner {
    display: none;
  }

  .auth-form-container {
    padding: 40px 24px;
  }
}
</style>
