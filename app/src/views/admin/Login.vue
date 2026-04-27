<template>
  <div class="login-container">
    <div class="login-form">
      <div class="logo">
        <div class="logo-icon">
          <i class="el-icon-s-custom"></i>
        </div>
        <h1>银发智护管理系统</h1>
        <p>管理员登录</p>
      </div>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="0">
        <el-form-item prop="phone" class="form-item">
          <el-input v-model="loginForm.phone" placeholder="请输入手机号" prefix-icon="el-icon-mobile-phone">
          </el-input>
        </el-form-item>
        <el-form-item prop="password" class="form-item">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password prefix-icon="el-icon-lock">
          </el-input>
        </el-form-item>
        <el-form-item class="form-item">
          <el-button type="primary" class="login-btn" @click="handleLogin" :loading="loading">
            登录
          </el-button>
        </el-form-item>
        <el-form-item v-if="errorMsg" class="error-message">
          <el-alert :title="errorMsg" type="error" show-icon :closable="false" />
        </el-form-item>
        <div class="login-footer">
          <p>© 2024 银发智护管理系统 - 专业的老年人照护管理平台</p>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { authAPI } from '../../services/api'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)
const errorMsg = ref('')

const loginForm = reactive({
  phone: '',
  password: ''
})

const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  try {
    await loginFormRef.value.validate()
    loading.value = true
    errorMsg.value = ''
    
    // 调用登录API
    const response = await authAPI.login(loginForm.phone, loginForm.password)
    // 保存token到localStorage
    localStorage.setItem('token', response.token)
    // 跳转到仪表盘
    router.push('/admin/dashboard')
  } catch (error) {
    loading.value = false
    errorMsg.value = error.message || '登录失败，请检查账号和密码'
    console.error('登录失败:', error)
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
  padding: 20px;
}

.login-form {
  width: 100%;
  max-width: 420px;
  background: white;
  border-radius: 16px;
  padding: 40px 30px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
}

.login-form:hover {
  box-shadow: 0 15px 40px rgba(0, 0, 0, 0.25);
  transform: translateY(-5px);
}

.logo {
  text-align: center;
  margin-bottom: 40px;
}

.logo-icon {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #409eff 0%, #667eea 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  box-shadow: 0 4px 15px rgba(64, 158, 255, 0.3);
  font-size: 40px;
  color: white;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    box-shadow: 0 4px 15px rgba(64, 158, 255, 0.3);
  }
  50% {
    box-shadow: 0 6px 20px rgba(64, 158, 255, 0.5);
  }
  100% {
    box-shadow: 0 4px 15px rgba(64, 158, 255, 0.3);
  }
}

.logo h1 {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
  background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.logo p {
  font-size: 16px;
  color: #666;
  margin: 0;
}

.form-item {
  margin-bottom: 24px;
}

.form-item .el-input {
  height: 48px;
  border-radius: 8px;
  font-size: 16px;
  transition: all 0.3s ease;
}

.form-item .el-input:hover {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.1);
}

.form-item .el-input.is-focus {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.login-btn {
  width: 100%;
  height: 48px;
  font-size: 18px;
  font-weight: bold;
  border-radius: 8px;
  background: linear-gradient(135deg, #409eff 0%, #667eea 100%);
  border: none;
  transition: all 0.3s ease;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(64, 158, 255, 0.4);
}

.login-btn:active {
  transform: translateY(0);
}

.error-message {
  margin-top: 20px;
  margin-bottom: 15px;
}

.login-footer {
  margin-top: 30px;
  text-align: center;
}

.login-footer p {
  font-size: 14px;
  color: #999;
  margin: 0;
}

@media (max-width: 768px) {
  .login-form {
    margin: 0 20px;
    padding: 30px 20px;
  }
  
  .logo h1 {
    font-size: 24px;
  }
  
  .logo-icon {
    width: 60px;
    height: 60px;
    font-size: 30px;
  }
  
  .form-item .el-input {
    height: 44px;
    font-size: 15px;
  }
  
  .login-btn {
    height: 44px;
    font-size: 16px;
  }
}
</style>