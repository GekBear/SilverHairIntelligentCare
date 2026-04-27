<template>
  <div class="health-records">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">健康记录</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 今日健康数据 -->
      <div class="card">
        <h3>今日健康数据</h3>
        <div class="mt-16">
          <div class="health-item" v-for="(item, index) in todayHealthData" :key="index">
            <span class="health-label">{{ item.label }}</span>
            <span class="health-value">{{ item.value }}</span>
          </div>
        </div>
      </div>

      <!-- 历史记录 -->
      <div class="card">
        <h3>历史记录</h3>
        <div class="mt-16">
          <div class="health-item" v-for="(record, index) in historyRecords" :key="index" @click="viewRecordDetail(record)">
            <span class="health-label">{{ record.date }}</span>
            <span class="health-value">{{ record.status }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加按钮 -->
    <button class="add-btn" @click="addHealthData">
      <el-icon><Plus /></el-icon>
    </button>
  </div>
</template>

<script setup>
import { ArrowLeft, Plus } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 今日健康数据
const todayHealthData = ref([
  { label: '血压', value: '120/80 mmHg' },
  { label: '心率', value: '72 次/分' },
  { label: '血糖', value: '5.6 mmol/L' },
  { label: '步数', value: '3,200 步' }
])

// 历史记录
const historyRecords = ref([
  { date: '3月22日', status: '正常' },
  { date: '3月21日', status: '正常' },
  { date: '3月20日', status: '正常' },
  { date: '3月19日', status: '正常' },
  { date: '3月18日', status: '正常' }
])

const goBack = () => {
  router.back()
}

const addHealthData = () => {
  // 跳转到健康数据录入页面
  router.push('/elderly/health-input')
}

const viewRecordDetail = (record) => {
  // 查看历史记录详情
  alert('查看历史记录详情: ' + record.date + ' - ' + record.status)
}
</script>

<style scoped>
.health-records {
  height: 100vh;
  background-color: #f5f5f5;
  position: relative;
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
  padding-bottom: 120px; /* 为底部导航栏留出空间 */
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

.mt-16 {
  margin-top: 16px;
}

.health-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 48px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
}

.health-item:last-child {
  border-bottom: none;
}

.health-label {
  font-size: 14px;
  color: #606266;
}

.health-value {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.add-btn {
  position: fixed;
  bottom: 100px;
  right: 20px;
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background-color: #409eff;
  color: #fff;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
  cursor: pointer;
  font-size: 24px;
}
</style>