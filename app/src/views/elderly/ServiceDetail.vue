<template>
  <div class="service-detail">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="back-button" @click="goBack">
        <span class="back-icon">←</span>
        <span class="back-text">返回</span>
      </div>
      <h1 class="page-title">{{ serviceName }}</h1>
    </div>

    <!-- 服务详情 -->
    <div class="detail-section">
      <div class="service-icon-large">{{ getServiceIcon(serviceName) }}</div>
      <h2 class="service-title">{{ serviceName }}</h2>
      <p class="service-description">{{ getServiceDescription(serviceName) }}</p>
    </div>

    <!-- 服务内容 -->
    <div class="content-section">
      <h3 class="section-title">服务内容</h3>
      <div class="content-list">
        <div class="content-item" v-for="(item, index) in getServiceContent(serviceName)" :key="index">
          <div class="content-icon">✅</div>
          <div class="content-text">{{ item }}</div>
        </div>
      </div>
    </div>

    <!-- 服务价格 -->
    <div class="price-section">
      <h3 class="section-title">服务价格</h3>
      <div class="price-card">
        <div class="price-item">
          <div class="price-name">基础服务</div>
          <div class="price-value">¥{{ getServicePrice(serviceName).basic }}/次</div>
        </div>
        <div class="price-item">
          <div class="price-name">标准服务</div>
          <div class="price-value">¥{{ getServicePrice(serviceName).standard }}/次</div>
        </div>
        <div class="price-item">
          <div class="price-name">高级服务</div>
          <div class="price-value">¥{{ getServicePrice(serviceName).premium }}/次</div>
        </div>
      </div>
    </div>

    <!-- 预约按钮 -->
    <div class="action-section">
      <button class="book-button" @click="bookService">
        <span class="book-icon">📅</span>
        立即预约
      </button>
    </div>

    <!-- 成功提示 -->
    <div class="success-dialog" v-if="showSuccessDialog">
      <div class="success-content">
        <div class="success-icon">✅</div>
        <h3 class="success-title">预约成功</h3>
        <p class="success-message">已成功预约{{ serviceName }}服务，我们会尽快与您联系确认详情。</p>
        <button class="success-button" @click="closeSuccessDialog">
          确定
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

// 获取服务名称
const serviceName = ref(route.query.name || '服务详情')

// 导航方法
const goBack = () => {
  router.back()
}

// 获取服务图标
const getServiceIcon = (name) => {
  const iconMap = {
    '家政服务': '🏠',
    '餐饮服务': '🍜',
    '购物服务': '🛒',
    '出行服务': '🚗',
    '医疗咨询': '👨‍⚕️',
    '康复护理': '🏥',
    '健康监测': '📊',
    '用药指导': '💊',
    '老年活动': '🎯',
    '心理咨询': '💬',
    '技能学习': '📚',
    '志愿者服务': '🤝'
  }
  return iconMap[name] || '🛎️'
}

// 获取服务描述
const getServiceDescription = (name) => {
  const descMap = {
    '家政服务': '专业家政人员上门服务，包括打扫卫生、整理房间等',
    '餐饮服务': '营养配餐，送餐上门，满足老年人的饮食需求',
    '购物服务': '代买日用品、药品等，为老年人提供便利',
    '出行服务': '陪同就医、外出陪护，确保老年人安全出行',
    '医疗咨询': '专业医生在线咨询，解答健康问题',
    '康复护理': '专业康复师上门服务，帮助老年人恢复健康',
    '健康监测': '定期健康检查服务，监测身体状况',
    '用药指导': '专业药师用药咨询，确保用药安全',
    '老年活动': '社区活动、兴趣小组，丰富老年人的生活',
    '心理咨询': '专业心理疏导服务，关注老年人心理健康',
    '技能学习': '智能手机使用培训，帮助老年人适应现代科技',
    '志愿者服务': '一对一陪伴服务，给予老年人关怀'
  }
  return descMap[name] || '专业服务，为老年人提供贴心关怀'
}

// 获取服务内容
const getServiceContent = (name) => {
  const contentMap = {
    '家政服务': [
      '日常保洁',
      '衣物清洗',
      '厨房清洁',
      '卫生间清洁',
      '整理收纳'
    ],
    '餐饮服务': [
      '营养配餐',
      '送餐上门',
      '特殊饮食定制',
      '餐具清洗',
      '饮食咨询'
    ],
    '购物服务': [
      '日用品采购',
      '药品代买',
      '食材购买',
      '快递代收',
      '购物咨询'
    ],
    '出行服务': [
      '陪同就医',
      '外出购物',
      '公园散步',
      '银行办理',
      '交通接送'
    ],
    '医疗咨询': [
      '在线问诊',
      '健康咨询',
      '用药指导',
      '检查报告解读',
      '健康管理建议'
    ],
    '康复护理': [
      '康复训练',
      '伤口护理',
      '生活照料',
      '康复评估',
      '康复计划制定'
    ],
    '健康监测': [
      '血压测量',
      '血糖监测',
      '心率监测',
      '健康记录',
      '健康分析报告'
    ],
    '用药指导': [
      '用药咨询',
      '用药时间安排',
      '药物相互作用分析',
      '用药依从性监测',
      '药品不良反应监测'
    ],
    '老年活动': [
      '棋牌娱乐',
      '书画活动',
      '音乐欣赏',
      '手工制作',
      '集体出游'
    ],
    '心理咨询': [
      '情绪疏导',
      '压力管理',
      '人际关系咨询',
      '生活适应指导',
      '心理评估'
    ],
    '技能学习': [
      '智能手机使用',
      '社交媒体操作',
      '网上购物',
      '视频通话',
      '数字设备维护'
    ],
    '志愿者服务': [
      '陪伴聊天',
      '读书读报',
      '陪同活动',
      '节日关怀',
      '生活协助'
    ]
  }
  return contentMap[name] || ['专业服务', '贴心关怀', '个性化定制', '安全保障', '品质保证']
}

// 获取服务价格
const getServicePrice = (name) => {
  const priceMap = {
    '家政服务': { basic: 50, standard: 80, premium: 120 },
    '餐饮服务': { basic: 30, standard: 50, premium: 80 },
    '购物服务': { basic: 20, standard: 40, premium: 60 },
    '出行服务': { basic: 40, standard: 70, premium: 100 },
    '医疗咨询': { basic: 60, standard: 100, premium: 150 },
    '康复护理': { basic: 80, standard: 120, premium: 180 },
    '健康监测': { basic: 50, standard: 80, premium: 120 },
    '用药指导': { basic: 40, standard: 70, premium: 100 },
    '老年活动': { basic: 30, standard: 50, premium: 80 },
    '心理咨询': { basic: 60, standard: 100, premium: 150 },
    '技能学习': { basic: 40, standard: 70, premium: 100 },
    '志愿者服务': { basic: 0, standard: 0, premium: 0 }
  }
  return priceMap[name] || { basic: 50, standard: 80, premium: 120 }
}

// 成功提示状态
const showSuccessDialog = ref(false)

// 预约服务
const bookService = () => {
  console.log(`预约${serviceName.value}服务`)
  showSuccessDialog.value = true
}

// 关闭成功提示
const closeSuccessDialog = () => {
  showSuccessDialog.value = false
  router.back()
}

onMounted(() => {
  console.log('加载服务详情:', serviceName.value)
})
</script>

<style scoped>
.service-detail {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
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

/* 服务详情 */
.detail-section {
  text-align: center;
  padding: 40px 20px;
  background: rgba(255, 255, 255, 0.9);
  margin: 20px;
  border-radius: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.service-icon-large {
  font-size: 5rem;
  margin-bottom: 20px;
}

.service-title {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
}

.service-description {
  font-size: 1.2rem;
  color: #666;
  line-height: 1.6;
  margin: 0;
}

/* 服务内容 */
.content-section {
  background: rgba(255, 255, 255, 0.9);
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
  padding-left: 10px;
  border-left: 4px solid #ff6b6b;
}

.content-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.content-item {
  display: flex;
  align-items: flex-start;
  gap: 15px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 10px;
  transition: all 0.3s ease;
}

.content-item:hover {
  background: #e9ecef;
  transform: translateY(-2px);
}

.content-icon {
  font-size: 1.2rem;
  color: #4CAF50;
  margin-top: 2px;
}

.content-text {
  flex: 1;
  font-size: 1.1rem;
  color: #666;
  line-height: 1.5;
}

/* 服务价格 */
.price-section {
  background: rgba(255, 255, 255, 0.9);
  margin: 0 20px 20px;
  padding: 25px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.price-card {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.price-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 10px;
  transition: all 0.3s ease;
}

.price-item:hover {
  background: #e9ecef;
  transform: translateY(-2px);
}

.price-name {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
}

.price-value {
  font-size: 1.3rem;
  font-weight: bold;
  color: #ff6b6b;
}

/* 预约按钮 */
.action-section {
  padding: 0 20px 40px;
}

.book-button {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 1.3rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(255, 217, 61, 0.4);
}

.book-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(255, 217, 61, 0.5);
}

.book-icon {
  margin-right: 10px;
  font-size: 1.5rem;
}

/* 成功提示 */
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

.success-content {
  background: white;
  border-radius: 20px;
  padding: 40px;
  width: 90%;
  max-width: 400px;
  text-align: center;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  animation: slideUp 0.3s ease;
}

.success-icon {
  font-size: 4rem;
  margin-bottom: 20px;
}

.success-title {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
}

.success-message {
  font-size: 1.3rem;
  color: #666;
  line-height: 1.6;
  margin-bottom: 30px;
}

.success-button {
  padding: 15px 40px;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  border: none;
  border-radius: 50px;
  color: white;
  font-size: 1.2rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(255, 217, 61, 0.4);
}

.success-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(255, 217, 61, 0.5);
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

/* 响应式设计 */
@media (max-width: 768px) {
  .detail-section {
    padding: 30px 15px;
    margin: 15px;
  }
  
  .service-icon-large {
    font-size: 4rem;
  }
  
  .service-title {
    font-size: 1.8rem;
  }
  
  .content-section,
  .price-section {
    margin: 0 15px 15px;
    padding: 20px;
  }
  
  .action-section {
    padding: 0 15px 30px;
  }
  
  .book-button {
    padding: 18px;
    font-size: 1.2rem;
  }
  
  .success-content {
    padding: 30px;
  }
  
  .success-icon {
    font-size: 3rem;
  }
  
  .success-title {
    font-size: 1.8rem;
  }
  
  .success-message {
    font-size: 1.1rem;
  }
  
  .success-button {
    padding: 12px 30px;
    font-size: 1.1rem;
  }
}
</style>