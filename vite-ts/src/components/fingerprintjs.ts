// utils/useVisitorId.ts
import { ref } from 'vue'

// 初始化 visitorId 为响应式 ref
const visitorId = ref('')

// 加载 FingerprintJS 并获取 visitorId
const fpPromise = import('../js/fingerprint.min.js')
  .then(FingerprintJS => FingerprintJS.load())
  .then(fp => fp.get())
  .then(result => {
    visitorId.value = result.visitorId
    console.log(visitorId.value)
  })
  .catch(error => {
    console.error('Error loading FingerprintJS:', error)
  })

// 导出 visitorId
export { visitorId }

