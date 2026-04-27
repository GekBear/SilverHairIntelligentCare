<template>
  <div class="landing">
    <div class="hero">
      <div class="container">
        <h1 class="title">银发智护</h1>
        <p class="subtitle">智能养老陪伴平台</p>
        <div class="buttons">
          <el-button type="primary" @click="goToElderly">老人端</el-button>
          <el-button @click="goToFamily">子女端</el-button>
          <el-button @click="goToAdmin">管理端</el-button>
        </div>
      </div>
    </div>
    <div class="features">
      <div class="container">
        <h2 class="section-title">核心功能</h2>
        <div class="feature-list">
          <div class="feature-item">
            <el-icon class="feature-icon"><i class="el-icon-warning"></i></el-icon>
            <h3>SOS紧急求助</h3>
            <p>一键呼叫，快速响应，保障老人安全</p>
          </div>
          <div class="feature-item">
            <el-icon class="feature-icon"><i class="el-icon-data-analysis"></i></el-icon>
            <h3>健康管理</h3>
            <p>实时监测，异常提醒，关注老人健康</p>
          </div>
          <div class="feature-item">
            <el-icon class="feature-icon"><i class="el-icon-microphone"></i></el-icon>
            <h3>AI语音陪伴</h3>
            <p>智能对话，情感陪伴，缓解老人孤独</p>
          </div>
          <div class="feature-item">
            <el-icon class="feature-icon"><i class="el-icon-s-order"></i></el-icon>
            <h3>生活服务</h3>
            <p>助餐、家政、维修，全方位服务</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goToElderly = () => {
  router.push('/elderly')
}

const goToFamily = () => {
  router.push('/family')
}

const goToAdmin = () => {
  router.push('/admin')
}
</script>

<style scoped>
.landing {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.hero {
  padding: 100px 0;
  text-align: center;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.title {
  font-size: 4rem;
  font-weight: bold;
  margin-bottom: 20px;
}

.subtitle {
  font-size: 1.5rem;
  margin-bottom: 40px;
  opacity: 0.9;
}

.buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.buttons .el-button {
  font-size: 1.2rem;
  padding: 12px 30px;
}

.features {
  padding: 100px 0;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
}

.section-title {
  font-size: 2.5rem;
  text-align: center;
  margin-bottom: 60px;
}

.feature-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
}

.feature-item {
  text-align: center;
  padding: 30px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  backdrop-filter: blur(10px);
  transition: transform 0.3s ease;
}

.feature-item:hover {
  transform: translateY(-10px);
}

.feature-icon {
  font-size: 3rem;
  margin-bottom: 20px;
  color: #ffd700;
}

.feature-item h3 {
  font-size: 1.5rem;
  margin-bottom: 15px;
}

.feature-item p {
  opacity: 0.8;
  line-height: 1.6;
}

@media (max-width: 768px) {
  .title {
    font-size: 3rem;
  }
  
  .buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .feature-list {
    grid-template-columns: 1fr;
  }
}
</style>
