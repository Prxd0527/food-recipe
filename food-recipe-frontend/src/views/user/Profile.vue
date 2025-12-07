<template>
  <div class="profile-container">
    <PageHeader title="个人中心" back-to="/" />

    <el-card>
      <el-form 
        ref="formRef" 
        :model="form" 
        :rules="rules" 
        label-width="100px"
        v-loading="loading"
      >
        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <el-avatar :size="100" :src="avatarUrl" />
            <div class="avatar-tip">点击更换头像</div>
          </el-upload>
        </el-form-item>

        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>

        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>

        <el-form-item label="个人简介">
          <el-input 
            v-model="form.bio" 
            type="textarea" 
            :rows="4"
            placeholder="介绍一下自己吧..."
            maxlength="200"
            show-word-limit
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="saving" @click="handleSave">
            保存修改
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, type FormInstance, type FormRules, type UploadProps } from 'element-plus'
import PageHeader from '@/components/PageHeader.vue'
import { useUserStore } from '@/store/user'
import { updateUserInfo, type UserUpdateRequest } from '@/api/user'
import { getToken } from '@/utils/auth'

const userStore = useUserStore()
const formRef = ref<FormInstance>()
const loading = ref(false)
const saving = ref(false)

const uploadUrl = 'http://localhost:8080/api/upload/image'
const uploadHeaders = computed(() => ({
  Authorization: `Bearer ${getToken()}`
}))

const form = reactive({
  username: '',
  nickname: '',
  email: '',
  bio: '',
  avatar: ''
})

const rules: FormRules = {
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 2, max: 20, message: '昵称长度在2-20个字符', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ]
}

const avatarUrl = computed(() => {
  if (!form.avatar) return ''
  if (form.avatar.startsWith('http')) return form.avatar
  return `http://localhost:8080${form.avatar}`
})

const loadUserInfo = () => {
  if (userStore.userInfo) {
    form.username = userStore.userInfo.username || ''
    form.nickname = userStore.userInfo.nickname || ''
    form.email = userStore.userInfo.email || ''
    form.bio = userStore.userInfo.bio || ''
    form.avatar = userStore.userInfo.avatar || ''
  }
}

const handleAvatarSuccess: UploadProps['onSuccess'] = (response) => {
  if (response.code === 200) {
    form.avatar = response.data
    ElMessage.success('头像上传成功')
  }
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (!rawFile.type.startsWith('image/')) {
    ElMessage.error('只能上传图片文件!')
    return false
  }
  if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('图片大小不能超过2MB!')
    return false
  }
  return true
}

const handleSave = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    saving.value = true
    try {
      const updateData: UserUpdateRequest = {
        nickname: form.nickname,
        email: form.email,
        bio: form.bio,
        avatar: form.avatar
      }
      await updateUserInfo(updateData)
      ElMessage.success('保存成功')
      // 刷新用户信息
      await userStore.fetchUserInfo()
    } catch (error) {
      console.error('保存失败:', error)
    } finally {
      saving.value = false
    }
  })
}

onMounted(async () => {
  // 如果有token但没有用户信息，先获取用户信息
  if (getToken() && !userStore.userInfo) {
    loading.value = true
    try {
      await userStore.fetchUserInfo()
    } finally {
      loading.value = false
    }
  }
  loadUserInfo()
})
</script>

<style scoped>
.profile-container {
  max-width: 600px;
  margin: 40px auto;
  padding: 0 20px;
}

.profile-container h2 {
  margin: 0;
  font-size: 20px;
  color: #303133;
}

.avatar-uploader {
  cursor: pointer;
  text-align: center;
}

.avatar-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 8px;
}
</style>
