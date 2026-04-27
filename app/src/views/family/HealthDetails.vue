<template>
  <div class="health-details">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">健康详情</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 切换按钮 -->
      <div class="toggle-buttons">
        <el-button 
          v-for="period in periods" 
          :key="period.value"
          :type="selectedPeriod === period.value ? 'primary' : ''"
          @click="selectedPeriod = period.value"
        >
          {{ period.label }}
        </el-button>
      </div>

      <!-- 健康数据图表 -->
      <div class="card">
        <h3>血压</h3>
        <div class="chart-container">
          <div ref="bloodPressureChart" class="chart"></div>
        </div>
        <div class="data-stats">
          <div class="stat-item">
            <p class="stat-label">收缩压</p>
            <p class="stat-value">120 mmHg</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">舒张压</p>
            <p class="stat-value">80 mmHg</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">状态</p>
            <p class="stat-value status-normal">正常</p>
          </div>
        </div>
      </div>

      <div class="card">
        <h3>心率</h3>
        <div class="chart-container">
          <div ref="heartRateChart" class="chart"></div>
        </div>
        <div class="data-stats">
          <div class="stat-item">
            <p class="stat-label">心率</p>
            <p class="stat-value">72 次/分</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">最高</p>
            <p class="stat-value">85 次/分</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">状态</p>
            <p class="stat-value status-normal">正常</p>
          </div>
        </div>
      </div>

      <div class="card">
        <h3>血糖</h3>
        <div class="chart-container">
          <div ref="bloodSugarChart" class="chart"></div>
        </div>
        <div class="data-stats">
          <div class="stat-item">
            <p class="stat-label">血糖</p>
            <p class="stat-value status-danger">7.8 mmol/L</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">正常范围</p>
            <p class="stat-value">3.9-6.1</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">状态</p>
            <p class="stat-value status-danger">偏高</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft } from '@element-plus/icons-vue'
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import * as echarts from 'echarts'

const router = useRouter()

// 时间周期选项
const periods = [
  { label: '日', value: 'day' },
  { label: '周', value: 'week' },
  { label: '月', value: 'month' }
]

const selectedPeriod = ref('day')

// 图表引用
const bloodPressureChart = ref(null)
const heartRateChart = ref(null)
const bloodSugarChart = ref(null)

// 图表实例
let bloodPressureChartInstance = null
let heartRateChartInstance = null
let bloodSugarChartInstance = null

const goBack = () => {
  router.back()
}

// 初始化图表
const initCharts = () => {
  // 血压图表
  if (bloodPressureChart.value) {
    bloodPressureChartInstance = echarts.init(bloodPressureChart.value)
    bloodPressureChartInstance.setOption({
      tooltip: {
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        data: ['00:00', '04:00', '08:00', '12:00', '16:00', '20:00']
      },
      yAxis: {
        type: 'value',
        min: 60,
        max: 160
      },
      series: [
        {
          name: '收缩压',
          type: 'line',
          data: [118, 120, 125, 122, 119, 120],
          lineStyle: {
            color: '#409eff'
          }
        },
        {
          name: '舒张压',
          type: 'line',
          data: [78, 80, 82, 81, 79, 80],
          lineStyle: {
            color: '#67c23a'
          }
        }
      ]
    })
  }

  // 心率图表
  if (heartRateChart.value) {
    heartRateChartInstance = echarts.init(heartRateChart.value)
    heartRateChartInstance.setOption({
      tooltip: {
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        data: ['00:00', '04:00', '08:00', '12:00', '16:00', '20:00']
      },
      yAxis: {
        type: 'value',
        min: 60,
        max: 100
      },
      series: [
        {
          name: '心率',
          type: 'line',
          data: [70, 68, 75, 85, 78, 72],
          lineStyle: {
            color: '#67c23a'
          }
        }
      ]
    })
  }

  // 血糖图表
  if (bloodSugarChart.value) {
    bloodSugarChartInstance = echarts.init(bloodSugarChart.value)
    bloodSugarChartInstance.setOption({
      tooltip: {
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        data: ['早餐前', '早餐后', '午餐前', '午餐后', '晚餐前', '晚餐后']
      },
      yAxis: {
        type: 'value',
        min: 3,
        max: 10
      },
      series: [
        {
          name: '血糖',
          type: 'line',
          data: [6.2, 8.5, 6.5, 9.2, 7.0, 7.8],
          lineStyle: {
            color: '#e6a23c'
          }
        }
      ]
    })
  }
}

// 监听窗口大小变化，调整图表大小
const handleResize = () => {
  bloodPressureChartInstance?.resize()
  heartRateChartInstance?.resize()
  bloodSugarChartInstance?.resize()
}

onMounted(() => {
  initCharts()
  window.addEventListener('resize', handleResize)
})

// 监听时间周期变化
watch(selectedPeriod, (newPeriod) => {
  // 这里可以根据选择的时间周期更新图表数据
  console.log('切换到', newPeriod, '视图')
})
</script>

<style scoped>
.health-details {
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

.toggle-buttons {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-bottom: 20px;
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

.chart-container {
  height: 200px;
  background-color: #f8f9fa;
  border-radius: 16px;
  margin-bottom: 16px;
}

.chart {
  width: 100%;
  height: 100%;
}

.data-stats {
  display: flex;
  justify-content: space-between;
}

.stat-item {
  flex: 1;
  text-align: center;
}

.stat-label {
  font-size: 12px;
  color: #909399;
  margin-bottom: 4px;
}

.stat-value {
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

.status-normal {
  color: #67c23a;
}

.status-danger {
  color: #f56c6c;
}
</style>