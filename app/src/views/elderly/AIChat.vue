<template>
  <div class="ai-chat">
    <!-- 顶部导航 -->
    <div class="header">
      <div class="back-button" @click="goBack">
        <span class="back-icon">←</span>
        <span class="back-text">返回</span>
      </div>
      <h1 class="page-title">AI智能助手</h1>
      <div class="header-right">
        <button class="settings-button" @click="showSettings">
          <span class="settings-icon">⚙️</span>
        </button>
      </div>
    </div>

    <!-- 语音通话界面 -->
    <div class="voice-call-container">
      <!-- 内容区域 -->
    <div class="content-area">
      <!-- 提示标签 -->
      <div class="prompt-tags" v-if="!voiceResult && !aiResponse && !medicineResult">
        <span class="tag" v-for="(prompt, index) in prompts" :key="index" @click="selectPrompt(prompt)">{{ prompt }}</span>
      </div>

        <!-- 视频区域 -->
        <div class="video-section" v-if="isVideoMode">
          <div class="video-container">
            <video ref="videoElement" class="video-feed" autoplay></video>
            <div class="video-overlay">
              <div class="video-instructions">请将药物放在摄像头前</div>
              <div class="video-focus"></div>
            </div>
          </div>
          <button class="capture-button" @click="captureImage">
            <span class="capture-icon">📷</span>
            <span class="capture-text">拍摄识别</span>
          </button>
        </div>
        <!-- 中央AI球体 -->
        <div class="ai-orb" :class="{ active: isListening }" v-else>
          <div class="orb-inner"></div>
          <div class="orb-glow"></div>
        </div>

        <!-- 状态提示 -->
        <div class="status-prompt" v-if="!medicineResult">
          <span v-if="isListening" class="listening-indicator">🔊</span>
          {{ statusText }}
        </div>

        <!-- 语音识别结果 -->
        <div class="voice-result" v-if="voiceResult">
          <p class="result-content">{{ voiceResult }}</p>
        </div>

        <!-- AI回复 -->
        <div class="ai-response" v-if="aiResponse">
          <p class="response-content">{{ aiResponse }}</p>
        </div>

        <!-- 药物识别结果弹窗 -->
        <div class="medicine-dialog" v-if="medicineResult">
          <div class="dialog-content">
            <button class="close-button" @click="closeMedicineDialog">
              <span class="close-icon">✕</span>
            </button>
            <h3 class="medicine-title">药物识别结果</h3>
            <div class="medicine-info">
              <div class="medicine-name">{{ medicineResult.name }}</div>
              <div class="medicine-desc">{{ medicineResult.description }}</div>
              
              <!-- 药物用法 -->
              <div class="medicine-section">
                <h4 class="section-subtitle">
                  <span class="section-icon">💊</span>
                  药物用法
                </h4>
                <div class="medicine-usage">{{ medicineResult.usage }}</div>
              </div>
              
              <!-- 药物禁忌 -->
              <div class="medicine-section">
                <h4 class="section-subtitle">
                  <span class="section-icon">⚠️</span>
                  药物禁忌
                </h4>
                <div class="medicine-contraindications">{{ medicineResult.contraindications }}</div>
              </div>
              
              <!-- 过期提醒 -->
              <div class="medicine-section" :class="medicineResult.expiryStatus">
                <h4 class="section-subtitle">
                  <span class="section-icon">{{ medicineResult.expiryIcon }}</span>
                  过期提醒
                </h4>
                <div class="medicine-expiry">
                  <div class="expiry-date">有效期至：{{ medicineResult.expiryDate }}</div>
                  <div class="expiry-message">{{ medicineResult.expiryMessage }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 控制按钮区域 -->
      <div class="controls-area">
        <div class="control-buttons">
          <button class="control-button" @click="toggleMute">
            <span class="button-icon">{{ isMuted ? '🔇' : '🔊' }}</span>
          </button>
          <button class="control-button main" :class="{ active: isListening }" @mousedown="startListening" @mouseup="stopListening" @touchstart="startListening" @touchend="stopListening">
            <span class="button-icon">🎤</span>
          </button>
          <button class="control-button" @click="toggleVideoMode">
            <span class="button-icon">{{ isVideoMode ? '🎤' : '📷' }}</span>
          </button>
          <button class="control-button end" @click="endCall">
            <span class="button-icon">🔴</span>
          </button>
        </div>
      </div>
    </div>

    <!-- 设置对话框 -->
    <div class="settings-dialog" v-if="showSettingsDialog">
      <div class="settings-content">
        <h3 class="settings-title">设置</h3>
        <div class="settings-item">
          <label class="settings-label">方言设置</label>
          <select v-model="dialect" class="settings-select">
            <option value="mandarin">普通话</option>
            <option value="cantonese">粤语</option>
            <option value="sichuan">四川话</option>
            <option value="shanghai">上海话</option>
          </select>
        </div>
        <div class="settings-item">
          <label class="settings-label">语音速度</label>
          <input type="range" v-model="speechRate" min="0.5" max="1.5" step="0.1" class="settings-range" />
          <span class="settings-value">{{ speechRate }}</span>
        </div>
        <div class="settings-buttons">
          <button class="settings-save" @click="saveSettings">保存</button>
          <button class="settings-cancel" @click="closeSettings">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 状态管理
const isListening = ref(false)
const voiceResult = ref('')
const aiResponse = ref('')
const showSettingsDialog = ref(false)
const isMuted = ref(false)
const isTextMode = ref(false)
const statusText = ref('说话或点击麦克风')
const isVideoMode = ref(false)
const medicineResult = ref(null)
const videoElement = ref(null)

// 设置
const dialect = ref('mandarin')
const speechRate = ref(1.0)

// 提示标签
const prompts = [
  '今天天气怎么样？',
  '提醒我吃药',
  '给儿子打电话',
  '今天有什么新闻',
  '如何保持健康',
  '讲个笑话'
]

// 导航方法
const goBack = () => {
  router.push('/elderly')
}

// 显示设置
const showSettings = () => {
  showSettingsDialog.value = true
}

// 关闭设置
const closeSettings = () => {
  showSettingsDialog.value = false
}

// 保存设置
const saveSettings = () => {
  console.log('保存设置:', { dialect: dialect.value, speechRate: speechRate.value })
  showSettingsDialog.value = false
  // 实际应用中可以保存设置到本地存储
}

// 选择提示
const selectPrompt = (prompt) => {
  voiceResult.value = prompt
  processVoiceInput(prompt)
}

// 开始监听
const startListening = () => {
  isListening.value = true
  statusText.value = '正在聆听...'
  console.log('开始语音识别')
  // 实际应用中可以使用Web Speech API
  // 模拟语音识别
  setTimeout(() => {
    voiceResult.value = '今天天气怎么样？'
    processVoiceInput('今天天气怎么样？')
  }, 2000)
}

// 停止监听
const stopListening = () => {
  isListening.value = false
  statusText.value = '正在处理...'
  console.log('停止语音识别')
}

// 处理语音输入
const processVoiceInput = (input) => {
  // 模拟AI回复
  setTimeout(() => {
    let response = ''
    
    if (input.includes('天气')) {
      response = '今天天气晴朗，温度22°C，非常适合外出活动。'
    } else if (input.includes('吃药') || input.includes('提醒')) {
      response = '已为您设置下午3点的用药提醒，到时我会提醒您。'
    } else if (input.includes('打电话')) {
      response = '正在为您拨打儿子的电话...'
    } else if (input.includes('新闻')) {
      response = '今天的重要新闻有：1. 国家发布新的养老政策 2. 本地医院推出老年人健康体检优惠活动 3. 社区将举办老年人文艺活动。'
    } else if (input.includes('健康')) {
      response = '保持健康的方法包括：1. 每天适量运动 2. 均衡饮食 3. 充足睡眠 4. 保持心情愉悦。'
    } else if (input.includes('笑话')) {
      response = '有一天，一只蚂蚁对大象说："我怀了你的孩子！"大象听了吓得晕了过去。等大象醒来，蚂蚁又说："骗你的啦！"大象听了又晕了过去，因为它以为蚂蚁把孩子生下来了。'
    } else {
      response = '我理解您的意思，这是一个很好的问题。我会尽力为您提供帮助。'
    }
    
    aiResponse.value = response
    statusText.value = 'AI助手正在回复'
    
    // 模拟语音播放
    setTimeout(() => {
      statusText.value = '说话或点击麦克风'
    }, 3000)
  }, 1500)
}

// 结束通话
const endCall = () => {
  statusText.value = '感谢使用AI助手'
  setTimeout(() => {
    router.push('/elderly')
  }, 2000)
}

// 切换静音
const toggleMute = () => {
  isMuted.value = !isMuted.value
}

// 切换文本模式
const toggleTextMode = () => {
  isTextMode.value = !isTextMode.value
}

// 切换视频模式
const toggleVideoMode = () => {
  isVideoMode.value = !isVideoMode.value
  if (isVideoMode.value) {
    initCamera()
  } else {
    stopCamera()
  }
}

// 初始化摄像头
const initCamera = () => {
  if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
    navigator.mediaDevices.getUserMedia({ video: true })
      .then(stream => {
        if (videoElement.value) {
          videoElement.value.srcObject = stream
          statusText.value = '请将药物放在摄像头前'
        }
      })
      .catch(error => {
        console.error('摄像头访问失败:', error)
        statusText.value = '摄像头访问失败，请检查权限'
        isVideoMode.value = false
      })
  } else {
    statusText.value = '您的浏览器不支持摄像头'
    isVideoMode.value = false
  }
}

// 停止摄像头
const stopCamera = () => {
  if (videoElement.value && videoElement.value.srcObject) {
    const stream = videoElement.value.srcObject
    const tracks = stream.getTracks()
    tracks.forEach(track => track.stop())
    videoElement.value.srcObject = null
  }
  statusText.value = '说话或点击麦克风'
}

// 捕获图像
const captureImage = () => {
  statusText.value = '正在识别药物...'
  // 模拟药物识别
  setTimeout(() => {
    // 生成随机过期状态
    const expiryStatus = Math.random() > 0.3 ? 'safe' : 'warning';
    const expiryDate = expiryStatus === 'safe' ? '2026-12-31' : '2025-01-31';
    const expiryIcon = expiryStatus === 'safe' ? '✅' : '⚠️';
    const expiryMessage = expiryStatus === 'safe' ? '药物在有效期内，可以正常使用' : '药物已过期，请勿使用';
    
    medicineResult.value = {
      name: '阿莫西林胶囊',
      description: '抗生素类药物，用于治疗敏感菌所致的感染',
      usage: '成人一次1-2粒，一日3次，饭后服用',
      contraindications: '对青霉素类药物过敏者禁用；肝肾功能不全者慎用；孕妇、哺乳期妇女应在医生指导下使用；避免与某些药物如丙磺舒、别嘌醇等同时使用。',
      expiryStatus: expiryStatus,
      expiryDate: expiryDate,
      expiryIcon: expiryIcon,
      expiryMessage: expiryMessage
    }
    statusText.value = '说话或点击麦克风'
  }, 2000)
}

// 关闭药物识别结果弹窗
const closeMedicineDialog = () => {
  medicineResult.value = null
}

onMounted(() => {
  // 模拟连接过程
  setTimeout(() => {
    statusText.value = '说话或点击麦克风'
  }, 2000)
})

onUnmounted(() => {
  // 清理资源
  stopCamera()
  console.log('清理AI通话资源')
})
</script>

<style scoped>
.ai-chat {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
  display: flex;
  flex-direction: column;
  position: relative;
  overflow: hidden;
}

/* 顶部导航 */
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.back-button {
  display: flex;
  align-items: center;
  background: none;
  border: none;
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;
  transition: background 0.3s ease;
  color: #333;
}

.back-button:hover {
  background: rgba(0, 0, 0, 0.1);
}

.back-icon {
  font-size: 1.5rem;
  margin-right: 5px;
}

.back-text {
  font-size: 1.2rem;
  font-weight: bold;
}

.page-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.header-right {
  width: 80px;
  display: flex;
  justify-content: flex-end;
}

.settings-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;
  transition: background 0.3s ease;
  color: #333;
}

.settings-button:hover {
  background: rgba(0, 0, 0, 0.1);
}

.settings-icon {
  font-size: 1.2rem;
}

/* 语音通话界面 */
.voice-call-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  text-align: center;
  position: relative;
  min-height: calc(100vh - 100px);
}

/* 内容区域 */
.content-area {
  flex: 1;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  overflow-y: auto;
  padding-top: 20px;
  gap: 20px;
  max-height: calc(100vh - 200px);
}

/* 确保AI球体有足够的空间 */
.ai-orb {
  margin: 30px 0;
}

/* 调整语音识别结果和AI回复的最大高度 */
.voice-result,
.ai-response {
  max-height: 200px;
  overflow-y: auto;
}

/* 药物识别结果需要更大的高度 */
.medicine-result {
  max-height: 300px;
  overflow-y: auto;
}

/* 控制按钮区域 */
.controls-area {
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 20px 0;
  background: rgba(255, 255, 255, 0.1);
  border-top: 1px solid rgba(255, 255, 255, 0.2);
  margin-top: auto;
  flex-shrink: 0;
}

/* 提示标签 */
.prompt-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  padding: 20px;
  max-width: 600px;
  width: 100%;
  justify-content: center;
}

.tag {
  background: rgba(255, 255, 255, 0.8);
  padding: 10px 15px;
  border-radius: 20px;
  font-size: 1rem;
  font-weight: bold;
  color: #333;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.tag:hover {
  background: white;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* 中央AI球体 */
.ai-orb {
  position: relative;
  width: 250px;
  height: 250px;
  border-radius: 50%;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 20px 40px rgba(255, 217, 61, 0.5);
  animation: pulse 3s infinite;
  margin: 40px 0;
}

.ai-orb.active {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
  box-shadow: 0 20px 40px rgba(255, 107, 107, 0.5);
  animation: pulse 1s infinite;
}

.orb-inner {
  width: 80%;
  height: 80%;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
}

.orb-glow {
  position: absolute;
  top: -20px;
  left: -20px;
  right: -20px;
  bottom: -20px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(255,217,61,0.3) 0%, rgba(255,217,61,0) 70%);
  animation: glow 2s infinite;
}

/* 状态提示 */
.status-prompt {
  font-size: 1.2rem;
  font-weight: bold;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  margin: 20px 0;
  display: flex;
  align-items: center;
  gap: 10px;
}

.listening-indicator {
  font-size: 1.5rem;
  animation: bounce 1s infinite;
}

/* 语音识别结果 */
.voice-result {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 16px;
  padding: 20px;
  margin: 20px 0;
  width: 100%;
  max-width: 600px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: left;
  align-self: center;
}

.result-content {
  font-size: 1.1rem;
  color: #666;
  line-height: 1.6;
  margin: 0;
}

/* AI回复 */
.ai-response {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 16px;
  padding: 20px;
  margin: 20px 0;
  width: 100%;
  max-width: 600px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: left;
  align-self: center;
}

.response-content {
  font-size: 1.1rem;
  color: #666;
  line-height: 1.6;
  margin: 0;
}

/* 控制按钮 */
.control-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 30px;
  margin-top: 40px;
  width: 100%;
  padding-bottom: 20px;
}

.control-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: none;
  background: rgba(255, 255, 255, 0.8);
  color: #333;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.control-button:hover {
  background: white;
  transform: scale(1.1);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.control-button.main {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  color: white;
  box-shadow: 0 8px 20px rgba(255, 217, 61, 0.4);
}

.control-button.main:hover {
  transform: scale(1.1);
  box-shadow: 0 10px 25px rgba(255, 217, 61, 0.5);
}

.control-button.main.active {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
  box-shadow: 0 8px 20px rgba(255, 107, 107, 0.4);
  animation: pulse 1s infinite;
}

.control-button.end {
  background: rgba(255, 107, 107, 0.1);
  color: #ff6b6b;
  border: 2px solid #ff6b6b;
}

.control-button.end:hover {
  background: #ff6b6b;
  color: white;
}

.button-icon {
  font-size: 1.5rem;
}

/* 设置对话框 */
.settings-dialog {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.settings-content {
  background: white;
  border-radius: 20px;
  padding: 30px;
  width: 90%;
  max-width: 400px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.settings-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
  text-align: center;
}

.settings-item {
  margin-bottom: 20px;
}

.settings-label {
  display: block;
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.settings-select {
  width: 100%;
  padding: 12px;
  border: 2px solid #eee;
  border-radius: 10px;
  font-size: 1rem;
  background: #f8f9fa;
}

.settings-range {
  width: 100%;
  margin: 10px 0;
}

.settings-value {
  font-size: 1rem;
  color: #666;
  float: right;
}

.settings-buttons {
  display: flex;
  gap: 10px;
  margin-top: 30px;
}

.settings-save {
  flex: 1;
  padding: 12px;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  border: none;
  border-radius: 10px;
  color: white;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.settings-save:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 217, 61, 0.3);
}

.settings-cancel {
  flex: 1;
  padding: 12px;
  background: #f8f9fa;
  border: 2px solid #eee;
  border-radius: 10px;
  color: #333;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.settings-cancel:hover {
  background: #e9ecef;
  transform: translateY(-2px);
}

/* 视频区域 */
.video-section {
  width: 100%;
  max-width: 500px;
  margin: 40px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.video-container {
  position: relative;
  width: 100%;
  aspect-ratio: 4/3;
  background: rgba(0, 0, 0, 0.8);
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.video-feed {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.video-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.3);
}

.video-instructions {
  color: white;
  font-size: 1.2rem;
  font-weight: bold;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
  margin-bottom: 20px;
}

.video-focus {
  width: 70%;
  height: 70%;
  border: 2px solid white;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.video-focus::before {
  content: '';
  position: absolute;
  top: -10px;
  left: -10px;
  right: -10px;
  bottom: -10px;
  border: 2px dashed rgba(255, 255, 255, 0.5);
  border-radius: 15px;
  animation: focusPulse 2s infinite;
}

.capture-button {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 15px 30px;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  border: none;
  border-radius: 50px;
  color: white;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(255, 217, 61, 0.4);
}

.capture-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(255, 217, 61, 0.5);
}

.capture-icon {
  font-size: 1.3rem;
}

/* 药物识别结果弹窗 */
.medicine-dialog {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeIn 0.3s ease;
}

.dialog-content {
  background: white;
  border-radius: 20px;
  padding: 30px;
  width: 90%;
  max-width: 600px;
  max-height: 80vh;
  overflow-y: auto;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  animation: slideUp 0.3s ease;
  position: relative;
}

.close-button {
  position: absolute;
  top: 15px;
  right: 15px;
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #666;
  padding: 5px;
  border-radius: 50%;
  transition: all 0.3s ease;
}

.close-button:hover {
  background: rgba(0, 0, 0, 0.1);
  color: #333;
}

.close-icon {
  font-size: 1.5rem;
}

.medicine-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
  text-align: center;
  padding-top: 10px;
}

.medicine-info {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.medicine-name {
  font-size: 1.2rem;
  font-weight: bold;
  color: #ff9e43;
  padding-bottom: 5px;
  border-bottom: 1px solid #f0f0f0;
}

.medicine-desc {
  font-size: 1rem;
  color: #666;
  line-height: 1.5;
}

.medicine-section {
  margin-top: 15px;
  padding: 15px;
  border-radius: 10px;
  background: #f8f9fa;
  transition: all 0.3s ease;
}

.medicine-section.warning {
  background: #fef3c7;
  border-left: 4px solid #d97706;
}

.section-subtitle {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.section-icon {
  font-size: 1.2rem;
}

.medicine-usage {
  font-size: 1rem;
  color: #666;
  line-height: 1.5;
  padding: 10px;
  background: rgba(255, 217, 61, 0.1);
  border-radius: 8px;
}

.medicine-contraindications {
  font-size: 1rem;
  color: #666;
  line-height: 1.5;
  padding: 10px;
  background: rgba(255, 107, 107, 0.1);
  border-radius: 8px;
}

.medicine-expiry {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.expiry-date {
  font-size: 1rem;
  font-weight: bold;
  color: #333;
}

.expiry-message {
  font-size: 1rem;
  color: #666;
  line-height: 1.5;
  padding: 10px;
  background: rgba(76, 175, 80, 0.1);
  border-radius: 8px;
}

.medicine-section.warning .expiry-message {
  background: rgba(255, 107, 107, 0.1);
  color: #dc2626;
  font-weight: bold;
}

/* 动画效果 */
@keyframes pulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

@keyframes glow {
  0%, 100% {
    opacity: 0.5;
    transform: scale(1);
  }
  50% {
    opacity: 0.8;
    transform: scale(1.1);
  }
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

@keyframes focusPulse {
  0%, 100% {
    opacity: 0.5;
    transform: scale(1);
  }
  50% {
    opacity: 0.8;
    transform: scale(1.05);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .voice-call-container {
    padding: 15px;
  }
  
  .ai-orb {
    width: 180px;
    height: 180px;
    margin: 20px 0;
  }
  
  .control-buttons {
    gap: 15px;
    margin-top: 20px;
  }
  
  .control-button {
    width: 50px;
    height: 50px;
  }
  
  .control-button.main {
    width: 70px;
    height: 70px;
  }
  
  .button-icon {
    font-size: 1.2rem;
  }
  
  .prompt-tags {
    padding: 10px;
  }
  
  .tag {
    font-size: 0.9rem;
    padding: 8px 12px;
  }
  
  .status-prompt {
    font-size: 1rem;
    margin: 15px 0;
  }
  
  .voice-result,
  .ai-response {
    padding: 15px;
  }
  
  /* 药物识别结果响应式调整 */
  .medicine-result {
    padding: 15px;
    margin: 15px 0;
  }
  
  .medicine-title {
    font-size: 1.2rem;
    margin-bottom: 10px;
  }
  
  .medicine-name {
    font-size: 1.1rem;
  }
  
  .medicine-desc,
  .medicine-usage {
    font-size: 0.95rem;
  }
  
  /* 内容区域响应式调整 */
  .content-area {
    max-height: calc(100vh - 180px);
    padding-top: 15px;
    gap: 15px;
  }
}
</style>