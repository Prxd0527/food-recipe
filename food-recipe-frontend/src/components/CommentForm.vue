<template>
  <el-card class="comment-form">
    <template #header>
      <h3>发表评论</h3>
    </template>

    <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="评分" prop="rating">
        <el-rate
          v-model="form.rating"
          show-text
          :texts="['极差', '失望', '一般', '满意', '惊喜']"
        />
      </el-form-item>

      <el-form-item label="评论内容" prop="content">
        <el-input
          v-model="form.content"
          type="textarea"
          :rows="4"
          placeholder="分享你的制作心得吧..."
          maxlength="500"
          show-word-limit
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" :loading="submitting" @click="handleSubmit">
          发表评论
        </el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { createComment } from '@/api/comment'
import type { CommentRequest } from '@/types/comment'

interface Props {
  recipeId: number
}

interface Emits {
  (e: 'success'): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const formRef = ref<FormInstance>()
const submitting = ref(false)

const form = reactive<CommentRequest>({
  content: '',
  rating: 5
})

const rules: FormRules = {
  content: [
    { required: true, message: '请输入评论内容', trigger: 'blur' },
    { min: 5, message: '评论内容至少5个字符', trigger: 'blur' }
  ],
  rating: [
    { required: true, message: '请选择评分', trigger: 'change' }
  ]
}

const handleSubmit = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (!valid) return

    submitting.value = true
    try {
      await createComment(props.recipeId, form)
      ElMessage.success('评论成功')
      emit('success')
      handleReset()
    } catch (error) {
      console.error('发表评论失败:', error)
    } finally {
      submitting.value = false
    }
  })
}

const handleReset = () => {
  formRef.value?.resetFields()
  form.content = ''
  form.rating = 5
}
</script>

<style scoped>
.comment-form {
  margin-bottom: 20px;
}

.comment-form h3 {
  margin: 0;
  font-size: 18px;
  color: #303133;
}
</style>
