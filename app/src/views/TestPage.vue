<template>
  <div class="test-page">
    <h1>测试页面</h1>
    <div class="test-buttons">
      <button @click="testServices()" class="test-button">测试服务按钮</button>
      <button @click="testRouter()" class="test-button">测试路由功能</button>
      <button @click="testAlert()" class="test-button">测试点击事件</button>
    </div>
    <div class="test-result">
      <p>测试结果: {{ testResult }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const testResult = ref('未测试')

const testServices = () => {
  testResult.value = '点击了服务按钮，准备跳转...'
  console.log('测试服务按钮点击')
  try {
    router.push('/elderly/services')
    testResult.value = '跳转成功'
  } catch (error) {
    testResult.value = '跳转失败: ' + error.message
    console.error('跳转失败:', error)
  }
}

const testRouter = () => {
  testResult.value = '测试路由功能...'
  console.log('测试路由功能')
  try {
    // 测试其他路由
    router.push('/elderly/health-dashboard')
    testResult.value = '路由功能正常'
  } catch (error) {
    testResult.value = '路由功能异常: ' + error.message
    console.error('路由功能异常:', error)
  }
}

const testAlert = () => {
  testResult.value = '测试点击事件...'
  console.log('测试点击事件')
  alert('点击事件正常')
  testResult.value = '点击事件正常'
}
</script>

<style scoped>
.test-page {
  min-height: 100vh;
  background-color: #f8f9fa;
  padding: 40px;
  text-align: center;
}

h1 {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 40px;
}

.test-buttons {
  display: flex;
  flex-direction: column;
  gap: 20px;
  max-width: 400px;
  margin: 0 auto;
}

.test-button {
  padding: 20px;
  font-size: 1.2rem;
  font-weight: bold;
  color: white;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.test-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
}

.test-result {
  margin-top: 40px;
  padding: 20px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  margin-left: auto;
  margin-right: auto;
}

.test-result p {
  font-size: 1.1rem;
  color: #333;
}
</style>