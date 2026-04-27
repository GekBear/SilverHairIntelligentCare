<template>
  <div class="help">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">帮助中心</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <div class="help-item" v-for="(item, index) in helpItems" :key="index" @click="handleHelpItem(item)">
        <div class="help-title">
          <el-icon :class="['icon', item.iconClass]">{{ item.icon }}</el-icon>
          {{ item.title }}
        </div>
        <div class="help-content">{{ item.content }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, VideoPlay, Phone, QuestionFilled, ChatLineRound } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 帮助项数据
const helpItems = [
  {
    title: '使用教程',
    content: '查看详细的使用指南和视频教程',
    icon: VideoPlay,
    iconClass: 'icon-primary'
  },
  {
    title: '客服电话',
    content: '400-123-4567',
    icon: Phone,
    iconClass: 'icon-success'
  },
  {
    title: '常见问题',
    content: '查看常见问题解答',
    icon: QuestionFilled,
    iconClass: 'icon-warning'
  },
  {
    title: '在线客服',
    content: '实时在线咨询服务',
    icon: ChatLineRound,
    iconClass: 'icon-danger'
  }
]

const goBack = () => {
  router.back()
}

const handleHelpItem = (item) => {
  switch (item.title) {
    case '使用教程':
      // 跳转到使用教程页面
      console.log('进入使用教程页面')
      ElMessage.info('进入使用教程页面')
      break
    case '客服电话':
      // 拨打电话
      console.log('拨打电话：400-123-4567')
      ElMessage.info('拨打电话：400-123-4567')
      break
    case '常见问题':
      // 跳转到常见问题页面
      console.log('查看常见问题解答')
      ElMessage.info('查看常见问题解答')
      break
    case '在线客服':
      // 跳转到在线客服页面
      console.log('进入在线客服聊天页面')
      ElMessage.info('进入在线客服聊天页面')
      break
  }
}
</script>

<style scoped>
.help {
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

.help-item {
  padding: 16px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.help-item:hover {
  background-color: #f5f7fa;
}

.help-item:last-child {
  border-bottom: none;
}

.help-title {
  display: flex;
  align-items: center;
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 8px;
}

.icon {
  margin-right: 16px;
  font-size: 18px;
}

.icon-primary {
  color: #409eff;
}

.icon-success {
  color: #67c23a;
}

.icon-warning {
  color: #e6a23c;
}

.icon-danger {
  color: #f56c6c;
}

.help-content {
  font-size: 12px;
  color: #909399;
  margin-left: 34px;
}
</style>