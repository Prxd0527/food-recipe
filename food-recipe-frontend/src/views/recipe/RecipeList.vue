<template>
  <div class="recipe-list-container">
    <!-- 搜索和筛选栏 -->
    <el-card class="filter-card">
      <el-form :inline="true">
        <el-form-item label="关键词">
          <el-input
            v-model="searchParams.keyword"
            placeholder="搜索食谱名称"
            clearable
            @clear="handleSearch"
            @keyup.enter="handleSearch"
          >
            <template #append>
              <el-button :icon="Search" @click="handleSearch" />
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="菜系">
          <el-select
            v-model="searchParams.cuisineType"
            placeholder="选择菜系"
            clearable
            @change="handleSearch"
          >
            <el-option label="川菜" value="川菜" />
            <el-option label="粤菜" value="粤菜" />
            <el-option label="湘菜" value="湘菜" />
            <el-option label="家常菜" value="家常菜" />
          </el-select>
        </el-form-item>

        <el-form-item label="难度">
          <el-select
            v-model="searchParams.difficulty"
            placeholder="选择难度"
            clearable
            @change="handleSearch"
          >
            <el-option label="简单" value="EASY" />
            <el-option label="中等" value="MEDIUM" />
            <el-option label="困难" value="HARD" />
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 食谱列表 -->
    <div v-loading="loading" class="recipe-grid">
      <RecipeCard
        v-for="recipe in recipeList"
        :key="recipe.id"
        :recipe="recipe"
      />
    </div>

    <!-- 空状态 -->
    <el-empty v-if="!loading && recipeList.length === 0" description="暂无食谱" />

    <!-- 分页 -->
    <div v-if="total > 0" class="pagination-container">
      <el-pagination
        v-model:current-page="searchParams.current"
        v-model:page-size="searchParams.size"
        :total="total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="loadRecipeList"
        @size-change="loadRecipeList"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import RecipeCard from '@/components/RecipeCard.vue'
import { getRecipeList } from '@/api/recipe'
import type { Recipe } from '@/types/recipe'

const loading = ref(false)
const recipeList = ref<Recipe[]>([])
const total = ref(0)

const searchParams = reactive({
  current: 1,
  size: 12,
  keyword: '',
  cuisineType: '',
  difficulty: ''
})

const loadRecipeList = async () => {
  loading.value = true
  try {
    const result = await getRecipeList(searchParams)
    recipeList.value = result.records
    total.value = result.total
  } catch (error) {
    console.error('加载食谱列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  searchParams.current = 1
  loadRecipeList()
}

onMounted(() => {
  loadRecipeList()
})
</script>

<style scoped>
.recipe-list-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.filter-card {
  margin-bottom: 20px;
}

.recipe-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
