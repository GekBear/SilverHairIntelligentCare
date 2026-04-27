<template>
  <div class="notifications">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">消息通知</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 消息列表 -->
      <div class="message-item" v-for="(message, index) in messages" :key="index" @click="viewMessageDetail(message)">
        <div class="message-unread" v-if="message.unread"></div>
        <h3>{{ message.title }}</h3>
        <p class="message-content">{{ message.content }}</p>
        <p class="message-time">{{ message.time }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 消息数据
const messages = ref([
  {
    id: 1,
    title: '健康告警',
    content: '您的血压偏高，请及时测量并咨询医生。',
    time: '10:30',
    unread: true
  },
  {
    id: 2,
    title: '子女留言',
    content: '爸爸，今天晚上我会来看您，给您带了您喜欢的水果。',
    time: '09:15',
    unread: true
  },
  {
    id: 3,
    title: '系统提醒',
    content: '您的健康数据已同步到子女端。',
    time: '昨天',
    unread: false
  },
  {
    id: 4,
    title: '服务提醒',
    content: '您预约的家政服务将在明天上午9点到达。',
    time: '昨天',
    unread: false
  },
  {
    id: 5,
    title: '系统通知',
    content: '系统已更新至最新版本，新增了语音唤醒功能。',
    time: '3月20日',
    unread: false
  }
])

const goBack = () => {
  router.back()
}

const viewMessageDetail = (message) => {
  // 标记为已读
  message.unread = false
  // 查看消息详情
  alert('查看消息详情: ' + message.title + '\n' + message.content)
}
</script>

<style scoped>
.notifications {
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
  margin-top: 10px;
  background-color: #fff;
}

.message-item {
  position: relative;
  padding: 16px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.message-item:hover {
  background-color: #f5f7fa;
}

.message-item:last-child {
  border-bottom: none;
}

.message-unread {
  position: absolute;
  top: 20px;
  left: 10px;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #f56c6c;
}

.message-item h3 {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
  padding-left: 8px;
}

.message-content {
  font-size: 13px;
  line-height: 1.4;
  color: #606266;
  margin-bottom: 8px;
  padding-left: 8px;
}

.message-time {
  font-size: 12px;
  color: #909399;
  padding-left: 8px;
}
</style>