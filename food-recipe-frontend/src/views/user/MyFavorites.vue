<template>
  <div class="my-favorites-container">
    <PageHeader title="我的收藏" back-to="/" />

    <el-card>

      <div v-loading="loading" class="recipe-grid">
        <RecipeCard
          v-for="recipe in recipeList"
          :key="recipe.id"
          :recipe="recipe"
        />
      </div>

      <el-empty v-if="!loading && recipeList.length === 0" description="还没有收藏任何食谱哦" />

      <div v-if="total > 0" class="pagination-container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="loadMyFavorites"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import PageHeader from '@/components/PageHeader.vue'
import RecipeCard from '@/components/RecipeCard.vue'
import { getMyFavorites } from '@/api/favorite'
import type { Recipe } from '@/types/recipe'

const loading = ref(false)
const recipeList = ref<Recipe[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(12)

const loadMyFavorites = async () => {
  loading.value = true
  try {
    const result = await getMyFavorites({
      current: currentPage.value,
      size: pageSize.value
    })
    recipeList.value = result.records
    total.value = result.total
  } catch (error) {
    console.error('加载我的收藏失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadMyFavorites()
})
</script>

<style scoped>
.my-favorites-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
}

.recipe-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  min-height: 200px;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
