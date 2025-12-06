<template>
  <div class="recipe-detail-container">
    <el-card v-loading="loading">
      <!-- 顶部信息 -->
      <div class="recipe-header">
        <el-image
          :src="getImageUrl(recipe?.coverImage)"
          fit="cover"
          class="cover-image"
        />
        
        <div class="header-content">
          <h1 class="recipe-title">{{ recipe?.title }}</h1>
          
          <div class="recipe-meta">
            <el-tag v-if="recipe?.cuisineType" type="success">{{ recipe.cuisineType }}</el-tag>
            <el-tag v-if="recipe?.difficulty" type="warning">{{ difficultyText }}</el-tag>
            <el-tag v-if="recipe?.cookingTime" type="info">{{ recipe.cookingTime }}分钟</el-tag>
            <el-tag v-if="recipe?.servings">{{ recipe.servings }}人份</el-tag>
          </div>

          <p class="recipe-description">{{ recipe?.description }}</p>

          <div class="recipe-stats">
            <span>浏览 {{ recipe?.viewCount || 0 }}</span>
            <span>收藏 {{ recipe?.favoriteCount || 0 }}</span>
            <span>评论 {{ recipe?.commentCount || 0 }}</span>
          </div>

          <div class="recipe-author">
            <el-avatar :src="recipe?.userAvatar" :size="40" />
            <div>
              <div class="author-name">{{ recipe?.userNickname }}</div>
              <div class="create-time">{{ formatTime(recipe?.createTime) }}</div>
            </div>
          </div>
        </div>
      </div>

      <el-divider />

      <!-- 食材列表 -->
      <div class="section">
        <h2 class="section-title">所需食材</h2>
        <el-table :data="recipe?.ingredients" border>
          <el-table-column prop="name" label="食材名称" />
          <el-table-column prop="amount" label="用量" width="150" />
        </el-table>
      </div>

      <el-divider />

      <!-- 制作步骤 -->
      <div class="section">
        <h2 class="section-title">制作步骤</h2>
        <div v-for="(step, index) in recipe?.steps" :key="step.id" class="step-item">
          <div class="step-header">
            <el-tag type="primary" size="large">步骤 {{ index + 1 }}</el-tag>
          </div>
          <div class="step-content">
            <div v-html="step.content" class="step-text"></div>
            <el-image
              v-if="step.imageUrl"
              :src="getImageUrl(step.imageUrl)"
              fit="cover"
              class="step-image"
            />
          </div>
        </div>
      </div>
      <el-divider />

      <!-- 评论区 -->
      <div class="section">
        <h2 class="section-title">用户评论（{{ commentTotal }}）</h2>
        
        <!-- 评论表单 -->
        <CommentForm v-if="userStore.userInfo" :recipe-id="Number(route.params.id)" @success="loadComments" />
        <el-alert v-else type="info" :closable="false" style="margin-bottom: 20px">
          <template #title>
            请先<router-link to="/login" style="color: #409eff">登录</router-link>后发表评论
          </template>
        </el-alert>

        <!-- 评论列表 -->
        <CommentList
          :comments="comments"
          :total="commentTotal"
          :current-page="commentPage"
          :page-size="10"
          @page-change="handleCommentPageChange"
          @delete-success="loadComments"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getRecipeDetail } from '@/api/recipe'
import { getRecipeComments } from '@/api/comment'
import { useUserStore } from '@/store/user'
import CommentForm from '@/components/CommentForm.vue'
import CommentList from '@/components/CommentList.vue'
import type { Recipe } from '@/types/recipe'
import type { Comment } from '@/types/comment'

const route = useRoute()
const userStore = useUserStore()
const loading = ref(false)
const recipe = ref<Recipe>()
const comments = ref<Comment[]>([])
const commentTotal = ref(0)
const commentPage = ref(1)

const difficultyText = computed(() => {
  const map: Record<string, string> = {
    EASY: '简单',
    MEDIUM: '中等',
    HARD: '困难'
  }
  return map[recipe.value?.difficulty || ''] || recipe.value?.difficulty
})

const getImageUrl = (url?: string) => {
  if (!url) return ''
  if (url.startsWith('http')) return url
  return `http://localhost:8080${url}`
}

const formatTime = (time?: string) => {
  if (!time) return ''
  return new Date(time).toLocaleString('zh-CN')
}

const loadRecipeDetail = async () => {
  const id = Number(route.params.id)
  loading.value = true
  try {
    recipe.value = await getRecipeDetail(id)
  } catch (error) {
    console.error('加载食谱详情失败:', error)
  } finally {
    loading.value = false
  }
}

const loadComments = async () => {
  const id = Number(route.params.id)
  try {
    const result = await getRecipeComments(id, {
      current: commentPage.value,
      size: 10
    })
    comments.value = result.records
    commentTotal.value = result.total
  } catch (error) {
    console.error('加载评论失败:', error)
  }
}

const handleCommentPageChange = (page: number) => {
  commentPage.value = page
  loadComments()
}

onMounted(() => {
  loadRecipeDetail()
  loadComments()
})
</script>

<style scoped>
.recipe-detail-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.recipe-header {
  display: flex;
  gap: 30px;
  margin-bottom: 20px;
}

.cover-image {
  width: 400px;
  height: 300px;
  border-radius: 8px;
  flex-shrink: 0;
}

.header-content {
  flex: 1;
}

.recipe-title {
  font-size: 28px;
  font-weight: bold;
  margin: 0 0 16px 0;
}

.recipe-meta {
  display: flex;
  gap: 8px;
  margin-bottom: 16px;
}

.recipe-description {
  color: #606266;
  line-height: 1.6;
  margin-bottom: 16px;
}

.recipe-stats {
  display: flex;
  gap: 20px;
  color: #909399;
  margin-bottom: 16px;
}

.recipe-author {
  display: flex;
  align-items: center;
  gap: 12px;
}

.author-name {
  font-weight: 500;
  color: #303133;
}

.create-time {
  font-size: 12px;
  color: #909399;
}

.section {
  margin-bottom: 30px;
}

.section-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 16px;
}

.step-item {
  margin-bottom: 24px;
  padding: 16px;
  background: #f5f7fa;
  border-radius: 8px;
}

.step-header {
  margin-bottom: 12px;
}

.step-content {
  display: flex;
  gap: 20px;
  align-items: flex-start;
}

.step-text {
  flex: 1;
  line-height: 1.8;
  color: #303133;
}

.step-text :deep(img) {
  max-width: 100%;
  height: auto;
  border-radius: 4px;
  margin: 8px 0;
}

.step-image {
  width: 200px;
  height: 150px;
  border-radius: 8px;
  flex-shrink: 0;
}
</style>
