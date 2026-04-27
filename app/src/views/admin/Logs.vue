<template>
  <div class="logs-container">
    <div class="header">
      <h1>系统日志</h1>
    </div>
    
    <div class="search-bar">
      <el-input
        v-model="searchQuery"
        placeholder="搜索日志内容"
        prefix-icon="el-icon-search"
        style="width: 300px"
      />
      <el-select v-model="logType" placeholder="日志类型" style="margin-left: 10px">
        <el-option label="全部" value="" />
        <el-option label="登录日志" value="login" />
        <el-option label="操作日志" value="operation" />
        <el-option label="系统日志" value="system" />
      </el-select>
      <el-date-picker
        v-model="dateRange"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        style="margin-left: 10px"
      />
      <el-button type="primary" @click="handleSearch" style="margin-left: 10px">
        搜索
      </el-button>
    </div>
    
    <el-table :data="logList" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="type" label="类型" width="120">
        <template #default="scope">
          <el-tag :type="getTagType(scope.row.type)">
            {{ getTypeName(scope.row.type) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="operator" label="操作人" width="120" />
      <el-table-column prop="ip" label="IP地址" width="150" />
      <el-table-column prop="createdAt" label="操作时间" width="180" />
    </el-table>
    
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'

const searchQuery = ref('')
const logType = ref('')
const dateRange = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)

const logList = ref([
  {
    id: 1,
    type: 'login',
    content: '管理员登录系统',
    operator: 'admin',
    ip: '192.168.1.100',
    createdAt: '2024-01-15 10:30:00'
  },
  {
    id: 2,
    type: 'operation',
    content: '添加老人信息',
    operator: 'admin',
    ip: '192.168.1.100',
    createdAt: '2024-01-15 10:25:00'
  },
  {
    id: 3,
    type: 'system',
    content: '系统启动',
    operator: 'system',
    ip: '127.0.0.1',
    createdAt: '2024-01-15 08:00:00'
  },
  {
    id: 4,
    type: 'operation',
    content: '编辑老人信息',
    operator: 'admin',
    ip: '192.168.1.100',
    createdAt: '2024-01-14 16:45:00'
  },
  {
    id: 5,
    type: 'login',
    content: '管理员登录系统',
    operator: 'admin',
    ip: '192.168.1.100',
    createdAt: '2024-01-14 09:15:00'
  }
])

const getTagType = (type) => {
  switch (type) {
    case 'login':
      return 'primary'
    case 'operation':
      return 'success'
    case 'system':
      return 'info'
    default:
      return 'default'
  }
}

const getTypeName = (type) => {
  switch (type) {
    case 'login':
      return '登录日志'
    case 'operation':
      return '操作日志'
    case 'system':
      return '系统日志'
    default:
      return '未知类型'
  }
}

const handleSearch = () => {
  // 调用API搜索日志
  console.log('搜索日志:', searchQuery.value, logType.value, dateRange.value)
}

const handleSizeChange = (size) => {
  pageSize.value = size
  // 调用API获取数据
}

const handleCurrentChange = (current) => {
  currentPage.value = current
  // 调用API获取数据
}
</script>

<style scoped>
.logs-container {
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

.search-bar {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

@media (max-width: 768px) {
  .logs-container {
    padding: 10px;
  }
  
  .header h1 {
    font-size: 20px;
  }
  
  .search-bar {
    flex-direction: column;
    align-items: stretch;
  }
  
  .search-bar .el-input,
  .search-bar .el-select,
  .search-bar .el-date-picker {
    margin-left: 0 !important;
    margin-bottom: 10px;
  }
  
  .search-bar .el-button {
    margin-left: 0 !important;
  }
}
</style>