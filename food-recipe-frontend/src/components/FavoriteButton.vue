<template>
  <el-button
    :type="isFavorited ? 'danger' : 'primary'"
    :icon="isFavorited ? StarFilled : Star"
    :loading="loading"
    @click="handleToggleFavorite"
  >
    {{ isFavorited ? '已收藏' : '收藏' }}
  </el-button>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Star, StarFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { addFavorite, removeFavorite, checkFavoriteStatus } from '@/api/favorite'
import { isAuthenticated } from '@/utils/auth'
import { useRouter } from 'vue-router'

interface Props {
  recipeId: number
}

const props = defineProps<Props>()
const router = useRouter()

const isFavorited = ref(false)
const loading = ref(false)

const loadFavoriteStatus = async () => {
  if (!isAuthenticated()) return
  
  try {
    isFavorited.value = await checkFavoriteStatus(props.recipeId)
  } catch (error) {
    console.error('检查收藏状态失败:', error)
  }
}

const handleToggleFavorite = async () => {
  if (!isAuthenticated()) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  loading.value = true
  try {
    if (isFavorited.value) {
      await removeFavorite(props.recipeId)
      isFavorited.value = false
      ElMessage.success('已取消收藏')
    } else {
      await addFavorite(props.recipeId)
      isFavorited.value = true
      ElMessage.success('收藏成功')
    }
  } catch (error) {
    console.error('操作失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadFavoriteStatus()
})
</script>
