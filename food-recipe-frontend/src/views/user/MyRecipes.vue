<template>
  <div class="my-recipes-container">
    <PageHeader title="我的食谱" back-to="/">
      <template #actions>
        <el-button type="primary" @click="goToCreate">发布新食谱</el-button>
      </template>
    </PageHeader>

    <el-card>
      <div v-loading="loading" class="recipe-list">
        <div v-for="recipe in recipeList" :key="recipe.id" class="recipe-item">
          <div class="recipe-cover">
            <el-image :src="getImageUrl(recipe.coverImage)" fit="cover" />
            <el-tag 
              class="status-tag" 
              :type="recipe.status === 'PUBLISHED' ? 'success' : 'info'"
            >
              {{ recipe.status === 'PUBLISHED' ? '已发布' : '草稿' }}
            </el-tag>
          </div>
          <div class="recipe-info">
            <h3 class="recipe-title" @click="goToDetail(recipe.id)">{{ recipe.title }}</h3>
            <p class="recipe-desc">{{ recipe.description }}</p>
            <div class="recipe-meta">
              <span v-if="recipe.cuisineType">{{ recipe.cuisineType }}</span>
              <span v-if="recipe.cookingTime">{{ recipe.cookingTime }}分钟</span>
            </div>
            <div class="recipe-stats">
              <span><el-icon><View /></el-icon> {{ recipe.viewCount || 0 }}</span>
              <span><el-icon><Star /></el-icon> {{ recipe.favoriteCount || 0 }}</span>
              <span><el-icon><ChatDotRound /></el-icon> {{ recipe.commentCount || 0 }}</span>
            </div>
          </div>
          <div class="recipe-actions">
            <el-button type="primary" size="small" :icon="Edit" @click="handleEdit(recipe.id)">
              编辑
            </el-button>
            <el-button 
              v-if="recipe.status !== 'PUBLISHED'" 
              type="success" 
              size="small" 
              :icon="Upload"
              @click="handlePublish(recipe.id)"
            >
              发布
            </el-button>
            <el-button type="danger" size="small" :icon="Delete" @click="handleDelete(recipe)">
              删除
            </el-button>
          </div>
        </div>
      </div>

      <el-empty v-if="!loading && recipeList.length === 0" description="还没有发布食谱哦">
        <el-button type="primary" @click="goToCreate">发布第一个食谱</el-button>
      </el-empty>

      <div v-if="total > 0" class="pagination-container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next"
          background
          @current-change="loadMyRecipes"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Edit, Delete, View, Star, ChatDotRound, Upload } from '@element-plus/icons-vue'
import PageHeader from '@/components/PageHeader.vue'
import { getMyRecipes, deleteRecipe, publishRecipe } from '@/api/recipe'
import type { Recipe } from '@/types/recipe'

const router = useRouter()
const loading = ref(false)
const recipeList = ref<Recipe[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

const getImageUrl = (url?: string) => {
  if (!url) return 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400'
  if (url.startsWith('http')) return url
  return `http://localhost:8080${url}`
}

const loadMyRecipes = async () => {
  loading.value = true
  try {
    const result = await getMyRecipes({
      current: currentPage.value,
      size: pageSize.value
    })
    recipeList.value = result.records
    total.value = result.total
  } catch (error) {
    console.error('加载我的食谱失败:', error)
  } finally {
    loading.value = false
  }
}

const goToCreate = () => {
  router.push('/recipe/create')
}

const goToDetail = (id: number) => {
  router.push(`/recipe/${id}`)
}

const handleEdit = (id: number) => {
  router.push(`/recipe/edit/${id}`)
}

const handlePublish = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要发布这个食谱吗？', '提示', {
      type: 'info'
    })
    await publishRecipe(id)
    ElMessage.success('发布成功')
    loadMyRecipes()
  } catch (error: any) {
    if (error !== 'cancel') {
      console.error('发布失败:', error)
    }
  }
}

const handleDelete = async (recipe: Recipe) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除食谱"${recipe.title}"吗？此操作不可恢复。`,
      '删除确认',
      { type: 'warning', confirmButtonClass: 'el-button--danger' }
    )
    await deleteRecipe(recipe.id)
    ElMessage.success('删除成功')
    loadMyRecipes()
  } catch (error: any) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
    }
  }
}

onMounted(() => {
  loadMyRecipes()
})
</script>

<style scoped>
.my-recipes-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
}

.recipe-list {
  min-height: 200px;
}

.recipe-item {
  display: flex;
  gap: 20px;
  padding: 20px;
  border-bottom: 1px solid var(--border-color, #e2e8f0);
  transition: background 0.3s;
}

.recipe-item:last-child {
  border-bottom: none;
}

.recipe-item:hover {
  background: var(--bg-secondary, #f7f8fc);
}

.recipe-cover {
  position: relative;
  width: 200px;
  height: 140px;
  flex-shrink: 0;
  border-radius: 12px;
  overflow: hidden;
}

.recipe-cover .el-image {
  width: 100%;
  height: 100%;
}

.status-tag {
  position: absolute;
  top: 8px;
  left: 8px;
}

.recipe-info {
  flex: 1;
  min-width: 0;
}

.recipe-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary, #1a1a2e);
  margin: 0 0 8px;
  cursor: pointer;
  transition: color 0.3s;
}

.recipe-title:hover {
  color: var(--primary-color, #667eea);
}

.recipe-desc {
  font-size: 14px;
  color: var(--text-secondary, #4a5568);
  margin: 0 0 12px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.recipe-meta {
  display: flex;
  gap: 12px;
  font-size: 13px;
  color: var(--text-muted, #718096);
  margin-bottom: 12px;
}

.recipe-stats {
  display: flex;
  gap: 16px;
  font-size: 13px;
  color: var(--text-muted, #718096);
}

.recipe-stats span {
  display: flex;
  align-items: center;
  gap: 4px;
}

.recipe-actions {
  display: flex;
  flex-direction: column;
  gap: 8px;
  justify-content: center;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

@media (max-width: 768px) {
  .recipe-item {
    flex-direction: column;
  }

  .recipe-cover {
    width: 100%;
    height: 200px;
  }

  .recipe-actions {
    flex-direction: row;
    justify-content: flex-start;
  }
}
</style>
