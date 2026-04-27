<template>
  <div class="settings">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">设置</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <div class="card">
        <div class="list-item" @click="navigateTo('/family/relative-management')">
          <span class="list-label">
            <el-icon class="icon icon-primary"><UserFilled /></el-icon>
            亲属管理
          </span>
          <el-icon><ArrowRight /></el-icon>
        </div>
        <div class="list-item" @click="navigateTo('/family/safety-guard')">
          <span class="list-label">
            <el-icon class="icon icon-success"><Lock /></el-icon>
            安全设置
          </span>
          <el-icon><ArrowRight /></el-icon>
        </div>
        <div class="list-item" @click="navigateTo('/family/about')">
          <span class="list-label">
            <el-icon class="icon icon-info"><InfoFilled /></el-icon>
            关于我们
          </span>
          <el-icon><ArrowRight /></el-icon>
        </div>
        <div class="list-item" @click="navigateTo('/family/help')">
          <span class="list-label">
            <el-icon class="icon icon-warning"><QuestionFilled /></el-icon>
            帮助中心
          </span>
          <el-icon><ArrowRight /></el-icon>
        </div>
        <div class="list-item" @click="logout">
          <span class="list-label">
            <el-icon class="icon icon-danger"><SwitchButton /></el-icon>
            退出登录
          </span>
          <el-icon><ArrowRight /></el-icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, ArrowRight, UserFilled, Lock, InfoFilled, QuestionFilled, SwitchButton } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'

const router = useRouter()

const goBack = () => {
  router.back()
}

const navigateTo = (path) => {
  router.push(path)
}

const logout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '退出登录', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 执行退出登录逻辑
    console.log('已退出登录')
    // 跳转到登录页面
    router.push('/')
  }).catch(() => {
    // 取消退出
  })
}
</script>

<style scoped>
.settings {
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
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.list-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 56px;
  padding: 0 16px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.list-item:hover {
  background-color: #f5f7fa;
}

.list-item:last-child {
  border-bottom: none;
}

.list-label {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #333;
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

.icon-info {
  color: #909399;
}
</style>