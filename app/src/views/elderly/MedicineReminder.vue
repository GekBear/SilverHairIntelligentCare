<template>
  <div class="medicine-reminder">
    <!-- 头部 -->
    <div class="header">
      <button class="back-button" @click="goBack">
        <span class="back-icon">←</span>
      </button>
      <h1 class="title">药物提醒</h1>
      <button class="add-button" @click="showAddDialog">
        <span class="add-icon">+</span>
      </button>
    </div>

    <!-- 今日药物 -->
    <div class="today-medicine">
      <h2 class="section-title">今日药物</h2>
      <div class="medicine-list">
        <div 
          v-for="medicine in todayMedicines" 
          :key="medicine.id"
          class="medicine-item"
          @click="showMedicineReminder(medicine)"
        >
          <div class="medicine-info">
            <div class="medicine-icon">{{ getMedicineIcon(medicine.type) }}</div>
            <div class="medicine-details">
              <h3 class="medicine-name">{{ medicine.name }}</h3>
              <p class="medicine-time">{{ medicine.time }}</p>
              <p class="medicine-dosage">{{ medicine.dosage }}</p>
            </div>
          </div>
          <div class="medicine-status">
            <el-switch 
              v-model="medicine.taken" 
              @change="updateMedicineStatus(medicine.id)"
              active-color="#67c23a"
              inactive-color="#e4e7ed"
            />
          </div>
        </div>
      </div>
    </div>

    <!-- 所有药物 -->
    <div class="all-medicine">
      <h2 class="section-title">所有药物</h2>
      <div class="medicine-list">
        <div 
          v-for="medicine in medicines" 
          :key="medicine.id"
          class="medicine-item"
        >
          <div class="medicine-info">
            <div class="medicine-icon">{{ getMedicineIcon(medicine.type) }}</div>
            <div class="medicine-details">
              <h3 class="medicine-name">{{ medicine.name }}</h3>
              <p class="medicine-time">{{ medicine.time }}</p>
              <p class="medicine-dosage">{{ medicine.dosage }}</p>
            </div>
          </div>
          <div class="medicine-actions">
            <button class="edit-button" @click="showEditDialog(medicine)">
              <span class="edit-icon">✏️</span>
            </button>
            <button class="delete-button" @click="deleteMedicine(medicine.id)">
              <span class="delete-icon">🗑️</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 药物提示 -->
    <div class="medicine-tips">
      <h2 class="section-title">用药提示</h2>
      <div class="tips-list">
        <div class="tip-item">
          <div class="tip-icon">💡</div>
          <div class="tip-content">
            <h3 class="tip-title">按时服药</h3>
            <p class="tip-text">请严格按照医嘱按时服药，不要擅自增减药量</p>
          </div>
        </div>
        <div class="tip-item">
          <div class="tip-icon">💧</div>
          <div class="tip-content">
            <h3 class="tip-title">多喝水</h3>
            <p class="tip-text">服药时请用温水送服，有助于药物吸收</p>
          </div>
        </div>
        <div class="tip-item">
          <div class="tip-icon">🍎</div>
          <div class="tip-content">
            <h3 class="tip-title">注意饮食</h3>
            <p class="tip-text">某些药物需要空腹服用，某些需要饭后服用，请遵医嘱</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加药物对话框 -->
    <el-dialog
      v-model="addDialogVisible"
      title="添加药物"
      width="80%"
    >
      <el-form :model="newMedicine" label-width="80px">
        <el-form-item label="药物名称">
          <el-input v-model="newMedicine.name" placeholder="请输入药物名称" />
        </el-form-item>
        <el-form-item label="服药时间">
          <el-time-picker
            v-model="newMedicine.time"
            placeholder="选择时间"
            format="HH:mm"
            value-format="HH:mm"
          />
        </el-form-item>
        <el-form-item label="用药剂量">
          <el-input v-model="newMedicine.dosage" placeholder="例如：1片" />
        </el-form-item>
        <el-form-item label="药物类型">
          <el-select v-model="newMedicine.type" placeholder="选择药物类型">
            <el-option label="口服药" value="oral" />
            <el-option label="外用药" value="external" />
            <el-option label="注射药" value="injection" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addMedicine">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑药物对话框 -->
    <el-dialog
      v-model="editDialogVisible"
      title="编辑药物"
      width="80%"
    >
      <el-form :model="editingMedicine" label-width="80px">
        <el-form-item label="药物名称">
          <el-input v-model="editingMedicine.name" placeholder="请输入药物名称" />
        </el-form-item>
        <el-form-item label="服药时间">
          <el-time-picker
            v-model="editingMedicine.time"
            placeholder="选择时间"
            format="HH:mm"
            value-format="HH:mm"
          />
        </el-form-item>
        <el-form-item label="用药剂量">
          <el-input v-model="editingMedicine.dosage" placeholder="例如：1片" />
        </el-form-item>
        <el-form-item label="药物类型">
          <el-select v-model="editingMedicine.type" placeholder="选择药物类型">
            <el-option label="口服药" value="oral" />
            <el-option label="外用药" value="external" />
            <el-option label="注射药" value="injection" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="updateMedicine">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 用药提醒详情弹窗（融合安全审查） -->
    <el-dialog
      v-model="medicineReminderDialogVisible"
      :title="selectedMedicine?.name + ' - 用药提醒'"
      width="90%"
      :close-on-click-modal="false"
    >
      <div class="medicine-reminder-content" v-if="selectedMedicine">
        <!-- 药物基本信息 -->
        <div class="medicine-basic-info">
          <div class="medicine-header">
            <div class="medicine-icon-large">{{ getMedicineIcon(selectedMedicine.type) }}</div>
            <div class="medicine-header-info">
              <h3 class="medicine-name-large">{{ selectedMedicine.name }}</h3>
              <p class="medicine-time-large">⏰ {{ selectedMedicine.time }}</p>
              <p class="medicine-dosage-large">💊 {{ selectedMedicine.dosage }}</p>
            </div>
          </div>
        </div>

        <!-- 用药安全审查结果 -->
        <div class="safety-review-section">
          <h4 class="review-section-title">
            <span class="section-icon">🛡️</span>
            用药安全审查
          </h4>
          
          <!-- 审查结果概览 -->
          <div class="review-overview" :class="reviewResult.overallStatus">
            <div class="overview-icon">{{ reviewResult.overallIcon }}</div>
            <div class="overview-content">
              <h3 class="overview-title">{{ reviewResult.overallTitle }}</h3>
              <p class="overview-text">{{ reviewResult.overallText }}</p>
            </div>
          </div>

          <!-- 详细审查结果 -->
          <div class="review-details">
            <!-- 药物冲突检查 -->
            <div class="review-item" :class="reviewResult.conflictStatus">
              <h5 class="review-item-title">
                <span class="item-icon">⚡</span>
                药物冲突检查
              </h5>
              <div class="review-item-content">
                <p class="item-result">{{ reviewResult.conflictTitle }}</p>
                <p class="item-desc">{{ reviewResult.conflictText }}</p>
                <div v-if="reviewResult.conflicts.length > 0" class="warning-list">
                  <div v-for="(conflict, index) in reviewResult.conflicts" :key="index" class="warning-item">
                    <span class="warning-drugs">{{ conflict.drugs }}</span>
                    <span class="warning-message">{{ conflict.message }}</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- 重复用药检查 -->
            <div class="review-item" :class="reviewResult.duplicateStatus">
              <h5 class="review-item-title">
                <span class="item-icon">🔄</span>
                重复用药检查
              </h5>
              <div class="review-item-content">
                <p class="item-result">{{ reviewResult.duplicateTitle }}</p>
                <p class="item-desc">{{ reviewResult.duplicateText }}</p>
                <div v-if="reviewResult.duplicates.length > 0" class="warning-list">
                  <div v-for="(duplicate, index) in reviewResult.duplicates" :key="index" class="warning-item">
                    <span class="warning-drug">{{ duplicate.drug }}</span>
                    <span class="warning-message">{{ duplicate.message }}</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- 剂量超标检查 -->
            <div class="review-item" :class="reviewResult.dosageStatus">
              <h5 class="review-item-title">
                <span class="item-icon">📊</span>
                剂量超标检查
              </h5>
              <div class="review-item-content">
                <p class="item-result">{{ reviewResult.dosageTitle }}</p>
                <p class="item-desc">{{ reviewResult.dosageText }}</p>
                <div v-if="reviewResult.dosageIssues.length > 0" class="warning-list">
                  <div v-for="(issue, index) in reviewResult.dosageIssues" :key="index" class="warning-item">
                    <span class="warning-drug">{{ issue.drug }}</span>
                    <span class="warning-message">{{ issue.message }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 个性化用药建议 -->
        <div class="personalized-advice-section">
          <h4 class="review-section-title">
            <span class="section-icon">👤</span>
            个性化用药建议
          </h4>
          <div class="advice-list">
            <div v-for="(advice, index) in reviewResult.personalizedAdvice" :key="index" class="advice-card">
              <div class="advice-icon">{{ advice.icon }}</div>
              <div class="advice-info">
                <p class="advice-title">{{ advice.title }}</p>
                <p class="advice-text">{{ advice.text }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 用药提醒 -->
        <div class="reminder-section">
          <h4 class="review-section-title">
            <span class="section-icon">⏰</span>
            用药提醒
          </h4>
          <div class="reminder-content">
            <p class="reminder-text">请按时服用 {{ selectedMedicine.name }}，不要忘记哦！</p>
            <div class="reminder-actions">
              <el-button type="primary" @click="confirmTakeMedicine">我已服用</el-button>
              <el-button @click="snoozeReminder">稍后提醒</el-button>
            </div>
          </div>
        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="medicineReminderDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const addDialogVisible = ref(false)
const editDialogVisible = ref(false)
const medicineReminderDialogVisible = ref(false)
const selectedMedicine = ref(null)

const medicines = ref([
  {
    id: 1,
    name: '降压药',
    time: '08:00',
    dosage: '1片',
    type: 'oral',
    taken: false
  },
  {
    id: 2,
    name: '降糖药',
    time: '15:00',
    dosage: '1片',
    type: 'oral',
    taken: false
  },
  {
    id: 3,
    name: '维生素',
    time: '20:00',
    dosage: '1片',
    type: 'oral',
    taken: false
  }
])

const newMedicine = ref({
  name: '',
  time: '',
  dosage: '',
  type: 'oral'
})

const editingMedicine = ref({
  id: '',
  name: '',
  time: '',
  dosage: '',
  type: 'oral'
})

// 计算今日药物
const todayMedicines = computed(() => {
  return medicines.value
})

// 用药安全审查结果
const reviewResult = ref({
  overallStatus: 'safe',
  overallIcon: '✅',
  overallTitle: '用药安全',
  overallText: '您的用药方案总体安全，未发现严重问题',
  conflictStatus: 'safe',
  conflictIcon: '✅',
  conflictTitle: '未发现药物冲突',
  conflictText: '您当前服用的药物之间未发现明显冲突',
  conflicts: [],
  duplicateStatus: 'safe',
  duplicateIcon: '✅',
  duplicateTitle: '未发现重复用药',
  duplicateText: '您当前服用的药物中未发现重复用药情况',
  duplicates: [],
  dosageStatus: 'safe',
  dosageIcon: '✅',
  dosageTitle: '剂量正常',
  dosageText: '您当前服用的药物剂量在安全范围内',
  dosageIssues: [],
  personalizedAdvice: [
    {
      icon: '❤️',
      title: '高血压注意事项',
      text: '由于您有高血压，请定期监测血压，保持低盐饮食，避免情绪激动'
    },
    {
      icon: '🍬',
      title: '糖尿病注意事项',
      text: '由于您有糖尿病，请控制碳水化合物摄入，定期监测血糖'
    },
    {
      icon: '⏰',
      title: '用药时间建议',
      text: '建议您在固定时间服药，以保持药物在体内的稳定浓度'
    }
  ]
})

// 获取药物图标
const getMedicineIcon = (type) => {
  switch (type) {
    case 'oral':
      return '💊'
    case 'external':
      return '🩹'
    case 'injection':
      return '💉'
    default:
      return '📦'
  }
}

// 显示添加药物对话框
const showAddDialog = () => {
  newMedicine.value = {
    name: '',
    time: '',
    dosage: '',
    type: 'oral'
  }
  addDialogVisible.value = true
}

// 显示编辑药物对话框
const showEditDialog = (medicine) => {
  editingMedicine.value = { ...medicine }
  editDialogVisible.value = true
}

// 添加药物
const addMedicine = () => {
  if (!newMedicine.value.name || !newMedicine.value.time || !newMedicine.value.dosage) {
    alert('请填写完整信息')
    return
  }
  
  const newId = medicines.value.length + 1
  medicines.value.push({
    id: newId,
    ...newMedicine.value,
    taken: false
  })
  
  addDialogVisible.value = false
  alert('药物添加成功')
}

// 更新药物
const updateMedicine = () => {
  if (!editingMedicine.value.name || !editingMedicine.value.time || !editingMedicine.value.dosage) {
    alert('请填写完整信息')
    return
  }
  
  const index = medicines.value.findIndex(m => m.id === editingMedicine.value.id)
  if (index !== -1) {
    medicines.value[index] = { ...editingMedicine.value }
  }
  
  editDialogVisible.value = false
  alert('药物更新成功')
}

// 删除药物
const deleteMedicine = (id) => {
  if (confirm('确定要删除这个药物吗？')) {
    medicines.value = medicines.value.filter(m => m.id !== id)
    alert('药物删除成功')
  }
}

// 更新药物状态
const updateMedicineStatus = (id) => {
  const medicine = medicines.value.find(m => m.id === id)
  if (medicine) {
    alert(medicine.taken ? '已标记为已服用' : '已标记为未服用')
  }
}

// 返回上一页
const goBack = () => {
  router.back()
}

// 显示用药提醒弹窗（融合安全审查）
const showMedicineReminder = (medicine) => {
  selectedMedicine.value = medicine
  // 执行用药安全审查
  performSafetyReview()
  medicineReminderDialogVisible.value = true
}

// 确认服药
const confirmTakeMedicine = () => {
  if (selectedMedicine.value) {
    selectedMedicine.value.taken = true
    alert(`已确认服用 ${selectedMedicine.value.name}`)
    medicineReminderDialogVisible.value = false
  }
}

// 稍后提醒
const snoozeReminder = () => {
  alert('将在10分钟后再次提醒您')
  medicineReminderDialogVisible.value = false
}

// 执行用药安全审查
const performSafetyReview = () => {
  // 模拟审查过程
  setTimeout(() => {
    // 基于当前药物列表进行审查
    const currentMedicines = medicines.value
    
    // 重置审查结果
    reviewResult.value = {
      overallStatus: 'safe',
      overallIcon: '✅',
      overallTitle: '用药安全',
      overallText: '您的用药方案总体安全，未发现严重问题',
      conflictStatus: 'safe',
      conflictIcon: '✅',
      conflictTitle: '未发现药物冲突',
      conflictText: '您当前服用的药物之间未发现明显冲突',
      conflicts: [],
      duplicateStatus: 'safe',
      duplicateIcon: '✅',
      duplicateTitle: '未发现重复用药',
      duplicateText: '您当前服用的药物中未发现重复用药情况',
      duplicates: [],
      dosageStatus: 'safe',
      dosageIcon: '✅',
      dosageTitle: '剂量正常',
      dosageText: '您当前服用的药物剂量在安全范围内',
      dosageIssues: [],
      personalizedAdvice: [
        {
          icon: '❤️',
          title: '高血压注意事项',
          text: '由于您有高血压，请定期监测血压，保持低盐饮食，避免情绪激动'
        },
        {
          icon: '🍬',
          title: '糖尿病注意事项',
          text: '由于您有糖尿病，请控制碳水化合物摄入，定期监测血糖'
        },
        {
          icon: '⏰',
          title: '用药时间建议',
          text: '建议您在固定时间服药，以保持药物在体内的稳定浓度'
        }
      ]
    }
    
    // 模拟药物冲突检查
    checkDrugConflicts(currentMedicines)
    
    // 模拟重复用药检查
    checkDuplicateMedicines(currentMedicines)
    
    // 模拟剂量超标检查
    checkDosageIssues(currentMedicines)
    
    // 更新总体状态
    updateOverallStatus()
  }, 500)
}

// 检查药物冲突
const checkDrugConflicts = (medicinesList) => {
  // 模拟药物冲突检查逻辑
  // 这里可以根据实际药物名称和类型进行更复杂的检查
  const drugNames = medicinesList.map(m => m.name.toLowerCase())
  
  // 示例：如果同时服用降压药和降糖药，可能存在潜在冲突
  if (drugNames.includes('降压药') && drugNames.includes('降糖药')) {
    reviewResult.value.conflictStatus = 'warning'
    reviewResult.value.conflictIcon = '⚠️'
    reviewResult.value.conflictTitle = '潜在药物冲突'
    reviewResult.value.conflictText = '发现潜在的药物相互作用，请咨询医生'
    reviewResult.value.conflicts = [
      {
        drugs: '降压药 + 降糖药',
        message: '同时服用可能会影响血糖控制，建议在医生指导下调整用药时间'
      }
    ]
  }
}

// 检查重复用药
const checkDuplicateMedicines = (medicinesList) => {
  // 模拟重复用药检查逻辑
  const drugCounts = {}
  medicinesList.forEach(medicine => {
    const name = medicine.name.toLowerCase()
    drugCounts[name] = (drugCounts[name] || 0) + 1
  })
  
  // 检查是否有重复用药
  const duplicates = Object.entries(drugCounts)
    .filter(([_, count]) => count > 1)
    .map(([name, count]) => ({
      drug: name,
      message: `发现重复用药 ${count} 次，请确认是否需要调整`
    }))
  
  if (duplicates.length > 0) {
    reviewResult.value.duplicateStatus = 'warning'
    reviewResult.value.duplicateIcon = '⚠️'
    reviewResult.value.duplicateTitle = '发现重复用药'
    reviewResult.value.duplicateText = '您当前服用的药物中存在重复用药情况'
    reviewResult.value.duplicates = duplicates
  }
}

// 检查剂量超标
const checkDosageIssues = (medicinesList) => {
  // 模拟剂量超标检查逻辑
  const dosageIssues = []
  
  medicinesList.forEach(medicine => {
    // 示例：如果剂量中包含数字，检查是否超标
    const doseMatch = medicine.dosage.match(/\d+/)
    if (doseMatch) {
      const dose = parseInt(doseMatch[0])
      // 模拟：如果剂量大于3，认为可能超标
      if (dose > 3) {
        dosageIssues.push({
          drug: medicine.name,
          message: `当前剂量 ${medicine.dosage} 可能偏高，建议咨询医生`
        })
      }
    }
  })
  
  if (dosageIssues.length > 0) {
    reviewResult.value.dosageStatus = 'warning'
    reviewResult.value.dosageIcon = '⚠️'
    reviewResult.value.dosageTitle = '剂量可能超标'
    reviewResult.value.dosageText = '您当前服用的部分药物剂量可能偏高'
    reviewResult.value.dosageIssues = dosageIssues
  }
}

// 更新总体状态
const updateOverallStatus = () => {
  // 根据各项检查结果更新总体状态
  const hasWarning = [
    reviewResult.value.conflictStatus,
    reviewResult.value.duplicateStatus,
    reviewResult.value.dosageStatus
  ].some(status => status === 'warning')
  
  if (hasWarning) {
    reviewResult.value.overallStatus = 'warning'
    reviewResult.value.overallIcon = '⚠️'
    reviewResult.value.overallTitle = '用药需注意'
    reviewResult.value.overallText = '您的用药方案存在一些需要注意的问题，请查看详细信息'
  }
}
</script>

<style scoped>
.medicine-reminder {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
  padding: 20px;
  padding-bottom: 80px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 30px;
  padding: 20px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 20px;
  color: #333;
}

.back-button, .add-button {
  background: none;
  border: none;
  color: #333;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 10px;
  border-radius: 50%;
  transition: background 0.3s ease;
}

.back-button:hover, .add-button:hover {
  background: rgba(0, 0, 0, 0.1);
}

.back-icon {
  font-size: 2rem;
  font-weight: bold;
}

.add-icon {
  font-size: 2rem;
  font-weight: bold;
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
}

.section-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
  background: rgba(255, 255, 255, 0.8);
  padding: 15px 20px;
  border-radius: 15px;
  display: inline-block;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.today-medicine {
  margin-bottom: 30px;
}

.medicine-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.medicine-item {
  background: white;
  border-radius: 15px;
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.medicine-item:hover {
  transform: translateY(-5px);
}

.medicine-info {
  display: flex;
  align-items: center;
  gap: 15px;
  flex: 1;
}

.medicine-icon {
  font-size: 2rem;
  width: 60px;
  height: 60px;
  background: #f0f0f0;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.medicine-details {
  flex: 1;
}

.medicine-name {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.medicine-time {
  font-size: 1rem;
  color: #666;
  margin-bottom: 5px;
}

.medicine-dosage {
  font-size: 1rem;
  color: #666;
}

.medicine-status {
  margin-left: 20px;
}

.medicine-actions {
  display: flex;
  gap: 10px;
}

.edit-button, .delete-button {
  background: none;
  border: none;
  font-size: 1.2rem;
  cursor: pointer;
  padding: 10px;
  border-radius: 50%;
  transition: background 0.3s ease;
}

.edit-button:hover {
  background: #f0f0f0;
}

.delete-button:hover {
  background: #fee2e2;
}

.all-medicine {
  margin-bottom: 30px;
}

.medicine-tips {
  background: rgba(255, 255, 255, 0.8);
  border-radius: 20px;
  padding: 20px;
  backdrop-filter: blur(10px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  margin-top: 30px;
}

.tips-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.tip-item {
  display: flex;
  align-items: flex-start;
  gap: 15px;
  padding: 15px;
  background: rgba(255, 217, 61, 0.1);
  border-radius: 15px;
  transition: transform 0.3s ease;
}

.tip-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.tip-icon {
  font-size: 1.5rem;
  flex-shrink: 0;
  margin-top: 5px;
  width: 50px;
  height: 50px;
  background: rgba(255, 217, 61, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.tip-content {
  flex: 1;
}

.tip-title {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.tip-text {
  font-size: 1rem;
  color: #666;
  line-height: 1.5;
}

.dialog-footer {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.dialog-footer .el-button {
  font-size: 1.1rem;
  padding: 10px 30px;
}

/* 用药提醒弹窗样式 */
.medicine-reminder-content {
  max-height: 70vh;
  overflow-y: auto;
  padding: 10px;
}

/* 药物基本信息 */
.medicine-basic-info {
  margin-bottom: 25px;
}

.medicine-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  border-radius: 15px;
  color: white;
}

.medicine-icon-large {
  font-size: 3rem;
  width: 80px;
  height: 80px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.medicine-header-info {
  flex: 1;
}

.medicine-name-large {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 8px;
  color: white;
}

.medicine-time-large,
.medicine-dosage-large {
  font-size: 1.1rem;
  color: rgba(255, 255, 255, 0.9);
  margin-bottom: 5px;
}

/* 用药安全审查部分 */
.safety-review-section {
  margin-bottom: 25px;
}

.review-section-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 15px;
  color: #333;
}

.review-overview {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px;
  border-radius: 12px;
  margin-bottom: 20px;
  transition: all 0.3s ease;
}

.review-overview.safe {
  background: #d1fae5;
  border-left: 4px solid #059669;
}

.review-overview.warning {
  background: #fef3c7;
  border-left: 4px solid #d97706;
}

.review-details {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.review-item {
  background: #f8f9fa;
  border-radius: 12px;
  padding: 15px;
  border-left: 4px solid transparent;
  transition: all 0.3s ease;
}

.review-item.safe {
  border-left-color: #059669;
  background: #f0fdf4;
}

.review-item.warning {
  border-left-color: #d97706;
  background: #fffbeb;
}

.review-item-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 1.1rem;
  font-weight: bold;
  margin-bottom: 10px;
  color: #333;
}

.item-icon {
  font-size: 1.2rem;
}

.item-result {
  font-size: 1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.item-desc {
  font-size: 0.95rem;
  color: #666;
  line-height: 1.4;
}

.warning-list {
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.warning-item {
  background: rgba(255, 255, 255, 0.8);
  padding: 10px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.warning-drugs,
.warning-drug {
  font-weight: bold;
  color: #333;
  font-size: 0.95rem;
}

.warning-message {
  font-size: 0.9rem;
  color: #666;
  line-height: 1.4;
}

/* 个性化用药建议 */
.personalized-advice-section {
  margin-bottom: 25px;
}

.advice-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.advice-card {
  display: flex;
  align-items: flex-start;
  gap: 15px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.advice-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.advice-icon {
  font-size: 1.5rem;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #e3f2fd;
  border-radius: 50%;
  flex-shrink: 0;
}

.advice-info {
  flex: 1;
}

.advice-title {
  font-size: 1.1rem;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

.advice-text {
  font-size: 0.95rem;
  color: #666;
  line-height: 1.4;
}

/* 用药提醒部分 */
.reminder-section {
  margin-bottom: 20px;
}

.reminder-content {
  background: #f0f9ff;
  border-radius: 12px;
  padding: 20px;
  text-align: center;
}

.reminder-text {
  font-size: 1.1rem;
  color: #333;
  margin-bottom: 15px;
  line-height: 1.5;
}

.reminder-actions {
  display: flex;
  justify-content: center;
  gap: 15px;
}

@media (max-width: 768px) {
  .medicine-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }
  
  .medicine-status {
    margin-left: 0;
    align-self: flex-end;
  }
  
  .medicine-actions {
    align-self: flex-end;
  }
  
  .medicine-header {
    flex-direction: column;
    text-align: center;
    gap: 15px;
  }
  
  .medicine-icon-large {
    width: 60px;
    height: 60px;
    font-size: 2rem;
  }
  
  .review-overview {
    flex-direction: column;
    text-align: center;
    gap: 10px;
  }
  
  .review-item {
    padding: 12px;
  }
  
  .advice-card {
    flex-direction: column;
    align-items: center;
    text-align: center;
    gap: 10px;
  }
  
  .reminder-actions {
    flex-direction: column;
    gap: 10px;
  }
}
</style>
