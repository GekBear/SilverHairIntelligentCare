<template>
  <div class="health-report">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">健康报告</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 报告类型切换 -->
      <div class="report-type-toggle">
        <el-button 
          v-for="type in reportTypes" 
          :key="type.value"
          :type="selectedReportType === type.value ? 'primary' : ''"
          @click="selectedReportType = type.value"
        >
          {{ type.label }}
        </el-button>
      </div>

      <!-- 健康报告内容 -->
      <div class="card">
        <div class="report-header">
          <h3>健康报告</h3>
          <p class="report-date">{{ currentDate }}</p>
        </div>
        
        <div class="report-section">
          <h4>健康概览</h4>
          <p class="report-content">
            本周老人健康状况整体良好，各项指标基本在正常范围内。血压和心率保持稳定，血糖略有偏高，建议注意饮食控制。
          </p>
        </div>

        <div class="report-section">
          <h4>关键指标</h4>
          <div class="health-indicators">
            <div class="indicator-item">
              <span class="indicator-label">血压</span>
              <span class="indicator-value">120/80 mmHg</span>
              <span class="indicator-status status-normal">正常</span>
            </div>
            <div class="indicator-item">
              <span class="indicator-label">心率</span>
              <span class="indicator-value">72 次/分</span>
              <span class="indicator-status status-normal">正常</span>
            </div>
            <div class="indicator-item">
              <span class="indicator-label">血糖</span>
              <span class="indicator-value">7.8 mmol/L</span>
              <span class="indicator-status status-danger">偏高</span>
            </div>
            <div class="indicator-item">
              <span class="indicator-label">步数</span>
              <span class="indicator-value">5,200 步/天</span>
              <span class="indicator-status status-normal">正常</span>
            </div>
          </div>
        </div>

        <div class="report-section">
          <h4>健康建议</h4>
          <ul class="suggestion-list">
            <li>注意控制饮食，减少糖分摄入</li>
            <li>保持适量运动，每天步行30分钟</li>
            <li>按时服药，定期监测血糖</li>
            <li>保持良好的作息习惯，保证充足睡眠</li>
          </ul>
        </div>

        <div class="report-actions">
          <el-button type="primary" @click="downloadReport">
            <el-icon><Download /></el-icon>
            下载报告
          </el-button>
          <el-button @click="shareReport">
            <el-icon><Share /></el-icon>
            分享报告
          </el-button>
        </div>
      </div>

      <!-- 历史报告 -->
      <div class="card">
        <h3>历史报告</h3>
        <div class="history-report-item" v-for="(report, index) in historyReports" :key="index" @click="viewHistoryReport(report)">
          <div class="report-info">
            <h4>{{ report.title }}</h4>
            <p class="report-date">{{ report.date }}</p>
          </div>
          <el-icon><ArrowRight /></el-icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, Download, Share, ArrowRight } from '@element-plus/icons-vue'
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 报告类型
const reportTypes = [
  { label: '日报', value: 'daily' },
  { label: '周报', value: 'weekly' },
  { label: '月报', value: 'monthly' }
]

const selectedReportType = ref('weekly')

// 当前日期
const currentDate = computed(() => {
  const now = new Date()
  return now.toISOString().split('T')[0]
})

// 历史报告数据
const historyReports = ref([
  { title: '周报 - 2026年3月第2周', date: '2026-03-16' },
  { title: '周报 - 2026年3月第1周', date: '2026-03-09' },
  { title: '月报 - 2026年2月', date: '2026-02-29' }
])

const goBack = () => {
  router.back()
}

const downloadReport = () => {
  // 模拟下载报告
  console.log('下载报告')
  ElMessage.success('报告已开始下载')
}

const shareReport = () => {
  // 模拟分享报告
  console.log('分享报告')
  ElMessage.success('报告分享成功')
}

const viewHistoryReport = (report) => {
  // 查看历史报告
  console.log('查看历史报告:', report)
  ElMessage.info(`查看${report.title}报告`)
}
</script>

<style scoped>
.health-report {
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

.report-type-toggle {
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

.report-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.report-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.report-date {
  font-size: 14px;
  color: #909399;
}

.report-section {
  margin-bottom: 24px;
}

.report-section h4 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 16px;
}

.report-content {
  font-size: 14px;
  line-height: 1.5;
  color: #606266;
}

.health-indicators {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.indicator-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.indicator-label {
  font-size: 14px;
  color: #606266;
}

.indicator-value {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.indicator-status {
  font-size: 12px;
  font-weight: 500;
  padding: 2px 8px;
  border-radius: 10px;
}

.status-normal {
  color: #67c23a;
  background-color: #f0f9eb;
}

.status-danger {
  color: #f56c6c;
  background-color: #fef0f0;
}

.suggestion-list {
  font-size: 14px;
  line-height: 1.5;
  color: #606266;
  list-style-position: inside;
  margin-left: 8px;
}

.suggestion-list li {
  margin-bottom: 8px;
}

.report-actions {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-top: 32px;
}

.history-report-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.history-report-item:hover {
  background-color: #f5f7fa;
}

.history-report-item:last-child {
  border-bottom: none;
}

.report-info h4 {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
}
</style>