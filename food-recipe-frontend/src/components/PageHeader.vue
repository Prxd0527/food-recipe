<template>
  <div class="page-header-bar">
    <div class="header-left">
      <el-button 
        v-if="showBack" 
        class="back-btn"
        :icon="ArrowLeft"
        round
        @click="handleBack"
      >
        返回
      </el-button>
      <h1 class="page-title">{{ title }}</h1>
    </div>
    <div class="header-right">
      <slot name="actions"></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ArrowLeft } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

interface Props {
  title: string
  showBack?: boolean
  backTo?: string
}

const props = withDefaults(defineProps<Props>(), {
  showBack: true,
  backTo: ''
})

const router = useRouter()

const handleBack = () => {
  if (props.backTo) {
    router.push(props.backTo)
  } else {
    router.back()
  }
}
</script>

<style scoped>
.page-header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  background: white;
  border-radius: 16px;
  margin-bottom: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.back-btn {
  color: var(--text-secondary, #4a5568);
  border-color: var(--border-color, #e2e8f0);
}

.back-btn:hover {
  color: var(--primary-color, #667eea);
  border-color: var(--primary-color, #667eea);
  background: rgba(102, 126, 234, 0.05);
}

.page-title {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  color: var(--text-primary, #1a1a2e);
}

.header-right {
  display: flex;
  gap: 12px;
}
</style>
