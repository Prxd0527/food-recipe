<template>
  <div class="comment-list">
    <div v-if="comments.length > 0" class="comment-items">
      <div v-for="comment in comments" :key="comment.id" class="comment-item">
        <div class="comment-header">
          <el-avatar :src="comment.userAvatar" :size="40" />
          <div class="user-info">
            <div class="user-name">{{ comment.userNickname }}</div>
            <div class="comment-time">{{ formatTime(comment.createTime) }}</div>
          </div>
          <el-rate
            v-if="comment.rating"
            v-model="comment.rating"
            disabled
            show-score
            text-color="#ff9900"
          />
        </div>
        
        <div class="comment-content">{{ comment.content }}</div>

        <div v-if="canDelete(comment)" class="comment-actions">
          <el-button
            type="danger"
            size="small"
            text
            @click="handleDelete(comment.id)"
          >
            删除
          </el-button>
        </div>
      </div>
    </div>

    <el-empty v-else description="暂无评论" />

    <div v-if="total > pageSize" class="pagination">
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next"
        @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { deleteComment } from '@/api/comment'
import { useUserStore } from '@/store/user'
import type { Comment } from '@/types/comment'

interface Props {
  comments: Comment[]
  total: number
  currentPage: number
  pageSize: number
}

interface Emits {
  (e: 'page-change', page: number): void
  (e: 'delete-success'): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const userStore = useUserStore()

const formatTime = (time: string) => {
  return new Date(time).toLocaleString('zh-CN')
}

const canDelete = (comment: Comment) => {
  return userStore.userInfo?.id === comment.userId || userStore.isAdmin()
}

const handleDelete = (id: number) => {
  ElMessageBox.confirm('确定要删除这条评论吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteComment(id)
      ElMessage.success('删除成功')
      emit('delete-success')
    } catch (error) {
      console.error('删除评论失败:', error)
    }
  })
}

const handlePageChange = (page: number) => {
  emit('page-change', page)
}
</script>

<style scoped>
.comment-list {
  margin-top: 20px;
}

.comment-items {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.comment-item {
  padding: 16px;
  background: #f5f7fa;
  border-radius: 8px;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.user-info {
  flex: 1;
}

.user-name {
  font-weight: 500;
  color: #303133;
  margin-bottom: 4px;
}

.comment-time {
  font-size: 12px;
  color: #909399;
}

.comment-content {
  color: #606266;
  line-height: 1.6;
  margin-bottom: 8px;
}

.comment-actions {
  display: flex;
  justify-content: flex-end;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
