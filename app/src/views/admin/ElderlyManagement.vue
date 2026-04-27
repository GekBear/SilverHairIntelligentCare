<template>
  <div class="elderly-management">
    <div class="header">
      <h1>老人管理</h1>
      <el-button type="primary" @click="handleAddElderly">
        <el-icon><Plus /></el-icon>
        添加老人
      </el-button>
    </div>
    
    <div class="search-bar">
      <el-input
        v-model="searchQuery"
        placeholder="搜索老人姓名、手机号"
        prefix-icon="el-icon-search"
        style="width: 300px"
      />
      <el-select v-model="statusFilter" placeholder="状态" style="margin-left: 10px">
        <el-option label="全部" value="" />
        <el-option label="正常" value="normal" />
        <el-option label="异常" value="abnormal" />
      </el-select>
      <el-button type="primary" @click="handleSearch" style="margin-left: 10px">
        搜索
      </el-button>
    </div>
    
    <el-table :data="elderlyList" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="gender" label="性别" width="80">
        <template #default="scope">
          {{ scope.row.gender === 'male' ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="80" />
      <el-table-column prop="phone" label="手机号" width="150" />
      <el-table-column prop="address" label="地址" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 'normal' ? 'success' : 'warning'">
            {{ scope.row.status === 'normal' ? '正常' : '异常' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEditElderly(scope.row)">
            编辑
          </el-button>
          <el-button type="danger" size="small" @click="handleDeleteElderly(scope.row.id)">
            删除
          </el-button>
          <el-button type="info" size="small" @click="handleViewDetails(scope.row)">
            详情
          </el-button>
        </template>
      </el-table-column>
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
    
    <!-- 添加/编辑老人对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
    >
      <el-form :model="elderlyForm" :rules="rules" ref="elderlyFormRef" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="elderlyForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="elderlyForm.gender">
            <el-radio label="male">男</el-radio>
            <el-radio label="female">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="elderlyForm.age" type="number" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="elderlyForm.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="elderlyForm.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="elderlyForm.status">
            <el-option label="正常" value="normal" />
            <el-option label="异常" value="abnormal" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSaveElderly">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Plus } from '@element-plus/icons-vue'

const dialogVisible = ref(false)
const dialogTitle = ref('添加老人')
const elderlyFormRef = ref(null)
const searchQuery = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)

const elderlyForm = reactive({
  id: '',
  name: '',
  gender: 'male',
  age: '',
  phone: '',
  address: '',
  status: 'normal'
})

const rules = {
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' }
  ]
}

const elderlyList = ref([
  {
    id: 1,
    name: '张奶奶',
    gender: 'female',
    age: 78,
    phone: '13800138001',
    address: '北京市朝阳区建国路88号',
    status: 'normal',
    createdAt: '2024-01-01 10:00:00'
  },
  {
    id: 2,
    name: '李爷爷',
    gender: 'male',
    age: 82,
    phone: '13900139001',
    address: '上海市浦东新区陆家嘴环路1000号',
    status: 'normal',
    createdAt: '2024-01-02 11:00:00'
  },
  {
    id: 3,
    name: '王奶奶',
    gender: 'female',
    age: 75,
    phone: '13700137001',
    address: '广州市天河区天河路385号',
    status: 'abnormal',
    createdAt: '2024-01-03 12:00:00'
  }
])

const handleAddElderly = () => {
  dialogTitle.value = '添加老人'
  Object.assign(elderlyForm, {
    id: '',
    name: '',
    gender: 'male',
    age: '',
    phone: '',
    address: '',
    status: 'normal'
  })
  dialogVisible.value = true
}

const handleEditElderly = (row) => {
  dialogTitle.value = '编辑老人'
  Object.assign(elderlyForm, row)
  dialogVisible.value = true
}

const handleSaveElderly = async () => {
  if (!elderlyFormRef.value) return
  
  try {
    await elderlyFormRef.value.validate()
    // 调用API保存老人信息
    dialogVisible.value = false
    // 刷新列表
  } catch (error) {
    console.error('验证失败:', error)
  }
}

const handleDeleteElderly = (id) => {
  // 调用API删除老人
  console.log('删除老人:', id)
}

const handleViewDetails = (row) => {
  // 跳转到老人详情页
  console.log('查看老人详情:', row)
}

const handleSearch = () => {
  // 调用API搜索老人
  console.log('搜索老人:', searchQuery.value, statusFilter.value)
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
.elderly-management {
  padding: 20px;
  min-height: 100vh;
  background: #f5f7fa;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

@media (max-width: 768px) {
  .elderly-management {
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
  .search-bar .el-select {
    margin-bottom: 10px;
    margin-left: 0 !important;
  }
  
  .search-bar .el-button {
    margin-left: 0 !important;
  }
}
</style>