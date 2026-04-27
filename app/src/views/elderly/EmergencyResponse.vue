<template>
  <div class="emergency-response">
    <!-- 顶部导航栏 -->
    <div class="header">
      <button class="back-button" @click="goBack">
        <span class="back-icon">←</span>
        <span class="back-text">返回</span>
      </button>
      <h1 class="page-title">紧急求助</h1>
    </div>

    <!-- 求助状态 -->
    <div class="status-section">
      <div class="status-icon" :class="currentStep.iconClass">{{ currentStep.icon }}</div>
      <h2 class="status-title">{{ currentStep.title }}</h2>
      <p class="status-description">{{ currentStep.description }}</p>
      <div class="status-progress">
        <div class="progress-bar">
          <div class="progress-fill" :style="{ width: progress + '%' }"></div>
        </div>
        <p class="progress-text">{{ currentStep.progressText }}</p>
      </div>
    </div>

    <!-- 预计响应时间 - 只在第一步显示 -->
    <div class="response-time" v-if="currentStep.id === 1">
      <div class="time-icon">⏱️</div>
      <div class="time-info">
        <h3 class="time-title">预计响应时间</h3>
        <p class="time-value">{{ estimatedTime }} 分钟</p>
      </div>
    </div>

    <!-- 紧急联系人 - 只在第一步显示 -->
    <div class="contacts-section" v-if="currentStep.id === 1">
      <h3 class="section-title">紧急联系人</h3>
      <div class="contact-list">
        <div class="contact-item" v-for="contact in emergencyContacts" :key="contact.id">
          <div class="contact-icon">{{ contact.icon }}</div>
          <div class="contact-info">
            <p class="contact-name">{{ contact.name }}</p>
            <p class="contact-status">{{ contact.status }}</p>
          </div>
          <button class="contact-call" @click="callContact(contact.phone)">
            <span class="call-icon">📞</span>
          </button>
        </div>
      </div>
    </div>

    <!-- 附近子女端 - 只在第二步显示 -->
    <div class="nearby-section" v-if="currentStep.id === 2">
      <h3 class="section-title">附近子女端响应中...</h3>
      <div class="nearby-map" v-if="nearbyContacts.length > 0">
        <div class="map-container">
          <div class="map-center">
            <div class="user-icon">👴</div>
          </div>
          <div class="map-circles">
            <div class="circle circle-1"></div>
            <div class="circle circle-2"></div>
            <div class="circle circle-3"></div>
          </div>
          <div class="nearby-contact" v-for="(contact, index) in nearbyContacts" :key="contact.id" :style="getContactPosition(index)">
            <div class="contact-circle">
              <span class="contact-name">{{ contact.name }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="nearby-info" v-else>
        <p class="nearby-description">附近未找到子女端设备</p>
      </div>
    </div>

    <!-- 急救中心 - 只在第三步显示 -->
    <div class="emergency-center-section" v-if="currentStep.id === 3">
      <h3 class="section-title">联系急救中心</h3>
      <div class="emergency-center-info">
        <p class="emergency-description">正在联系急救中心，请稍候...</p>
        <button class="emergency-call-button" @click="callEmergencyCenter">
          <span class="emergency-call-icon">🚑</span>
          立即拨打 120
        </button>
      </div>
    </div>

    <!-- 操作按钮 -->
    <div class="action-buttons">
      <button class="cancel-button" @click="cancelSOS">
        <span class="cancel-icon">✕</span>
        取消求助
      </button>
      <button class="confirm-button" @click="confirmHelp">
        <span class="confirm-icon">✅</span>
        已获得帮助
      </button>
    </div>

    <!-- 自定义弹窗 -->
    <div class="custom-dialog" v-if="showDialog">
      <div class="dialog-content">
        <h3 class="dialog-title">{{ dialogTitle }}</h3>
        <p class="dialog-message">{{ dialogMessage }}</p>
        <div class="dialog-buttons">
          <button class="dialog-button" @click="closeDialog">确定</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const progress = ref(0)
const estimatedTime = ref(1)
const currentStep = ref({
  id: 1,
  title: '向子女发送求助信号',
  description: '我们正在联系您的紧急联系人',
  icon: '🚨',
  iconClass: 'step-1',
  progressText: '正在联系紧急联系人...'
})

const emergencyContacts = ref([
  { id: 1, name: '儿子', icon: '👨', phone: '13800138000', status: '已通知' },
  { id: 2, name: '女儿', icon: '👩', phone: '13900139000', status: '联系中' },
  { id: 3, name: '社区医生', icon: '👨‍⚕️', phone: '13700137000', status: '待通知' }
])

const nearbyContacts = ref([])

// 自定义弹窗状态
const showDialog = ref(false)
const dialogTitle = ref('')
const dialogMessage = ref('')

// 求助流程步骤
const steps = [
  {
    id: 1,
    title: '向子女发送求助信号',
    description: '我们正在联系您的紧急联系人',
    icon: '🚨',
    iconClass: 'step-1',
    progressText: '正在联系紧急联系人...'
  },
  {
    id: 2,
    title: '向附近子女端发送求助信号',
    description: '子女未响应，正在联系附近的子女端设备',
    icon: '📱',
    iconClass: 'step-2',
    progressText: '正在联系附近子女端...'
  },
  {
    id: 3,
    title: '联系急救中心',
    description: '附近无响应，正在联系急救中心',
    icon: '🚑',
    iconClass: 'step-3',
    progressText: '正在联系急救中心...'
  }
]

onMounted(() => {
  // 模拟求助流程
  simulateEmergencyProcess()
})

const simulateEmergencyProcess = () => {
  // 第一步：向子女发送求助信号（20秒）
  const step1Interval = setInterval(() => {
    if (progress.value < 33) {
      progress.value += 1.8 // 加速进度
    } else {
      clearInterval(step1Interval)
      // 进入第二步
      currentStep.value = steps[1]
      progress.value = 33
      
      // 模拟附近子女端
      simulateNearbyContacts()
      
      // 第二步：向附近子女端发送求助信号（25秒）
      const step2Interval = setInterval(() => {
        if (progress.value < 66) {
          progress.value += 1.5 // 加速进度
        } else {
          clearInterval(step2Interval)
          // 进入第三步
          currentStep.value = steps[2]
          progress.value = 66
          
          // 第三步：联系急救中心（15秒）
          const step3Interval = setInterval(() => {
            if (progress.value < 100) {
              progress.value += 2.5 // 加速进度
            } else {
              clearInterval(step3Interval)
            }
          }, 1000)
        }
      }, 1000)
    }
  }, 1000)
}

const simulateNearbyContacts = () => {
  // 模拟附近有子女端设备
  setTimeout(() => {
    nearbyContacts.value = [
      { id: 1, name: '小王', icon: '👨', phone: '13600136000', status: '联系中' },
      { id: 2, name: '小李', icon: '👩', phone: '13500135000', status: '联系中' },
      { id: 3, name: '小张', icon: '👨', phone: '13400134000', status: '联系中' },
      { id: 4, name: '小刘', icon: '👩', phone: '13300133000', status: '联系中' },
      { id: 5, name: '小陈', icon: '👨', phone: '13200132000', status: '联系中' },
      { id: 6, name: '小赵', icon: '👨', phone: '13100131000', status: '联系中' },
      { id: 7, name: '小周', icon: '👨', phone: '13000130000', status: '联系中' },
      { id: 8, name: '小吴', icon: '👩', phone: '12900129000', status: '联系中' },
      { id: 9, name: '小郑', icon: '👨', phone: '12800128000', status: '联系中' },
      { id: 10, name: '小冯', icon: '👩', phone: '12700127000', status: '联系中' }
    ]
  }, 1000)
  
  // 15秒后模拟附近无响应
  setTimeout(() => {
    nearbyContacts.value = []
  }, 16000)
}

// 计算附近联系人的位置
const getContactPosition = (index) => {
  const positions = [
    { top: '20%', right: '20%'},
    { top: '35%', left: '15%'},
    { top: '50%', right: '25%'},
    { top: '65%', left: '20%'},
    { top: '80%', right: '30%'},
    {top: '20%', left: '50%'},
    {top: '30%', left: '60%'},
    {top: '50%', left: '70%'},
    {top: '70%', right: '80%'},
    {top: '90%', left: '40%'}
  ]
  return positions[index % positions.length]
}

const goBack = () => {
  router.back()
}

const callContact = (phone) => {
  console.log(`拨打紧急联系人电话：${phone}`)
  // 找到对应的联系人姓名
  const contact = [...emergencyContacts.value, ...nearbyContacts.value].find(c => c.phone === phone)
  const contactName = contact ? contact.name : '紧急联系人'
  // 跳转到app内部通话界面
  router.push({
    path: '/elderly/emergency-call',
    query: { name: contactName, phone: phone }
  })
}

const callEmergencyCenter = () => {
  console.log('拨打急救中心电话：120')
  // 模拟拨打120
  // 跳转到app内部通话界面
  router.push({
    path: '/elderly/emergency-call',
    query: { name: "急救中心", phone: "120" }
  })
}

// 显示自定义弹窗
const showCustomDialog = (title, message) => {
  dialogTitle.value = title
  dialogMessage.value = message
  showDialog.value = true
}

// 关闭自定义弹窗
const closeDialog = () => {
  showDialog.value = false
}

const cancelSOS = () => {
  showCustomDialog('取消求助', '确定要取消紧急求助吗？')
  // 监听弹窗关闭后执行取消操作
  setTimeout(() => {
    if (!showDialog.value) {
      console.log('取消紧急求助')
      router.push('/elderly')
    }
  }, 3000)
}

const confirmHelp = () => {
  showCustomDialog('已获得帮助', '感谢您的反馈，祝您健康！')
  // 监听弹窗关闭后执行操作
  setTimeout(() => {
    if (!showDialog.value) {
      router.push('/elderly')
    }
  }, 2000)
}
</script>

<style scoped>
.emergency-response {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
  padding-bottom: 80px;
}

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

.status-section {
  text-align: center;
  padding: 50px 20px;
  background: rgba(255, 255, 255, 0.9);
  margin: 20px;
  border-radius: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.status-icon {
  font-size: 5rem;
  margin-bottom: 20px;
}

.status-title {
  font-size: 2rem;
  font-weight: bold;
  color: #ff4757;
  margin-bottom: 10px;
}

.status-description {
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 30px;
}

.status-progress {
  margin-top: 30px;
}

.progress-bar {
  width: 100%;
  height: 15px;
  background: #f0f0f0;
  border-radius: 10px;
  overflow: hidden;
  margin-bottom: 10px;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #ff6b6b 0%, #ee5a6f 100%);
  transition: width 1s ease;
  border-radius: 10px;
}

.progress-text {
  font-size: 1rem;
  color: #666;
  margin: 0;
}

.response-time {
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.85);
  margin: 0 20px 20px;
  padding: 25px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.time-icon {
  font-size: 3rem;
  margin-right: 20px;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 190, 70, 0.2);
  border-radius: 50%;
}

.time-info {
  flex: 1;
}

.time-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.time-value {
  font-size: 1.8rem;
  font-weight: bold;
  color: #ff9e43;
  margin: 0;
}

.contacts-section {
  background: rgba(255, 255, 255, 0.85);
  margin: 0 20px 20px;
  padding: 25px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

.contact-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.contact-item {
  display: flex;
  align-items: center;
  background: #f8f9fa;
  border-radius: 12px;
  padding: 20px;
  transition: all 0.3s ease;
}

.contact-item:hover {
  background: #e9ecef;
  transform: translateY(-2px);
}

.contact-icon {
  font-size: 2.5rem;
  margin-right: 20px;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 154, 158, 0.1);
  border-radius: 50%;
}

.contact-info {
  flex: 1;
}

.contact-name {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.contact-status {
  font-size: 1rem;
  color: #666;
  margin: 0;
}

.contact-call {
  background: none;
  border: none;
  cursor: pointer;
  padding: 10px;
  border-radius: 50%;
  transition: all 0.3s ease;
}

.contact-call:hover {
  background: rgba(0, 0, 0, 0.1);
  transform: scale(1.1);
}

.call-icon {
  font-size: 1.5rem;
  color: #ff6b6b;
}

.action-buttons {
  display: flex;
  flex-direction: row;
  gap: 15px;
  padding: 0 20px 40px;
  justify-content: center;
  flex-wrap: wrap;
}

.cancel-button {
  flex: 1;
  min-width: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 15px 20px;
  background: white;
  border: 2px solid #ff6b6b;
  border-radius: 12px;
  color: #ff6b6b;
  font-size: 1.2rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.cancel-button:hover {
  background: #ff6b6b;
  color: white;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 107, 107, 0.3);
}

.confirm-button {
  flex: 1;
  min-width: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 15px 20px;
  background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 1.2rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.confirm-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
}

.cancel-icon,
.confirm-icon {
  margin-right: 10px;
  font-size: 1.2rem;
}

/* 步骤图标样式 */
.status-icon.step-1 {
  color: #ff4757;
  animation: pulse 1s infinite;
}

.status-icon.step-2 {
  color: #409eff;
  animation: pulse 1s infinite;
}

.status-icon.step-3 {
  color: #ff9e43;
  animation: pulse 1s infinite;
}

/* 附近子女端样式 */
.nearby-section {
  background: rgba(255, 255, 255, 0.85);
  margin: 0 20px 20px;
  padding: 25px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.nearby-info {
  margin-top: 15px;
}

.nearby-description {
  font-size: 1.1rem;
  color: #666;
  margin-bottom: 15px;
}

/* 附近子女端地图样式 */
.nearby-map {
  margin-top: 20px;
}

.map-container {
  position: relative;
  width: 100%;
  height: 350px;
  border-radius: 12px;
  background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
  overflow: hidden;
  box-shadow: inset 0 0 20px rgba(0, 0, 0, 0.1);
}

/* 地图网格背景 */
.map-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: 
    linear-gradient(rgba(0, 0, 0, 0.1) 1px, transparent 1px),
    linear-gradient(90deg, rgba(0, 0, 0, 0.1) 1px, transparent 1px);
  background-size: 20px 20px;
  z-index: 1;
}

.map-center {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 10;
}

.user-icon {
  font-size: 3.5rem;
  animation: pulse 2s infinite;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 50%;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.map-circles {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.circle {
  position: absolute;
  border: 2px solid #ff4757;
  border-radius: 50%;
  animation: pulse-ring 3s infinite;
  background: rgba(255, 71, 87, 0.1);
}

.circle-1 {
  width: 120px;
  height: 120px;
  top: -60px;
  left: -60px;
  animation-delay: 0s;
}

.circle-2 {
  width: 200px;
  height: 200px;
  top: -100px;
  left: -100px;
  animation-delay: 0.5s;
}

.circle-3 {
  width: 280px;
  height: 280px;
  top: -140px;
  left: -140px;
  animation-delay: 1s;
}

.nearby-contact {
  position: absolute;
  z-index: 5;
  animation: slideIn 0.5s ease forwards;
}

.nearby-contact:nth-child(1) { animation-delay: 0.1s; }
.nearby-contact:nth-child(2) { animation-delay: 0.2s; }
.nearby-contact:nth-child(3) { animation-delay: 0.3s; }
.nearby-contact:nth-child(4) { animation-delay: 0.4s; }
.nearby-contact:nth-child(5) { animation-delay: 0.5s; }

.contact-circle {
  width: 65px;
  height: 65px;
  background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: bold;
  animation: pulse 2s infinite;
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.4);
  border: 3px solid white;
}

.contact-circle::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 20px;
  height: 20px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 50%;
  animation: pulse 1s infinite;
}

.contact-name {
  font-size: 0.9rem;
  z-index: 1;
  margin-top: 5px;
}

/* 新增动画 */
@keyframes slideIn {
  from {
    transform: scale(0);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

/* 自定义弹窗样式 */
.custom-dialog {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeIn 0.3s ease;
}

.dialog-content {
  background: white;
  border-radius: 16px;
  padding: 30px;
  width: 80%;
  max-width: 400px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  text-align: center;
  animation: slideUp 0.3s ease;
}

.dialog-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
}

.dialog-message {
  font-size: 1.1rem;
  color: #666;
  margin-bottom: 30px;
  line-height: 1.5;
}

.dialog-buttons {
  display: flex;
  justify-content: center;
}

.dialog-button {
  padding: 12px 30px;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  border: none;
  border-radius: 10px;
  color: white;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.dialog-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 217, 61, 0.3);
}

/* 新增动画 */
@keyframes pulse-ring {
  0% {
    transform: scale(0.8);
    opacity: 1;
  }
  100% {
    transform: scale(1.2);
    opacity: 0;
  }
}

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
    transform: translateY(50px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

/* 急救中心样式 */
.emergency-center-section {
  background: rgba(255, 255, 255, 0.85);
  margin: 0 20px 20px;
  padding: 25px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.emergency-center-info {
  margin-top: 15px;
  text-align: center;
}

.emergency-description {
  font-size: 1.1rem;
  color: #666;
  margin-bottom: 20px;
}

.emergency-call-button {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 1.2rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  margin: 0 auto;
  max-width: 200px;
}

.emergency-call-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 107, 107, 0.3);
}

.emergency-call-icon {
  margin-right: 10px;
  font-size: 1.5rem;
}

/* 动画效果 */
@keyframes pulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
}

@media (max-width: 768px) {
  .status-section {
    padding: 40px 15px;
    margin: 15px;
  }
  
  .status-icon {
    font-size: 4rem;
  }
  
  .status-title {
    font-size: 1.8rem;
  }
  
  .response-time,
  .contacts-section,
  .nearby-section,
  .emergency-center-section {
    margin: 0 15px 15px;
    padding: 20px;
  }
  
  .time-icon {
    font-size: 2.5rem;
    width: 60px;
    height: 60px;
  }
  
  .action-buttons {
    padding: 0 15px 30px;
  }
  
  .cancel-button,
  .confirm-button {
    padding: 18px;
  }
}
</style>