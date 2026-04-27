<template>
  <div class="app" :class="fontSizeClass">
    <router-view />
    <MedicineReminder />
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import MedicineReminder from './components/MedicineReminder.vue'

const fontSizeClass = ref('font-size-medium')

const updateFontSize = (size) => {
  switch (size) {
    case 'small':
      fontSizeClass.value = 'font-size-small'
      break
    case 'medium':
      fontSizeClass.value = 'font-size-medium'
      break
    case 'large':
      fontSizeClass.value = 'font-size-large'
      break
    case 'xlarge':
      fontSizeClass.value = 'font-size-xlarge'
      break
    default:
      fontSizeClass.value = 'font-size-medium'
  }
}

const handleFontSizeChange = (event) => {
  updateFontSize(event.detail)
}

onMounted(() => {
  // 从localStorage读取字体大小设置
  const savedSize = localStorage.getItem('fontSize') || 'medium'
  updateFontSize(savedSize)
  // 监听字体大小变化事件
  window.addEventListener('fontSizeChanged', handleFontSizeChange)
})

onUnmounted(() => {
  // 移除事件监听器
  window.removeEventListener('fontSizeChanged', handleFontSizeChange)
})
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.app {
  min-height: 100vh;
  font-family: 'PingFang SC', 'Helvetica Neue', Arial, sans-serif;
  transition: font-size 0.3s ease;
}

/* 字体大小类 */
.app.font-size-small {
  font-size: 16px !important;
}

.app.font-size-medium {
  font-size: 20px !important;
}

.app.font-size-large {
  font-size: 24px !important;
}

.app.font-size-xlarge {
  font-size: 28px !important;
}

/* 标题字体大小 */
.app.font-size-small h1 {
  font-size: 28px !important;
}

.app.font-size-small h2 {
  font-size: 24px !important;
}

.app.font-size-small h3 {
  font-size: 22px !important;
}

.app.font-size-medium h1 {
  font-size: 36px !important;
}

.app.font-size-medium h2 {
  font-size: 32px !important;
}

.app.font-size-medium h3 {
  font-size: 28px !important;
}

.app.font-size-large h1 {
  font-size: 42px !important;
}

.app.font-size-large h2 {
  font-size: 38px !important;
}

.app.font-size-large h3 {
  font-size: 34px !important;
}

.app.font-size-xlarge h1 {
  font-size: 50px !important;
}

.app.font-size-xlarge h2 {
  font-size: 46px !important;
}

.app.font-size-xlarge h3 {
  font-size: 42px !important;
}

/* 段落和其他文本元素 */
.app.font-size-small p, 
.app.font-size-small span, 
.app.font-size-small div {
  font-size: 16px !important;
}

.app.font-size-medium p, 
.app.font-size-medium span, 
.app.font-size-medium div {
  font-size: 20px !important;
}

.app.font-size-large p, 
.app.font-size-large span, 
.app.font-size-large div {
  font-size: 24px !important;
}

.app.font-size-xlarge p, 
.app.font-size-xlarge span, 
.app.font-size-xlarge div {
  font-size: 30px !important;
}
</style>
