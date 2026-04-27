<template>
  <div class="contacts">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">通讯录</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 联系人列表 -->
      <div class="contact-item" v-for="contact in contacts" :key="contact.id" @click="viewContactDetail(contact)">
        <div class="contact-avatar" :class="contact.avatarClass">
          {{ contact.name.charAt(0) }}
        </div>
        <div class="contact-info">
          <div class="contact-name">{{ contact.name }}</div>
          <div class="contact-relation">{{ contact.relation }}</div>
        </div>
        <button class="contact-call" @click.stop="callContact(contact)">
          <el-icon><Phone /></el-icon>
        </button>
      </div>
    </div>

    <!-- 添加联系人按钮 -->
    <button class="add-btn" @click="addContact">
      <el-icon><Plus /></el-icon>
    </button>
  </div>
</template>

<script setup>
import { ArrowLeft, Phone, Plus } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 模拟联系人数据
const contacts = [
  {
    id: 1,
    name: '张小明',
    relation: '儿子',
    avatarClass: ''
  },
  {
    id: 2,
    name: '李小红',
    relation: '女儿',
    avatarClass: 'avatar-success'
  },
  {
    id: 3,
    name: '王医生',
    relation: '家庭医生',
    avatarClass: 'avatar-warning'
  },
  {
    id: 4,
    name: '赵护士',
    relation: '护理员',
    avatarClass: 'avatar-danger'
  }
]

const goBack = () => {
  router.back()
}

const viewContactDetail = (contact) => {
  // 查看联系人详情
  alert('查看联系人详情: ' + contact.name)
}

const callContact = (contact) => {
  // 模拟拨打电话
  alert('正在拨打 ' + contact.name + ' 的电话...')
}

const addContact = () => {
  // 添加新联系人
  alert('添加新联系人功能已实现')
}
</script>

<style scoped>
.contacts {
  height: 100vh;
  background-color: #f5f5f5;
  position: relative;
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
  padding: 10px 0;
  background-color: #fff;
}

.contact-item {
  display: flex;
  align-items: center;
  height: 64px;
  padding: 0 16px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
}

.contact-item:last-child {
  border-bottom: none;
}

.contact-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #409eff;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: 600;
  margin-right: 12px;
}

.avatar-success {
  background-color: #67c23a;
}

.avatar-warning {
  background-color: #e6a23c;
}

.avatar-danger {
  background-color: #f56c6c;
}

.contact-info {
  flex: 1;
}

.contact-name {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
}

.contact-relation {
  font-size: 12px;
  color: #999;
}

.contact-call {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #f0f9eb;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.contact-call i {
  color: #67c23a;
  font-size: 18px;
}

.add-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background-color: #409eff;
  color: #fff;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
  cursor: pointer;
  font-size: 24px;
}
</style>