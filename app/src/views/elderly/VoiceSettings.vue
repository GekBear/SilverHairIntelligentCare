<template>
  <div class="voice-settings">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">语音设置</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <div class="card">
        <div class="voice-setting-item" @click="selectDialect">
          <span class="voice-setting-label">方言选择</span>
          <span class="voice-setting-value">{{ settings.dialect }}</span>
        </div>
        <div class="voice-setting-item" @click="selectSpeed">
          <span class="voice-setting-label">语速调节</span>
          <span class="voice-setting-value">{{ settings.speed }}</span>
        </div>
        <div class="voice-setting-item" @click="adjustVolume">
          <span class="voice-setting-label">音量大小</span>
          <span class="voice-setting-value">{{ settings.volume }}%</span>
        </div>
        <div class="voice-setting-item" @click="toggleVoiceWakeup">
          <span class="voice-setting-label">语音唤醒</span>
          <span class="voice-setting-value">{{ settings.voiceWakeup ? '开启' : '关闭' }}</span>
        </div>
      </div>

      <div class="card mt-16">
        <h3>语音测试</h3>
        <p class="mt-16">点击下方按钮测试语音效果</p>
        <el-button type="primary" class="mt-16" @click="playTestVoice">
          <el-icon><Headset /></el-icon>
          播放测试语音
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, Headset } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'

const router = useRouter()

// 语音设置状态
const settings = ref({
  dialect: '普通话',
  speed: '正常',
  volume: 50,
  voiceWakeup: true
})

const goBack = () => {
  router.back()
}

const selectDialect = () => {
  // 模拟选择方言
  ElMessageBox.confirm('请选择方言：\n1. 普通话\n2. 粤语\n3. 四川话\n4. 东北话', '方言选择', {
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  }).then(() => {
    // 实际项目中可以提供更多选择方式
    console.log('选择方言')
  })
}

const selectSpeed = () => {
  // 模拟选择语速
  ElMessageBox.confirm('请选择语速：\n1. 慢速\n2. 正常\n3. 快速', '语速调节', {
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  }).then(() => {
    // 实际项目中可以提供更多选择方式
    console.log('选择语速')
  })
}

const adjustVolume = () => {
  // 模拟调节音量
  ElMessageBox.confirm('请调节音量：0-100%', '音量大小', {
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  }).then(() => {
    // 实际项目中可以提供滑块调节
    console.log('调节音量')
  })
}

const toggleVoiceWakeup = () => {
  settings.value.voiceWakeup = !settings.value.voiceWakeup
  console.log('语音唤醒已' + (settings.value.voiceWakeup ? '开启' : '关闭'))
}

const playTestVoice = () => {
  // 模拟播放测试语音
  console.log('正在播放测试语音...\n你好，这是一段测试语音，用于检测语音设置效果。')
  ElMessageBox.alert('正在播放测试语音...\n你好，这是一段测试语音，用于检测语音设置效果。', '语音测试', {
    confirmButtonText: '确定'
  })
}
</script>

<style scoped>
.voice-settings {
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
}

.mt-16 {
  margin-top: 16px;
}

.voice-setting-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 48px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.voice-setting-item:hover {
  background-color: #f5f7fa;
}

.voice-setting-item:last-child {
  border-bottom: none;
}

.voice-setting-label {
  font-size: 14px;
  color: #333;
}

.voice-setting-value {
  font-size: 14px;
  color: #909399;
}

.card h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 16px;
}

.card p {
  font-size: 14px;
  color: #606266;
  line-height: 1.4;
}

.card .el-button {
  width: 100%;
  margin-top: 16px;
}
</style>