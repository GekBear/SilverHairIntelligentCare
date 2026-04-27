<template>
  <div class="services-page">
    <!-- 头部 -->
    <div class="header">
      <div class="back-button" @click="goBack">
        <span class="back-icon">←</span>
        <span class="back-text">返回</span>
      </div>
      <h1 class="page-title">服务中心</h1>
      <div class="header-right"></div>
    </div>

    <!-- 服务分类 -->
    <div class="service-categories">
      <!-- 生活服务 -->
      <div class="category-section">
        <h2 class="category-title">生活服务</h2>
        <div class="quick-access">
          <button class="quick-card" @click="goToServiceDetail('家政服务')">
            <div class="card-icon home-icon">🏠</div>
            <h3 class="card-title">家政服务</h3>
            <p class="card-subtitle">专业家政人员上门服务</p>
          </button>
          <button class="quick-card" @click="goToServiceDetail('餐饮服务')">
            <div class="card-icon food-icon">🍜</div>
            <h3 class="card-title">餐饮服务</h3>
            <p class="card-subtitle">营养配餐，送餐上门</p>
          </button>
          <button class="quick-card" @click="goToServiceDetail('购物服务')">
            <div class="card-icon shop-icon">🛒</div>
            <h3 class="card-title">购物服务</h3>
            <p class="card-subtitle">代买日用品，药品等</p>
          </button>
          <button class="quick-card" @click="goToServiceDetail('出行服务')">
            <div class="card-icon travel-icon">🚗</div>
            <h3 class="card-title">出行服务</h3>
            <p class="card-subtitle">陪同就医，外出陪护</p>
          </button>
        </div>
      </div>

      <!-- 健康服务 -->
      <div class="category-section">
        <h2 class="category-title">健康服务</h2>
        <div class="quick-access">
          <button class="quick-card" @click="goToServiceDetail('医疗咨询')">
            <div class="card-icon doctor-icon">👨‍⚕️</div>
            <h3 class="card-title">医疗咨询</h3>
            <p class="card-subtitle">专业医生在线咨询</p>
          </button>
          <button class="quick-card" @click="goToServiceDetail('康复护理')">
            <div class="card-icon hospital-icon">🏥</div>
            <h3 class="card-title">康复护理</h3>
            <p class="card-subtitle">专业康复师上门服务</p>
          </button>
          <button class="quick-card" @click="goToServiceDetail('健康监测')">
            <div class="card-icon health-icon">📊</div>
            <h3 class="card-title">健康监测</h3>
            <p class="card-subtitle">定期健康检查服务</p>
          </button>
          <button class="quick-card" @click="goToServiceDetail('用药指导')">
            <div class="card-icon medicine-icon">💊</div>
            <h3 class="card-title">用药指导</h3>
            <p class="card-subtitle">专业药师用药咨询</p>
          </button>
        </div>
      </div>

      <!-- 社交服务 -->
      <div class="category-section">
        <h2 class="category-title">社交服务</h2>
        <div class="quick-access">
          <button class="quick-card" @click="goToServiceDetail('老年活动')">
            <div class="card-icon activity-icon">🎯</div>
            <h3 class="card-title">老年活动</h3>
            <p class="card-subtitle">社区活动，兴趣小组</p>
          </button>
          <button class="quick-card" @click="goToServiceDetail('心理咨询')">
            <div class="card-icon心理-icon">💬</div>
            <h3 class="card-title">心理咨询</h3>
            <p class="card-subtitle">专业心理疏导服务</p>
          </button>
          <button class="quick-card" @click="goToServiceDetail('技能学习')">
            <div class="card-icon study-icon">📚</div>
            <h3 class="card-title">技能学习</h3>
            <p class="card-subtitle">智能手机使用培训</p>
          </button>
          <button class="quick-card" @click="goToServiceDetail('志愿者服务')">
            <div class="card-icon volunteer-icon">🤝</div>
            <h3 class="card-title">志愿者服务</h3>
            <p class="card-subtitle">一对一陪伴服务</p>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/elderly')
}

const goToServiceDetail = (serviceName) => {
  console.log(`进入${serviceName}详情页`)
  router.push({
    path: '/elderly/service-detail',
    query: { name: serviceName }
  })
}
</script>

<style scoped>
.services-page {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
  padding-bottom: 20px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  background: rgba(255, 255, 255, 0.1);
  color: white;
  position: sticky;
  top: 0;
  z-index: 100;
}

.back-button {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;
  transition: background 0.3s ease;
}

.back-button:hover {
  background: rgba(255, 255, 255, 0.1);
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
  margin: 0;
}

.header-right {
  width: 80px;
}

.service-categories {
  padding: 20px;
}

.category-section {
  margin-bottom: 30px;
}

.category-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: white;
  margin-bottom: 20px;
  padding-left: 10px;
  border-left: 4px solid #ff6b6b;
}

/* 快捷功能卡片 */
.quick-access {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  padding: 0 20px;
  margin-bottom: 30px;
}

.quick-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 25px 15px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border: none;
}

.quick-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.card-icon {
  font-size: 36px;
  margin-bottom: 12px;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  background: rgba(255, 217, 61, 0.1);
}

.home-icon {
  background: rgba(102, 126, 234, 0.2);
}

.food-icon {
  background: rgba(255, 190, 70, 0.2);
}

.shop-icon {
  background: rgba(78, 205, 196, 0.2);
}

.travel-icon {
  background: rgba(107, 142, 35, 0.2);
}

.doctor-icon {
  background: rgba(255, 107, 107, 0.2);
}

.hospital-icon {
  background: rgba(102, 126, 234, 0.2);
}

.health-icon {
  background: rgba(78, 205, 196, 0.2);
}

.medicine-icon {
  background: rgba(248, 180, 0, 0.2);
}

.activity-icon {
  background: rgba(255, 190, 70, 0.2);
}

.心理-icon {
  background: rgba(102, 126, 234, 0.2);
}

.study-icon {
  background: rgba(255, 107, 107, 0.2);
}

.volunteer-icon {
  background: rgba(78, 205, 196, 0.2);
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
  text-align: center;
  margin: 0;
}

@media (max-width: 768px) {
  .quick-access {
    gap: 15px;
    padding: 0 15px;
  }
  
  .quick-card {
    padding: 20px 10px;
  }
  
  .card-icon {
    font-size: 28px;
    width: 50px;
    height: 50px;
  }
  
  .card-title {
    font-size: 1.3rem;
  }
  
  .card-subtitle {
    font-size: 1rem;
  }
}
</style>