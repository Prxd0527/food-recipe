<template>
  <el-card class="recipe-card" shadow="hover" @click="goToDetail">
    <template #header>
      <div class="card-header">
        <el-image
          :src="getImageUrl(recipe.coverImage)"
          fit="cover"
          class="cover-image"
        >
          <template #error>
            <div class="image-slot">
              <el-icon><Picture /></el-icon>
            </div>
          </template>
        </el-image>
      </div>
    </template>

    <div class="card-content">
      <h3 class="recipe-title">{{ recipe.title }}</h3>
      
      <p class="recipe-description">{{ recipe.description || '暂无简介' }}</p>

      <div class="recipe-meta">
        <el-tag v-if="recipe.cuisineType" size="small" type="success">
          {{ recipe.cuisineType }}
        </el-tag>
        <el-tag v-if="recipe.difficulty" size="small" type="warning">
          {{ difficultyText }}
        </el-tag>
        <el-tag v-if="recipe.cookingTime" size="small" type="info">
          {{ recipe.cookingTime }}分钟
        </el-tag>
      </div>

      <div class="recipe-stats">
        <span class="stat-item">
          <el-icon><View /></el-icon>
          {{ recipe.viewCount }}
        </span>
        <span class="stat-item">
          <el-icon><Star /></el-icon>
          {{ recipe.favoriteCount }}
        </span>
        <span class="stat-item">
          <el-icon><ChatDotRound /></el-icon>
          {{ recipe.commentCount }}
        </span>
      </div>

      <div class="recipe-author">
        <el-avatar :size="24" :src="recipe.userAvatar" />
        <span class="author-name">{{ recipe.userNickname }}</span>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { Picture, View, Star, ChatDotRound } from '@element-plus/icons-vue'
import type { Recipe } from '@/types/recipe'

interface Props {
  recipe: Recipe
}

const props = defineProps<Props>()
const router = useRouter()

const difficultyText = computed(() => {
  const map: Record<string, string> = {
    EASY: '简单',
    MEDIUM: '中等',
    HARD: '困难'
  }
  return map[props.recipe.difficulty || ''] || props.recipe.difficulty
})

const getImageUrl = (url?: string) => {
  if (!url) return ''
  if (url.startsWith('http')) return url
  return `http://localhost:8080${url}`
}

const goToDetail = () => {
  router.push(`/recipe/${props.recipe.id}`)
}
</script>

<style scoped>
.recipe-card {
  cursor: pointer;
  transition: transform 0.3s;
}

.recipe-card:hover {
  transform: translateY(-4px);
}

.card-header {
  padding: 0;
  margin: -20px -20px 0 -20px;
}

.cover-image {
  width: 100%;
  height: 200px;
  display: block;
}

.image-slot {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  color: #909399;
  font-size: 30px;
}

.card-content {
  padding: 16px 0 0 0;
}

.recipe-title {
  margin: 0 0 12px 0;
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.recipe-description {
  margin: 0 0 12px 0;
  font-size: 14px;
  color: #606266;
  line-height: 1.5;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  min-height: 42px;
}

.recipe-meta {
  margin-bottom: 12px;
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.recipe-stats {
  display: flex;
  gap: 16px;
  margin-bottom: 12px;
  padding: 12px 0;
  border-top: 1px solid #ebeef5;
  border-bottom: 1px solid #ebeef5;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  color: #909399;
}

.stat-item .el-icon {
  font-size: 16px;
}

.recipe-author {
  display: flex;
  align-items: center;
  gap: 8px;
}

.author-name {
  font-size: 14px;
  color: #606266;
}
</style>
