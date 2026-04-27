<template>
  <div class="family-management">
    <div class="header">
      <h1>子女管理</h1>
      <el-button type="primary" @click="handleAddFamily">
        <el-icon><Plus /></el-icon>
        添加子女
      </el-button>
    </div>
    
    <div class="search-bar">
      <el-input
        v-model="searchQuery"
        placeholder="搜索子女姓名、手机号"
        prefix-icon="el-icon-search"
        style="width: 300px"
      />
      <el-button type="primary" @click="handleSearch" style="margin-left: 10px">
        搜索
      </el-button>
    </div>
    
    <el-table :data="familyList" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="phone" label="手机号" width="150" />
      <el-table-column prop="relation" label="与老人关系" width="120" />
      <el-table-column prop="elderlyName" label="关联老人" width="120" />
      <el-table-column prop="createdAt" label="创建时间" width="180" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEditFamily(scope.row)">
            编辑
          </el-button>
          <el-button type="danger" size="small" @click="handleDeleteFamily(scope.row.id)">
            删除
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
    
    <!-- 添加/编辑子女对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
    >
      <el-form :model="familyForm" :rules="rules" ref="familyFormRef" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="familyForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="familyForm.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="与老人关系" prop="relation">
          <el-select v-model="familyForm.relation">
            <el-option label="子女" value="child" />
            <el-option label="孙子女" value="grandchild" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
        <el-form-item label="关联老人" prop="elderlyId">
          <el-select v-model="familyForm.elderlyId" placeholder="请选择老人">
            <el-option v-for="elderly in elderlyOptions" :key="elderly.id" :label="elderly.name" :value="elderly.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSaveFamily">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Plus } from '@element-plus/icons-vue'

const dialogVisible = ref(false)
const dialogTitle = ref('添加子女')
const familyFormRef = ref(null)
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)

const familyForm = reactive({
  id: '',
  name: '',
  phone: '',
  relation: 'child',
  elderlyId: ''
})

const rules = {
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' }
  ],
  elderlyId: [
    { required: true, message: '请选择关联老人', trigger: 'blur' }
  ]
}

const elderlyOptions = ref([
  { id: 1, name: '张奶奶' },
  { id: 2, name: '李爷爷' },
  { id: 3, name: '王奶奶' }
])

const familyList = ref([
  {
    id: 1,
    name: '张三',
    phone: '13800138002',
    relation: 'child',
    elderlyName: '张奶奶',
    createdAt: '2024-01-01 10:00:00'
  },
  {
    id: 2,
    name: '李四',
    phone: '13900139002',
    relation: 'child',
    elderlyName: '李爷爷',
    createdAt: '2024-01-02 11:00:00'
  },
  {
    id: 3,
    name: '王五',
    phone: '13700137002',
    relation: 'grandchild',
    elderlyName: '王奶奶',
    createdAt: '2024-01-03 12:00:00'
  }
])

const handleAddFamily = () => {
  dialogTitle.value = '添加子女'
  Object.assign(familyForm, {
    id: '',
    name: '',
    phone: '',
    relation: 'child',
    elderlyId: ''
  })
  dialogVisible.value = true
}

const handleEditFamily = (row) => {
  dialogTitle.value = '编辑子女'
  Object.assign(familyForm, row)
  dialogVisible.value = true
}

const handleSaveFamily = async () => {
  if (!familyFormRef.value) return
  
  try {
    await familyFormRef.value.validate()
    // 调用API保存子女信息
    dialogVisible.value = false
    // 刷新列表
  } catch (error) {
    console.error('验证失败:', error)
  }
}

const handleDeleteFamily = (id) => {
  // 调用API删除子女
  console.log('删除子女:', id)
}

const handleSearch = () => {
  // 调用API搜索子女
  console.log('搜索子女:', searchQuery.value)
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
.family-management {
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
  .family-management {
    padding: 10px;
  }
  
  .header h1 {
    font-size: 20px;
  }
  
  .search-bar {
    flex-direction: column;
    align-items: stretch;
  }
  
  .search-bar .el-input {
    margin-bottom: 10px;
    margin-left: 0 !important;
  }
  
  .search-bar .el-button {
    margin-left: 0 !important;
  }
}
</style>