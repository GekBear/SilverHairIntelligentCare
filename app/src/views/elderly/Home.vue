<template>
  <div class="elderly-home">
    <!-- 头部 -->
    <div class="header">
      <div class="user-info">
        <div class="avatar">👴</div>
        <div class="greeting-section">
          <h1 class="greeting-text">{{ greeting }}</h1>
          <p class="user-name">{{ userInfo.name || '加载中...' }}</p>
          <div class="date">{{ date }}</div>
        </div>
        <div class="weather-section">
          <div class="weather-item">
            <span class="weather-icon">☀️</span>
            <div class="weather-info">
              <p class="weather-status">{{ weatherData.condition || '晴朗' }}</p>
              <p class="weather-temp">{{ weatherData.temperature || '22°C' }}</p>
            </div>
          </div>
          <div class="weather-item">
            <span class="weather-icon">💧</span>
            <div class="weather-info">
              <p class="weather-status">适宜</p>
              <p class="weather-temp">湿度 {{ weatherData.humidity || '45%' }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 主体内容 -->
    <div class="content">
      <!-- SOS紧急求助模块 -->
      <div class="sos-module">
        <div class="sos-module-header">
          <h2 class="sos-module-title">SOS紧急求助</h2>
          <p class="sos-module-subtitle">遇到紧急情况，立即求助</p>
        </div>
        <button class="sos-button" @click="triggerSOS">
          <div class="sos-icon">🚨</div>
          <p class="sos-text">紧急求助</p>
          <p class="sos-subtext">点击发送求助信号</p>
        </button>
      </div>

      <!-- 快捷功能卡片 -->
      <div class="quick-access">
        <button class="quick-card" @click="goToHealthDashboard">
          <div class="card-icon health-icon">❤️</div>
          <h3 class="card-title">健康监测</h3>
          <p class="card-subtitle">健康数据</p>
        </button>
        <button class="quick-card" @click="goToMedicineReminder">
          <div class="card-icon medicine-icon">💊</div>
          <h3 class="card-title">药物提醒</h3>
          <p class="card-subtitle">服药时间</p>
        </button>
        <button class="quick-card" @click="goToDigitalAlbum">
          <div class="card-icon album-icon">📷</div>
          <h3 class="card-title">数字相册</h3>
          <p class="card-subtitle">照片回忆</p>
        </button>
        <button class="quick-card" @click="goToCommunity">
          <div class="card-icon community-icon">👥</div>
          <h3 class="card-title">老年社区</h3>
          <p class="card-subtitle">分享交流</p>
        </button>
      </div>
      
      <!-- 紧急联系人 -->
      <div class="emergency-module">
        <div class="emergency-header">
          <h2 class="emergency-title">紧急联系人</h2>
        </div>
        <div class="emergency-contacts">
          <div class="contact-list">
            <button 
              v-for="contact in emergencyContacts" 
              :key="contact.id"
              class="contact-item" 
              @click="callEmergencyContact(contact.name, contact.phone)"
            >
              <div class="contact-icon">{{ contact.icon }}</div>
              <div class="contact-info">
                <p class="contact-name">{{ contact.name }}</p>
                <p class="contact-phone">{{ contact.phone }}</p>
              </div>
              <div class="contact-call">📞</div>
            </button>
            <div v-if="emergencyContacts.length === 0" class="no-contacts">
              <p>暂无紧急联系人</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 底部导航 -->
    <div class="bottom-nav">
      <button class="nav-item" @click="goToHome">
        <span class="nav-icon">🏠</span>
        <span class="nav-text">首页</span>
      </button>
      <button class="nav-item" @click="goToCommunity">
        <span class="nav-icon">👥</span>
        <span class="nav-text">社区</span>
      </button>
      <button class="nav-item ai-nav-item" @click="goToAIChat">
        <div class="ai-nav-icon">
          <span class="ai-icon">🤖</span>
        </div>
        <span class="nav-text">AI对话</span>
      </button>
      <button class="nav-item" @click="goToServices">
        <span class="nav-icon">🛎️</span>
        <span class="nav-text">服务</span>
      </button>
      <button class="nav-item" @click="goToSettings">
        <span class="nav-icon">👤</span>
        <span class="nav-text">我的</span>
      </button>
    </div>


  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { safetyAPI, elderlyAPI, authAPI } from '../../services/api'

const router = useRouter()
const greeting = ref('')
const date = ref('')
const loading = ref(false)
const userInfo = ref({ name: '' })
const weatherData = ref({ condition: '晴朗', temperature: '22°C', humidity: '45%' })
const emergencyContacts = ref([])
const userId = ref(localStorage.getItem('userId'))
const elderId = ref('')

onMounted(async () => {
  // 计算问候语
  const hour = new Date().getHours()
  if (hour < 12) {
    greeting.value = '早上好！'
  } else if (hour < 18) {
    greeting.value = '下午好！'
  } else {
    greeting.value = '晚上好！'
  }

  // 格式化日期
  const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    weekday: 'long'
  }
  date.value = new Date().toLocaleDateString('zh-CN', options)

  // 获取用户信息
  await getUserInfo()
  
  // 获取紧急联系人
  await getEmergencyContacts()
  
  // 初始化位置信息
  getLocation()
  
  // 获取天气信息
  getWeatherData()
})

const getUserInfo = async () => {
  try {
    if (userId.value) {
      const user = await authAPI.getUserById(userId.value)
      // 后端返回的用户对象中没有 name 属性，而是有 realName 属性
      userInfo.value = {
        name: user.realName || '加载中...'
      }
      
      // 获取老人信息
      const elderlyResponse = await elderlyAPI.getElderlyList({ userId: userId.value })
      if (elderlyResponse && elderlyResponse.data && elderlyResponse.data.length > 0) {
        elderId.value = elderlyResponse.data[0].id
      }
    }
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

const getEmergencyContacts = async () => {
  try {
    // 从后端API获取紧急联系人
    // 这里使用模拟数据，实际应从API获取
    emergencyContacts.value = [
      { id: 1, name: '儿子', phone: '138****8000', icon: '👨' },
      { id: 2, name: '女儿', phone: '139****9000', icon: '👩' },
      { id: 3, name: '社区医生', phone: '137****7000', icon: '👨‍⚕️' }
    ]
  } catch (error) {
    console.error('获取紧急联系人失败:', error)
  }
}

const getWeatherData = async () => {
  try {
    // 从天气API获取天气信息
    // 这里使用模拟数据，实际应从API获取
    weatherData.value = {
      condition: '晴朗',
      temperature: '22°C',
      humidity: '45%'
    }
  } catch (error) {
    console.error('获取天气信息失败:', error)
  }
}

const goToHome = () => {
  router.push('/elderly')
}

const goToAIChat = () => {
  router.push('/elderly/ai-chat')
}

const goToHealthDashboard = () => {
  router.push('/elderly/health-dashboard')
}

const goToMedicineReminder = () => {
  router.push('/elderly/medicine-reminder')
}

const goToDigitalAlbum = () => {
  router.push('/elderly/digital-album')
}

const goToCommunity = async () => {
  console.log('点击了社区按钮，准备跳转到社区页面')
  try {
    console.log('当前路由:', router.currentRoute.value.path)
    console.log('目标路由路径:', '/elderly/community')
    console.log('开始导航...')
    const result = await router.push('/elderly/community')
    console.log('导航结果:', result)
    console.log('跳转命令已执行')
    console.log('导航后路由:', router.currentRoute.value.path)
  } catch (error) {
    console.error('导航失败:', error)
    alert('导航失败，请重试')
  }
}

const goToServices = () => {
  console.log('点击了服务按钮')
  router.push('/elderly/services')
}

const goToSettings = () => {
  router.push('/elderly/settings')
}

const callEmergencyContact = (name, phone) => {
  console.log(`拨打紧急联系人 ${name}：${phone}`)
  // 进入APP内部的通话界面
  router.push({
    path: '/elderly/emergency-call',
    query: { name, phone }
  })
}

const triggerSOS = async () => {
  console.log('触发SOS紧急求助')
  try {
    if (!elderId.value) {
      alert('请先登录')
      return
    }
    
    // 发送SOS求助信号
    const sosData = {
      elderId: elderId.value,
      location: '获取中...',
      description: '老人触发了SOS紧急求助',
      requestTime: new Date().toISOString()
    }
    
    const response = await safetyAPI.createSos(sosData)
    console.log('SOS求助信号已发送:', response)
    
    // 跳转到紧急求助响应页面
    router.push('/elderly/emergency-response')
    
    // 模拟紧急求助逻辑
    setTimeout(() => {
      console.log('检查子女响应状态')
      // 模拟子女未响应的情况
      const childrenResponded = false
      
      if (!childrenResponded) {
        console.log('子女未响应，向附近子女端发送求助信号')
        // 模拟附近有无子女端响应
        const nearbyResponded = false
        
        if (!nearbyResponded) {
          console.log('附近无响应，联系急救中心或警察局')
          // 联系急救中心
          window.location.href = 'tel:120'
        }
      }
    }, 30000) // 30秒后检查响应状态
  } catch (error) {
    console.error('发送SOS求助失败:', error)
    alert('发送求助信号失败，请重试')
  }
}

const getLocation = async () => {
  try {
    if (!elderId.value) return
    
    // 获取位置信息
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(async (position) => {
        const location = {
          elderId: elderId.value,
          longitude: position.coords.longitude.toString(),
          latitude: position.coords.latitude.toString(),
          recordTime: new Date().toISOString()
        }
        // 保存位置信息
        await safetyAPI.createLocation(location)
      }, (error) => {
        console.error('获取位置失败:', error)
      })
    }
  } catch (error) {
    console.error('获取位置信息失败:', error)
  }
}
</script>

<style scoped>
.elderly-home {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
  padding-bottom: 80px;
}

.header {
  padding: 40px 20px 30px;
  color: white;
}

.user-info {
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 25px;
  padding: 30px 25px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  flex-wrap: wrap;
  gap: 20px;
}

.avatar {
  width: 80px;
  height: 80px;
  background: rgba(255, 154, 158, 0.1);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 3rem;
  flex-shrink: 0;
  margin-right: 0;
}

.greeting-section {
  flex: 1;
  min-width: 200px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.greeting-text {
  font-size: 3.5rem;
  font-weight: bold;
  color: black;
  line-height: 1.2;
  margin: 0;
}

.user-name {
  font-size: 2rem;
  color: black;
  line-height: 1.2;
  margin: 0;
}

.date {
  font-size: 1.5rem;
  color: #666;
  line-height: 1.2;
  margin: 0;
}

.weather-section {
  display: flex;
  flex-direction: row;
  gap: 15px;
  flex-shrink: 0;
  min-width: 200px;
}

.weather-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.weather-icon {
  font-size: 2.5rem;
  flex-shrink: 0;
}

.weather-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.weather-status {
  font-size: 1.5rem;
  font-weight: bold;
  color: black;
  line-height: 1.2;
  margin: 0;
}

.weather-temp {
  font-size: 1.3rem;
  color: #666;
  line-height: 1.2;
  margin: 0;
}

.content {
  padding: 0 20px;
  margin-top: -20px;
}

.sos-module {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 25px;
  padding: 30px 25px;
  margin-bottom: 30px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.sos-module-header {
  text-align: center;
  margin-bottom: 30px;
}

.sos-module-title {
  font-size: 2rem;
  font-weight: bold;
  color: #ff4757;
  margin-bottom: 10px;
}

.sos-module-subtitle {
  font-size: 1.2rem;
  color: #666;
}

.sos-button {
  width: 100%;
  padding: 40px;
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
  border: none;
  border-radius: 20px;
  color: white;
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  cursor: pointer;
  box-shadow: 0 10px 30px rgba(255, 107, 107, 0.4);
  transition: all 0.3s ease;
}

.sos-button:hover {
  transform: scale(1.02);
  box-shadow: 0 15px 40px rgba(255, 107, 107, 0.5);
}

.sos-icon {
  font-size: 4rem;
  margin-bottom: 15px;
}

.sos-text {
  margin-bottom: 10px;
  font-size: 1.8rem;
  font-weight: bold;
}

.sos-subtext {
  font-size: 1.2rem;
  opacity: 0.9;
}

.quick-access {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.quick-card {
  background: rgba(255, 255, 255, 0.85);
  border-radius: 20px;
  padding: 30px 20px;
  text-align: center;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.quick-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
}

.card-icon {
  font-size: 3rem;
  margin-bottom: 15px;
}

.health-icon {
  color: #ff6b6b;
}

.medicine-icon {
  color: #ff9a9e;
}

.album-icon {
  color: #fad0c4;
}

.community-icon {
  color: #f8b400;
}

.card-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

.card-subtitle {
  font-size: 1.1rem;
  color: #666;
}

.emergency-module {
  background: rgba(255, 255, 255, 0.8);
  border-radius: 25px;
  padding: 30px 25px;
  margin-bottom: 30px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.emergency-header {
  margin-bottom: 20px;
}

.emergency-title {
  font-size: 1.8rem;
  font-weight: bold;
  color: #ff4757;
}

.emergency-contacts {
  margin-top: 10px;
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
  border-radius: 15px;
  padding: 20px;
  border: none;
  cursor: pointer;
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

.contact-phone {
  font-size: 1.1rem;
  color: #666;
}

.contact-call {
  font-size: 1.8rem;
  color: #ff6b6b;
}

.bottom-nav {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: white;
  border-top: 2px solid #eee;
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 15px 0;
  box-shadow: 0 -5px 15px rgba(0, 0, 0, 0.1);
  z-index: 99;
  height: 90px;
  pointer-events: auto;
}

.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  border: none;
  background: none;
  cursor: pointer;
  padding: 10px 15px;
  border-radius: 10px;
  transition: all 0.3s ease;
  min-width: 80px;
  position: relative;
  top: 0;
  z-index: 100;
  pointer-events: auto;
  user-select: none;
}

.nav-item.active {
  background: rgba(255, 154, 158, 0.1);
  color: #ff6b6b;
}

.nav-icon {
  font-size: 1.8rem;
  margin-bottom: 5px;
}

.nav-text {
  font-size: 1rem;
  font-weight: bold;
  color: #333;
}

.ai-nav-item {
  transform: translateY(-15px);
}

.ai-nav-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.8rem;
  color: white;
  box-shadow: 0 5px 20px rgba(255, 217, 61, 0.4);
  margin-bottom: 5px;
  transition: all 0.3s ease;
}

.ai-icon {
  font-size: 2rem;
  color: white;
}

.ai-nav-item:hover .ai-nav-icon {
  transform: scale(1.1);
  box-shadow: 0 8px 25px rgba(255, 217, 61, 0.5);
}

.ai-nav-item .nav-text {
  color: #ff9e43;
  font-weight: bold;
}

.sos-dialog-content {
  text-align: center;
  padding: 20px 0;
}

.sos-icon {
  font-size: 4rem;
  margin-bottom: 20px;
}

.sos-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 15px;
  color: #ff4757;
}

.sos-text {
  font-size: 1.1rem;
  line-height: 1.6;
  color: #666;
}

.dialog-footer {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.dialog-footer .el-button {
  font-size: 1.1rem;
  padding: 10px 30px;
}

@media (max-width: 768px) {
  .greeting-text {
    font-size: 2rem;
  }
  
  .user-name {
    font-size: 1.2rem;
  }
  
  .date {
    font-size: 1rem;
  }
  
  .sos-button {
    padding: 30px;
  }
  
  .sos-icon {
    font-size: 3rem;
  }
  
  .sos-text {
    font-size: 1.2rem;
  }
  
  .card-title {
    font-size: 1.2rem;
  }
  
  .card-subtitle {
    font-size: 1rem;
  }
}
</style>
