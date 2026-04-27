<template>
  <div class="message-center">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">消息中心</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 消息列表 -->
      <div class="message-item" v-for="(message, index) in messages" :key="index" @click="viewMessageDetail(message)">
        <div class="message-unread" v-if="message.unread"></div>
        <div class="message-avatar" :class="message.avatarClass">
          <el-icon>{{ message.icon }}</el-icon>
        </div>
        <div class="message-content">
          <div class="message-header">
            <h4>{{ message.title }}</h4>
            <span class="message-time">{{ message.time }}</span>
          </div>
          <p class="message-body">{{ message.content }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, Bell, Message, Warning, Phone } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 消息数据
const messages = ref([
  {
    id: 1,
    title: '健康告警',
    content: '您父亲的血糖偏高，请及时关注。',
    time: '10:30',
    unread: true,
    icon: Warning,
    avatarClass: 'avatar-warning'
  },
  {
    id: 2,
    title: '父亲留言',
    content: '今天感觉很好，不用担心。',
    time: '09:15',
    unread: true,
    icon: Message,
    avatarClass: 'avatar-primary'
  },
  {
    id: 3,
    title: '系统提醒',
    content: '您父亲的健康数据已更新。',
    time: '昨天',
    unread: false,
    icon: Bell,
    avatarClass: 'avatar-info'
  },
  {
    id: 4,
    title: '紧急求助',
    content: '您父亲触发了SOS求助，请立即查看。',
    time: '3月20日',
    unread: false,
    icon: Phone,
    avatarClass: 'avatar-danger'
  }
])

const goBack = () => {
  router.back()
}

const viewMessageDetail = (message) => {
  // 标记为已读
  message.unread = false
  // 查看消息详情
  console.log('查看消息详情:', message)
  ElMessage.info(`查看${message.title}详情`)
}
</script>

<style scoped>
.message-center {
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
  display: flex;
  align-items: flex-start;
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
  top: 16px;
  left: 12px;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #f56c6c;
  z-index: 1;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
  font-size: 18px;
  color: #fff;
}

.avatar-primary {
  background-color: #409eff;
}

.avatar-success {
  background-color: #67c23a;
}

.avatar-warning {
  background-color: #e6a23c;
}

.avatar-danger {
  background-color: #f56c6c;
}

.avatar-info {
  background-color: #909399;
}

.message-content {
  flex: 1;
  min-width: 0;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.message-header h4 {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex: 1;
  margin-right: 12px;
}

.message-time {
  font-size: 12px;
  color: #909399;
  white-space: nowrap;
}

.message-body {
  font-size: 13px;
  line-height: 1.4;
  color: #606266;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>