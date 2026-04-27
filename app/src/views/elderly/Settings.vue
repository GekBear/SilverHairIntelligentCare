<template>
  <div class="my-page">
    <!-- 顶部导航栏 -->
    <div class="header">
      <button class="back-button" @click="goBack">
        <span class="back-icon">←</span>
        <span class="back-text">返回</span>
      </button>
      <h1 class="page-title">我的</h1>
      <div class="header-right">
        <button class="settings-button" @click="goToMoreSettings">
          <span class="settings-icon">⚙️</span>
        </button>
      </div>
    </div>

    <!-- 用户信息卡片 -->
    <div class="user-card">
      <div class="user-avatar">
        <span class="avatar-icon">👴</span>
      </div>
      <div class="user-info">
        <h2 class="user-name">张大爷</h2>
        <p class="user-id">ID: 100001</p>
        <div class="user-tags">
          <span class="tag certification-tag">高级实名认证</span>
          <span class="tag vip-tag">VIP会员</span>
        </div>
      </div>
      <button class="edit-profile-button" @click="goToProfile">
        <span class="edit-icon">✏️</span>
      </button>
    </div>

    <!-- 快捷功能 -->
    <div class="quick-functions">
      <div class="function-item" @click="goToHealthRecords">
        <div class="function-icon health-icon">❤️</div>
        <span class="function-text">健康记录</span>
      </div>
      <div class="function-item" @click="goToEmergencyContacts">
        <div class="function-icon emergency-icon">📞</div>
        <span class="function-text">紧急联系人</span>
      </div>
      <div class="function-item" @click="goToMedicineReminder">
        <div class="function-icon medicine-icon">💊</div>
        <span class="function-text">用药提醒</span>
      </div>
      <div class="function-item" @click="goToDigitalAlbum">
        <div class="function-icon album-icon">📷</div>
        <span class="function-text">数字相册</span>
      </div>
      <div class="function-item" @click="goToCommunity">
        <div class="function-icon community-icon">👥</div>
        <span class="function-text">老年社区</span>
      </div>
      <div class="function-item" @click="goToServices">
        <div class="function-icon service-icon">🛎️</div>
        <span class="function-text">生活服务</span>
      </div>
    </div>

    <!-- 服务记录 -->
    <div class="section">
      <div class="section-header">
        <h3 class="section-title">服务记录</h3>
        <span class="more-link" @click="goToAllRecords">查看全部 ></span>
      </div>
      <div class="record-cards">
        <div class="record-card">
          <div class="record-icon completed-icon">✅</div>
          <div class="record-info">
            <h4 class="record-title">家政服务</h4>
            <p class="record-time">2026-03-20</p>
            <p class="record-status">已完成</p>
          </div>
        </div>
        <div class="record-card">
          <div class="record-icon processing-icon">⏳</div>
          <div class="record-info">
            <h4 class="record-title">健康体检</h4>
            <p class="record-time">2026-03-22</p>
            <p class="record-status">处理中</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 个人设置 -->
    <div class="section">
      <div class="section-header">
        <h3 class="section-title">个人设置</h3>
      </div>
      <div class="settings-menu">
        <div class="settings-item" @click="goToChangePhone">
          <div class="settings-icon phone-icon">📱</div>
          <span class="settings-text">修改手机号</span>
          <span class="settings-arrow">></span>
        </div>
        <div class="settings-item" @click="goToChangePassword">
          <div class="settings-icon password-icon">🔒</div>
          <span class="settings-text">修改密码</span>
          <span class="settings-arrow">></span>
        </div>
        <div class="settings-item" @click="goToNotificationSettings">
          <div class="settings-icon notification-icon">🔔</div>
          <span class="settings-text">通知设置</span>
          <span class="settings-arrow">></span>
        </div>
        <div class="settings-item" @click="goToFontSettings">
          <div class="settings-icon font-icon">📝</div>
          <span class="settings-text">字体大小</span>
          <span class="settings-arrow">></span>
        </div>
        <div class="settings-item" @click="goToPrivacySettings">
          <div class="settings-icon privacy-icon">🔐</div>
          <span class="settings-text">隐私设置</span>
          <span class="settings-arrow">></span>
        </div>
        <div class="settings-item" @click="goToAbout">
          <div class="settings-icon about-icon">ℹ️</div>
          <span class="settings-text">关于我们</span>
          <span class="settings-arrow">></span>
        </div>
      </div>
    </div>

    <!-- 退出登录 -->
    <div class="logout-section">
      <button class="logout-button" @click="logout">退出登录</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

// 返回上一页
const goBack = () => {
  router.back()
}

// 导航方法
const goToProfile = () => {
  console.log('个人档案')
  // 实际应用中跳转到个人档案页面
}

const goToHealthRecords = () => {
  console.log('健康记录')
  // 实际应用中跳转到健康记录页面
  router.push('/elderly/health-records')
}

const goToEmergencyContacts = () => {
  console.log('紧急联系人')
  // 实际应用中跳转到紧急联系人页面
  router.push('/elderly/emergency-contacts')
}

const goToMedicineReminder = () => {
  console.log('用药提醒')
  // 实际应用中跳转到用药提醒页面
  router.push('/elderly/medicine-reminder')
}

const goToDigitalAlbum = () => {
  console.log('数字相册')
  // 实际应用中跳转到数字相册页面
  router.push('/elderly/digital-album')
}

const goToCommunity = () => {
  console.log('老年社区')
  // 实际应用中跳转到老年社区页面
  router.push('/elderly/community')
}

const goToServices = () => {
  console.log('生活服务')
  // 实际应用中跳转到生活服务页面
  router.push('/elderly/services')
}

const goToAllRecords = () => {
  console.log('全部记录')
  // 实际应用中跳转到全部记录页面
}

const goToMoreSettings = () => {
  console.log('更多设置')
  // 实际应用中跳转到更多设置页面
}

const goToChangePhone = () => {
  console.log('修改手机号')
  // 实际应用中跳转到修改手机号页面
}

const goToChangePassword = () => {
  console.log('修改密码')
  // 实际应用中跳转到修改密码页面
}

const goToNotificationSettings = () => {
  console.log('通知设置')
  // 实际应用中跳转到通知设置页面
}

const goToFontSettings = () => {
  console.log('字体大小设置')
  router.push('/elderly/font-settings')
}

const goToPrivacySettings = () => {
  console.log('隐私设置')
  // 实际应用中跳转到隐私设置页面
}

const goToAbout = () => {
  console.log('关于我们')
  // 实际应用中跳转到关于我们页面
}

const logout = () => {
  console.log('退出登录')
  // 实际应用中执行退出登录逻辑
  alert('退出登录成功')
}
</script>

<style scoped>
.my-page {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
  padding-bottom: 80px;
}

/* 顶部导航栏 */
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
  display: flex;
  align-items: center;
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

/* 用户信息卡片 */
.user-card {
  display: flex;
  align-items: center;
  padding: 30px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  margin: 20px;
  border-radius: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.user-avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 25px;
  box-shadow: 0 4px 15px rgba(255, 217, 61, 0.3);
}

.avatar-icon {
  font-size: 48px;
}

.user-info {
  flex: 1;
}

.user-name {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin: 0 0 10px 0;
}

.user-id {
  font-size: 14px;
  color: #666;
  margin: 0 0 15px 0;
}

.user-tags {
  display: flex;
  gap: 10px;
}

.tag {
  padding: 6px 14px;
  border-radius: 16px;
  font-size: 12px;
  font-weight: 500;
}

.certification-tag {
  background: #4CAF50;
  color: white;
}

.vip-tag {
  background: #ff9e43;
  color: white;
}

.edit-profile-button {
  background: rgba(0, 0, 0, 0.05);
  border: none;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.edit-profile-button:hover {
  background: rgba(0, 0, 0, 0.1);
  transform: scale(1.1);
}

.edit-icon {
  font-size: 16px;
}

/* 快捷功能 */
.quick-functions {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  padding: 0 20px;
  margin-bottom: 30px;
}

.function-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 25px 15px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.function-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.function-icon {
  font-size: 36px;
  margin-bottom: 12px;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  background: rgba(255, 217, 61, 0.1);
}

.health-icon {
  background: rgba(255, 107, 107, 0.2);
}

.emergency-icon {
  background: rgba(255, 190, 70, 0.2);
}

.medicine-icon {
  background: rgba(78, 205, 196, 0.2);
}

.album-icon {
  background: rgba(102, 126, 234, 0.2);
}

.community-icon {
  background: rgba(248, 180, 0, 0.2);
}

.service-icon {
  background: rgba(107, 142, 35, 0.2);
}

.function-text {
  font-size: 14px;
  color: #333;
  font-weight: 500;
  text-align: center;
}

/* 通用 section 样式 */
.section {
  margin: 0 20px 30px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.more-link {
  color: #ff9e43;
  font-size: 14px;
  cursor: pointer;
  transition: color 0.3s ease;
  font-weight: 500;
}

.more-link:hover {
  color: #ffd93d;
}

/* 服务记录 */
.record-cards {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.record-card {
  display: flex;
  align-items: center;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.record-card:hover {
  background: #f0f0f0;
  transform: translateY(-3px);
}

.record-icon {
  font-size: 24px;
  margin-right: 20px;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  background: rgba(255, 217, 61, 0.1);
}

.completed-icon {
  background: rgba(78, 205, 196, 0.2);
}

.processing-icon {
  background: rgba(255, 190, 70, 0.2);
}

.record-info {
  flex: 1;
}

.record-title {
  font-size: 16px;
  font-weight: bold;
  color: #333;
  margin: 0 0 5px 0;
}

.record-time {
  font-size: 14px;
  color: #666;
  margin: 0 0 5px 0;
}

.record-status {
  font-size: 14px;
  font-weight: 500;
  color: #4CAF50;
}

.record-card:nth-child(2) .record-status {
  color: #ff9e43;
}

/* 个人设置 */
.settings-menu {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.settings-item {
  display: flex;
  align-items: center;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.settings-item:hover {
  background: #f0f0f0;
  transform: translateY(-2px);
}

.settings-icon {
  font-size: 24px;
  margin-right: 15px;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  background: rgba(255, 217, 61, 0.1);
}

.phone-icon {
  background: rgba(102, 126, 234, 0.2);
}

.password-icon {
  background: rgba(255, 107, 107, 0.2);
}

.notification-icon {
  background: rgba(255, 190, 70, 0.2);
}

.font-icon {
  background: rgba(107, 142, 35, 0.2);
}

.privacy-icon {
  background: rgba(78, 205, 196, 0.2);
}

.about-icon {
  background: rgba(102, 126, 234, 0.2);
}

.settings-text {
  flex: 1;
  font-size: 16px;
  font-weight: 500;
  color: #333;
}

.settings-arrow {
  font-size: 18px;
  color: #999;
}

/* 退出登录 */
.logout-section {
  padding: 0 20px 40px;
}

.logout-button {
  width: 100%;
  padding: 18px;
  background: rgba(255, 107, 107, 0.1);
  border: 1px solid #ff6b6b;
  border-radius: 12px;
  color: #ff6b6b;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.logout-button:hover {
  background: #ff6b6b;
  color: white;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 107, 107, 0.3);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .user-card {
    padding: 20px;
    margin: 15px;
  }
  
  .user-avatar {
    width: 80px;
    height: 80px;
    margin-right: 20px;
  }
  
  .avatar-icon {
    font-size: 36px;
  }
  
  .user-name {
    font-size: 24px;
  }
  
  .quick-functions {
    gap: 15px;
  }
  
  .function-item {
    padding: 20px 10px;
  }
  
  .function-icon {
    font-size: 28px;
    width: 50px;
    height: 50px;
  }
  
  .section {
    margin: 0 15px 20px;
    padding: 15px;
  }
  
  .record-card {
    padding: 15px;
  }
  
  .record-icon {
    font-size: 20px;
    width: 40px;
    height: 40px;
  }
  
  .settings-item {
    padding: 15px;
  }
  
  .logout-section {
    padding: 0 15px 30px;
  }
  
  .logout-button {
    padding: 15px;
  }
}
</style>