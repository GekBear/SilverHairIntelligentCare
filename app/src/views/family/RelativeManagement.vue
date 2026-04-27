<template>
  <div class="relative-management">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">亲属管理</div>
      <div style="width: 24px;"></div> <!-- 占位，保持标题居中 -->
    </div>

    <div class="container">
      <!-- 亲属列表 -->
      <div class="relative-item" v-for="(relative, index) in relatives" :key="index" @click="editRelative(relative)">
        <div class="relative-avatar" :class="relative.avatarClass">
          {{ relative.name.charAt(0) }}
        </div>
        <div class="relative-info">
          <div class="relative-name">{{ relative.name }}</div>
          <div class="relative-relation">{{ relative.relation }}</div>
        </div>
        <div class="relative-actions">
          <el-icon><ArrowRight /></el-icon>
        </div>
      </div>

      <!-- 添加亲属按钮 -->
      <el-button type="primary" class="add-relative-btn" @click="addRelative">
        <el-icon><Plus /></el-icon>
        添加亲属
      </el-button>
    </div>
  </div>
</template>

<script setup>
import { ArrowLeft, Plus, ArrowRight } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 亲属数据
const relatives = ref([
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
    name: '王叔叔',
    relation: '叔叔',
    avatarClass: 'avatar-warning'
  }
])

const goBack = () => {
  router.back()
}

const addRelative = () => {
  // 模拟添加亲属
  console.log('添加新亲属')
  ElMessage.info('添加新亲属')
}

const editRelative = (relative) => {
  // 模拟编辑亲属
  console.log('编辑亲属:', relative)
  ElMessage.info(`编辑${relative.name}的信息`)
}
</script>

<style scoped>
.relative-management {
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

.relative-item {
  display: flex;
  align-items: center;
  background-color: #fff;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s;
}

.relative-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.relative-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background-color: #409eff;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 600;
  margin-right: 16px;
}

.avatar-success {
  background-color: #67c23a;
}

.avatar-warning {
  background-color: #e6a23c;
}

.relative-info {
  flex: 1;
}

.relative-name {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
}

.relative-relation {
  font-size: 14px;
  color: #909399;
}

.relative-actions {
  color: #909399;
}

.add-relative-btn {
  width: 100%;
  margin-top: 20px;
}
</style>