<template>
  <div class="login-container" :class="selectedRole">
    <!-- 老人端登录界面 -->
    <div v-if="selectedRole === 'elderly'" class="login-panel elderly-panel">
      <div class="panel-header">
        <div class="logo">
          <span class="logo-icon">👴</span>
          <h1 class="logo-text">银发智护</h1>
        </div>
        <p class="panel-subtitle">简单易用，关爱老人</p>
      </div>
      
      <div class="login-form">
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">📱</span>
            <input 
              type="tel" 
              v-model="form.phone"
              placeholder="请输入手机号"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">🔒</span>
            <input 
              type="password" 
              v-model="form.password"
              placeholder="请输入密码"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-actions">
          <button class="login-btn" @click="login">
            <span class="btn-icon">🔑</span>
            登录
          </button>
          <button class="register-btn" @click="switchToRegister">
            <span class="btn-icon">📝</span>
            注册
          </button>
        </div>
      </div>
      
      <div class="register-form" v-if="showRegister">
        <h3 class="register-title">注册账号</h3>
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">📱</span>
            <input 
              type="tel" 
              v-model="registerForm.phone"
              placeholder="请输入手机号"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">🔒</span>
            <input 
              type="password" 
              v-model="registerForm.password"
              placeholder="请输入密码"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">👤</span>
            <input 
              type="text" 
              v-model="registerForm.name"
              placeholder="请输入姓名"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-actions">
          <button class="register-btn" @click="register">
            <span class="btn-icon">✅</span>
            注册
          </button>
          <button class="cancel-btn" @click="switchToLogin">
            <span class="btn-icon">🔑</span>
            登录
          </button>
        </div>
      </div>
    </div>
    
    <!-- 子女端登录界面 -->
    <div v-else-if="selectedRole === 'family'" class="login-panel family-panel">
      <div class="panel-header">
        <div class="logo">
          <span class="logo-icon">👨‍👩‍👧‍👦</span>
          <h1 class="logo-text">银发智护</h1>
        </div>
        <p class="panel-subtitle">随时随地，关爱家人</p>
      </div>
      
      <div class="login-form">
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">📱</span>
            <input 
              type="tel" 
              v-model="form.phone"
              placeholder="请输入手机号"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">🔒</span>
            <input 
              type="password" 
              v-model="form.password"
              placeholder="请输入密码"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-actions">
          <button class="login-btn" @click="login">
            <span class="btn-icon">🔑</span>
            登录
          </button>
          <button class="register-btn" @click="switchToRegister">
            <span class="btn-icon">📝</span>
            注册
          </button>
        </div>
      </div>
      
      <div class="register-form" v-if="showRegister">
        <h3 class="register-title">注册账号</h3>
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">📱</span>
            <input 
              type="tel" 
              v-model="registerForm.phone"
              placeholder="请输入手机号"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">🔒</span>
            <input 
              type="password" 
              v-model="registerForm.password"
              placeholder="请输入密码"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">👤</span>
            <input 
              type="text" 
              v-model="registerForm.name"
              placeholder="请输入姓名"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-actions">
          <button class="register-btn" @click="register">
            <span class="btn-icon">✅</span>
            注册
          </button>
          <button class="cancel-btn" @click="switchToLogin">
            <span class="btn-icon">🔑</span>
            登录
          </button>
        </div>
      </div>
    </div>
    
    <!-- 管理员端登录界面 -->
    <div v-else-if="selectedRole === 'admin'" class="login-panel admin-panel">
      <div class="panel-header">
        <div class="logo">
          <span class="logo-icon">🏢</span>
          <h1 class="logo-text">银发智护系统</h1>
        </div>
        <h2 class="panel-title">管理员端登录</h2>
        <p class="panel-subtitle">专业管理，服务老人</p>
      </div>
      
      <div class="login-form">
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">📱</span>
            <input 
              type="tel" 
              v-model="form.phone"
              placeholder="请输入手机号"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">🔒</span>
            <input 
              type="password" 
              v-model="form.password"
              placeholder="请输入密码"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-actions">
          <button class="login-btn" @click="login">
            <span class="btn-icon">🔑</span>
            登录
          </button>
          <button class="register-btn" @click="switchToRegister">
            <span class="btn-icon">📝</span>
            注册
          </button>
        </div>
      </div>
      
      <div class="register-form" v-if="showRegister">
        <h3 class="register-title">注册账号</h3>
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">📱</span>
            <input 
              type="tel" 
              v-model="registerForm.phone"
              placeholder="请输入手机号"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">🔒</span>
            <input 
              type="password" 
              v-model="registerForm.password"
              placeholder="请输入密码"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-group">
          <div class="input-wrapper">
            <span class="input-icon">👤</span>
            <input 
              type="text" 
              v-model="registerForm.name"
              placeholder="请输入姓名"
              class="form-input"
            >
          </div>
        </div>
        
        <div class="form-actions">
          <button class="register-btn" @click="register">
            <span class="btn-icon">✅</span>
            注册
          </button>
          <button class="cancel-btn" @click="switchToLogin">
            <span class="btn-icon">🔑</span>
            登录
          </button>
        </div>
      </div>
    </div>
    
    <!-- 角色选择器 -->
    <div class="role-selector">
      <button 
        class="role-btn elderly-role" 
        :class="{ active: selectedRole === 'elderly' }"
        @click="selectedRole = 'elderly'"
      >
        <span class="role-icon">👴</span>
        <span class="role-text">老人端</span>
      </button>
      <button 
        class="role-btn family-role" 
        :class="{ active: selectedRole === 'family' }"
        @click="selectedRole = 'family'"
      >
        <span class="role-icon">👨‍👩‍👧‍👦</span>
        <span class="role-text">子女端</span>
      </button>
      <button 
        class="role-btn admin-role" 
        :class="{ active: selectedRole === 'admin' }"
        @click="selectedRole = 'admin'"
      >
        <span class="role-icon">🏢</span>
        <span class="role-text">管理员端</span>
      </button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { authAPI } from '../services/api';

export default {
  name: 'Login',
  setup() {
    const router = useRouter();
    const selectedRole = ref('elderly');
    const showRegister = ref(false);
    const form = ref({
      phone: '',
      password: ''
    });
    const registerForm = ref({
      phone: '',
      password: '',
      name: ''
    });
    
    const login = async () => {
      try {
        const response = await authAPI.login(form.value.phone, form.value.password);
        localStorage.setItem('token', response.token);
        localStorage.setItem('userId', response.userId);
        localStorage.setItem('role', response.role);
        
        // 根据角色跳转到对应首页
        switch (selectedRole.value) {
          case 'elderly':
            router.push('/elderly');
            break;
          case 'family':
            router.push('/family');
            break;
          case 'admin':
            router.push('/admin/dashboard');
            break;
          default:
            router.push('/elderly');
        }
      } catch (error) {
        console.error('登录失败:', error);
        alert('登录失败，请检查手机号和密码');
      }
    };
    
    const register = async () => {
      try {
        const user = {
          phone: registerForm.value.phone,
          password: registerForm.value.password,
          realName: registerForm.value.name,
          role: selectedRole.value === 'elderly' ? 1 : selectedRole.value === 'family' ? 2 : 9
        };
        
        await authAPI.register(user);
        alert('注册成功，请登录');
        switchToLogin();
      } catch (error) {
        console.error('注册失败:', error);
        alert('注册失败，请稍后重试');
      }
    };
    
    const switchToRegister = () => {
      showRegister.value = true;
    };
    
    const switchToLogin = () => {
      showRegister.value = false;
    };
    
    return {
      selectedRole,
      showRegister,
      form,
      registerForm,
      login,
      register,
      switchToRegister,
      switchToLogin
    };
  }
};
</script>

<style scoped>
/* 通用样式 */
.login-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
  transition: all 0.5s ease;
}

.login-panel {
  background-color: white;
  border-radius: 20px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
  padding: 40px;
  width: 100%;
  max-width: 450px;
  margin-bottom: 30px;
  transition: all 0.5s ease;
}

.panel-header {
  text-align: center;
  margin-bottom: 40px;
}

.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}

.logo-icon {
  font-size: 3rem;
  margin-right: 15px;
}

.logo-text {
  font-size: 2rem;
  font-weight: bold;
  margin: 0;
}

.panel-title {
  font-size: 1.8rem;
  font-weight: bold;
  margin: 10px 0;
}

.panel-subtitle {
  font-size: 1.1rem;
  opacity: 0.7;
  margin: 0;
}

.form-group {
  margin-bottom: 25px;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 15px;
  font-size: 1.2rem;
  z-index: 1;
}

.form-input {
  width: 100%;
  padding: 15px 50px 15px 50px;
  border: 2px solid #e0e0e0;
  border-radius: 10px;
  font-size: 1.1rem;
  transition: all 0.3s ease;
}

.form-input:focus {
  outline: none;
  border-color: currentColor;
  box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.1);
}

.form-actions {
  display: flex;
  gap: 15px;
  margin-top: 40px;
}

.login-btn,
.register-btn,
.cancel-btn {
  flex: 1;
  padding: 15px;
  border: none;
  border-radius: 10px;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.btn-icon {
  font-size: 1.2rem;
}

.register-form {
  margin-top: 30px;
  padding-top: 30px;
  border-top: 2px solid #f0f0f0;
}

.register-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 25px;
  text-align: center;
}

/* 角色选择器 */
.role-selector {
  display: flex;
  gap: 20px;
  background-color: white;
  border-radius: 15px;
  padding: 15px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.role-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 20px;
  border: 2px solid #e0e0e0;
  background-color: white;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 120px;
}

.role-btn:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
}

.role-btn.active {
  border-color: currentColor;
  background-color: rgba(0, 0, 0, 0.05);
}

.role-icon {
  font-size: 2rem;
}

.role-text {
  font-size: 1rem;
  font-weight: bold;
}

/* 老人端样式 */
.login-container.elderly {
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
}

.elderly-panel .logo-text,
.elderly-panel .panel-title {
  color: #ff9e43;
}

.elderly-panel .form-input:focus {
  color: #ff9e43;
}

.elderly-panel .login-btn {
  background-color: #ff9e43;
  color: white;
}

.elderly-panel .login-btn:hover {
  background-color: #ff8c29;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(255, 158, 67, 0.4);
}

.elderly-panel .register-btn {
  background-color: #67c23a;
  color: white;
}

.elderly-panel .register-btn:hover {
  background-color: #529b2e;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(103, 194, 58, 0.4);
}

.elderly-panel .cancel-btn {
  background-color: #909399;
  color: white;
}

.elderly-panel .cancel-btn:hover {
  background-color: #73767a;
  transform: translateY(-2px);
}

.elderly-role.active {
  color: #ff9e43;
}

/* 子女端样式 */
.login-container.family {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.family-panel .logo-text,
.family-panel .panel-title {
  color: #667eea;
}

.family-panel .form-input:focus {
  color: #667eea;
}

.family-panel .login-btn {
  background-color: #667eea;
  color: white;
}

.family-panel .login-btn:hover {
  background-color: #5a6fd8;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
}

.family-panel .register-btn {
  background-color: #4ecdc4;
  color: white;
}

.family-panel .register-btn:hover {
  background-color: #3db8af;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(78, 205, 196, 0.4);
}

.family-panel .cancel-btn {
  background-color: #909399;
  color: white;
}

.family-panel .cancel-btn:hover {
  background-color: #73767a;
  transform: translateY(-2px);
}

.family-role.active {
  color: #667eea;
}

/* 管理员端样式 */
.login-container.admin {
  background: linear-gradient(135deg, #45b7d1 0%, #2a5298 100%);
}

.admin-panel .logo-text,
.admin-panel .panel-title {
  color: #45b7d1;
}

.admin-panel .form-input:focus {
  color: #45b7d1;
}

.admin-panel .login-btn {
  background-color: #45b7d1;
  color: white;
}

.admin-panel .login-btn:hover {
  background-color: #38a0b8;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(69, 183, 209, 0.4);
}

.admin-panel .register-btn {
  background-color: #2a5298;
  color: white;
}

.admin-panel .register-btn:hover {
  background-color: #21407a;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(42, 82, 152, 0.4);
}

.admin-panel .cancel-btn {
  background-color: #909399;
  color: white;
}

.admin-panel .cancel-btn:hover {
  background-color: #73767a;
  transform: translateY(-2px);
}

.admin-role.active {
  color: #45b7d1;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-panel {
    padding: 30px 20px;
  }
  
  .logo-text {
    font-size: 1.5rem;
  }
  
  .panel-title {
    font-size: 1.5rem;
  }
  
  .form-input {
    padding: 12px 40px 12px 40px;
    font-size: 1rem;
  }
  
  .form-actions {
    flex-direction: column;
  }
  
  .role-selector {
    flex-direction: column;
    width: 100%;
    max-width: 450px;
  }
  
  .role-btn {
    flex-direction: row;
    justify-content: center;
    min-width: auto;
  }
}
</style>