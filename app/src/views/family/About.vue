<template>
  <div class="about">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">关于我们</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <div class="card">
        <div class="logo">
          <el-icon class="logo-icon"><HeartPulse /></el-icon>
        </div>
        <h2>银发智护</h2>
        <p class="subtitle">智能养老陪伴平台</p>
        <p class="version">版本 3.0.0</p>
        
        <div class="content">
          <h3>应用介绍</h3>
          <p class="description">
            银发智护是一款专为老年人设计的智能养老陪伴平台，旨在通过科技手段为老年人提供更好的养老服务，让子女更放心。
          </p>
          
          <h3>核心功能</h3>
          <ul class="feature-list">
            <li>健康监测与管理</li>
            <li>AI智能陪伴</li>
            <li>紧急求助（SOS）</li>
            <li>远程关爱</li>
            <li>养老服务对接</li>
          </ul>
          
          <h3>联系我们</h3>
          <p class="contact-info">客服电话：400-123-4567</p>
          <p class="contact-info">邮箱：support@yinfa.com</p>
          <p class="contact-info">地址：北京市朝阳区科技园区</p>
        </div>
        
        <p class="copyright">© 2026 银发智护 版权所有</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, HeartPulse } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.back()
}
</script>

<style scoped>
.about {
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
  padding: 32px 24px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.logo {
  margin-bottom: 24px;
}

.logo-icon {
  font-size: 64px;
  color: #409eff;
}

.card h2 {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
}

.subtitle {
  font-size: 14px;
  color: #909399;
  margin-bottom: 16px;
}

.version {
  font-size: 16px;
  color: #606266;
  margin-bottom: 32px;
}

.content {
  text-align: left;
  margin-bottom: 24px;
}

.content h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-top: 24px;
  margin-bottom: 16px;
}

.content h3:first-child {
  margin-top: 0;
}

.description {
  font-size: 14px;
  line-height: 1.5;
  color: #606266;
  margin-bottom: 24px;
}

.feature-list {
  font-size: 14px;
  line-height: 1.5;
  color: #606266;
  list-style-position: inside;
  margin-bottom: 24px;
}

.contact-info {
  font-size: 14px;
  line-height: 1.5;
  color: #606266;
}

.copyright {
  font-size: 14px;
  color: #909399;
  margin-top: 24px;
}
</style>