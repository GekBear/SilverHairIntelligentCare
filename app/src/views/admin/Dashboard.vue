<template>
  <div class="admin-dashboard">
    <!-- 顶部导航栏 -->
    <div class="top-nav">
      <div class="logo">
        <div class="logo-icon">👴</div>
        <span class="logo-text">银发智护管理系统</span>
      </div>
      <div class="nav-menu">
        <router-link to="/admin/dashboard" class="nav-item active">
          <span class="nav-icon">📊</span>
          <span class="nav-text">仪表盘</span>
        </router-link>
        <router-link to="/admin/elderly-management" class="nav-item">
          <span class="nav-icon">👵</span>
          <span class="nav-text">老人管理</span>
        </router-link>
        <router-link to="/admin/family-management" class="nav-item">
          <span class="nav-icon">👨‍👩‍👧‍👦</span>
          <span class="nav-text">子女管理</span>
        </router-link>
        <router-link to="/admin/work-orders" class="nav-item">
          <span class="nav-icon">📋</span>
          <span class="nav-text">工单管理</span>
        </router-link>
        <router-link to="/admin/logs" class="nav-item">
          <span class="nav-icon">📃</span>
          <span class="nav-text">系统日志</span>
        </router-link>
        <router-link to="/admin/system-settings" class="nav-item">
          <span class="nav-icon">⚙️</span>
          <span class="nav-text">系统设置</span>
        </router-link>
      </div>
      <div class="top-right">
        <div class="search-box">
          <input type="text" placeholder="搜索..." class="search-input">
          <span class="search-icon">🔍</span>
        </div>
        <div class="user-info" @click="showUserMenu = !showUserMenu">
          <el-avatar size="small">管</el-avatar>
          <span class="user-name">管理员</span>
          <span class="user-arrow">▼</span>
          <div v-if="showUserMenu" class="user-dropdown">
            <div class="dropdown-item" @click="handleProfile">个人中心</div>
            <div class="dropdown-item" @click="handleSettings">设置</div>
            <div class="dropdown-item logout" @click="handleLogout">退出登录</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 主内容区 -->
    <div class="main-content">
      <!-- 欢迎区域 -->
      <div class="welcome-section">
        <h1 class="welcome-title">欢迎回来，管理员</h1>
        <p class="welcome-subtitle">这里是银发智护管理系统的控制面板</p>
      </div>

      <!-- 核心数据卡片 -->
      <div class="core-cards">
        <div class="core-card" @click="goToElderlyManagement">
          <div class="card-icon">👵</div>
          <div class="card-content">
            <h3 class="card-title">老人总数</h3>
            <p class="card-value">128</p>
            <p class="card-desc">较上月增长 5.2%</p>
          </div>
          <div class="card-arrow">→</div>
        </div>
        <div class="core-card" @click="goToFamilyManagement">
          <div class="card-icon">👨‍👩‍👧‍👦</div>
          <div class="card-content">
            <h3 class="card-title">子女用户</h3>
            <p class="card-value">236</p>
            <p class="card-desc">较上月增长 8.7%</p>
          </div>
          <div class="card-arrow">→</div>
        </div>
        <div class="core-card" @click="goToWorkOrders">
          <div class="card-icon">📋</div>
          <div class="card-content">
            <h3 class="card-title">今日工单</h3>
            <p class="card-value">12</p>
            <p class="card-desc">3 个待处理</p>
          </div>
          <div class="card-arrow">→</div>
        </div>
        <div class="core-card" @click="goToLogs">
          <div class="card-icon">🚨</div>
          <div class="card-content">
            <h3 class="card-title">紧急求助</h3>
            <p class="card-value">2</p>
            <p class="card-desc">今日新增</p>
          </div>
          <div class="card-arrow">→</div>
        </div>
      </div>

      <!-- 健康数据趋势 -->
      <div class="health-trend">
        <div class="section-header">
          <h2 class="section-title">健康数据趋势</h2>
          <button class="section-action">查看详情</button>
        </div>
        <div class="chart-container">
          <div ref="healthChartRef" class="health-chart"></div>
        </div>
      </div>

      <!-- 最近工单和紧急求助 -->
      <div class="recent-activities">
        <!-- 最近工单 -->
        <div class="activity-card">
          <div class="card-header">
            <h3 class="card-title">最近工单</h3>
            <button class="view-all">查看全部</button>
          </div>
          <div class="order-list">
            <div v-for="order in recentOrders" :key="order.id" class="order-item">
              <div class="order-info">
                <div class="order-title">{{ order.title }}</div>
                <div class="order-meta">
                  <span class="order-time">{{ order.time }}</span>
                  <span :class="['order-status', order.status]">{{ order.statusText }}</span>
                </div>
              </div>
              <button class="order-action">处理</button>
            </div>
          </div>
        </div>

        <!-- 紧急求助 -->
        <div class="activity-card">
          <div class="card-header">
            <h3 class="card-title">紧急求助</h3>
            <button class="view-all">查看全部</button>
          </div>
          <div class="emergency-list">
            <div v-for="emergency in recentEmergencies" :key="emergency.id" class="emergency-item">
              <div class="emergency-icon">🚨</div>
              <div class="emergency-info">
                <div class="emergency-name">{{ emergency.name }}</div>
                <div class="emergency-time">{{ emergency.time }}</div>
                <div class="emergency-location">{{ emergency.location }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import * as echarts from 'echarts'

const router = useRouter()
const showUserMenu = ref(false)
const healthChartRef = ref(null)
let healthChart = null

// 模拟数据
const recentOrders = [
  { id: 1, title: '家政服务预约', time: '2024-01-15 09:30', status: 'pending', statusText: '待处理' },
  { id: 2, title: '医疗咨询', time: '2024-01-15 08:15', status: 'processing', statusText: '处理中' },
  { id: 3, title: '送餐服务', time: '2024-01-14 17:45', status: 'completed', statusText: '已完成' },
  { id: 4, title: '康复护理', time: '2024-01-14 14:20', status: 'completed', statusText: '已完成' }
]

const recentEmergencies = [
  { id: 1, name: '张大爷', time: '2024-01-15 10:15', location: '北京市海淀区中关村南大街5号' },
  { id: 2, name: '李奶奶', time: '2024-01-14 16:30', location: '北京市朝阳区建国路88号' }
]

// 导航方法
const goToElderlyManagement = () => {
  router.push('/admin/elderly-management')
}

const goToFamilyManagement = () => {
  router.push('/admin/family-management')
}

const goToWorkOrders = () => {
  router.push('/admin/work-orders')
}

const goToLogs = () => {
  router.push('/admin/logs')
}

const handleProfile = () => {
  showUserMenu.value = false
  console.log('个人中心')
}

const handleSettings = () => {
  showUserMenu.value = false
  router.push('/admin/system-settings')
}

const handleLogout = () => {
  showUserMenu.value = false
  router.push('/admin')
}

// 初始化健康数据趋势图表
const initHealthChart = () => {
  if (healthChartRef.value) {
    healthChart = echarts.init(healthChartRef.value)
    const option = {
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['血压', '心率', '血糖']
      },
      xAxis: {
        type: 'category',
        data: ['1月10日', '1月11日', '1月12日', '1月13日', '1月14日', '1月15日']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '血压',
          type: 'line',
          data: [120, 125, 118, 130, 122, 128],
          smooth: true,
          lineStyle: {
            color: '#409EFF'
          }
        },
        {
          name: '心率',
          type: 'line',
          data: [75, 78, 72, 80, 76, 79],
          smooth: true,
          lineStyle: {
            color: '#67C23A'
          }
        },
        {
          name: '血糖',
          type: 'line',
          data: [5.2, 5.5, 5.1, 5.8, 5.3, 5.6],
          smooth: true,
          lineStyle: {
            color: '#F56C6C'
          }
        }
      ]
    }
    healthChart.setOption(option)
  }
}

onMounted(() => {
  initHealthChart()

  // 响应式调整
  window.addEventListener('resize', () => {
    healthChart?.resize()
  })

  // 点击外部关闭用户菜单
  document.addEventListener('click', (e) => {
    if (!e.target.closest('.user-info')) {
      showUserMenu.value = false
    }
  })
})
</script>

<style scoped>
.admin-dashboard {
  min-height: 100vh;
  background: #f8f9fa;
}

/* 顶部导航栏 */
.top-nav {
  background: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 0 30px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: sticky;
  top: 0;
  z-index: 100;
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-icon {
  font-size: 24px;
}

.logo-text {
  font-size: 18px;
  font-weight: bold;
  color: #1E88E5;
}

.nav-menu {
  display: flex;
  align-items: center;
  gap: 20px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  border-radius: 8px;
  text-decoration: none;
  color: #333;
  transition: all 0.3s ease;
  font-weight: 500;
}

.nav-item:hover {
  background: #f0f7ff;
  color: #1E88E5;
}

.nav-item.active {
  background: #e3f2fd;
  color: #1E88E5;
  box-shadow: 0 2px 4px rgba(30, 136, 229, 0.2);
}

.nav-icon {
  font-size: 18px;
}

.nav-text {
  font-size: 14px;
}

.top-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.search-box {
  position: relative;
}

.search-input {
  width: 200px;
  padding: 8px 12px 8px 36px;
  border: 1px solid #e0e0e0;
  border-radius: 20px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: #1E88E5;
  width: 250px;
  box-shadow: 0 0 0 2px rgba(30, 136, 229, 0.1);
}

.search-icon {
  position: absolute;
  left: 12px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 14px;
  color: #999;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 8px 12px;
  border-radius: 8px;
  transition: background 0.3s ease;
  position: relative;
}

.user-info:hover {
  background: #f5f5f5;
}

.user-name {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.user-arrow {
  font-size: 12px;
  color: #666;
  transition: transform 0.3s ease;
}

.user-info:hover .user-arrow {
  transform: rotate(180deg);
}

.user-dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  margin-top: 8px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  min-width: 160px;
  z-index: 1000;
}

.dropdown-item {
  padding: 12px 16px;
  font-size: 14px;
  color: #333;
  cursor: pointer;
  transition: background 0.3s ease;
}

.dropdown-item:hover {
  background: #f5f5f5;
}

.dropdown-item.logout {
  color: #F56C6C;
  border-top: 1px solid #f0f0f0;
  margin-top: 8px;
  padding-top: 12px;
}

/* 主内容区 */
.main-content {
  padding: 30px;
}

.welcome-section {
  margin-bottom: 30px;
}

.welcome-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin: 0 0 10px 0;
}

.welcome-subtitle {
  font-size: 16px;
  color: #666;
  margin: 0;
}

/* 核心数据卡片 */
.core-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.core-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
  display: flex;
  align-items: center;
  gap: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.core-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.card-icon {
  font-size: 32px;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0f7ff;
  border-radius: 12px;
  color: #1E88E5;
}

.card-content {
  flex: 1;
}

.card-title {
  font-size: 14px;
  color: #666;
  margin: 0 0 8px 0;
}

.card-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin: 0 0 4px 0;
}

.card-desc {
  font-size: 12px;
  color: #999;
  margin: 0;
}

.card-arrow {
  font-size: 20px;
  color: #1E88E5;
  font-weight: bold;
}

/* 健康数据趋势 */
.health-trend {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
  margin-bottom: 30px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.section-action {
  background: none;
  border: none;
  color: #1E88E5;
  font-size: 14px;
  cursor: pointer;
  padding: 6px 12px;
  border-radius: 6px;
  transition: background 0.3s ease;
}

.section-action:hover {
  background: #f0f7ff;
}

.health-chart {
  height: 300px;
}

/* 最近活动 */
.recent-activities {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.activity-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.card-header .card-title {
  font-size: 16px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.view-all {
  background: none;
  border: none;
  color: #1E88E5;
  font-size: 14px;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 4px;
  transition: background 0.3s ease;
}

.view-all:hover {
  background: #f0f7ff;
}

/* 工单列表 */
.order-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.order-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background: #f8f9fa;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.order-item:hover {
  background: #f0f7ff;
}

.order-title {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 8px;
}

.order-meta {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 12px;
  color: #666;
}

.order-status {
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: 500;
}

.order-status.pending {
  background: #fff3cd;
  color: #856404;
}

.order-status.processing {
  background: #cce7ff;
  color: #004085;
}

.order-status.completed {
  background: #d4edda;
  color: #155724;
}

.order-action {
  background: #1E88E5;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.order-action:hover {
  background: #1976D2;
  transform: translateY(-1px);
}

/* 紧急求助列表 */
.emergency-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.emergency-item {
  display: flex;
  gap: 12px;
  padding: 16px;
  background: #fff3f3;
  border-radius: 8px;
  border-left: 4px solid #F56C6C;
}

.emergency-icon {
  font-size: 24px;
  color: #F56C6C;
}

.emergency-info {
  flex: 1;
}

.emergency-name {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
}

.emergency-time {
  font-size: 12px;
  color: #666;
  margin-bottom: 4px;
}

.emergency-location {
  font-size: 12px;
  color: #999;
  line-height: 1.4;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .core-cards {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .recent-activities {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .top-nav {
    padding: 0 20px;
    flex-wrap: wrap;
    height: auto;
    gap: 10px;
    padding: 10px 20px;
  }
  
  .nav-menu {
    order: 3;
    width: 100%;
    justify-content: center;
    flex-wrap: wrap;
    gap: 10px;
  }
  
  .main-content {
    padding: 20px;
  }
  
  .core-cards {
    grid-template-columns: 1fr;
  }
  
  .welcome-title {
    font-size: 20px;
  }
  
  .section-title {
    font-size: 16px;
  }
  
  .health-chart {
    height: 250px;
  }
}
</style>