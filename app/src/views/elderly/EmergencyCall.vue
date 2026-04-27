<template>
  <div class="emergency-call">
    <!-- 顶部导航 -->
    <div class="header">
      <div class="back-button" @click="goBack">
        <span class="back-icon">←</span>
        <span class="back-text">返回</span>
      </div>
      <h1 class="page-title">紧急通话</h1>
    </div>

    <!-- 通话界面 -->
    <div class="call-container">
      <div class="contact-info">
        <div class="contact-avatar">{{ getAvatar(name) }}</div>
        <h2 class="contact-name">{{ name }}</h2>
        <p class="contact-phone">{{ phone }}</p>
      </div>

      <div class="call-status">
        <p class="status-text">{{ callStatus }}</p>
        <div class="call-timer" v-if="isCalling">{{ callTime }}</div>
      </div>

      <!-- 控制按钮 -->
      <div class="call-buttons">
        <button class="call-button mute" @click="toggleMute">
          <span class="button-icon">{{ isMuted ? '🔇' : '🔊' }}</span>
          <span class="button-text">静音</span>
        </button>
        <button class="call-button speaker" @click="toggleSpeaker">
          <span class="button-icon">📢</span>
          <span class="button-text">扬声器</span>
        </button>
        <button class="call-button end" @click="endCall">
          <span class="button-icon">🔴</span>
          <span class="button-text">结束</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

// 获取路由参数
const name = ref(route.query.name || '联系人')
const phone = ref(route.query.phone || '')

// 状态管理
const callStatus = ref('正在呼叫...')
const isCalling = ref(false)
const isMuted = ref(false)
const isSpeakerOn = ref(false)
const callTime = ref('00:00')
let timer = null
let seconds = 0

// 导航方法
const goBack = () => {
  router.back()
}

// 获取头像
const getAvatar = (name) => {
  const avatarMap = {
    '儿子': '👨',
    '女儿': '👩',
    '社区医生': '👨‍⚕️'
  }
  return avatarMap[name] || '👤'
}

// 切换静音
const toggleMute = () => {
  isMuted.value = !isMuted.value
}

// 切换扬声器
const toggleSpeaker = () => {
  isSpeakerOn.value = !isSpeakerOn.value
}

// 结束通话
const endCall = () => {
  clearInterval(timer)
  callStatus.value = '通话结束'
  setTimeout(() => {
    router.back()
  }, 1500)
}

// 更新通话时间
const updateCallTime = () => {
  seconds++
  const mins = Math.floor(seconds / 60).toString().padStart(2, '0')
  const secs = (seconds % 60).toString().padStart(2, '0')
  callTime.value = `${mins}:${secs}`
}

onMounted(() => {
  // 模拟呼叫过程
  setTimeout(() => {
    callStatus.value = '通话中'
    isCalling.value = true
    timer = setInterval(updateCallTime, 1000)
  }, 2000)
})

onUnmounted(() => {
  if (timer) {
    clearInterval(timer)
  }
})
</script>

<style scoped>
.emergency-call {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
  display: flex;
  flex-direction: column;
}

/* 顶部导航 */
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.back-button {
  display: flex;
  align-items: center;
  background: none;
  border: none;
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;
  transition: background 0.3s ease;
  color: #333;
}

.back-button:hover {
  background: rgba(0, 0, 0, 0.1);
}

.back-icon {
  font-size: 1.5rem;
  margin-right: 5px;
}

.back-text {
  font-size: 1.2rem;
  font-weight: bold;
}

.page-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin: 0;
}

/* 通话界面 */
.call-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 60px 20px;
  text-align: center;
}

.contact-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 60px;
}

.contact-avatar {
  font-size: 6rem;
  margin-bottom: 20px;
}

.contact-name {
  font-size: 2rem;
  font-weight: bold;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  margin-bottom: 10px;
}

.contact-phone {
  font-size: 1.2rem;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  opacity: 0.9;
}

.call-status {
  margin: 40px 0;
}

.status-text {
  font-size: 1.5rem;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  margin-bottom: 20px;
}

.call-timer {
  font-size: 3rem;
  font-weight: bold;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

/* 控制按钮 */
.call-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 40px;
  margin-top: 60px;
}

.call-button {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border: none;
  background: rgba(255, 255, 255, 0.8);
  color: #333;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.call-button:hover {
  background: white;
  transform: scale(1.1);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.call-button.end {
  background: rgba(255, 107, 107, 0.1);
  color: #ff6b6b;
  border: 2px solid #ff6b6b;
}

.call-button.end:hover {
  background: #ff6b6b;
  color: white;
}

.button-icon {
  font-size: 1.5rem;
  margin-bottom: 5px;
}

.button-text {
  font-size: 0.9rem;
  font-weight: bold;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .contact-avatar {
    font-size: 5rem;
  }
  
  .contact-name {
    font-size: 1.5rem;
  }
  
  .call-timer {
    font-size: 2rem;
  }
  
  .call-buttons {
    gap: 30px;
  }
  
  .call-button {
    width: 70px;
    height: 70px;
  }
  
  .button-icon {
    font-size: 1.2rem;
  }
  
  .button-text {
    font-size: 0.8rem;
  }
}
</style>