<template>
  <div class="my-recipes-container">
    <PageHeader title="我的食谱" back-to="/">
      <template #actions>
        <el-button type="primary" @click="goToCreate">发布新食谱</el-button>
      </template>
    </PageHeader>

    <el-card>

      <div v-loading="loading" class="recipe-grid">
        <RecipeCard
          v-for="recipe in recipeList"
          :key="recipe.id"
          :recipe="recipe"
        />
      </div>

      <el-empty v-if="!loading && recipeList.length === 0" description="还没有发布食谱哦" />

      <div v-if="total > 0" class="pagination-container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="loadMyRecipes"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'
import RecipeCard from '@/components/RecipeCard.vue'
import { getMyRecipes } from '@/api/recipe'
import type { Recipe } from '@/types/recipe'

const router = useRouter()
const loading = ref(false)
const recipeList = ref<Recipe[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

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
