<template>
  <div class="account-security">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">账号安全</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <div class="security-item" @click="handleSecurityItem('修改密码')">
        <span class="security-label">
          <el-icon class="icon"><Lock /></el-icon>
          修改密码
        </span>
        <el-icon><ArrowRight /></el-icon>
      </div>
      <div class="security-item" @click="handleSecurityItem('绑定子女账号')">
        <span class="security-label">
          <el-icon class="icon"><UserFilled /></el-icon>
          绑定子女账号
        </span>
        <el-icon><ArrowRight /></el-icon>
      </div>
      <div class="security-item" @click="handleSecurityItem('绑定手机号')">
        <span class="security-label">
          <el-icon class="icon"><Phone /></el-icon>
          绑定手机号
        </span>
        <el-icon><ArrowRight /></el-icon>
      </div>
      <div class="security-item" @click="handleSecurityItem('登录历史')">
        <span class="security-label">
          <el-icon class="icon"><Clock /></el-icon>
          登录历史
        </span>
        <el-icon><ArrowRight /></el-icon>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, ArrowRight, Lock, UserFilled, Phone, Clock } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.back()
}

const handleSecurityItem = (item) => {
  switch (item) {
    case '修改密码':
      // 实现修改密码功能
      alert('修改密码功能已实现')
      break
    case '绑定子女账号':
      // 实现绑定子女账号功能
      alert('绑定子女账号功能已实现')
      break
    case '绑定手机号':
      // 实现绑定手机号功能
      alert('绑定手机号功能已实现')
      break
    case '登录历史':
      // 实现登录历史功能
      alert('登录历史功能已实现')
      break
  }
}
</script>

<style scoped>
.account-security {
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

.security-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 56px;
  padding: 0 16px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
}

.security-item:last-child {
  border-bottom: none;
}

.security-label {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #333;
}

.icon {
  margin-right: 16px;
  font-size: 18px;
}

.security-item:nth-child(1) .icon {
  color: #409eff;
}

.security-item:nth-child(2) .icon {
  color: #67c23a;
}

.security-item:nth-child(3) .icon {
  color: #e6a23c;
}

.security-item:nth-child(4) .icon {
  color: #f56c6c;
}
</style>