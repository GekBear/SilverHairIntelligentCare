<template>
  <div class="remote-assist">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">远程协助</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 远程控制功能 -->
      <div class="card">
        <h3>远程控制</h3>
        <div class="remote-features">
          <div class="feature-item" @click="controlDevice('lights')">
            <div class="feature-icon feature-primary">
              <el-icon><Sunny /></el-icon>
            </div>
            <div class="feature-name">灯光控制</div>
          </div>
          <div class="feature-item" @click="controlDevice('temperature')">
            <div class="feature-icon feature-success">
              <el-icon><ColdDrink /></el-icon>
            </div>
            <div class="feature-name">温度调节</div>
          </div>
          <div class="feature-item" @click="controlDevice('camera')">
            <div class="feature-icon feature-warning">
              <el-icon><VideoCamera /></el-icon>
            </div>
            <div class="feature-name">摄像头</div>
          </div>
          <div class="feature-item" @click="controlDevice('alarm')">
            <div class="feature-icon feature-danger">
              <el-icon><Bell /></el-icon>
            </div>
            <div class="feature-name">报警器</div>
          </div>
        </div>
      </div>

      <!-- 远程查看 -->
      <div class="card">
        <h3>远程查看</h3>
        <div class="remote-view">
          <div class="view-item" @click="viewHealthData">
            <div class="view-icon">
              <el-icon><UserFilled /></el-icon>
            </div>
            <div class="view-name">健康数据</div>
          </div>
          <div class="view-item" @click="viewLocation">
            <div class="view-icon">
              <el-icon><Location /></el-icon>
            </div>
            <div class="view-name">位置信息</div>
          </div>
          <div class="view-item" @click="viewActivity">
            <div class="view-icon">
              <el-icon><Clock /></el-icon>
            </div>
            <div class="view-name">活动记录</div>
          </div>
        </div>
      </div>

      <!-- 紧急操作 -->
      <div class="card">
        <h3>紧急操作</h3>
        <div class="emergency-actions">
          <el-button type="danger" class="emergency-btn" @click="callEmergency">
            <el-icon><Phone /></el-icon>
            紧急呼叫
          </el-button>
          <el-button type="warning" class="emergency-btn" @click="sendAlert">
            <el-icon><Warning /></el-icon>
            发送警报
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, Sunny, ColdDrink, VideoCamera, Bell, UserFilled, Location, Clock, Phone, Warning } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

const goBack = () => {
  router.back()
}

const controlDevice = (device) => {
  // 模拟控制设备
  console.log('控制设备:', device)
  ElMessage.info(`控制${device}设备`)
}

const viewHealthData = () => {
  // 跳转到健康数据页面
  console.log('查看健康数据')
  ElMessage.info('查看健康数据')
}

const viewLocation = () => {
  // 跳转到位置信息页面
  console.log('查看位置信息')
  ElMessage.info('查看位置信息')
}

const viewActivity = () => {
  // 跳转到活动记录页面
  console.log('查看活动记录')
  ElMessage.info('查看活动记录')
}

const callEmergency = () => {
  // 模拟紧急呼叫
  console.log('紧急呼叫')
  ElMessage.info('正在发起紧急呼叫')
}

const sendAlert = () => {
  // 模拟发送警报
  console.log('发送警报')
  ElMessage.info('正在发送警报')
}
</script>

<style scoped>
.remote-assist {
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

.remote-features {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.feature-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.feature-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.feature-icon {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12px;
  font-size: 24px;
  color: #fff;
}

.feature-primary {
  background-color: #409eff;
}

.feature-success {
  background-color: #67c23a;
}

.feature-warning {
  background-color: #e6a23c;
}

.feature-danger {
  background-color: #f56c6c;
}

.feature-name {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.remote-view {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.view-item {
  display: flex;
  align-items: center;
  padding: 16px;
  background-color: #f8f9fa;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.view-item:hover {
  background-color: #e9ecef;
}

.view-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #409eff;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  font-size: 18px;
}

.view-name {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.emergency-actions {
  display: flex;
  gap: 16px;
}

.emergency-btn {
  flex: 1;
}
</style>