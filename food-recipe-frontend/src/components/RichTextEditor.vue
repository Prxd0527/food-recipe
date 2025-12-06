<template>
  <div class="rich-text-editor">
    <QuillEditor
      v-model:content="content"
      :options="editorOptions"
      content-type="html"
      theme="snow"
      @ready="onEditorReady"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { uploadImage } from '@/api/upload'
import { ElMessage } from 'element-plus'

interface Props {
  modelValue?: string
  placeholder?: string
}

interface Emits {
  (e: 'update:modelValue', value: string): void
}

const props = withDefaults(defineProps<Props>(), {
  placeholder: '请输入内容...'
})

const emit = defineEmits<Emits>()

const content = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val || '')
})

const editorOptions = {
  placeholder: props.placeholder,
  modules: {
    toolbar: {
      container: [
        ['bold', 'italic', 'underline', 'strike'],
        ['blockquote', 'code-block'],
        [{ list: 'ordered' }, { list: 'bullet' }],
        [{ header: [1, 2, 3, 4, 5, 6, false] }],
        [{ color: [] }, { background: [] }],
        [{ align: [] }],
        ['link', 'image'],
        ['clean']
      ],
      handlers: {
        image: imageHandler
      }
    }
  }
}

let quillInstance: any = null

const onEditorReady = (quill: any) => {
  quillInstance = quill
}

/**
 * 自定义图片上传处理
 */
function imageHandler() {
  const input = document.createElement('input')
  input.setAttribute('type', 'file')
  input.setAttribute('accept', 'image/*')
  input.click()

  input.onchange = async () => {
    const file = input.files?.[0]
    if (!file) return

    try {
      const imageUrl = await uploadImage(file)
      
      // 获取当前光标位置
      const range = quillInstance.getSelection()
      const index = range ? range.index : 0

      // 插入图片（使用完整URL）
      const fullUrl = imageUrl.startsWith('http') 
        ? imageUrl 
        : `http://localhost:8080${imageUrl}`
      
      quillInstance.insertEmbed(index, 'image', fullUrl)
      
      // 移动光标到图片后面
      quillInstance.setSelection(index + 1)
    } catch (error) {
      ElMessage.error('图片上传失败')
    }
  }
}
</script>

<style scoped>
.rich-text-editor {
  background: #fff;
}

.rich-text-editor :deep(.ql-toolbar) {
  border: 1px solid #dcdfe6;
  border-radius: 4px 4px 0 0;
}

.rich-text-editor :deep(.ql-container) {
  border: 1px solid #dcdfe6;
  border-top: none;
  border-radius: 0 0 4px 4px;
  min-height: 200px;
  font-size: 14px;
}

.rich-text-editor :deep(.ql-editor) {
  min-height: 200px;
}

.rich-text-editor :deep(.ql-editor.ql-blank::before) {
  color: #c0c4cc;
  font-style: normal;
}
</style>
