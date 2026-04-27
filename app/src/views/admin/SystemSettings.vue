<template>
  <div class="system-settings">
    <div class="header">
      <h1>系统设置</h1>
    </div>
    
    <el-card class="setting-card">
      <template #header>
        <div class="card-header">
          <span>基本设置</span>
        </div>
      </template>
      <el-form :model="basicSettings" :rules="rules" ref="basicFormRef" label-width="120px">
        <el-form-item label="系统名称" prop="systemName">
          <el-input v-model="basicSettings.systemName" placeholder="请输入系统名称" />
        </el-form-item>
        <el-form-item label="系统版本" prop="systemVersion">
          <el-input v-model="basicSettings.systemVersion" placeholder="请输入系统版本" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="basicSettings.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="联系邮箱" prop="contactEmail">
          <el-input v-model="basicSettings.contactEmail" placeholder="请输入联系邮箱" />
        </el-form-item>
        <el-form-item label="系统状态">
          <el-switch v-model="basicSettings.systemStatus" active-text="启用" inactive-text="禁用" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSaveBasicSettings">保存设置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="setting-card" style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>安全设置</span>
        </div>
      </template>
      <el-form :model="securitySettings" :rules="securityRules" ref="securityFormRef" label-width="120px">
        <el-form-item label="密码有效期" prop="passwordExpiry">
          <el-input v-model.number="securitySettings.passwordExpiry" type="number" placeholder="请输入密码有效期（天）" />
        </el-form-item>
        <el-form-item label="登录失败次数" prop="loginFailLimit">
          <el-input v-model.number="securitySettings.loginFailLimit" type="number" placeholder="请输入登录失败次数限制" />
        </el-form-item>
        <el-form-item label="会话超时时间" prop="sessionTimeout">
          <el-input v-model.number="securitySettings.sessionTimeout" type="number" placeholder="请输入会话超时时间（分钟）" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSaveSecuritySettings">保存设置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="setting-card" style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>通知设置</span>
        </div>
      </template>
      <el-form :model="notificationSettings" :rules="notificationRules" ref="notificationFormRef" label-width="120px">
        <el-form-item label="邮件服务器" prop="emailServer">
          <el-input v-model="notificationSettings.emailServer" placeholder="请输入邮件服务器地址" />
        </el-form-item>
        <el-form-item label="邮件端口" prop="emailPort">
          <el-input v-model.number="notificationSettings.emailPort" type="number" placeholder="请输入邮件服务器端口" />
        </el-form-item>
        <el-form-item label="发件人邮箱" prop="emailSender">
          <el-input v-model="notificationSettings.emailSender" placeholder="请输入发件人邮箱" />
        </el-form-item>
        <el-form-item label="发件人密码" prop="emailPassword">
          <el-input v-model="notificationSettings.emailPassword" type="password" placeholder="请输入发件人密码" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSaveNotificationSettings">保存设置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'

const basicFormRef = ref(null)
const securityFormRef = ref(null)
const notificationFormRef = ref(null)

const basicSettings = reactive({
  systemName: '银发智护管理系统',
  systemVersion: '1.0.0',
  contactPhone: '400-123-4567',
  contactEmail: 'contact@silverhair.com',
  systemStatus: true
})

const securitySettings = reactive({
  passwordExpiry: 90,
  loginFailLimit: 5,
  sessionTimeout: 30
})

const notificationSettings = reactive({
  emailServer: 'smtp.qq.com',
  emailPort: 465,
  emailSender: 'noreply@silverhair.com',
  emailPassword: 'password'
})

const rules = {
  systemName: [
    { required: true, message: '请输入系统名称', trigger: 'blur' }
  ],
  systemVersion: [
    { required: true, message: '请输入系统版本', trigger: 'blur' }
  ]
}

const securityRules = {
  passwordExpiry: [
    { required: true, message: '请输入密码有效期', trigger: 'blur' }
  ],
  loginFailLimit: [
    { required: true, message: '请输入登录失败次数限制', trigger: 'blur' }
  ],
  sessionTimeout: [
    { required: true, message: '请输入会话超时时间', trigger: 'blur' }
  ]
}

const notificationRules = {
  emailServer: [
    { required: true, message: '请输入邮件服务器地址', trigger: 'blur' }
  ],
  emailPort: [
    { required: true, message: '请输入邮件服务器端口', trigger: 'blur' }
  ],
  emailSender: [
    { required: true, message: '请输入发件人邮箱', trigger: 'blur' }
  ],
  emailPassword: [
    { required: true, message: '请输入发件人密码', trigger: 'blur' }
  ]
}

const handleSaveBasicSettings = async () => {
  if (!basicFormRef.value) return
  
  try {
    await basicFormRef.value.validate()
    // 调用API保存基本设置
    console.log('保存基本设置:', basicSettings)
  } catch (error) {
    console.error('验证失败:', error)
  }
}

const handleSaveSecuritySettings = async () => {
  if (!securityFormRef.value) return
  
  try {
    await securityFormRef.value.validate()
    // 调用API保存安全设置
    console.log('保存安全设置:', securitySettings)
  } catch (error) {
    console.error('验证失败:', error)
  }
}

const handleSaveNotificationSettings = async () => {
  if (!notificationFormRef.value) return
  
  try {
    await notificationFormRef.value.validate()
    // 调用API保存通知设置
    console.log('保存通知设置:', notificationSettings)
  } catch (error) {
    console.error('验证失败:', error)
  }
}
</script>

<style scoped>
.system-settings {
  padding: 20px;
  min-height: 100vh;
  background: #f5f7fa;
}

.header {
  margin-bottom: 20px;
}

.header h1 {
  font-size: 24px;
  color: #333;
}

.setting-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

@media (max-width: 768px) {
  .system-settings {
    padding: 10px;
  }
  
  .header h1 {
    font-size: 20px;
  }
}
</style>