<template>
  <div class="recipe-card" @click="goToDetail">
    <div class="card-image">
      <img :src="getImageUrl(recipe.coverImage)" :alt="recipe.title" />
      <div class="card-overlay">
        <span class="cooking-time">
          <el-icon><Timer /></el-icon>
          {{ recipe.cookingTime || 30 }}分钟
        </span>
      </div>
      <div class="card-badge" v-if="recipe.difficulty">
        {{ difficultyText }}
      </div>
    </div>
    
    <div class="card-content">
      <h3 class="card-title">{{ recipe.title }}</h3>
      <p class="card-description">{{ recipe.description || '暂无简介' }}</p>
      
      <div class="card-tags" v-if="recipe.cuisineType">
        <el-tag size="small" effect="plain">{{ recipe.cuisineType }}</el-tag>
      </div>
      
      <div class="card-footer">
        <div class="card-author">
          <el-avatar :size="28" :src="recipe.userAvatar">
            {{ recipe.userNickname?.charAt(0) }}
          </el-avatar>
          <span class="author-name">{{ recipe.userNickname }}</span>
        </div>
        <div class="card-stats">
          <span class="stat">
            <el-icon><View /></el-icon>
            {{ formatNumber(recipe.viewCount) }}
          </span>
          <span class="stat">
            <el-icon><Star /></el-icon>
            {{ formatNumber(recipe.favoriteCount) }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { Timer, View, Star } from '@element-plus/icons-vue'
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
  if (!url) return 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400'
  if (url.startsWith('http')) return url
  return `http://localhost:8080${url}`
}

const formatNumber = (num?: number) => {
  if (!num) return 0
  if (num >= 1000) return `${(num / 1000).toFixed(1)}k`
  return num
}

const goToDetail = () => {
  router.push(`/recipe/${props.recipe.id}`)
}
</script>

<style scoped>
.recipe-card {
  background: var(--bg-primary, #fff);
  border-radius: var(--radius-lg, 16px);
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.recipe-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
}

.card-image {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.recipe-card:hover .card-image img {
  transform: scale(1.1);
}

.card-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 40px 16px 12px;
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
}

.cooking-time {
  display: flex;
  align-items: center;
  gap: 4px;
  color: white;
  font-size: 13px;
  font-weight: 500;
}

.card-badge {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 4px 12px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  color: #667eea;
}

.card-content {
  padding: 20px;
}

.card-title {
  margin: 0 0 8px;
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary, #1a1a2e);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.card-description {
  margin: 0 0 12px;
  font-size: 14px;
  color: var(--text-muted, #718096);
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  min-height: 42px;
}

.card-tags {
  margin-bottom: 16px;
}

.card-tags .el-tag {
  border-radius: 6px;
}

.card-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 16px;
  border-top: 1px solid var(--border-light, #edf2f7);
}

.card-author {
  display: flex;
  align-items: center;
  gap: 8px;
}

.author-name {
  font-size: 13px;
  color: var(--text-secondary, #4a5568);
  font-weight: 500;
}

.card-stats {
  display: flex;
  gap: 12px;
}

.stat {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: var(--text-muted, #718096);
}

.stat .el-icon {
  font-size: 14px;
}
</style>
