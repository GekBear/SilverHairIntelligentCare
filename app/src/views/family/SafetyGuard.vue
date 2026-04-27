<template>
  <div class="safety-guard">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">安全守护</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 老人位置地图 -->
      <div class="card map-card">
        <h3>老人位置</h3>
        <div id="map-container" class="map-container"></div>
      </div>

      <!-- 老人状态 -->
      <div class="card">
        <h3>老人状态</h3>
        <div class="status-info">
          <div class="status-item">
            <div class="status-icon status-normal">
              <el-icon><UserFilled /></el-icon>
            </div>
            <div class="status-content">
              <div class="status-label">当前状态</div>
              <div class="status-value">正常</div>
            </div>
          </div>
          <div class="status-item">
            <div class="status-icon status-info">
              <el-icon><Location /></el-icon>
            </div>
            <div class="status-content">
              <div class="status-label">当前位置</div>
              <div class="status-value">家中</div>
            </div>
          </div>
          <div class="status-item">
            <div class="status-icon status-success">
              <el-icon><Clock /></el-icon>
            </div>
            <div class="status-content">
              <div class="status-label">最后活动</div>
              <div class="status-value">10分钟前</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 安全设置 -->
      <div class="card">
        <h3>安全设置</h3>
        <div class="setting-item">
          <span>电子围栏</span>
          <el-switch v-model="settings.geoFence" @change="toggleGeoFence" />
        </div>
        <div class="setting-item">
          <span>异常行为检测</span>
          <el-switch v-model="settings.abnormalDetection" @change="toggleAbnormalDetection" />
        </div>
        <div class="setting-item">
          <span>跌倒检测</span>
          <el-switch v-model="settings.fallDetection" @change="toggleFallDetection" />
        </div>
        <div class="setting-item">
          <span>夜间监护</span>
          <el-switch v-model="settings.nightMonitoring" @change="toggleNightMonitoring" />
        </div>
      </div>

      <!-- 告警记录 -->
      <div class="card">
        <h3>告警记录</h3>
        <div class="alarm-item" v-for="(alarm, index) in alarms" :key="index">
          <div class="alarm-icon" :class="alarm.iconClass">
            <el-icon>{{ alarm.icon }}</el-icon>
          </div>
          <div class="alarm-content">
            <div class="alarm-title">{{ alarm.title }}</div>
            <div class="alarm-time">{{ alarm.time }}</div>
          </div>
          <div class="alarm-status" :class="alarm.statusClass">{{ alarm.status }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, UserFilled, Location, Clock, Warning, InfoFilled, Check } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import AMapLoader from '@amap/amap-jsapi-loader'

const router = useRouter()

// 安全设置状态
const settings = ref({
  geoFence: true,
  abnormalDetection: true,
  fallDetection: true,
  nightMonitoring: false
})

// 告警记录数据
const alarms = ref([
  {
    id: 1,
    title: 'SOS求助',
    time: '2026-03-22 14:30',
    status: '已处理',
    statusClass: 'status-success',
    icon: Warning,
    iconClass: 'alarm-warning'
  },
  {
    id: 2,
    title: '出电子围栏',
    time: '2026-03-21 10:15',
    status: '已处理',
    statusClass: 'status-success',
    icon: InfoFilled,
    iconClass: 'alarm-info'
  },
  {
    id: 3,
    title: '异常活动',
    time: '2026-03-20 22:00',
    status: '已处理',
    statusClass: 'status-success',
    icon: Warning,
    iconClass: 'alarm-warning'
  }
])

const goBack = () => {
  router.back()
}

const toggleGeoFence = () => {
  console.log('电子围栏:', settings.value.geoFence ? '开启' : '关闭')
  ElMessage.info(`电子围栏已${settings.value.geoFence ? '开启' : '关闭'}`)
}

const toggleAbnormalDetection = () => {
  console.log('异常行为检测:', settings.value.abnormalDetection ? '开启' : '关闭')
  ElMessage.info(`异常行为检测已${settings.value.abnormalDetection ? '开启' : '关闭'}`)
}

const toggleFallDetection = () => {
  console.log('跌倒检测:', settings.value.fallDetection ? '开启' : '关闭')
  ElMessage.info(`跌倒检测已${settings.value.fallDetection ? '开启' : '关闭'}`)
}

const toggleNightMonitoring = () => {
  console.log('夜间监护:', settings.value.nightMonitoring ? '开启' : '关闭')
  ElMessage.info(`夜间监护已${settings.value.nightMonitoring ? '开启' : '关闭'}`)
}

// 初始化地图
onMounted(() => {
  // 检查地图容器是否存在
  const mapContainer = document.getElementById('map-container')
  if (!mapContainer) {
    console.error('地图容器不存在')
    return
  }

  // 先显示加载中提示
  mapContainer.innerHTML = '<div style="text-align: center; padding: 40px; color: #999;">地图加载中...</div>'

  // 模拟地图加载（由于没有真实的API密钥，我们使用模拟数据）
  setTimeout(() => {
    // 替换为更真实、更有设计感的模拟地图内容
    mapContainer.innerHTML = `
      <div style="width: 100%; height: 100%; background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%); position: relative; border-radius: 8px; overflow: hidden;">
        <!-- 地图控制栏 -->
        <div style="position: absolute; top: 15px; left: 15px; background: white; padding: 10px 15px; border-radius: 20px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); font-size: 14px; font-weight: bold; color: #333;">
          老人位置追踪
        </div>
        
        <!-- 地图标记 -->
        <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); text-align: center; z-index: 10;">
          <div style="width: 40px; height: 40px; background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%); border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-size: 20px; box-shadow: 0 4px 12px rgba(255, 107, 107, 0.4); animation: pulse 2s infinite;">
            �
          </div>
          <div style="margin-top: 10px; background: white; padding: 8px 16px; border-radius: 20px; font-size: 14px; font-weight: bold; color: #333; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
            张大爷
          </div>
          <div style="margin-top: 5px; background: #f0f0f0; padding: 5px 12px; border-radius: 15px; font-size: 12px; color: #666;">
            家中
          </div>
        </div>
        
        <!-- 模拟道路 -->
        <div style="position: absolute; top: 50%; left: 0; width: 100%; height: 2px; background: #bdbdbd; z-index: 1;"></div>
        <div style="position: absolute; left: 50%; top: 0; height: 100%; width: 2px; background: #bdbdbd; z-index: 1;"></div>
        
        <!-- 模拟建筑物 -->
        <div style="position: absolute; top: 20%; left: 20%; width: 80px; height: 60px; background: rgba(255, 255, 255, 0.8); border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);"></div>
        <div style="position: absolute; top: 70%; left: 70%; width: 60px; height: 40px; background: rgba(255, 255, 255, 0.8); border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);"></div>
        <div style="position: absolute; top: 30%; left: 75%; width: 70px; height: 50px; background: rgba(255, 255, 255, 0.8); border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);"></div>
        <div style="position: absolute; top: 60%; left: 15%; width: 90px; height: 60px; background: rgba(255, 255, 255, 0.8); border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);"></div>
        
        <!-- 模拟轨迹 -->
        <div style="position: absolute; top: 50%; left: 30%; width: 40%; height: 2px; background: linear-gradient(90deg, #409eff 0%, #ff4757 100%); z-index: 2;"></div>
        <div style="position: absolute; top: 50%; left: 30%; width: 12px; height: 12px; background: #409eff; border-radius: 50%; transform: translateY(-50%); box-shadow: 0 2px 4px rgba(64, 158, 255, 0.4);"></div>
        <div style="position: absolute; top: 50%; left: 70%; width: 12px; height: 12px; background: #ff4757; border-radius: 50%; transform: translateY(-50%); box-shadow: 0 2px 4px rgba(255, 71, 87, 0.4);"></div>
        
        <!-- 地图比例尺 -->
        <div style="position: absolute; bottom: 15px; right: 15px; background: white; padding: 5px 10px; border-radius: 4px; font-size: 12px; color: #666; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
          100m
        </div>
      </div>
    `
  }, 1000)
})
</script>

<style scoped>
.safety-guard {
  height: 100vh;
  background-color: #f5f5f5;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 50px;
  background-color: #fff;
  padding: 0 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-back {
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.header-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.container {
  padding: 20px;
}

.card {
  background-color: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.card h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 16px;
}

.status-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.status-item {
  display: flex;
  align-items: center;
  padding: 12px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.status-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  font-size: 18px;
  color: #fff;
}

.status-normal {
  background-color: #67c23a;
}

.status-info {
  background-color: #409eff;
}

.status-warning {
  background-color: #e6a23c;
}

.status-danger {
  background-color: #f56c6c;
}

.status-content {
  flex: 1;
}

.status-label {
  font-size: 12px;
  color: #909399;
  margin-bottom: 4px;
}

.status-value {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.setting-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 48px;
  border-bottom: 1px solid #f0f0f0;
}

.setting-item:last-child {
  border-bottom: none;
}

.setting-item span {
  font-size: 14px;
  color: #333;
}

.alarm-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.alarm-item:last-child {
  border-bottom: none;
}

.alarm-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  font-size: 18px;
  color: #fff;
}

.alarm-warning {
  background-color: #e6a23c;
}

.alarm-info {
  background-color: #409eff;
}

.alarm-danger {
  background-color: #f56c6c;
}

.alarm-content {
  flex: 1;
}

.alarm-title {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
}

.alarm-time {
  font-size: 12px;
  color: #909399;
}

.alarm-status {
  font-size: 12px;
  font-weight: 500;
  padding: 2px 8px;
  border-radius: 10px;
}

.status-success {
  color: #67c23a;
  background-color: #f0f9eb;
}

.status-warning {
  color: #e6a23c;
  background-color: #fdf6ec;
}

.status-danger {
  color: #f56c6c;
  background-color: #fef0f0;
}

/* 地图样式 */
.map-card {
  padding: 24px;
}

.map-container {
  width: 100%;
  height: 400px;
  border-radius: 8px;
  overflow: hidden;
  margin-top: 16px;
}

/* 动画效果 */
@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.2);
    opacity: 0.8;
  }
}
</style>