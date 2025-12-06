/**
 * 文件上传API
 * @author prxd0527
 * @date 2025-12-07
 */
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { getToken } from '@/utils/auth'

/**
 * 上传图片
 */
export function uploadImage(file: File) {
    const formData = new FormData()
    formData.append('file', file)

    return axios.post('/api/upload/image', formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': `Bearer ${getToken()}`
        }
    }).then(res => {
        if (res.data.code === 200) {
            return res.data.data
        } else {
            ElMessage.error(res.data.message || '上传失败')
            return Promise.reject(res.data.message)
        }
    }).catch(error => {
        ElMessage.error('上传失败')
        return Promise.reject(error)
    })
}
