<template>
  <div class="emergency-contacts">
    <!-- 头部 -->
    <div class="header">
      <button class="back-button" @click="goBack">
        <span class="back-icon">←</span>
      </button>
      <h1 class="title">紧急联系人</h1>
    </div>

    <!-- 紧急情况卡片 -->
    <div class="emergency-card">
      <div class="emergency-icon">🚨</div>
      <h2 class="emergency-title">紧急情况</h2>
      <p class="emergency-text">请选择联系人或使用紧急求助</p>
      <button class="emergency-button" @click="callEmergency(2)">
        <span class="button-icon">❤️</span>
        <span class="button-text">一键呼叫急救中心</span>
      </button>
    </div>

    <!-- 联系人列表 -->
    <div class="contacts-list">
      <h2 class="section-title">联系人</h2>
      <div class="contact-items">
        <div 
          v-for="contact in contacts" 
          :key="contact.id"
          class="contact-item"
        >
          <div class="contact-info">
            <div class="contact-avatar">{{ contact.icon }}</div>
            <div class="contact-details">
              <h3 class="contact-name">{{ contact.name }}</h3>
              <p class="contact-relationship">{{ contact.relationship }}</p>
              <p class="contact-phone">{{ contact.phone }}</p>
            </div>
          </div>
          <button class="call-button" @click="callContact(contact.id)">
            <span class="call-icon">📞</span>
          </button>
        </div>
      </div>
    </div>

    <!-- 位置共享卡片 -->
    <div class="location-card">
      <div class="location-icon">📍</div>
      <h2 class="location-title">位置共享</h2>
      <p class="location-text">点击下方按钮，将您的当前位置共享给所有紧急联系人</p>
      <button class="location-button" @click="shareLocation">
        <span class="button-icon">📱</span>
        <span class="button-text">共享我的位置</span>
      </button>
    </div>

    <!-- 呼叫模拟对话框 -->
    <div v-if="isCalling" class="call-modal">
      <div class="call-content">
        <div class="call-avatar">
          {{ getContactById(selectedContact)?.icon }}
        </div>
        <h3 class="call-name">
          {{ getContactById(selectedContact)?.name }}
        </h3>
        <p class="call-status">正在呼叫...</p>
        <button class="end-call-button" @click="endCall">
          <span class="end-call-icon">❌</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { safetyAPI } from '../../services/api'

const router = useRouter()
const isCalling = ref(false)
const selectedContact = ref(null)
const contacts = ref([
  {
    id: 1,
    name: '子女 - 李明',
    relationship: '儿子',
    phone: '138****8888',
    icon: '👨‍👧‍👦'
  },
  {
    id: 2,
    name: '紧急救援',
    relationship: '医疗急救',
    phone: '120',
    icon: '🏥'
  },
  {
    id: 3,
    name: '社区服务中心',
    relationship: '社区帮助',
    phone: '12345',
    icon: '🏘️'
  },
  {
    id: 4,
    name: '安全监护',
    relationship: '安保服务',
    phone: '110',
    icon: '👮'
  }
])

onMounted(() => {
  // 初始化位置信息
  getLocation()
})

// 呼叫联系人
const callContact = (contactId) => {
  selectedContact.value = contactId
  isCalling.value = true
  
  // 模拟呼叫时长
  setTimeout(() => {
    endCall()
  }, 5000)
}

// 呼叫紧急救援
const callEmergency = (contactId) => {
  selectedContact.value = contactId
  isCalling.value = true
  
  // 模拟呼叫时长
  setTimeout(() => {
    endCall()
  }, 3000)
}

// 结束呼叫
const endCall = () => {
  isCalling.value = false
  selectedContact.value = null
}

// 共享位置
const shareLocation = async () => {
  try {
    // 模拟位置信息
    const location = {
      elderId: 1,
      longitude: 116.397428,
      latitude: 39.90923,
      address: '北京市海淀区中关村南大街5号'
    }
    // 保存位置信息
    await safetyAPI.createLocation(location)
    alert('位置已共享给紧急联系人')
  } catch (error) {
    console.error('位置共享失败:', error)
    alert('位置共享失败，请重试')
  }
}

// 获取位置信息
const getLocation = async () => {
  try {
    // 模拟获取位置信息
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(async (position) => {
        const location = {
          elderId: 1,
          longitude: position.coords.longitude,
          latitude: position.coords.latitude,
          address: '获取中...'
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

// 根据ID获取联系人
const getContactById = (id) => {
  return contacts.value.find(contact => contact.id === id)
}

// 返回上一页
const goBack = () => {
  router.back()
}
</script>

<style scoped>
.emergency-contacts {
  min-height: 100vh;
  background: linear-gradient(to bottom, #667eea 0%, #764ba2 100%);
  padding: 20px;
  padding-bottom: 40px;
}

.header {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
  color: white;
}

.back-button {
  background: none;
  border: none;
  color: white;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 10px;
  border-radius: 50%;
  transition: background 0.3s ease;
  margin-right: 20px;
}

.back-button:hover {
  background: rgba(255, 255, 255, 0.1);
}

.back-icon {
  font-size: 2rem;
  font-weight: bold;
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
}

.emergency-card {
  background: linear-gradient(135deg, #ff4757 0%, #ff3742 100%);
  border-radius: 20px;
  padding: 30px;
  text-align: center;
  color: white;
  margin-bottom: 30px;
  box-shadow: 0 10px 30px rgba(255, 71, 87, 0.4);
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    box-shadow: 0 10px 30px rgba(255, 71, 87, 0.4);
  }
  50% {
    box-shadow: 0 15px 40px rgba(255, 71, 87, 0.6);
  }
  100% {
    box-shadow: 0 10px 30px rgba(255, 71, 87, 0.4);
  }
}

.emergency-icon {
  font-size: 4rem;
  margin-bottom: 20px;
}

.emergency-title {
  font-size: 1.8rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.emergency-text {
  font-size: 1.2rem;
  margin-bottom: 30px;
  opacity: 0.9;
}

.emergency-button {
  background: white;
  color: #ff4757;
  border: none;
  border-radius: 30px;
  padding: 15px 30px;
  font-size: 1.2rem;
  font-weight: bold;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  transition: all 0.3s ease;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.emergency-button:hover {
  transform: scale(1.05);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}

.button-icon {
  font-size: 1.5rem;
}

.section-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: white;
  margin-bottom: 20px;
}

.contacts-list {
  margin-bottom: 30px;
}

.contact-items {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.contact-item {
  background: white;
  border-radius: 15px;
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.contact-item:hover {
  transform: translateY(-5px);
}

.contact-info {
  display: flex;
  align-items: center;
  gap: 15px;
  flex: 1;
}

.contact-avatar {
  font-size: 2.5rem;
  width: 80px;
  height: 80px;
  background: #f0f0f0;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.contact-details {
  flex: 1;
}

.contact-name {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.contact-relationship {
  font-size: 1rem;
  color: #666;
  margin-bottom: 5px;
}

.contact-phone {
  font-size: 1rem;
  color: #666;
  font-weight: bold;
}

.call-button {
  background: #667eea;
  border: none;
  border-radius: 50%;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
}

.call-button:hover {
  background: #764ba2;
  transform: scale(1.1);
}

.call-icon {
  font-size: 1.5rem;
  color: white;
}

.location-card {
  background: white;
  border-radius: 20px;
  padding: 30px;
  text-align: center;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.location-icon {
  font-size: 3rem;
  margin-bottom: 20px;
}

.location-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
}

.location-text {
  font-size: 1.1rem;
  color: #666;
  margin-bottom: 30px;
  line-height: 1.5;
}

.location-button {
  background: #3b82f6;
  color: white;
  border: none;
  border-radius: 30px;
  padding: 15px 30px;
  font-size: 1.2rem;
  font-weight: bold;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  transition: all 0.3s ease;
  box-shadow: 0 5px 15px rgba(59, 130, 246, 0.4);
}

.location-button:hover {
  background: #2563eb;
  transform: scale(1.05);
  box-shadow: 0 8px 20px rgba(59, 130, 246, 0.6);
}

.call-modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.call-content {
  background: white;
  border-radius: 20px;
  padding: 40px;
  text-align: center;
  width: 80%;
  max-width: 400px;
}

.call-avatar {
  font-size: 4rem;
  margin-bottom: 20px;
}

.call-name {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.call-status {
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 40px;
}

.end-call-button {
  background: #ff4757;
  border: none;
  border-radius: 50%;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 5px 15px rgba(255, 71, 87, 0.4);
  margin: 0 auto;
}

.end-call-button:hover {
  transform: scale(1.1);
  box-shadow: 0 8px 20px rgba(255, 71, 87, 0.6);
}

.end-call-icon {
  font-size: 2rem;
  color: white;
}

@media (max-width: 768px) {
  .contact-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }
  
  .call-button {
    align-self: flex-end;
  }
  
  .emergency-button,
  .location-button {
    font-size: 1rem;
    padding: 12px 24px;
  }
  
  .call-content {
    padding: 30px;
  }
}
</style>
