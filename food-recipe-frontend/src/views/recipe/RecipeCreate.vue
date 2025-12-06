<template>
  <div class="recipe-create-container">
    <el-card>
      <template #header>
        <h2>{{ isEdit ? '编辑食谱' : '发布食谱' }}</h2>
      </template>

      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
        size="large"
      >
        <!-- 基本信息 -->
        <el-form-item label="食谱标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入食谱标题" maxlength="100" show-word-limit />
        </el-form-item>

        <el-form-item label="封面图片" prop="coverImage">
          <ImageUpload v-model="form.coverImage" placeholder="上传封面图" />
        </el-form-item>

        <el-form-item label="简介" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="3"
            placeholder="请输入食谱简介"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="菜系" prop="cuisineType">
              <el-select v-model="form.cuisineType" placeholder="请选择菜系">
                <el-option label="川菜" value="川菜" />
                <el-option label="粤菜" value="粤菜" />
                <el-option label="湘菜" value="湘菜" />
                <el-option label="家常菜" value="家常菜" />
                <el-option label="西餐" value="西餐" />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="难度" prop="difficulty">
              <el-select v-model="form.difficulty" placeholder="请选择难度">
                <el-option label="简单" value="EASY" />
                <el-option label="中等" value="MEDIUM" />
                <el-option label="困难" value="HARD" />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="烹饪时长" prop="cookingTime">
              <el-input-number v-model="form.cookingTime" :min="1" placeholder="分钟" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="份量" prop="servings">
          <el-input-number v-model="form.servings" :min="1" placeholder="人数" />
        </el-form-item>

        <!-- 食材列表 -->
        <el-form-item label="食材列表" required>
          <div class="ingredient-list">
            <div v-for="(item, index) in form.ingredients" :key="index" class="ingredient-item">
              <el-input v-model="item.name" placeholder="食材名称" style="width: 200px" />
              <el-input v-model="item.amount" placeholder="用量" style="width: 150px" />
              <el-button type="danger" :icon="Delete" @click="removeIngredient(index)" />
            </div>
            <el-button type="primary" :icon="Plus" @click="addIngredient">添加食材</el-button>
          </div>
        </el-form-item>

        <!-- 制作步骤 -->
        <el-form-item label="制作步骤" required>
          <div class="step-list">
            <div v-for="(item, index) in form.steps" :key="index" class="step-item">
              <div class="step-header">
                <span class="step-number">步骤 {{ index + 1 }}</span>
                <el-button type="danger" size="small" :icon="Delete" @click="removeStep(index)" />
              </div>
              <RichTextEditor v-model="item.content" placeholder="请输入步骤内容" />
            </div>
            <el-button type="primary" :icon="Plus" @click="addStep">添加步骤</el-button>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="submitting" @click="handleSubmit">
            {{ isEdit ? '保存' : '发布' }}
          </el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { Plus, Delete } from '@element-plus/icons-vue'
import ImageUpload from '@/components/ImageUpload.vue'
import RichTextEditor from '@/components/RichTextEditor.vue'
import { createRecipe } from '@/api/recipe'
import type { RecipeRequest, Ingredient, Step } from '@/types/recipe'

const router = useRouter()
const formRef = ref<FormInstance>()
const submitting = ref(false)
const isEdit = ref(false)

const form = reactive<RecipeRequest>({
  title: '',
  description: '',
  coverImage: '',
  cuisineType: '',
  difficulty: '',
  cookingTime: undefined,
  servings: undefined,
  ingredients: [{ name: '', amount: '' }],
  steps: [{ content: '' }]
})

const rules: FormRules = {
  title: [{ required: true, message: '请输入食谱标题', trigger: 'blur' }],
  coverImage: [{ required: true, message: '请上传封面图', trigger: 'change' }]
}

const addIngredient = () => {
  form.ingredients.push({ name: '', amount: '' })
}

const removeIngredient = (index: number) => {
  if (form.ingredients.length > 1) {
    form.ingredients.splice(index, 1)
  } else {
    ElMessage.warning('至少保留一个食材')
  }
}

const addStep = () => {
  form.steps.push({ content: '' })
}

const removeStep = (index: number) => {
  if (form.steps.length > 1) {
    form.steps.splice(index, 1)
  } else {
    ElMessage.warning('至少保留一个步骤')
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (!valid) return

    // 验证食材和步骤
    if (form.ingredients.some(item => !item.name)) {
      ElMessage.error('请填写食材名称')
      return
    }

    if (form.steps.some(item => !item.content)) {
      ElMessage.error('请填写步骤内容')
      return
    }

    submitting.value = true
    try {
      await createRecipe(form)
      ElMessage.success('发布成功')
      router.push('/my-recipes')
    } catch (error) {
      console.error('发布失败:', error)
    } finally {
      submitting.value = false
    }
  })
}

const handleCancel = () => {
  router.back()
}
</script>

<style scoped>
.recipe-create-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.ingredient-list,
.step-list {
  width: 100%;
}

.ingredient-item {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
  align-items: center;
}

.step-item {
  margin-bottom: 20px;
  padding: 16px;
  background: #f5f7fa;
  border-radius: 8px;
}

.step-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.step-number {
  font-weight: bold;
  color: #409eff;
}
</style>
