<template>
  <div class="work-orders">
    <div class="header">
      <h1>工单管理</h1>
    </div>
    
    <div class="search-bar">
      <el-input
        v-model="searchQuery"
        placeholder="搜索工单编号、老人姓名"
        prefix-icon="el-icon-search"
        style="width: 300px"
      />
      <el-select v-model="statusFilter" placeholder="工单状态" style="margin-left: 10px">
        <el-option label="全部" value="" />
        <el-option label="待处理" value="pending" />
        <el-option label="处理中" value="processing" />
        <el-option label="已完成" value="completed" />
        <el-option label="已取消" value="cancelled" />
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
    
    <el-table :data="workOrderList" style="width: 100%" border>
      <el-table-column prop="id" label="工单编号" width="120" />
      <el-table-column prop="elderlyName" label="老人姓名" width="120" />
      <el-table-column prop="serviceType" label="服务类型" width="120">
        <template #default="scope">
          <el-tag :type="getTagType(scope.row.serviceType)">
            {{ getServiceTypeName(scope.row.serviceType) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="服务内容" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusName(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180" />
      <el-table-column prop="handler" label="处理人" width="120" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleViewWorkOrder(scope.row)">
            查看
          </el-button>
          <el-button type="success" size="small" v-if="scope.row.status === 'pending'" @click="handleProcessWorkOrder(scope.row)">
            处理
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
    
    <!-- 查看工单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
    >
      <el-form :model="workOrderForm" label-width="120px">
        <el-form-item label="工单编号">
          <el-input v-model="workOrderForm.id" disabled />
        </el-form-item>
        <el-form-item label="老人姓名">
          <el-input v-model="workOrderForm.elderlyName" disabled />
        </el-form-item>
        <el-form-item label="服务类型">
          <el-input v-model="workOrderForm.serviceType" disabled />
        </el-form-item>
        <el-form-item label="服务内容">
          <el-input v-model="workOrderForm.description" type="textarea" :rows="4" disabled />
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input v-model="workOrderForm.createdAt" disabled />
        </el-form-item>
        <el-form-item label="状态" v-if="workOrderForm.status">
          <el-select v-model="workOrderForm.status" v-if="isProcessing">
            <el-option label="待处理" value="pending" />
            <el-option label="处理中" value="processing" />
            <el-option label="已完成" value="completed" />
            <el-option label="已取消" value="cancelled" />
          </el-select>
          <el-input :value="getStatusName(workOrderForm.status)" disabled v-else />
        </el-form-item>
        <el-form-item label="处理人" v-if="isProcessing">
          <el-input v-model="workOrderForm.handler" placeholder="请输入处理人" />
        </el-form-item>
        <el-form-item label="处理备注" v-if="isProcessing">
          <el-input v-model="workOrderForm.remark" type="textarea" :rows="3" placeholder="请输入处理备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" v-if="isProcessing" @click="handleSaveWorkOrder">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'

const dialogVisible = ref(false)
const dialogTitle = ref('查看工单')
const searchQuery = ref('')
const statusFilter = ref('')
const dateRange = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)
const isProcessing = ref(false)

const workOrderForm = reactive({
  id: '',
  elderlyName: '',
  serviceType: '',
  description: '',
  status: '',
  createdAt: '',
  handler: '',
  remark: ''
})

const workOrderList = ref([
  {
    id: 'WO20240101001',
    elderlyName: '张奶奶',
    serviceType: 'meal',
    description: '需要上门送餐服务，每天中午12点',
    status: 'pending',
    createdAt: '2024-01-15 10:30:00',
    handler: ''
  },
  {
    id: 'WO20240101002',
    elderlyName: '李爷爷',
    serviceType: 'housekeeping',
    description: '需要家政服务，每周一次',
    status: 'processing',
    createdAt: '2024-01-15 09:15:00',
    handler: '王阿姨'
  },
  {
    id: 'WO20240101003',
    elderlyName: '王奶奶',
    serviceType: 'repair',
    description: '家里水龙头漏水，需要维修',
    status: 'completed',
    createdAt: '2024-01-14 16:45:00',
    handler: '刘师傅'
  }
])

const getTagType = (type) => {
  switch (type) {
    case 'meal':
      return 'primary'
    case 'housekeeping':
      return 'success'
    case 'repair':
      return 'warning'
    case 'consultation':
      return 'info'
    default:
      return 'default'
  }
}

const getServiceTypeName = (type) => {
  switch (type) {
    case 'meal':
      return '送餐服务'
    case 'housekeeping':
      return '家政服务'
    case 'repair':
      return '维修服务'
    case 'consultation':
      return '咨询服务'
    default:
      return '未知服务'
  }
}

const getStatusType = (status) => {
  switch (status) {
    case 'pending':
      return 'warning'
    case 'processing':
      return 'primary'
    case 'completed':
      return 'success'
    case 'cancelled':
      return 'info'
    default:
      return 'default'
  }
}

const getStatusName = (status) => {
  switch (status) {
    case 'pending':
      return '待处理'
    case 'processing':
      return '处理中'
    case 'completed':
      return '已完成'
    case 'cancelled':
      return '已取消'
    default:
      return '未知状态'
  }
}

const handleViewWorkOrder = (row) => {
  dialogTitle.value = '查看工单'
  isProcessing.value = false
  Object.assign(workOrderForm, row)
  dialogVisible.value = true
}

const handleProcessWorkOrder = (row) => {
  dialogTitle.value = '处理工单'
  isProcessing.value = true
  Object.assign(workOrderForm, row)
  dialogVisible.value = true
}

const handleSaveWorkOrder = () => {
  // 调用API保存工单处理结果
  console.log('保存工单处理结果:', workOrderForm)
  dialogVisible.value = false
  // 刷新列表
}

const handleSearch = () => {
  // 调用API搜索工单
  console.log('搜索工单:', searchQuery.value, statusFilter.value, dateRange.value)
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
.work-orders {
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
  .work-orders {
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