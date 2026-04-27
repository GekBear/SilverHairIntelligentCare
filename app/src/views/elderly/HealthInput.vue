<template>
  <div class="health-input">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">健康数据录入</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <div class="health-form">
        <div class="form-group">
          <label class="form-label">血压 (mmHg)</label>
          <el-input v-model="healthData.bloodPressure" placeholder="请输入血压，如 120/80" />
        </div>

        <div class="form-group">
          <label class="form-label">心率 (次/分)</label>
          <el-input v-model.number="healthData.heartRate" type="number" placeholder="请输入心率" />
        </div>

        <div class="form-group">
          <label class="form-label">血糖 (mmol/L)</label>
          <el-input v-model.number="healthData.bloodSugar" type="number" step="0.1" placeholder="请输入血糖" />
        </div>

        <div class="form-group">
          <label class="form-label">步数</label>
          <el-input v-model.number="healthData.steps" type="number" placeholder="请输入步数" />
        </div>

        <div class="form-buttons">
          <el-button @click="goBack" style="background-color: #E2E8F0; color: #333;">取消</el-button>
          <el-button type="primary" @click="saveHealthData">保存</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../../services/api'

const router = useRouter()

// 健康数据状态
const healthData = ref({
  bloodPressure: '',
  heartRate: '',
  bloodSugar: '',
  steps: ''
})

const goBack = () => {
  router.back()
}

const saveHealthData = async () => {
  try {
    // 模拟保存健康数据
    console.log('保存健康数据:', healthData.value)
    // 实际项目中调用API保存数据
    // await api.saveHealthData(healthData.value)
    
    // 保存成功后跳转到健康记录页面
    router.push('/elderly/health-records')
  } catch (error) {
    console.error('保存健康数据失败:', error)
  }
}
</script>

<style scoped>
.health-input {
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

.health-form {
  background-color: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 8px;
}

.form-buttons {
  display: flex;
  gap: 12px;
  margin-top: 30px;
}

.form-buttons button {
  flex: 1;
  height: 44px;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
}
</style>