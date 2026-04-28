<template>
  <div class="medicine-reminder" v-if="isVisible" @click="handleBackdropClick">
    <div class="reminder-container" @click.stop>
      <!-- 顶部区域：动画人物和标题 -->
      <div class="header-section">
        <div class="medicine-icon-container">
          <img src="/TakeMedicine.png" alt="用药提醒" class="medicine-image" />
        </div>
        <h3 class="reminder-title">用药提醒</h3>
      </div>
      
      <!-- 中间内容区域 -->
      <div class="content-section">
        <div class="medicine-info">
          <div class="medicine-name">{{ medicineName }}</div>
          <div class="medicine-time">{{ reminderTime }}</div>
        </div>
        
        <!-- 用药安全审查结果 -->
        <div class="safety-review-section" v-if="safetyReview">
          <div class="review-header" :class="safetyReview.status">
            <span class="review-icon">{{ safetyReview.icon }}</span>
            <span class="review-title">{{ safetyReview.title }}</span>
          </div>
          <div class="review-details">
            <div class="review-item" v-for="(item, index) in safetyReview.items" :key="index" :class="item.status">
              <span class="item-icon">{{ item.icon }}</span>
              <div class="item-content">
                <span class="item-name">{{ item.name }}</span>
                <span class="item-message" v-if="item.message">{{ item.message }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 底部操作按钮 -->
      <div class="reminder-actions">
        <button class="action-button take" @click="takeMedicine">
          <span class="button-icon">✅</span>
          已服用
        </button>
        <button class="action-button later" @click="remindLater">
          <span class="button-icon">⏰</span>
          稍后提醒
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, nextTick } from 'vue'

// 状态管理
const isVisible = ref(false)
const medicineName = ref('阿莫西林胶囊')
const reminderTime = ref('')
const reminderMessage = ref('该吃药啦！')
const containerHeight = ref('auto')

// 用药安全审查结果
const safetyReview = ref(null)

// 定时器
let reminderTimer = null
let checkTimer = null

// 背景滚动锁定
const lockBodyScroll = () => {
  const body = document.body
  body.style.overflow = 'hidden'
  body.style.position = 'fixed'
  body.style.width = '100%'
  body.style.height = '100%'
}

// 背景滚动解锁
const unlockBodyScroll = () => {
  const body = document.body
  body.style.overflow = ''
  body.style.position = ''
  body.style.width = ''
  body.style.height = ''
}

// 设置提醒时间
const setReminderTime = () => {
  // 模拟设置提醒时间为当前时间后10秒（实际应用中应该从用户设置中获取）
  const now = new Date()
  now.setSeconds(now.getSeconds() + 10)
  reminderTime.value = now.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
  return now
}

// 检查是否到提醒时间
const checkReminder = () => {
  const now = new Date()
  const reminderDateTime = setReminderTime()
  
  // 计算时间差
  const timeDiff = reminderDateTime.getTime() - now.getTime()
  
  if (timeDiff > 0) {
    // 设置定时器
    reminderTimer = setTimeout(() => {
      showReminder()
    }, timeDiff)
  }
}

// 执行用药安全审查
const performSafetyReview = () => {
  // 模拟自动用药安全审查
  const reviewItems = []
  
  // 检查药物冲突（模拟）
  const hasConflict = Math.random() > 0.7
  if (hasConflict) {
    reviewItems.push({
      name: '药物冲突',
      icon: '⚡',
      status: 'warning',
      message: '与降压药存在潜在冲突'
    })
  } else {
    reviewItems.push({
      name: '药物冲突',
      icon: '✅',
      status: 'safe',
      message: ''
    })
  }
  
  // 检查重复用药（模拟）
  const hasDuplicate = Math.random() > 0.8
  if (hasDuplicate) {
    reviewItems.push({
      name: '重复用药',
      icon: '🔄',
      status: 'warning',
      message: '今日已服用同类药物'
    })
  } else {
    reviewItems.push({
      name: '重复用药',
      icon: '✅',
      status: 'safe',
      message: ''
    })
  }
  
  // 检查剂量超标（模拟）
  const hasDosageIssue = Math.random() > 0.85
  if (hasDosageIssue) {
    reviewItems.push({
      name: '剂量检查',
      icon: '📊',
      status: 'warning',
      message: '今日剂量接近上限'
    })
  } else {
    reviewItems.push({
      name: '剂量检查',
      icon: '✅',
      status: 'safe',
      message: ''
    })
  }
  
  // 基础病适配性检查（模拟）
  const hasHealthIssue = Math.random() > 0.75
  if (hasHealthIssue) {
    reviewItems.push({
      name: '基础病适配',
      icon: '❤️',
      status: 'warning',
      message: '高血压患者需监测血压'
    })
  } else {
    reviewItems.push({
      name: '基础病适配',
      icon: '✅',
      status: 'safe',
      message: ''
    })
  }
  
  // 判断总体状态
  const hasWarnings = reviewItems.some(item => item.status === 'warning')
  
  safetyReview.value = {
    status: hasWarnings ? 'warning' : 'safe',
    icon: hasWarnings ? '⚠️' : '✅',
    title: hasWarnings ? '用药需注意' : '用药安全',
    items: reviewItems
  }
}

// 显示提醒
const showReminder = async () => {
  // 自动执行用药安全审查
  performSafetyReview()
  
  isVisible.value = true
  lockBodyScroll()
  
  // 等待DOM更新后计算高度
  await nextTick()
  const container = document.querySelector('.reminder-container')
  if (container) {
    containerHeight.value = `${container.offsetHeight}px`
  }
}

// 已服用
const takeMedicine = () => {
  isVisible.value = false
  unlockBodyScroll()
  // 重新设置提醒
  checkReminder()
}

// 稍后提醒
const remindLater = () => {
  isVisible.value = false
  unlockBodyScroll()
  // 5分钟后再次提醒
  setTimeout(() => {
    showReminder()
  }, 5 * 60 * 1000)
}

// 点击背景关闭
const handleBackdropClick = () => {
  isVisible.value = false
  unlockBodyScroll()
}

onMounted(() => {
  // 初始化提醒
  checkReminder()
  
  // 每小时检查一次提醒设置
  checkTimer = setInterval(() => {
    checkReminder()
  }, 60 * 60 * 1000)
})

onUnmounted(() => {
  // 清理定时器
  if (reminderTimer) {
    clearTimeout(reminderTimer)
  }
  if (checkTimer) {
    clearInterval(checkTimer)
  }
  // 确保解锁滚动
  unlockBodyScroll()
})
</script>

<style scoped>
.medicine-reminder {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10000;
  animation: fadeIn 0.3s ease;
  cursor: pointer;
}

/* 为弹窗添加与首页一致的配色元素 */
.reminder-container {
  background: white;
  border-radius: 20px;
  padding: 20px;
  width: 90%;
  max-width: 400px;
  max-height: 80vh;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  display: flex;
  flex-direction: column;
  animation: slideUp 0.3s ease;
  position: relative;
  overflow: hidden;
  cursor: default;
  transition: height 0.3s ease;
}

.reminder-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 8px;
  background: linear-gradient(to right, #ffd93d 0%, #ff9e43 100%);
  border-radius: 20px 20px 0 0;
}

.header-section {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 5px;
  margin-bottom: 15px;
  padding: 0;
  margin: 0 0 15px 0;
}

.medicine-icon-container {
  width: 60px;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  padding: 0;
  flex-shrink: 0;
}

.medicine-image {
  width: 100%;
  height: 100%;
  object-fit: contain;
  animation: bounce 2s infinite;
}

.content-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 15px;
  text-align: center;
  overflow-y: auto;
  max-height: 50vh;
}

.reminder-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin: 0;
  text-align: center;
}

.medicine-info {
  background: #f8f9fa;
  padding: 15px;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.medicine-name {
  font-size: 1.2rem;
  font-weight: bold;
  color: #ff9e43;
}

.medicine-time {
  font-size: 1rem;
  color: #666;
}

/* 用药安全审查样式 */
.safety-review-section {
  background: #f8f9fa;
  border-radius: 12px;
  padding: 15px;
  margin: 10px 0;
}

.review-header {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  border-radius: 8px;
  margin-bottom: 12px;
  font-weight: bold;
  font-size: 1.1rem;
}

.review-header.safe {
  background: #d1fae5;
  color: #059669;
}

.review-header.warning {
  background: #fef3c7;
  color: #d97706;
}

.review-icon {
  font-size: 1.3rem;
}

.review-details {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.review-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 10px;
  border-radius: 6px;
  background: white;
  border-left: 3px solid transparent;
}

.review-item.safe {
  border-left-color: #059669;
}

.review-item.warning {
  border-left-color: #d97706;
  background: #fffbeb;
}

.item-icon {
  font-size: 1.1rem;
  width: 24px;
  text-align: center;
}

.item-content {
  display: flex;
  flex-direction: column;
  flex: 1;
  text-align: left;
}

.item-name {
  font-size: 0.95rem;
  font-weight: 600;
  color: #333;
}

.item-message {
  font-size: 0.85rem;
  color: #666;
  margin-top: 2px;
}

.reminder-actions {
  display: flex;
  gap: 10px;
  width: 100%;
  padding-top: 15px;
  border-top: 1px solid #eee;
  margin-top: 10px;
}

.action-button {
  flex: 1;
  padding: 12px;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.action-button.take {
  background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
  color: white;
}

.action-button.take:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
}

.action-button.later {
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  color: white;
}

.action-button.later:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 217, 61, 0.3);
}

.button-icon {
  font-size: 1.2rem;
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideUp {
  from {
    transform: translateY(100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .reminder-container {
    padding: 20px;
    width: 90%;
  }
  
  .reminder-title {
    font-size: 1.3rem;
  }
  
  .medicine-name {
    font-size: 1.1rem;
  }
  
  .action-button {
    padding: 10px;
    font-size: 0.9rem;
  }
  
  .medicine-icon-container {
    width: 60px;
    height: 60px;
  }
  
  .header-section {
    flex-direction: row;
    gap: 5px;
  }
}
</style>