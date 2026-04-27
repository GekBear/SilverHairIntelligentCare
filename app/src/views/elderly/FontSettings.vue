<template>
  <div class="font-settings">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">字体大小</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <div class="card">
        <h3>选择字体大小</h3>
        <div class="font-size-option">
          <button class="font-size-btn" :class="{ active: selectedSize === 'small' }" @click="selectFontSize('small')">小</button>
          <button class="font-size-btn" :class="{ active: selectedSize === 'medium' }" @click="selectFontSize('medium')">中</button>
          <button class="font-size-btn" :class="{ active: selectedSize === 'large' }" @click="selectFontSize('large')">大</button>
          <button class="font-size-btn" :class="{ active: selectedSize === 'xlarge' }" @click="selectFontSize('xlarge')">超大</button>
        </div>

        <div class="mt-24">
          <h4>预览效果</h4>
          <p :style="{ fontSize: previewFontSize }" class="preview-text">
            这是一段预览文字，用于展示不同字体大小的效果。
            当您选择不同的字体大小时，这段文字的大小会相应变化。
            请选择一个适合您阅读的字体大小。
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft } from '@element-plus/icons-vue'
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 字体大小状态
const selectedSize = ref('medium')

// 根据选择的字体大小计算预览字体大小
const previewFontSize = computed(() => {
  switch (selectedSize.value) {
    case 'small':
      return '24px'
    case 'medium':
      return '30px'
    case 'large':
      return '36px'
    case 'xlarge':
      return '42px'
    default:
      return '30px'
  }
})

const goBack = () => {
  router.back()
}

const selectFontSize = (size) => {
  selectedSize.value = size
  // 保存字体大小设置到localStorage
  localStorage.setItem('fontSize', size)
  // 触发全局字体大小更新
  window.dispatchEvent(new CustomEvent('fontSizeChanged', { detail: size }))
  console.log('字体大小已设置为：', size)
}

onMounted(() => {
  // 从localStorage读取字体大小设置
  const savedSize = localStorage.getItem('fontSize')
  if (savedSize) {
    selectedSize.value = savedSize
  }
  // 初始化字体大小
  window.dispatchEvent(new CustomEvent('fontSizeChanged', { detail: selectedSize.value }))
})
</script>

<style scoped>
.font-settings {
  height: 100vh;
  background-color: #f5f5f5;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 50px;
  background-color: #fff;
  padding: 0 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-back {
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.header-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.container {
  padding: 20px;
}

.card {
  background-color: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.card h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
}

.font-size-option {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
}

.font-size-btn {
  flex: 1;
  height: 40px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  background-color: #fff;
  font-size: 14px;
  color: #606266;
  cursor: pointer;
  transition: all 0.3s;
}

.font-size-btn:hover {
  border-color: #409eff;
  color: #409eff;
}

.font-size-btn.active {
  background-color: #409eff;
  border-color: #409eff;
  color: #fff;
}

.mt-24 {
  margin-top: 24px;
}

.card h4 {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 16px;
}

.preview-text {
  line-height: 1.5;
  color: #606266;
}
</style>