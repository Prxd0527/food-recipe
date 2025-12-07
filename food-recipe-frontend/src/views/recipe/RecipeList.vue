<template>
  <div class="recipe-list-page">
    <!-- 页面头部 -->
    <header class="page-header">
      <div class="header-content">
        <h1 class="page-title">发现食谱</h1>
        <p class="page-subtitle">探索来自世界各地的美味佳肴</p>
      </div>
    </header>

    <div class="page-container">
      <!-- 搜索和筛选栏 -->
      <div class="filter-section">
        <div class="search-box">
          <el-input
            v-model="searchParams.keyword"
            size="large"
            placeholder="搜索你想要的食谱..."
            clearable
            @clear="handleSearch"
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

        <div class="filter-tags">
          <div class="filter-group">
            <span class="filter-label">菜系：</span>
            <el-radio-group v-model="searchParams.cuisineType" @change="handleSearch">
              <el-radio-button value="">全部</el-radio-button>
              <el-radio-button value="川菜">川菜</el-radio-button>
              <el-radio-button value="粤菜">粤菜</el-radio-button>
              <el-radio-button value="湘菜">湘菜</el-radio-button>
              <el-radio-button value="家常菜">家常菜</el-radio-button>
              <el-radio-button value="西餐">西餐</el-radio-button>
            </el-radio-group>
          </div>
          <div class="filter-group">
            <span class="filter-label">难度：</span>
            <el-radio-group v-model="searchParams.difficulty" @change="handleSearch">
              <el-radio-button value="">全部</el-radio-button>
              <el-radio-button value="EASY">简单</el-radio-button>
              <el-radio-button value="MEDIUM">中等</el-radio-button>
              <el-radio-button value="HARD">困难</el-radio-button>
            </el-radio-group>
          </div>
        </div>
      </div>

      <!-- 结果统计 -->
      <div class="result-info">
        <span v-if="total > 0">共找到 <strong>{{ total }}</strong> 个食谱</span>
        <span v-else>暂无食谱</span>
      </div>

      <!-- 食谱列表 -->
      <div v-loading="loading" class="recipe-grid">
        <RecipeCard
          v-for="recipe in recipeList"
          :key="recipe.id"
          :recipe="recipe"
        />
      </div>

      <!-- 空状态 -->
      <div v-if="!loading && recipeList.length === 0" class="empty-state">
        <el-empty description="暂无食谱">
          <el-button type="primary" @click="$router.push('/recipe/create')">
            发布第一个食谱
          </el-button>
        </el-empty>
      </div>

      <!-- 分页 -->
      <div v-if="total > 0" class="pagination-container">
        <el-pagination
          v-model:current-page="searchParams.current"
          v-model:page-size="searchParams.size"
          :total="total"
          :page-sizes="[12, 24, 48]"
          layout="prev, pager, next"
          background
          @current-change="loadRecipeList"
          @size-change="loadRecipeList"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import RecipeCard from '@/components/RecipeCard.vue'
import { getRecipeList } from '@/api/recipe'
import type { Recipe } from '@/types/recipe'

const route = useRoute()

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
  // 从URL读取搜索关键词
  if (route.query.keyword) {
    searchParams.keyword = route.query.keyword as string
  }
  loadRecipeList()
})

// 监听URL参数变化
watch(() => route.query.keyword, (newKeyword) => {
  searchParams.keyword = (newKeyword as string) || ''
  searchParams.current = 1
  loadRecipeList()
})
</script>

<style scoped>
.recipe-list-page {
  min-height: 100vh;
  background: var(--bg-secondary, #f7f8fc);
}

.page-header {
  background: var(--primary-gradient, linear-gradient(135deg, #667eea 0%, #764ba2 100%));
  padding: 80px 24px 60px;
  text-align: center;
}

.page-title {
  font-size: 40px;
  font-weight: 700;
  color: white;
  margin: 0 0 12px;
}

.page-subtitle {
  font-size: 18px;
  color: rgba(255, 255, 255, 0.8);
  margin: 0;
}

.page-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 24px;
}

.filter-section {
  background: var(--bg-primary, #fff);
  border-radius: var(--radius-lg, 16px);
  padding: 24px;
  margin-bottom: 32px;
  box-shadow: var(--shadow-sm, 0 2px 8px rgba(0, 0, 0, 0.06));
}

.search-box {
  margin-bottom: 24px;
}

.search-box .el-input {
  max-width: 600px;
}

.filter-tags {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.filter-label {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary, #4a5568);
  min-width: 50px;
}

.result-info {
  margin-bottom: 24px;
  font-size: 14px;
  color: var(--text-muted, #718096);
}

.result-info strong {
  color: var(--primary-color, #667eea);
  font-weight: 600;
}

.recipe-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 32px;
  min-height: 200px;
}

.empty-state {
  padding: 60px 0;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 48px;
}

/* 响应式 */
@media (max-width: 1024px) {
  .recipe-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .page-header {
    padding: 60px 16px 40px;
  }

  .page-title {
    font-size: 28px;
  }

  .page-container {
    padding: 24px 16px;
  }

  .recipe-grid {
    grid-template-columns: 1fr;
  }

  .filter-group {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
