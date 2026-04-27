<template>
  <div class="care-interaction">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">关爱互动</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 留言区域 -->
      <div class="card">
        <h3>给父母留言</h3>
        <el-input
          v-model="messageContent"
          type="textarea"
          :rows="4"
          placeholder="请输入留言内容..."
          class="message-textarea"
        />
        <div class="message-actions">
          <el-button @click="recordVoiceMessage">
            <el-icon><Microphone /></el-icon>
            语音留言
          </el-button>
          <el-button type="primary" @click="sendMessage">
            <el-icon><Message /></el-icon>
            发送
          </el-button>
        </div>
      </div>

      <!-- 定时提醒 -->
      <div class="card">
        <h3>定时提醒</h3>
        <div class="reminder-item" v-for="(reminder, index) in reminders" :key="index" @click="editReminder(reminder)">
          <span>{{ reminder.title }}</span>
          <div class="reminder-time">
            <span>{{ reminder.time }}</span>
            <el-icon><ArrowRight /></el-icon>
          </div>
        </div>
        <el-button type="primary" class="add-reminder-btn" @click="addReminder">
          <el-icon><Plus /></el-icon>
          添加提醒
        </el-button>
      </div>

      <!-- 历史留言 -->
      <div class="card">
        <h3>历史留言</h3>
        <div class="message-item" v-for="(message, index) in historyMessages" :key="index">
          <h4>{{ message.title }}</h4>
          <p class="message-body">{{ message.content }}</p>
          <p class="message-time">{{ message.time }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, Microphone, Message, Plus, ArrowRight } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 留言内容
const messageContent = ref('')

// 定时提醒数据
const reminders = ref([
  { title: '吃药提醒', time: '每天 8:00' },
  { title: '喝水提醒', time: '每 2 小时' },
  { title: '散步提醒', time: '每天 16:00' }
])

// 历史留言数据
const historyMessages = ref([
  {
    title: '爸爸，今天晚上我会来看您',
    content: '给您带了您喜欢的水果，记得按时吃药哦。',
    time: '2026-03-23 09:15'
  },
  {
    title: '妈妈，周末一起去公园',
    content: '天气很好，我们一起去公园散步吧。',
    time: '2026-03-22 18:30'
  }
])

const goBack = () => {
  router.back()
}

const sendMessage = () => {
  if (!messageContent.value.trim()) {
    ElMessage.warning('请输入留言内容')
    return
  }
  
  // 模拟发送留言
  console.log('发送留言:', messageContent.value)
  ElMessage.success('留言已发送')
  messageContent.value = ''
}

const recordVoiceMessage = () => {
  // 模拟录制语音
  console.log('开始录制语音...')
  ElMessage.info('开始录制语音...')
}

const addReminder = () => {
  // 模拟添加提醒
  console.log('添加新提醒')
  ElMessage.info('添加新提醒')
}

const editReminder = (reminder) => {
  // 模拟编辑提醒
  console.log('编辑提醒:', reminder)
  ElMessage.info(`编辑${reminder.title}提醒`)
}
</script>

<style scoped>
.care-interaction {
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

.message-textarea {
  margin-bottom: 16px;
}

.message-actions {
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

.reminder-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 48px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.reminder-item:hover {
  background-color: #f5f7fa;
}

.reminder-item:last-child {
  border-bottom: none;
}

.reminder-time {
  display: flex;
  align-items: center;
  gap: 16px;
  color: #909399;
  font-size: 14px;
}

.add-reminder-btn {
  width: 100%;
  margin-top: 16px;
}

.message-item {
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f0;
}

.message-item:last-child {
  border-bottom: none;
}

.message-item h4 {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
}

.message-body {
  font-size: 13px;
  line-height: 1.4;
  color: #606266;
  margin-bottom: 8px;
}

.message-time {
  font-size: 12px;
  color: #909399;
}
</style>