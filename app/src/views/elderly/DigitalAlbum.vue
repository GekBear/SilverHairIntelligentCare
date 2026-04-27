<template>
  <div class="digital-album">
    <!-- 头部 -->
    <div class="header">
      <button class="back-button" @click="goBack">
        <span class="back-icon">←</span>
      </button>
      <h1 class="title">数字相册</h1>
      <button class="slideshow-button" @click="startSlideshow">
        <span class="slideshow-icon">🎞️</span>
        <span class="slideshow-text">幻灯片</span>
      </button>
    </div>

    <!-- 照片网格 -->
    <div class="photo-grid">
      <div 
        v-for="(photo, index) in photos" 
        :key="photo.id"
        class="photo-item"
        @click="openPhoto(index)"
      >
        <div class="photo-container">
          <img :src="photo.url" :alt="photo.title" class="photo" />
          <div class="photo-overlay">
            <h3 class="photo-title">{{ photo.title }}</h3>
            <p class="photo-date">{{ photo.date }}</p>
          </div>
        </div>
        <div class="photo-info">
          <h3 class="photo-title">{{ photo.title }}</h3>
          <p class="photo-date">{{ photo.date }}</p>
          <div class="photo-actions">
            <button 
              class="action-button like-button"
              :class="{ liked: photo.liked }"
              @click.stop="toggleLike(photo.id)"
            >
              <span class="action-icon">❤️</span>
            </button>
            <button class="action-button share-button" @click.stop="sharePhoto(photo.id)">
              <span class="action-icon">📤</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 照片查看器 -->
    <div v-if="selectedPhoto !== null" class="photo-viewer">
      <div class="viewer-content">
        <button class="close-button" @click="closeViewer">
          <span class="close-icon">❌</span>
        </button>
        <div class="viewer-header">
          <h3 class="viewer-title">{{ photos[selectedPhoto].title }}</h3>
        </div>
        <div class="viewer-image">
          <img :src="photos[selectedPhoto].url" :alt="photos[selectedPhoto].title" />
        </div>
        <div class="viewer-footer">
          <p class="viewer-description">{{ photos[selectedPhoto].description }}</p>
          <p class="viewer-date">{{ photos[selectedPhoto].date }}</p>
          <div class="viewer-actions">
            <button 
              class="action-button like-button"
              :class="{ liked: photos[selectedPhoto].liked }"
              @click="toggleLike(photos[selectedPhoto].id)"
            >
              <span class="action-icon">❤️</span>
              <span class="action-text">喜欢</span>
            </button>
            <button class="action-button share-button" @click="sharePhoto(photos[selectedPhoto].id)">
              <span class="action-icon">📤</span>
              <span class="action-text">分享</span>
            </button>
          </div>
        </div>
        <div class="viewer-navigation">
          <button class="nav-button prev-button" @click="prevPhoto">
            <span class="nav-icon">←</span>
          </button>
          <button class="nav-button next-button" @click="nextPhoto">
            <span class="nav-icon">→</span>
          </button>
        </div>
      </div>
    </div>

    <!-- 空状态 -->
    <div v-if="photos.length === 0" class="empty-state">
      <div class="empty-icon">📷</div>
      <h3 class="empty-title">暂无照片</h3>
      <p class="empty-text">您的相册还没有照片，家人可以通过他们的账户上传照片给您</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const selectedPhoto = ref(null)
const isSlideshow = ref(false)
const slideshowTimer = ref(null)

const photos = ref([
  {
    id: 1,
    url: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=happy%20elderly%20couple%20with%20family%20at%20home%2C%20warm%20lighting%2C%20realistic%20photo&image_size=landscape_16_9',
    title: '家庭聚会',
    date: '2024-01-15',
    description: '和孩子们一起在家庆祝生日',
    liked: false
  },
  {
    id: 2,
    url: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=elderly%20person%20in%20park%20with%20grandchild%2C%20sunny%20day%2C%20realistic%20photo&image_size=landscape_16_9',
    title: '公园散步',
    date: '2024-02-20',
    description: '和孙子在公园玩耍',
    liked: true
  },
  {
    id: 3,
    url: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=elderly%20family%20dinner%20at%20restaurant%2C%20festive%20atmosphere%2C%20realistic%20photo&image_size=landscape_16_9',
    title: '家庭聚餐',
    date: '2024-03-10',
    description: '全家一起在餐厅吃饭',
    liked: false
  },
  {
    id: 4,
    url: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=elderly%20person%20celebrating%20birthday%20with%20cake%2C%20happy%20expression%2C%20realistic%20photo&image_size=landscape_16_9',
    title: '生日庆祝',
    date: '2024-04-05',
    description: '我的80岁生日派对',
    liked: true
  },
  {
    id: 5,
    url: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=elderly%20couple%20traveling%20to%20scenic%20location%2C%20mountains%20background%2C%20realistic%20photo&image_size=landscape_16_9',
    title: '旅行回忆',
    date: '2024-05-18',
    description: '和老伴一起去爬山',
    liked: false
  },
  {
    id: 6,
    url: 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=elderly%20person%20with%20pet%20cat%20on%20lap%2C%20cozy%20home%2C%20realistic%20photo&image_size=landscape_16_9',
    title: '宠物时光',
    date: '2024-06-22',
    description: '和我的猫咪一起',
    liked: true
  }
])

// 打开照片查看器
const openPhoto = (index) => {
  selectedPhoto.value = index
}

// 关闭照片查看器
const closeViewer = () => {
  selectedPhoto.value = null
  stopSlideshow()
}

// 上一张照片
const prevPhoto = () => {
  if (selectedPhoto.value !== null) {
    selectedPhoto.value = selectedPhoto.value <= 0 ? photos.value.length - 1 : selectedPhoto.value - 1
  }
}

// 下一张照片
const nextPhoto = () => {
  if (selectedPhoto.value !== null) {
    selectedPhoto.value = selectedPhoto.value >= photos.value.length - 1 ? 0 : selectedPhoto.value + 1
  }
}

// 切换喜欢状态
const toggleLike = (photoId) => {
  const photo = photos.value.find(p => p.id === photoId)
  if (photo) {
    photo.liked = !photo.liked
  }
}

// 分享照片
const sharePhoto = (photoId) => {
  // 模拟分享功能
  alert('照片已分享给家人')
}

// 开始幻灯片播放
const startSlideshow = () => {
  if (photos.value.length === 0) return
  
  if (selectedPhoto.value === null) {
    selectedPhoto.value = 0
  }
  
  isSlideshow.value = true
  
  // 开始幻灯片播放
  slideshowTimer.value = setInterval(() => {
    nextPhoto()
  }, 3000)
}

// 停止幻灯片播放
const stopSlideshow = () => {
  isSlideshow.value = false
  if (slideshowTimer.value) {
    clearInterval(slideshowTimer.value)
    slideshowTimer.value = null
  }
}

// 返回上一页
const goBack = () => {
  router.back()
}

// 组件卸载时清理定时器
onUnmounted(() => {
  stopSlideshow()
})
</script>

<style scoped>
.digital-album {
  min-height: 100vh;
  background: linear-gradient(to bottom, #667eea 0%, #764ba2 100%);
  padding: 20px;
  padding-bottom: 40px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 30px;
  color: white;
}

.back-button {
  background: none;
  border: none;
  color: white;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 10px;
  border-radius: 50%;
  transition: background 0.3s ease;
}

.back-button:hover {
  background: rgba(255, 255, 255, 0.1);
}

.back-icon {
  font-size: 2rem;
  font-weight: bold;
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
}

.slideshow-button {
  background: rgba(255, 255, 255, 0.1);
  border: none;
  color: white;
  padding: 10px 20px;
  border-radius: 30px;
  font-size: 1rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.slideshow-button:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: scale(1.05);
}

.slideshow-icon {
  font-size: 1.2rem;
}

.photo-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.photo-item {
  background: white;
  border-radius: 15px;
  overflow: hidden;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
  cursor: pointer;
}

.photo-item:hover {
  transform: translateY(-5px);
}

.photo-container {
  position: relative;
  aspect-ratio: 4/3;
  overflow: hidden;
}

.photo {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.photo-item:hover .photo {
  transform: scale(1.1);
}

.photo-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.8), transparent);
  color: white;
  padding: 15px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.photo-item:hover .photo-overlay {
  opacity: 1;
}

.photo-info {
  padding: 15px;
}

.photo-title {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.photo-date {
  font-size: 0.9rem;
  color: #666;
  margin-bottom: 10px;
}

.photo-actions {
  display: flex;
  gap: 10px;
}

.action-button {
  background: none;
  border: none;
  font-size: 1.2rem;
  cursor: pointer;
  padding: 8px;
  border-radius: 50%;
  transition: background 0.3s ease;
}

.action-button:hover {
  background: #f0f0f0;
}

.like-button.liked {
  color: #ff4757;
}

.photo-viewer {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.9);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.viewer-content {
  width: 90%;
  max-width: 800px;
  height: 90%;
  display: flex;
  flex-direction: column;
  position: relative;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: rgba(255, 255, 255, 0.2);
  border: none;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background 0.3s ease;
  z-index: 10;
}

.close-button:hover {
  background: rgba(255, 255, 255, 0.3);
}

.close-icon {
  font-size: 1.5rem;
  color: white;
}

.viewer-header {
  padding: 20px;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(10px);
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.viewer-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: white;
}

.viewer-image {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.viewer-image img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  border-radius: 10px;
}

.viewer-footer {
  padding: 20px;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(10px);
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  color: white;
}

.viewer-description {
  font-size: 1.1rem;
  margin-bottom: 10px;
  line-height: 1.5;
}

.viewer-date {
  font-size: 1rem;
  opacity: 0.8;
  margin-bottom: 20px;
}

.viewer-actions {
  display: flex;
  gap: 20px;
}

.viewer-actions .action-button {
  color: white;
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 1rem;
  padding: 10px 20px;
  border-radius: 30px;
  background: rgba(255, 255, 255, 0.1);
}

.viewer-actions .action-button:hover {
  background: rgba(255, 255, 255, 0.2);
}

.action-text {
  font-size: 1rem;
  font-weight: bold;
}

.viewer-navigation {
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  transform: translateY(-50%);
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
}

.nav-button {
  background: rgba(255, 255, 255, 0.2);
  border: none;
  border-radius: 50%;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background 0.3s ease;
}

.nav-button:hover {
  background: rgba(255, 255, 255, 0.3);
}

.nav-icon {
  font-size: 2rem;
  color: white;
  font-weight: bold;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100px 20px;
  text-align: center;
  color: white;
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 20px;
}

.empty-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 15px;
}

.empty-text {
  font-size: 1.1rem;
  line-height: 1.5;
  max-width: 400px;
  opacity: 0.8;
}

@media (max-width: 768px) {
  .photo-grid {
    grid-template-columns: 1fr;
  }
  
  .viewer-content {
    width: 95%;
    height: 95%;
  }
  
  .viewer-actions {
    flex-direction: column;
  }
  
  .viewer-actions .action-button {
    justify-content: center;
  }
}
</style>
