<template>
  <div class="policy-library">
    <div class="header">
      <h1>政策库</h1>
      <el-button type="primary" @click="handleAddPolicy">
        <el-icon><Plus /></el-icon>
        添加政策
      </el-button>
    </div>
    
    <div class="search-bar">
      <el-input
        v-model="searchQuery"
        placeholder="搜索政策标题"
        prefix-icon="el-icon-search"
        style="width: 300px"
      />
      <el-select v-model="policyType" placeholder="政策类型" style="margin-left: 10px">
        <el-option label="全部" value="" />
        <el-option label="养老政策" value="elderly" />
        <el-option label="医疗政策" value="medical" />
        <el-option label="补贴政策" value="subsidy" />
      </el-select>
      <el-button type="primary" @click="handleSearch" style="margin-left: 10px">
        搜索
      </el-button>
    </div>
    
    <el-table :data="policyList" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="type" label="类型" width="120">
        <template #default="scope">
          <el-tag :type="getTagType(scope.row.type)">
            {{ getTypeName(scope.row.type) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="publishDate" label="发布日期" width="150" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 'active' ? 'success' : 'info'">
            {{ scope.row.status === 'active' ? '生效中' : '已过期' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEditPolicy(scope.row)">
            编辑
          </el-button>
          <el-button type="danger" size="small" @click="handleDeletePolicy(scope.row.id)">
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
    
    <!-- 添加/编辑政策对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
    >
      <el-form :model="policyForm" :rules="rules" ref="policyFormRef" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="policyForm.title" placeholder="请输入政策标题" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="policyForm.type">
            <el-option label="养老政策" value="elderly" />
            <el-option label="医疗政策" value="medical" />
            <el-option label="补贴政策" value="subsidy" />
          </el-select>
        </el-form-item>
        <el-form-item label="发布日期" prop="publishDate">
          <el-date-picker v-model="policyForm.publishDate" type="date" placeholder="请选择发布日期" />
        </el-form-item>
        <el-form-item label="生效日期" prop="effectiveDate">
          <el-date-picker v-model="policyForm.effectiveDate" type="date" placeholder="请选择生效日期" />
        </el-form-item>
        <el-form-item label="失效日期" prop="expireDate">
          <el-date-picker v-model="policyForm.expireDate" type="date" placeholder="请选择失效日期" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="policyForm.content" type="textarea" placeholder="请输入政策内容" :rows="5" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="policyForm.status">
            <el-option label="生效中" value="active" />
            <el-option label="已过期" value="expired" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSavePolicy">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Plus } from '@element-plus/icons-vue'

const dialogVisible = ref(false)
const dialogTitle = ref('添加政策')
const policyFormRef = ref(null)
const searchQuery = ref('')
const policyType = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)

const policyForm = reactive({
  id: '',
  title: '',
  type: 'elderly',
  publishDate: '',
  effectiveDate: '',
  expireDate: '',
  content: '',
  status: 'active'
})

const rules = {
  title: [
    { required: true, message: '请输入政策标题', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择政策类型', trigger: 'blur' }
  ],
  publishDate: [
    { required: true, message: '请选择发布日期', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入政策内容', trigger: 'blur' }
  ]
}

const policyList = ref([
  {
    id: 1,
    title: '关于加强老年人关爱服务的实施意见',
    type: 'elderly',
    publishDate: '2024-01-01',
    status: 'active'
  },
  {
    id: 2,
    title: '老年人医疗保障补贴办法',
    type: 'medical',
    publishDate: '2024-01-02',
    status: 'active'
  },
  {
    id: 3,
    title: '老年人居家养老服务补贴政策',
    type: 'subsidy',
    publishDate: '2024-01-03',
    status: 'active'
  }
])

const getTagType = (type) => {
  switch (type) {
    case 'elderly':
      return 'primary'
    case 'medical':
      return 'success'
    case 'subsidy':
      return 'warning'
    default:
      return 'default'
  }
}

const getTypeName = (type) => {
  switch (type) {
    case 'elderly':
      return '养老政策'
    case 'medical':
      return '医疗政策'
    case 'subsidy':
      return '补贴政策'
    default:
      return '未知类型'
  }
}

const handleAddPolicy = () => {
  dialogTitle.value = '添加政策'
  Object.assign(policyForm, {
    id: '',
    title: '',
    type: 'elderly',
    publishDate: '',
    effectiveDate: '',
    expireDate: '',
    content: '',
    status: 'active'
  })
  dialogVisible.value = true
}

const handleEditPolicy = (row) => {
  dialogTitle.value = '编辑政策'
  Object.assign(policyForm, row)
  dialogVisible.value = true
}

const handleSavePolicy = async () => {
  if (!policyFormRef.value) return
  
  try {
    await policyFormRef.value.validate()
    // 调用API保存政策信息
    dialogVisible.value = false
    // 刷新列表
  } catch (error) {
    console.error('验证失败:', error)
  }
}

const handleDeletePolicy = (id) => {
  // 调用API删除政策
  console.log('删除政策:', id)
}

const handleSearch = () => {
  // 调用API搜索政策
  console.log('搜索政策:', searchQuery.value, policyType.value)
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
.policy-library {
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
  .policy-library {
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