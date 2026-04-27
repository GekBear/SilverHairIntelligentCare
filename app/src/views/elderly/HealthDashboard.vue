<template>
  <div class="health-dashboard">
    <!-- 头部 -->
    <div class="header">
      <button class="back-button" @click="goBack">
        <span class="back-icon">←</span>
      </button>
      <h1 class="title">健康监测</h1>
      <button class="share-button" @click="shareData">
        <span class="share-icon">📤</span>
      </button>
    </div>

    <!-- 健康状态卡片 -->
    <div class="health-cards">
      <!-- 心率 -->
      <div class="health-card">
        <div class="card-header">
          <div class="card-icon heart-icon">❤️</div>
          <h3 class="card-title">心率</h3>
          <span class="card-status normal">正常</span>
        </div>
        <div class="card-content">
          <div class="value">{{ healthData.heartRate }}<span class="unit">BPM</span></div>
          <div class="chart-container">
            <div ref="heartRateChart" class="chart"></div>
          </div>
        </div>
      </div>

      <!-- 血压 -->
      <div class="health-card">
        <div class="card-header">
          <div class="card-icon blood-icon">💉</div>
          <h3 class="card-title">血压</h3>
          <span class="card-status normal">正常</span>
        </div>
        <div class="card-content">
          <div class="value">{{ healthData.bloodPressure.systolic }}<span class="unit">/{{ healthData.bloodPressure.diastolic }} mmHg</span></div>
          <div class="chart-container">
            <div ref="bloodPressureChart" class="chart"></div>
          </div>
        </div>
      </div>

      <!-- 血糖 -->
      <div class="health-card">
        <div class="card-header">
          <div class="card-icon sugar-icon">🍬</div>
          <h3 class="card-title">血糖</h3>
          <span class="card-status normal">正常</span>
        </div>
        <div class="card-content">
          <div class="value">{{ healthData.bloodSugar }}<span class="unit">mmol/L</span></div>
          <div class="chart-container">
            <div ref="bloodSugarChart" class="chart"></div>
          </div>
        </div>
      </div>

      <!-- 步数 -->
      <div class="health-card">
        <div class="card-header">
          <div class="card-icon steps-icon">🚶</div>
          <h3 class="card-title">步数</h3>
          <span class="card-status normal">良好</span>
        </div>
        <div class="card-content">
          <div class="value">{{ healthData.steps }}<span class="unit">步</span></div>
          <div class="progress-bar">
            <div class="progress" :style="{ width: stepsProgress + '%' }"></div>
          </div>
          <div class="progress-text">{{ stepsProgress }}% 目标</div>
        </div>
      </div>
    </div>

    <!-- 健康趋势 -->
    <div class="health-trend">
      <h2 class="section-title">健康趋势</h2>
      <div class="trend-chart">
        <div ref="trendChart" class="chart"></div>
      </div>
    </div>

    <!-- 健康建议 -->
    <div class="health-tips">
      <h2 class="section-title">健康建议</h2>
      <div class="tips-list">
        <div class="tip-item">
          <div class="tip-icon">💡</div>
          <div class="tip-content">
            <h3 class="tip-title">保持规律运动</h3>
            <p class="tip-text">每天至少步行30分钟，有助于维持健康的心血管系统</p>
          </div>
        </div>
        <div class="tip-item">
          <div class="tip-icon">🥗</div>
          <div class="tip-content">
            <h3 class="tip-title">饮食健康</h3>
            <p class="tip-text">多吃蔬菜水果，减少盐分摄入，有助于控制血压</p>
          </div>
        </div>
        <div class="tip-item">
          <div class="tip-icon">😴</div>
          <div class="tip-content">
            <h3 class="tip-title">充足睡眠</h3>
            <p class="tip-text">保证每晚7-8小时的睡眠时间，有助于身体恢复</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import * as echarts from 'echarts'
import { healthAPI } from '../../services/api'

const router = useRouter()
const heartRateChart = ref(null)
const bloodPressureChart = ref(null)
const bloodSugarChart = ref(null)
const trendChart = ref(null)
const loading = ref(false)

const healthData = ref({
  heartRate: 72,
  bloodPressure: { systolic: 120, diastolic: 80 },
  bloodSugar: 5.6,
  steps: 4567
})

const stepsProgress = computed(() => {
  return Math.round((healthData.value.steps / 8000) * 100)
})

const healthHistory = ref([
  { date: '10/01', heartRate: 75, systolic: 122, diastolic: 82, bloodSugar: 5.8, steps: 4200 },
  { date: '10/02', heartRate: 73, systolic: 118, diastolic: 79, bloodSugar: 5.6, steps: 4800 },
  { date: '10/03', heartRate: 71, systolic: 120, diastolic: 80, bloodSugar: 5.5, steps: 5100 },
  { date: '10/04', heartRate: 74, systolic: 125, diastolic: 83, bloodSugar: 5.9, steps: 3900 },
  { date: '10/05', heartRate: 72, systolic: 119, diastolic: 78, bloodSugar: 5.7, steps: 4500 },
  { date: '10/06', heartRate: 70, systolic: 117, diastolic: 77, bloodSugar: 5.4, steps: 4700 },
  { date: '10/07', heartRate: 72, systolic: 120, diastolic: 80, bloodSugar: 5.6, steps: 4567 },
])

// 初始化图表
const initCharts = () => {
  // 心率图表
  if (heartRateChart.value) {
    const chart = echarts.init(heartRateChart.value)
    chart.setOption({
      grid: {
        left: '0%',
        right: '0%',
        top: '0%',
        bottom: '0%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        data: healthHistory.value.map(item => item.date),
        show: false
      },
      yAxis: {
        type: 'value',
        show: false
      },
      series: [{
        data: healthHistory.value.map(item => item.heartRate),
        type: 'line',
        smooth: true,
        lineStyle: {
          color: '#ff6b6b',
          width: 3
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(255, 107, 107, 0.3)' },
            { offset: 1, color: 'rgba(255, 107, 107, 0.1)' }
          ])
        }
      }]
    })
  }

  // 血压图表
  if (bloodPressureChart.value) {
    const chart = echarts.init(bloodPressureChart.value)
    chart.setOption({
      grid: {
        left: '0%',
        right: '0%',
        top: '0%',
        bottom: '0%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        data: healthHistory.value.map(item => item.date),
        show: false
      },
      yAxis: {
        type: 'value',
        show: false
      },
      series: [
        {
          data: healthHistory.value.map(item => item.systolic),
          type: 'line',
          smooth: true,
          lineStyle: {
            color: '#3b82f6',
            width: 3
          },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(59, 130, 246, 0.3)' },
              { offset: 1, color: 'rgba(59, 130, 246, 0.1)' }
            ])
          }
        },
        {
          data: healthHistory.value.map(item => item.diastolic),
          type: 'line',
          smooth: true,
          lineStyle: {
            color: '#4ade80',
            width: 3
          }
        }
      ]
    })
  }

  // 血糖图表
  if (bloodSugarChart.value) {
    const chart = echarts.init(bloodSugarChart.value)
    chart.setOption({
      grid: {
        left: '0%',
        right: '0%',
        top: '0%',
        bottom: '0%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        data: healthHistory.value.map(item => item.date),
        show: false
      },
      yAxis: {
        type: 'value',
        show: false
      },
      series: [{
        data: healthHistory.value.map(item => item.bloodSugar),
        type: 'line',
        smooth: true,
        lineStyle: {
          color: '#f59e0b',
          width: 3
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(245, 158, 11, 0.3)' },
            { offset: 1, color: 'rgba(245, 158, 11, 0.1)' }
          ])
        }
      }]
    })
  }

  // 趋势图表
  if (trendChart.value) {
    const chart = echarts.init(trendChart.value)
    chart.setOption({
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        data: healthHistory.value.map(item => item.date)
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '心率',
          data: healthHistory.value.map(item => item.heartRate),
          type: 'line',
          smooth: true,
          lineStyle: {
            color: '#ff6b6b'
          }
        },
        {
          name: '收缩压',
          data: healthHistory.value.map(item => item.systolic),
          type: 'line',
          smooth: true,
          lineStyle: {
            color: '#3b82f6'
          }
        },
        {
          name: '血糖',
          data: healthHistory.value.map(item => item.bloodSugar),
          type: 'line',
          smooth: true,
          lineStyle: {
            color: '#f59e0b'
          }
        }
      ]
    })
  }
}

// 窗口大小变化时重新调整图表大小
const handleResize = () => {
  if (heartRateChart.value) {
    echarts.getInstanceByDom(heartRateChart.value)?.resize()
  }
  if (bloodPressureChart.value) {
    echarts.getInstanceByDom(bloodPressureChart.value)?.resize()
  }
  if (bloodSugarChart.value) {
    echarts.getInstanceByDom(bloodSugarChart.value)?.resize()
  }
  if (trendChart.value) {
    echarts.getInstanceByDom(trendChart.value)?.resize()
  }
}

// 分享数据
const shareData = () => {
  alert('健康数据已分享给家人')
}

// 返回上一页
const goBack = () => {
  router.back()
}

onMounted(async () => {
  try {
    loading.value = true
    // 获取健康数据
    const records = await healthAPI.getHealthRecords(1) // 假设当前老人ID为1
    if (records && records.length > 0) {
      const latestRecord = records[0]
      healthData.value = {
        heartRate: latestRecord.heartRate || 72,
        bloodPressure: {
          systolic: latestRecord.bloodPressureHigh || 120,
          diastolic: latestRecord.bloodPressureLow || 80
        },
        bloodSugar: latestRecord.bloodSugar || 5.6,
        steps: 4567 // 模拟步数
      }
    }
  } catch (error) {
    console.error('获取健康数据失败:', error)
  } finally {
    loading.value = false
  }
  
  initCharts()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  // 销毁图表实例
  if (heartRateChart.value) {
    echarts.getInstanceByDom(heartRateChart.value)?.dispose()
  }
  if (bloodPressureChart.value) {
    echarts.getInstanceByDom(bloodPressureChart.value)?.dispose()
  }
  if (bloodSugarChart.value) {
    echarts.getInstanceByDom(bloodSugarChart.value)?.dispose()
  }
  if (trendChart.value) {
    echarts.getInstanceByDom(trendChart.value)?.dispose()
  }
})
</script>

<style scoped>
.health-dashboard {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
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

.back-button, .share-button {
  background: none;
  border: none;
  color: white;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 10px;
  border-radius: 50%;
  transition: background 0.3s ease;
}

.back-button:hover, .share-button:hover {
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

.health-cards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.health-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.health-card:hover {
  transform: translateY(-5px);
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 15px;
}

.card-icon {
  font-size: 1.5rem;
}

.heart-icon {
  color: #ff6b6b;
}

.blood-icon {
  color: #ff9a9e;
}

.sugar-icon {
  color: #f8b400;
}

.steps-icon {
  color: #fad0c4;
}

.card-title {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
}

.card-status {
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 0.9rem;
  font-weight: bold;
}

.card-status.normal {
  background: #ffe6e6;
  color: #ff6b6b;
}

.card-status.warning {
  background: #fff7e6;
  color: #f8b400;
}

.card-status.danger {
  background: #fee2e2;
  color: #dc2626;
}

.card-content {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.value {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
}

.unit {
  font-size: 1rem;
  font-weight: normal;
  color: #666;
}

.chart-container {
  height: 80px;
}

.chart {
  width: 100%;
  height: 100%;
}

.progress-bar {
  width: 100%;
  height: 10px;
  background: #e5e7eb;
  border-radius: 5px;
  overflow: hidden;
}

.progress {
  height: 100%;
  background: linear-gradient(90deg, #ff6b6b 0%, #ee5a6f 100%);
  border-radius: 5px;
  transition: width 0.5s ease;
}

.progress-text {
  font-size: 0.9rem;
  color: #666;
  text-align: right;
}

.health-trend {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 20px;
  margin-bottom: 30px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

.trend-chart {
  height: 300px;
}

.health-tips {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.tips-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.tip-item {
  display: flex;
  align-items: flex-start;
  gap: 15px;
  padding: 15px;
  background: #f9fafb;
  border-radius: 15px;
}

.tip-icon {
  font-size: 1.5rem;
  flex-shrink: 0;
  margin-top: 5px;
}

.tip-content {
  flex: 1;
}

.tip-title {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.tip-text {
  font-size: 1rem;
  color: #666;
  line-height: 1.5;
}

@media (max-width: 768px) {
  .health-cards {
    grid-template-columns: 1fr;
  }
  
  .value {
    font-size: 1.8rem;
  }
  
  .trend-chart {
    height: 250px;
  }
}
</style>
