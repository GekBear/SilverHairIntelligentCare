<template>
  <div class="family-overview">
    <!-- 头部 -->
    <div class="header">
      <div class="header-left">
        <h1 class="title">家庭总览</h1>
        <p class="date">{{ currentDate }}</p>
      </div>
      <div class="header-right">
        <div class="time">{{ currentTime }}</div>
        <button class="notification-btn" @click="goToMessageCenter">
          <span class="notification-icon">🔔</span>
          <span class="notification-badge">2</span>
        </button>
      </div>
    </div>

    <!-- 老人状态卡片 -->
    <div class="status-card">
      <div class="status-header">
        <div class="elderly-info">
          <div class="elderly-avatar">
            <span class="avatar-icon">👴</span>
            <div class="avatar-border"></div>
          </div>
          <div class="elderly-details">
            <h2 class="elderly-name">张大爷</h2>
            <p class="elderly-relationship">父亲</p>
            <p class="elderly-id">ID: 10001</p>
          </div>
        </div>
        <div class="status-indicator" :class="elderlyStatus.status">
          <div class="status-dot"></div>
          <span class="status-text">{{ elderlyStatus.text }}</span>
        </div>
      </div>
      <div class="status-body">
        <div class="status-item" @click="goToHealthDetails">
          <div class="status-icon location-icon">📍</div>
          <div class="status-content">
            <p class="status-label">当前位置</p>
            <p class="status-value">{{ elderlyLocation }}</p>
          </div>
          <div class="status-arrow">→</div>
        </div>
        <div class="status-item" @click="goToHealthDetails">
          <div class="status-icon heart-icon">❤️</div>
          <div class="status-content">
            <p class="status-label">心率</p>
            <p class="status-value">{{ healthData.heartRate }} BPM</p>
          </div>
          <div class="status-arrow">→</div>
        </div>
        <div class="status-item" @click="goToHealthDetails">
          <div class="status-icon blood-icon">💉</div>
          <div class="status-content">
            <p class="status-label">血压</p>
            <p class="status-value">{{ healthData.bloodPressure.systolic }}/{{ healthData.bloodPressure.diastolic }} mmHg</p>
          </div>
          <div class="status-arrow">→</div>
        </div>
      </div>
    </div>

    <!-- 快捷入口 -->
    <div class="quick-access">
      <h2 class="section-title">快捷操作</h2>
      <div class="quick-buttons">
        <button class="quick-button" @click="goToHealthDetails">
          <div class="quick-icon health-icon">
            <span>📊</span>
            <div class="quick-icon-bg health-bg"></div>
          </div>
          <span class="quick-text">健康详情</span>
        </button>
        <button class="quick-button" @click="goToSafetyGuard">
          <div class="quick-icon safety-icon">
            <span>🛡️</span>
            <div class="quick-icon-bg safety-bg"></div>
          </div>
          <span class="quick-text">安全守护</span>
        </button>
        <button class="quick-button" @click="goToMessageCenter">
          <div class="quick-icon message-icon">
            <span>💬</span>
            <div class="quick-icon-bg message-bg"></div>
          </div>
          <span class="quick-text">留言中心</span>
        </button>
        <button class="quick-button" @click="goToHealthReport">
          <div class="quick-icon report-icon">
            <span>📋</span>
            <div class="quick-icon-bg report-bg"></div>
          </div>
          <span class="quick-text">健康报告</span>
        </button>
        <button class="quick-button" @click="goToEmergencyAssist">
          <div class="quick-icon emergency-icon">
            <span>🚨</span>
            <div class="quick-icon-bg emergency-bg"></div>
          </div>
          <span class="quick-text">紧急求助</span>
        </button>
      </div>
    </div>

    <!-- 健康数据概览 -->
    <div class="health-overview">
      <div class="section-header">
        <h2 class="section-title">健康数据</h2>
        <button class="section-action" @click="goToHealthDetails">查看全部</button>
      </div>
      <div class="health-cards">
        <div class="health-card" @click="goToHealthDetails">
          <div class="health-icon heart-icon">
            <span>❤️</span>
            <div class="health-icon-bg heart-bg"></div>
          </div>
          <div class="health-content">
            <h3 class="health-title">心率</h3>
            <p class="health-value">{{ healthData.heartRate }} <span class="health-unit">BPM</span></p>
            <p class="health-status normal">正常</p>
          </div>
          <div class="health-arrow">→</div>
        </div>
        <div class="health-card" @click="goToHealthDetails">
          <div class="health-icon blood-icon">
            <span>💉</span>
            <div class="health-icon-bg blood-bg"></div>
          </div>
          <div class="health-content">
            <h3 class="health-title">血压</h3>
            <p class="health-value">{{ healthData.bloodPressure.systolic }}/{{ healthData.bloodPressure.diastolic }} <span class="health-unit">mmHg</span></p>
            <p class="health-status normal">正常</p>
          </div>
          <div class="health-arrow">→</div>
        </div>
        <div class="health-card" @click="goToHealthDetails">
          <div class="health-icon sugar-icon">
            <span>🍬</span>
            <div class="health-icon-bg sugar-bg"></div>
          </div>
          <div class="health-content">
            <h3 class="health-title">血糖</h3>
            <p class="health-value">{{ healthData.bloodSugar }} <span class="health-unit">mmol/L</span></p>
            <p class="health-status normal">正常</p>
          </div>
          <div class="health-arrow">→</div>
        </div>
        <div class="health-card" @click="goToHealthDetails">
          <div class="health-icon steps-icon">
            <span>🚶</span>
            <div class="health-icon-bg steps-bg"></div>
          </div>
          <div class="health-content">
            <h3 class="health-title">步数</h3>
            <p class="health-value">{{ healthData.steps }} <span class="health-unit">步</span></p>
            <p class="health-status normal">良好</p>
          </div>
          <div class="health-arrow">→</div>
        </div>
      </div>
    </div>

    <!-- 告警信息 -->
    <div class="alerts">
      <div class="section-header">
        <h2 class="section-title">告警信息</h2>
        <button class="section-action" @click="goToMessageCenter">查看全部</button>
      </div>
      <div class="alert-list">
        <div class="alert-item">
          <div class="alert-icon warning">⚠️</div>
          <div class="alert-content">
            <h3 class="alert-title">用药提醒</h3>
            <p class="alert-text">张大爷需要在15:00服用降糖药</p>
            <p class="alert-time">10分钟前</p>
          </div>
          <div class="alert-action">
            <button class="action-btn">处理</button>
          </div>
        </div>
        <div class="alert-item">
          <div class="alert-icon info">ℹ️</div>
          <div class="alert-content">
            <h3 class="alert-title">健康数据更新</h3>
            <p class="alert-text">张大爷的健康数据已更新</p>
            <p class="alert-time">1小时前</p>
          </div>
          <div class="alert-action">
            <button class="action-btn">查看</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const currentTime = ref('')
const currentDate = ref('')

const elderlyStatus = ref({
  status: 'normal',
  text: '安全'
})

const elderlyLocation = ref('家')

const healthData = ref({
  heartRate: 72,
  bloodPressure: { systolic: 120, diastolic: 80 },
  bloodSugar: 5.6,
  steps: 4567
})

// 更新当前时间和日期
const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleTimeString('zh-CN', { 
    hour: '2-digit', 
    minute: '2-digit' 
  })
  currentDate.value = now.toLocaleDateString('zh-CN', { 
    year: 'numeric', 
    month: 'long', 
    day: 'numeric',
    weekday: 'long'
  })
}

// 导航到健康详情
const goToHealthDetails = () => {
  // 健康详情页面
  router.push('/family/health-details')
}

// 导航到安全守护
const goToSafetyGuard = () => {
  // 安全守护页面
  router.push('/family/safety-guard')
}

// 导航到留言中心
const goToMessageCenter = () => {
  // 留言中心页面
  router.push('/family/message-center')
}

// 导航到健康报告
const goToHealthReport = () => {
  // 健康报告页面
  router.push('/family/health-report')
}

// 导航到紧急求助
const goToEmergencyAssist = () => {
  // 紧急求助页面
  router.push('/family/emergency-assist')
}

onMounted(() => {
  updateTime()
  setInterval(updateTime, 60000) // 每分钟更新一次时间
})
</script>

<style scoped>
.family-overview {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  padding-bottom: 40px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 30px;
  color: white;
}

.header-left {
  flex: 1;
}

.title {
  font-size: 2.2rem;
  font-weight: bold;
  margin-bottom: 5px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.date {
  font-size: 1.1rem;
  opacity: 0.9;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.time {
  font-size: 1.3rem;
  opacity: 0.9;
  font-weight: bold;
}

.notification-btn {
  position: relative;
  background: rgba(255, 255, 255, 0.2);
  border: none;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.notification-btn:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: scale(1.1);
}

.notification-icon {
  font-size: 1.5rem;
}

.notification-badge {
  position: absolute;
  top: -5px;
  right: -5px;
  background: #ff4757;
  color: white;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  font-size: 0.8rem;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(255, 71, 87, 0.7);
  }
  70% {
    box-shadow: 0 0 0 10px rgba(255, 71, 87, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(255, 71, 87, 0);
  }
}

.status-card {
  background: white;
  border-radius: 24px;
  padding: 35px;
  margin-bottom: 30px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.status-card:hover {
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
  transform: translateY(-5px);
}

.status-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 35px;
}

.elderly-info {
  display: flex;
  align-items: center;
  gap: 25px;
}

.elderly-avatar {
  position: relative;
  width: 110px;
  height: 110px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
}

.avatar-icon {
  font-size: 3.5rem;
  z-index: 2;
}

.avatar-border {
  position: absolute;
  top: -5px;
  left: -5px;
  right: -5px;
  bottom: -5px;
  border: 3px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  animation: rotate 6s linear infinite;
}

@keyframes rotate {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.elderly-details {
  flex: 1;
}

.elderly-name {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.elderly-relationship {
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 5px;
}

.elderly-id {
  font-size: 1rem;
  color: #999;
}

.status-indicator {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 24px;
  border-radius: 30px;
  font-weight: bold;
  font-size: 1.1rem;
  transition: all 0.3s ease;
}

.status-indicator.normal {
  background: #d1fae5;
  color: #059669;
}

.status-indicator.warning {
  background: #fef3c7;
  color: #d97706;
}

.status-indicator.danger {
  background: #fee2e2;
  color: #dc2626;
}

.status-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: currentColor;
  animation: pulse-dot 2s infinite;
}

@keyframes pulse-dot {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}

.status-body {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 25px;
}

.status-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.status-item:hover {
  background: #e9ecef;
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.status-icon {
  font-size: 2.2rem;
  margin-right: 15px;
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.location-icon {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea;
}

.heart-icon {
  background: rgba(255, 107, 107, 0.1);
  color: #ff6b6b;
}

.blood-icon {
  background: rgba(78, 205, 196, 0.1);
  color: #4ecdc4;
}

.status-content {
  flex: 1;
}

.status-label {
  font-size: 1rem;
  color: #666;
  margin-bottom: 5px;
}

.status-value {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
}

.status-arrow {
  font-size: 1.2rem;
  color: #999;
  transition: all 0.3s ease;
}

.status-item:hover .status-arrow {
  transform: translateX(5px);
  color: #667eea;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.section-title {
  font-size: 1.6rem;
  font-weight: bold;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.section-action {
  background: rgba(255, 255, 255, 0.2);
  border: none;
  border-radius: 20px;
  padding: 8px 16px;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.section-action:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-2px);
}

.quick-access {
  margin-bottom: 30px;
}

.quick-buttons {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.quick-button {
  background: white;
  border: none;
  border-radius: 20px;
  padding: 25px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  position: relative;
  overflow: hidden;
  min-height: 140px;
  justify-content: center;
}

.quick-icon {
  position: relative;
  font-size: 2.2rem;
  z-index: 2;
  width: 70px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.quick-text {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  text-align: center;
  z-index: 2;
  position: relative;
  min-height: 40px;
  display: flex;
  align-items: center;
}

.quick-button:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 25px rgba(0, 0, 0, 0.15);
}

.quick-icon {
  position: relative;
  font-size: 2.2rem;
  z-index: 2;
}

.quick-icon-bg {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 70px;
  height: 70px;
  border-radius: 50%;
  z-index: 1;
  opacity: 0.1;
}

.health-bg {
  background: #667eea;
}

.safety-bg {
  background: #4ecdc4;
}

.message-bg {
  background: #ff6b6b;
}

.report-bg {
  background: #45b7d1;
}

.emergency-bg {
  background: #ff6b6b;
}

.quick-text {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  text-align: center;
  z-index: 2;
  position: relative;
}

.health-overview {
  margin-bottom: 30px;
}

.health-cards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.health-card {
  background: white;
  border-radius: 20px;
  padding: 25px;
  display: flex;
  align-items: center;
  gap: 20px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.health-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 25px rgba(0, 0, 0, 0.15);
}

.health-icon {
  position: relative;
  font-size: 2.5rem;
  width: 80px;
  height: 80px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;
}

.health-icon-bg {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80px;
  height: 80px;
  border-radius: 50%;
  z-index: 1;
  opacity: 0.1;
}

.heart-bg {
  background: #ff6b6b;
}

.blood-bg {
  background: #4ecdc4;
}

.sugar-bg {
  background: #ff9f43;
}

.steps-bg {
  background: #45b7d1;
}

.health-content {
  flex: 1;
  z-index: 2;
  position: relative;
}

.health-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 8px;
}

.health-value {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 8px;
}

.health-unit {
  font-size: 1.1rem;
  font-weight: normal;
  color: #666;
}

.health-status {
  font-size: 1rem;
  font-weight: bold;
  padding: 6px 16px;
  border-radius: 20px;
  display: inline-block;
}

.health-status.normal {
  background: #d1fae5;
  color: #059669;
}

.health-status.warning {
  background: #fef3c7;
  color: #d97706;
}

.health-status.danger {
  background: #fee2e2;
  color: #dc2626;
}

.health-arrow {
  font-size: 1.5rem;
  color: #999;
  transition: all 0.3s ease;
  z-index: 2;
  position: relative;
}

.health-card:hover .health-arrow {
  transform: translateX(5px);
  color: #667eea;
}

.alerts {
  margin-bottom: 30px;
}

.alert-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.alert-item {
  background: white;
  border-radius: 20px;
  padding: 25px;
  display: flex;
  gap: 20px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.alert-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
}

.alert-item::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 4px;
  background: #667eea;
}

.alert-item:nth-child(2)::before {
  background: #4ecdc4;
}

.alert-icon {
  font-size: 2.2rem;
  flex-shrink: 0;
  margin-top: 5px;
  z-index: 2;
  position: relative;
}

.alert-content {
  flex: 1;
  z-index: 2;
  position: relative;
}

.alert-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 8px;
}

.alert-text {
  font-size: 1.1rem;
  color: #666;
  margin-bottom: 12px;
  line-height: 1.5;
}

.alert-time {
  font-size: 0.95rem;
  color: #999;
}

.alert-action {
  display: flex;
  align-items: flex-start;
  z-index: 2;
  position: relative;
}

.action-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 12px;
  padding: 8px 16px;
  color: white;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-top: 5px;
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

@media (max-width: 768px) {
  .header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .header-right {
    align-self: flex-end;
  }
  
  .status-body {
    grid-template-columns: 1fr;
  }
  
  .quick-buttons {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .health-cards {
    grid-template-columns: 1fr;
  }
  
  .elderly-avatar {
    width: 90px;
    height: 90px;
  }
  
  .avatar-icon {
    font-size: 2.8rem;
  }
  
  .elderly-name {
    font-size: 1.6rem;
  }
  
  .elderly-relationship {
    font-size: 1rem;
  }
  
  .status-card {
    padding: 25px;
  }
  
  .health-card {
    padding: 20px;
  }
  
  .alert-item {
    padding: 20px;
  }
}
</style>
