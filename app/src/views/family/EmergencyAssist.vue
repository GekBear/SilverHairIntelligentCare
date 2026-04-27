<template>
  <div class="emergency-assist">
    <!-- 顶部导航栏 -->
    <div class="header">
      <button class="back-button" @click="goBack">
        <span class="back-icon">←</span>
        <span class="back-text">返回</span>
      </button>
      <h1 class="page-title">紧急求助响应</h1>
    </div>

    <!-- 紧急求助列表 -->
    <div class="emergency-list" v-if="emergencyRequests.length > 0">
      <div class="section-title">
        <h2>附近紧急求助</h2>
        <p class="subtitle">点击卡片查看详情并响应</p>
      </div>
      
      <div class="emergency-card" v-for="request in emergencyRequests" :key="request.id" @click="viewRequestDetail(request)">
        <div class="card-header">
          <div class="elderly-info">
            <div class="elderly-avatar">{{ request.elderlyAvatar }}</div>
            <div class="elderly-details">
              <h3 class="elderly-name">{{ request.elderlyName }}</h3>
              <p class="elderly-relation">{{ request.relation }}</p>
            </div>
          </div>
          <div class="emergency-level" :class="request.level">
            {{ request.levelText }}
          </div>
        </div>
        
        <div class="card-body">
          <div class="location-info">
            <span class="location-icon">📍</span>
            <p class="location-text">{{ request.location }}</p>
          </div>
          <div class="time-info">
            <span class="time-icon">⏰</span>
            <p class="time-text">{{ request.time }}</p>
          </div>
        </div>
        
        <div class="card-footer">
          <button class="respond-button" @click.stop="respondToRequest(request)">
            <span class="respond-icon">✅</span>
            响应求助
          </button>
          <button class="details-button" @click.stop="viewRequestDetail(request)">
            <span class="details-icon">📋</span>
            查看详情
          </button>
        </div>
      </div>
    </div>

    <!-- 无求助时的状态 -->
    <div class="empty-state" v-else>
      <div class="empty-icon">🛡️</div>
      <h2 class="empty-title">暂无紧急求助</h2>
      <p class="empty-description">当附近有老人发起紧急求助时，将会显示在这里</p>
      <div class="status-indicator">
        <div class="status-dot active"></div>
        <span class="status-text">正在监听中...</span>
      </div>
    </div>

    <!-- 求助详情弹窗 -->
    <div class="detail-dialog" v-if="showDetailDialog">
      <div class="dialog-content">
        <button class="close-button" @click="closeDetailDialog">
          <span class="close-icon">✕</span>
        </button>
        
        <div class="dialog-header">
          <div class="elderly-info">
            <div class="elderly-avatar">{{ selectedRequest?.elderlyAvatar }}</div>
            <div class="elderly-details">
              <h3 class="elderly-name">{{ selectedRequest?.elderlyName }}</h3>
              <p class="elderly-relation">{{ selectedRequest?.relation }}</p>
            </div>
          </div>
          <div class="emergency-level" :class="selectedRequest?.level">
            {{ selectedRequest?.levelText }}
          </div>
        </div>
        
        <div class="dialog-body">
          <!-- 位置信息 -->
          <div class="info-section">
            <h4 class="section-title">位置信息</h4>
            <div class="location-map">
              <div class="map-container">
                <div class="map-center">
                  <div class="user-icon">👴</div>
                </div>
                <div class="map-circles">
                  <div class="circle circle-1"></div>
                  <div class="circle circle-2"></div>
                  <div class="circle circle-3"></div>
                </div>
              </div>
              <p class="location-text">{{ selectedRequest?.location }}</p>
              <button class="navigate-button" @click="navigateToLocation">
                <span class="navigate-icon">🗺️</span>
                导航前往
              </button>
            </div>
          </div>
          
          <!-- 求助信息 -->
          <div class="info-section">
            <h4 class="section-title">求助信息</h4>
            <div class="info-item">
              <span class="info-label">求助时间：</span>
              <span class="info-value">{{ selectedRequest?.time }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">求助类型：</span>
              <span class="info-value">{{ selectedRequest?.type }}</span>
            </div>
            <div class="info-item" v-if="selectedRequest?.message">
              <span class="info-label">求助信息：</span>
              <span class="info-value">{{ selectedRequest?.message }}</span>
            </div>
          </div>
          
          <!-- 老人信息 -->
          <div class="info-section">
            <h4 class="section-title">老人信息</h4>
            <div class="info-item">
              <span class="info-label">年龄：</span>
              <span class="info-value">{{ selectedRequest?.age }}岁</span>
            </div>
            <div class="info-item">
              <span class="info-label">健康状况：</span>
              <span class="info-value">{{ selectedRequest?.healthStatus }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">联系电话：</span>
              <span class="info-value">{{ selectedRequest?.phone }}</span>
            </div>
          </div>
        </div>
        
        <div class="dialog-footer">
          <button class="call-button" @click="callElderly">
            <span class="call-icon">📞</span>
            拨打电话
          </button>
          <button class="accept-button" @click="acceptRequest">
            <span class="accept-icon">✅</span>
            接受求助
          </button>
        </div>
      </div>
    </div>

    <!-- 响应成功弹窗 -->
    <div class="success-dialog" v-if="showSuccessDialog">
      <div class="dialog-content">
        <div class="success-icon">✅</div>
        <h3 class="success-title">响应成功</h3>
        <p class="success-message">您已成功接受求助，老人已收到您的响应</p>
        <button class="ok-button" @click="closeSuccessDialog">
          确定
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 紧急求助列表
const emergencyRequests = ref([])

// 选中的求助请求
const selectedRequest = ref(null)

// 弹窗状态
const showDetailDialog = ref(false)
const showSuccessDialog = ref(false)

// 模拟数据 - 实际项目中应从API获取
const mockEmergencyRequests = [
  {
    id: 1,
    elderlyName: '张爷爷',
    elderlyAvatar: '👴',
    relation: '邻居',
    level: 'high',
    levelText: '紧急',
    location: '阳光小区3栋2单元501室',
    time: '2024-01-15 14:30',
    type: '跌倒求助',
    message: '我在浴室摔倒了，需要帮助',
    age: 78,
    healthStatus: '高血压',
    phone: '13800138000'
  },
  {
    id: 2,
    elderlyName: '李奶奶',
    elderlyAvatar: '👵',
    relation: '社区老人',
    level: 'medium',
    levelText: '重要',
    location: '幸福里小区1栋1单元302室',
    time: '2024-01-15 14:25',
    type: '身体不适',
    message: '感觉胸闷，需要帮助',
    age: 82,
    healthStatus: '心脏病',
    phone: '13900139000'
  }
]

onMounted(() => {
  // 模拟获取紧急求助列表
  fetchEmergencyRequests()
  
  // 模拟实时监听新的求助请求
  startListeningForRequests()
})

onUnmounted(() => {
  // 清理监听
  if (listeningInterval) {
    clearInterval(listeningInterval)
  }
})

let listeningInterval = null

// 模拟获取紧急求助列表
const fetchEmergencyRequests = () => {
  // 模拟API请求延迟
  setTimeout(() => {
    emergencyRequests.value = mockEmergencyRequests
  }, 500)
}

// 模拟实时监听新的求助请求
const startListeningForRequests = () => {
  listeningInterval = setInterval(() => {
    // 模拟随机生成新的求助请求
    if (Math.random() > 0.95) {
      const newRequest = {
        id: Date.now(),
        elderlyName: ['王爷爷', '赵奶奶', '钱爷爷', '孙奶奶'][Math.floor(Math.random() * 4)],
        elderlyAvatar: ['👴', '👵'][Math.floor(Math.random() * 2)],
        relation: ['邻居', '社区老人', '远亲'][Math.floor(Math.random() * 3)],
        level: ['high', 'medium', 'low'][Math.floor(Math.random() * 3)],
        levelText: ['紧急', '重要', '一般'][Math.floor(Math.random() * 3)],
        location: `${['阳光小区', '幸福里小区', '和谐社区'][Math.floor(Math.random() * 3)]}${Math.floor(Math.random() * 5) + 1}栋${Math.floor(Math.random() * 3) + 1}单元${Math.floor(Math.random() * 10) + 1}0${Math.floor(Math.random() * 10) + 1}室`,
        time: new Date().toLocaleString('zh-CN'),
        type: ['跌倒求助', '身体不适', '迷路', '其他'][Math.floor(Math.random() * 4)],
        message: ['我需要帮助', '感觉不舒服', '找不到回家的路'][Math.floor(Math.random() * 3)],
        age: Math.floor(Math.random() * 20) + 70,
        healthStatus: ['高血压', '心脏病', '糖尿病', '健康'][Math.floor(Math.random() * 4)],
        phone: `13${Math.floor(Math.random() * 10)}0013${Math.floor(Math.random() * 10)}000`
      }
      
      // 添加到列表开头
      emergencyRequests.value.unshift(newRequest)
      
      // 限制列表长度
      if (emergencyRequests.value.length > 10) {
        emergencyRequests.value = emergencyRequests.value.slice(0, 10)
      }
    }
  }, 5000)
}

// 返回上一页
const goBack = () => {
  router.back()
}

// 查看求助详情
const viewRequestDetail = (request) => {
  selectedRequest.value = request
  showDetailDialog.value = true
}

// 关闭详情弹窗
const closeDetailDialog = () => {
  showDetailDialog.value = false
  selectedRequest.value = null
}

// 响应求助
const respondToRequest = (request) => {
  selectedRequest.value = request
  showDetailDialog.value = true
}

// 接受求助
const acceptRequest = () => {
  // 模拟接受求助的API调用
  setTimeout(() => {
    // 从列表中移除已接受的求助
    emergencyRequests.value = emergencyRequests.value.filter(req => req.id !== selectedRequest.value.id)
    
    // 显示成功弹窗
    showDetailDialog.value = false
    showSuccessDialog.value = true
    
    // 3秒后关闭成功弹窗
    setTimeout(() => {
      showSuccessDialog.value = false
    }, 3000)
  }, 1000)
}

// 关闭成功弹窗
const closeSuccessDialog = () => {
  showSuccessDialog.value = false
}

// 拨打电话给老人
const callElderly = () => {
  if (selectedRequest.value) {
    console.log(`拨打老人电话：${selectedRequest.value.phone}`)
    // 实际项目中可以集成电话拨打功能
  }
}

// 导航到老人位置
const navigateToLocation = () => {
  if (selectedRequest.value) {
    console.log(`导航到：${selectedRequest.value.location}`)
    // 实际项目中可以集成地图导航功能
  }
}
</script>

<style scoped>
.emergency-assist {
  min-height: 100vh;
  background: linear-gradient(to bottom, #f5f7fa 0%, #c3cfe2 100%);
  padding-bottom: 80px;
}

/* 顶部导航栏 */
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

/* 紧急求助列表 */
.emergency-list {
  padding: 20px;
}

.section-title {
  margin-bottom: 20px;
}

.section-title h2 {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.subtitle {
  font-size: 1rem;
  color: #666;
  margin: 0;
}

/* 紧急求助卡片 */
.emergency-card {
  background: white;
  border-radius: 16px;
  padding: 20px;
  margin-bottom: 15px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
}

.emergency-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.elderly-info {
  display: flex;
  align-items: center;
}

.elderly-avatar {
  font-size: 2.5rem;
  margin-right: 15px;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(76, 175, 80, 0.1);
  border-radius: 50%;
}

.elderly-details h3 {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.elderly-relation {
  font-size: 1rem;
  color: #666;
  margin: 0;
}

.emergency-level {
  padding: 8px 16px;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: bold;
  color: white;
}

.emergency-level.high {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
}

.emergency-level.medium {
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
}

.emergency-level.low {
  background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
}

.card-body {
  margin-bottom: 15px;
}

.location-info,
.time-info {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.location-icon,
.time-icon {
  font-size: 1.2rem;
  margin-right: 10px;
  width: 30px;
}

.location-text,
.time-text {
  font-size: 1rem;
  color: #333;
  margin: 0;
}

.card-footer {
  display: flex;
  gap: 10px;
}

.respond-button,
.details-button {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.respond-button {
  background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
  color: white;
}

.respond-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
}

.details-button {
  background: #f8f9fa;
  color: #333;
  border: 1px solid #dee2e6;
}

.details-button:hover {
  background: #e9ecef;
  transform: translateY(-2px);
}

.respond-icon,
.details-icon {
  margin-right: 8px;
  font-size: 1.1rem;
}

/* 无求助时的状态 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 70vh;
  text-align: center;
  padding: 0 20px;
}

.empty-icon {
  font-size: 6rem;
  margin-bottom: 20px;
  opacity: 0.6;
}

.empty-title {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.empty-description {
  font-size: 1.1rem;
  color: #666;
  margin-bottom: 30px;
  max-width: 400px;
}

.status-indicator {
  display: flex;
  align-items: center;
  gap: 10px;
}

.status-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: #6c757d;
  transition: all 0.3s ease;
}

.status-dot.active {
  background: #28a745;
  box-shadow: 0 0 10px rgba(40, 167, 69, 0.5);
  animation: pulse 2s infinite;
}

.status-text {
  font-size: 1rem;
  color: #666;
}

/* 详情弹窗 */
.detail-dialog {
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
  width: 90%;
  max-width: 500px;
  max-height: 80vh;
  overflow-y: auto;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  animation: slideUp 0.3s ease;
  position: relative;
}

.close-button {
  position: absolute;
  top: 15px;
  right: 15px;
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #666;
  padding: 5px;
  border-radius: 50%;
  transition: all 0.3s ease;
}

.close-button:hover {
  background: rgba(0, 0, 0, 0.1);
  color: #333;
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e9ecef;
}

.dialog-body {
  margin-bottom: 20px;
}

.info-section {
  margin-bottom: 20px;
}

.info-section h4 {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
}

.location-map {
  margin-bottom: 15px;
}

.map-container {
  position: relative;
  width: 100%;
  height: 200px;
  border-radius: 12px;
  background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
  overflow: hidden;
  box-shadow: inset 0 0 20px rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
}

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
  font-size: 2.5rem;
  animation: pulse 2s infinite;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 50%;
  width: 60px;
  height: 60px;
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
  width: 80px;
  height: 80px;
  top: -40px;
  left: -40px;
  animation-delay: 0s;
}

.circle-2 {
  width: 120px;
  height: 120px;
  top: -60px;
  left: -60px;
  animation-delay: 0.5s;
}

.circle-3 {
  width: 160px;
  height: 160px;
  top: -80px;
  left: -80px;
  animation-delay: 1s;
}

.navigate-button {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px;
  background: linear-gradient(135deg, #409eff 0%, #66b1ff 100%);
  border: none;
  border-radius: 10px;
  color: white;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-top: 10px;
}

.navigate-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.navigate-icon {
  margin-right: 8px;
  font-size: 1.1rem;
}

.info-item {
  display: flex;
  margin-bottom: 10px;
}

.info-label {
  font-weight: bold;
  color: #333;
  min-width: 100px;
}

.info-value {
  color: #666;
  flex: 1;
}

.dialog-footer {
  display: flex;
  gap: 10px;
  padding-top: 20px;
  border-top: 1px solid #e9ecef;
}

.call-button,
.accept-button {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.call-button {
  background: #f8f9fa;
  color: #333;
  border: 1px solid #dee2e6;
}

.call-button:hover {
  background: #e9ecef;
  transform: translateY(-2px);
}

.accept-button {
  background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
  color: white;
}

.accept-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
}

.call-icon,
.accept-icon {
  margin-right: 8px;
  font-size: 1.1rem;
}

/* 成功弹窗 */
.success-dialog {
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

.success-icon {
  font-size: 4rem;
  margin-bottom: 20px;
  color: #4CAF50;
}

.success-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.success-message {
  font-size: 1.1rem;
  color: #666;
  margin-bottom: 30px;
  text-align: center;
  line-height: 1.5;
}

.ok-button {
  padding: 12px 30px;
  background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
  border: none;
  border-radius: 10px;
  color: white;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.ok-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
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
    transform: translateY(50px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
}

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

/* 响应式设计 */
@media (max-width: 768px) {
  .header {
    padding: 15px;
  }
  
  .page-title {
    font-size: 1.3rem;
  }
  
  .emergency-list {
    padding: 15px;
  }
  
  .section-title h2 {
    font-size: 1.5rem;
  }
  
  .emergency-card {
    padding: 15px;
  }
  
  .elderly-avatar {
    font-size: 2rem;
    width: 50px;
    height: 50px;
  }
  
  .elderly-details h3 {
    font-size: 1.1rem;
  }
  
  .dialog-content {
    width: 95%;
    padding: 20px;
  }
  
  .map-container {
    height: 180px;
  }
  
  .empty-icon {
    font-size: 5rem;
  }
  
  .empty-title {
    font-size: 1.5rem;
  }
}
</style>