<template>
  <div class="community">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="header-back" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </div>
      <div class="header-title">老年社区</div>
      <div class="header-right">
        <el-button type="primary" size="small" @click="showCreatePostDialog">
          <el-icon><Plus /></el-icon>
          发布
        </el-button>
      </div>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchQuery"
        placeholder="搜索社区内容"
        prefix-icon="Search"
        @keyup.enter="searchPosts"
      >
        <template #append>
          <el-button @click="searchPosts"><el-icon><Search /></el-icon></el-button>
        </template>
      </el-input>
    </div>

    <!-- 分类标签 -->
    <div class="category-tabs">
      <el-tabs v-model="activeCategory" @tab-click="filterPosts">
        <el-tab-pane label="全部" name="all">全部</el-tab-pane>
        <el-tab-pane label="生活分享" name="life">生活分享</el-tab-pane>
        <el-tab-pane label="健康知识" name="health">健康知识</el-tab-pane>
        <el-tab-pane label="兴趣爱好" name="hobby">兴趣爱好</el-tab-pane>
        <el-tab-pane label="求助问答" name="help">求助问答</el-tab-pane>
      </el-tabs>
    </div>

    <!-- 热门话题 -->
    <div class="hot-topics">
      <h3 class="section-title">热门话题</h3>
      <div class="topic-list">
        <div class="topic-item" v-for="(topic, index) in hotTopics" :key="index">
          <div class="topic-rank">{{ index + 1 }}</div>
          <div class="topic-content">
            <div class="topic-title">{{ topic.title }}</div>
            <div class="topic-stats">{{ topic.discussions }} 讨论 · {{ topic.participants }} 参与</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 推荐帖子 -->
    <div class="recommended-posts" v-if="activeCategory === 'all'">
      <h3 class="section-title">推荐帖子</h3>
      <div class="post-list">
        <div class="post-item" v-for="post in recommendedPosts" :key="post.id" @click="viewPostDetail(post)">
          <div class="post-header">
            <div class="user-info">
              <el-avatar size="small">{{ post.author.charAt(0) }}</el-avatar>
              <div class="user-details">
                <div class="username">{{ post.author }}</div>
                <div class="post-time">{{ post.time }}</div>
              </div>
            </div>
            <div class="post-category" :class="post.category">{{ getCategoryName(post.category) }}</div>
          </div>
          <div class="post-content">
            <h3 class="post-title">{{ post.title }}</h3>
            <p class="post-text">{{ post.content }}</p>
            <div class="post-media" v-if="post.media.length > 0">
              <div class="media-grid">
                <img v-for="(item, index) in post.media" :key="index" :src="item" class="media-item" />
              </div>
            </div>
          </div>
          <div class="post-footer">
            <div class="post-stats">
              <div class="stat-item" @click.stop="likePost(post)">
                <el-icon v-if="post.liked"><Star /></el-icon>
                <el-icon v-else><StarFilled /></el-icon>
                <span>{{ post.likes }}</span>
              </div>
              <div class="stat-item" @click.stop="showComments(post)">
                <el-icon><ChatDotRound /></el-icon>
                <span>{{ post.comments }}</span>
              </div>
              <div class="stat-item">
                <el-icon><Share /></el-icon>
                <span>{{ post.shares }}</span>
              </div>
              <div class="stat-item" @click.stop="bookmarkPost(post)">
                <el-icon v-if="post.bookmarked"><Collection /></el-icon>
                <el-icon v-else><Collection /></el-icon>
                <span>{{ post.bookmarks }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 帖子列表 -->
    <div class="post-list">
      <h3 class="section-title">{{ getCategoryName(activeCategory) }}帖子</h3>
      <div class="post-item" v-for="post in filteredPosts" :key="post.id" @click="viewPostDetail(post)">
        <div class="post-header">
          <div class="user-info">
            <el-avatar size="small">{{ post.author.charAt(0) }}</el-avatar>
            <div class="user-details">
              <div class="username">{{ post.author }}</div>
              <div class="post-time">{{ post.time }}</div>
            </div>
          </div>
          <div class="post-category" :class="post.category">{{ getCategoryName(post.category) }}</div>
        </div>
        <div class="post-content">
          <h3 class="post-title">{{ post.title }}</h3>
          <p class="post-text">{{ post.content }}</p>
          <div class="post-media" v-if="post.media.length > 0">
            <div class="media-grid">
              <img v-for="(item, index) in post.media" :key="index" :src="item" class="media-item" />
            </div>
          </div>
        </div>
        <div class="post-footer">
          <div class="post-stats">
            <div class="stat-item" @click.stop="likePost(post)">
              <el-icon v-if="post.liked"><Star /></el-icon>
              <el-icon v-else><StarFilled /></el-icon>
              <span>{{ post.likes }}</span>
            </div>
            <div class="stat-item" @click.stop="showComments(post)">
              <el-icon><ChatDotRound /></el-icon>
              <span>{{ post.comments }}</span>
            </div>
            <div class="stat-item">
              <el-icon><Share /></el-icon>
              <span>{{ post.shares }}</span>
            </div>
            <div class="stat-item" @click.stop="bookmarkPost(post)">
                <el-icon v-if="post.bookmarked"><Collection /></el-icon>
                <el-icon v-else><Collection /></el-icon>
                <span>{{ post.bookmarks }}</span>
              </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 发布帖子对话框 -->
    <el-dialog
      v-model="createDialogVisible"
      title="发布帖子"
      width="80%"
      center
    >
      <div class="create-post-form">
        <el-form :model="newPost" label-width="80px">
          <el-form-item label="标题">
            <el-input v-model="newPost.title" placeholder="请输入帖子标题" />
          </el-form-item>
          <el-form-item label="分类">
            <el-select v-model="newPost.category" placeholder="请选择分类">
              <el-option label="生活分享" value="life" />
              <el-option label="健康知识" value="health" />
              <el-option label="兴趣爱好" value="hobby" />
              <el-option label="求助问答" value="help" />
            </el-select>
          </el-form-item>
          <el-form-item label="内容">
            <el-input
              v-model="newPost.content"
              type="textarea"
              rows="5"
              placeholder="请输入帖子内容"
            />
          </el-form-item>
          <el-form-item label="图片">
            <el-upload
              class="upload-demo"
              action="#"
              :on-change="handleImageUpload"
              :auto-upload="false"
              multiple
            >
              <el-button type="primary">
                <el-icon><Plus /></el-icon>
                选择图片
              </el-button>
              <template #tip>
                <div class="el-upload__tip">
                  支持多图上传，最多5张
                </div>
              </template>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="createDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="createPost">发布</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 评论对话框 -->
    <el-dialog
      v-model="commentDialogVisible"
      title="评论"
      width="80%"
    >
      <div class="comments-section">
        <div class="comment-input">
          <el-input
            v-model="newComment"
            placeholder="写下你的评论"
            @keyup.enter="addComment"
          >
            <template #append>
              <el-button @click="addComment">发送</el-button>
            </template>
          </el-input>
        </div>
        <div class="comment-list">
          <div class="comment-item" v-for="(comment, index) in currentPostComments" :key="index">
            <div class="comment-header">
              <el-avatar size="small">{{ comment.author.charAt(0) }}</el-avatar>
              <div class="comment-info">
                <div class="comment-author">{{ comment.author }}</div>
                <div class="comment-time">{{ comment.time }}</div>
              </div>
            </div>
            <div class="comment-content">{{ comment.content }}</div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowLeft, Plus, Search, Star, StarFilled, ChatDotRound, Share, Collection } from '@element-plus/icons-vue'

const router = useRouter()

// 状态管理
const searchQuery = ref('')
const activeCategory = ref('all')
const createDialogVisible = ref(false)
const commentDialogVisible = ref(false)
const currentPost = ref(null)
const currentPostComments = ref([])
const newComment = ref('')

// 热门话题数据
const hotTopics = ref([
  { title: '如何保持健康的生活方式', discussions: 156, participants: 89 },
  { title: '老年人如何使用智能手机', discussions: 128, participants: 76 },
  { title: '分享我的退休生活', discussions: 98, participants: 54 },
  { title: '健康饮食小贴士', discussions: 87, participants: 45 },
  { title: '老年人旅游攻略', discussions: 76, participants: 38 }
])

// 推荐帖子数据
const recommendedPosts = ref([
  {
    id: 5,
    author: '孙爷爷',
    time: '1天前',
    category: 'health',
    title: '老年人如何预防骨质疏松',
    content: '骨质疏松是老年人常见的疾病，我想分享一些预防骨质疏松的方法，包括饮食、运动等方面的建议。希望对大家有所帮助。',
    media: [],
    likes: 56,
    comments: 23,
    shares: 8,
    bookmarks: 15,
    liked: false,
    bookmarked: false
  },
  {
    id: 6,
    author: '周奶奶',
    time: '2天前',
    category: 'hobby',
    title: '我的手工编织作品',
    content: '退休后开始学习手工编织，现在已经能织出各种漂亮的毛衣和围巾了。分享一些我的作品，希望大家喜欢。',
    media: [
      'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=handmade%20knitting%20works&image_size=landscape_4_3'
    ],
    likes: 48,
    comments: 18,
    shares: 12,
    bookmarks: 20,
    liked: true,
    bookmarked: false
  }
])

// 新帖子数据
const newPost = ref({
  title: '',
  content: '',
  category: 'life',
  media: []
})

// 模拟帖子数据
const posts = ref([
  {
    id: 1,
    author: '张爷爷',
    time: '2小时前',
    category: 'life',
    title: '今天天气真好，去公园散步了',
    content: '今天阳光明媚，和老伴一起去公园散步，看到了很多老朋友，心情特别好。公园里的花开得真漂亮，拍了几张照片分享给大家。',
    media: [
      'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=elderly%20couple%20walking%20in%20park%20with%20flowers&image_size=landscape_4_3',
      'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=beautiful%20flowers%20in%20park&image_size=landscape_4_3'
    ],
    likes: 25,
    comments: 8,
    shares: 3,
    bookmarks: 5,
    liked: false,
    bookmarked: false
  },
  {
    id: 2,
    author: '李奶奶',
    time: '5小时前',
    category: 'health',
    title: '分享一个降血压的小妙招',
    content: '最近我的血压有点高，医生建议我每天喝一杯芹菜汁，坚持了一个月，血压真的降下来了。分享给有需要的朋友们，希望对大家有帮助。',
    media: [],
    likes: 42,
    comments: 15,
    shares: 10,
    bookmarks: 12,
    liked: true,
    bookmarked: false
  },
  {
    id: 3,
    author: '王爷爷',
    time: '昨天',
    category: 'hobby',
    title: '我的书法作品',
    content: '练习书法已经有20年了，最近写了一幅作品，大家看看怎么样？书法是一种很好的修身养性的方式，推荐给大家。',
    media: [
      'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=chinese%20calligraphy%20work&image_size=portrait_4_3'
    ],
    likes: 38,
    comments: 12,
    shares: 5,
    bookmarks: 8,
    liked: false,
    bookmarked: true
  },
  {
    id: 4,
    author: '赵奶奶',
    time: '3天前',
    category: 'help',
    title: '求助：手机怎么连接WiFi？',
    content: '孩子们给我买了新手机，但是我不知道怎么连接家里的WiFi，有哪位朋友能教教我吗？谢谢大家了。',
    media: [],
    likes: 15,
    comments: 20,
    shares: 2,
    bookmarks: 3,
    liked: false,
    bookmarked: false
  }
])

// 过滤帖子
const filteredPosts = computed(() => {
  if (activeCategory.value === 'all') {
    return posts.value
  } else {
    return posts.value.filter(post => post.category === activeCategory.value)
  }
})

// 导航方法
const goBack = () => {
  router.back()
}

const viewPostDetail = (post) => {
  // 跳转到帖子详情页面
  console.log('查看帖子详情:', post.id)
  // 这里可以实现路由跳转
}

// 搜索帖子
const searchPosts = () => {
  console.log('搜索帖子:', searchQuery.value)
  // 这里可以实现搜索逻辑
}

// 过滤帖子
const filterPosts = () => {
  console.log('过滤帖子:', activeCategory.value)
  // 这里可以实现过滤逻辑
}

// 点赞帖子
const likePost = (post) => {
  post.liked = !post.liked
  post.likes += post.liked ? 1 : -1
}

// 收藏帖子
const bookmarkPost = (post) => {
  post.bookmarked = !post.bookmarked
  post.bookmarks += post.bookmarked ? 1 : -1
}

// 显示评论
const showComments = (post) => {
  currentPost.value = post
  // 模拟评论数据
  currentPostComments.value = [
    {
      author: '刘爷爷',
      time: '1小时前',
      content: '写得真好，我也想试试！'
    },
    {
      author: '陈奶奶',
      time: '30分钟前',
      content: '感谢分享，很有帮助！'
    }
  ]
  commentDialogVisible.value = true
}

// 添加评论
const addComment = () => {
  if (newComment.value.trim()) {
    currentPostComments.value.push({
      author: '我',
      time: '刚刚',
      content: newComment.value
    })
    newComment.value = ''
    // 这里可以实现评论提交逻辑
  }
}

// 显示发布帖子对话框
const showCreatePostDialog = () => {
  createDialogVisible.value = true
}

// 处理图片上传
const handleImageUpload = (file) => {
  // 这里可以实现图片上传逻辑
  console.log('上传图片:', file)
  // 模拟上传成功
  newPost.value.media.push('https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=test%20image&image_size=square')
}

// 创建帖子
const createPost = () => {
  if (newPost.value.title && newPost.value.content) {
    // 这里可以实现帖子发布逻辑
    console.log('发布帖子:', newPost.value)
    // 模拟发布成功
    posts.value.unshift({
      id: posts.value.length + 1,
      author: '我',
      time: '刚刚',
      category: newPost.value.category,
      title: newPost.value.title,
      content: newPost.value.content,
      media: newPost.value.media,
      likes: 0,
      comments: 0,
      shares: 0,
      bookmarks: 0,
      liked: false,
      bookmarked: false
    })
    createDialogVisible.value = false
    // 重置表单
    newPost.value = {
      title: '',
      content: '',
      category: 'life',
      media: []
    }
  }
}

// 获取分类名称
const getCategoryName = (category) => {
  const categoryMap = {
    life: '生活分享',
    health: '健康知识',
    hobby: '兴趣爱好',
    help: '求助问答'
  }
  return categoryMap[category] || category
}

onMounted(() => {
  // 初始化数据
  console.log('加载社区数据')
})
</script>

<style scoped>
.community {
  min-height: 100vh;
  background: linear-gradient(to bottom, #ffd93d 0%, #ff9e43 100%);
}

/* 顶部导航栏 */
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 60px;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  padding: 0 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-back {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  transition: all 0.3s ease;
}

.header-back:hover {
  background: rgba(255, 255, 255, 0.3);
}

.header-back .el-icon {
  font-size: 20px;
  color: white;
}

.header-title {
  font-size: 18px;
  font-weight: bold;
  color: white;
}

.header-right {
  display: flex;
  align-items: center;
}

.header-right .el-button {
  font-size: 14px;
  padding: 8px 16px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.3);
  color: white;
}

.header-right .el-button:hover {
  background: rgba(255, 255, 255, 0.3);
}

/* 搜索栏 */
.search-bar {
  padding: 15px 20px;
  background: rgba(255, 255, 255, 0.9);
  margin-bottom: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.search-bar .el-input {
  font-size: 16px;
  height: 48px;
  border-radius: 24px;
}

.search-bar .el-input__wrapper {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

/* 分类标签 */
.category-tabs {
  background: rgba(255, 255, 255, 0.9);
  margin-bottom: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.category-tabs .el-tabs__nav {
  height: 56px;
}

.category-tabs .el-tabs__item {
  font-size: 16px;
  height: 56px;
  line-height: 56px;
  padding: 0 20px;
}

.category-tabs .el-tabs__active-bar {
  height: 4px;
  background-color: #ff6b6b;
}

/* 热门话题 */
.hot-topics {
  background: rgba(255, 255, 255, 0.9);
  margin: 0 20px 15px;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.section-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.section-title::before {
  content: '';
  width: 4px;
  height: 20px;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  border-radius: 2px;
}

.topic-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.topic-item {
  display: flex;
  align-items: center;
  padding: 12px;
  background: #f8f9fa;
  border-radius: 8px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.topic-item:hover {
  background: #e9ecef;
  transform: translateX(5px);
}

.topic-rank {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: linear-gradient(135deg, #ffd93d 0%, #ff9e43 100%);
  color: white;
  font-size: 16px;
  font-weight: bold;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  flex-shrink: 0;
}

.topic-content {
  flex: 1;
}

.topic-title {
  font-size: 16px;
  font-weight: bold;
  color: #333;
  margin-bottom: 4px;
  line-height: 1.4;
}

.topic-stats {
  font-size: 14px;
  color: #999;
}

/* 推荐帖子 */
.recommended-posts {
  margin: 0 20px 20px;
}

/* 帖子列表 */
.post-list {
  padding: 0 20px 120px;
}

.post-item {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 15px;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: all 0.3s ease;
}

.post-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.12);
}

/* 帖子头部 */
.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-info .el-avatar {
  width: 48px;
  height: 48px;
  font-size: 20px;
  font-weight: bold;
  background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%);
}

.user-details {
  flex: 1;
}

.username {
  font-size: 16px;
  font-weight: bold;
  color: #333;
  margin-bottom: 4px;
}

.post-time {
  font-size: 14px;
  color: #999;
}

.post-category {
  padding: 4px 12px;
  border-radius: 16px;
  font-size: 14px;
  font-weight: bold;
}

.post-category.life {
  background-color: #ffe6e6;
  color: #ff6b6b;
}

.post-category.health {
  background-color: #f6ffed;
  color: #52c41a;
}

.post-category.hobby {
  background-color: #fff7e6;
  color: #fa8c16;
}

.post-category.help {
  background-color: #fff2f0;
  color: #f5222d;
}

/* 帖子内容 */
.post-content {
  margin-bottom: 16px;
}

.post-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 12px;
  line-height: 1.4;
}

.post-text {
  font-size: 16px;
  color: #666;
  line-height: 1.6;
  margin-bottom: 16px;
}

/* 媒体内容 */
.post-media {
  margin-top: 16px;
}

.media-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
}

.media-item {
  width: 100%;
  aspect-ratio: 1;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.media-item:hover {
  transform: scale(1.02);
}

/* 帖子底部 */
.post-footer {
  border-top: 2px solid #f0f0f0;
  padding-top: 16px;
}

.post-stats {
  display: flex;
  gap: 24px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  color: #666;
  cursor: pointer;
  transition: all 0.3s ease;
  padding: 8px 12px;
  border-radius: 20px;
}

.stat-item:hover {
  color: #ff6b6b;
  background: rgba(255, 107, 107, 0.1);
}

.stat-item .el-icon {
  font-size: 18px;
}

/* 发布帖子表单 */
.create-post-form {
  padding: 20px 0;
}

.create-post-form .el-form-item {
  margin-bottom: 20px;
}

.create-post-form .el-input,
.create-post-form .el-select,
.create-post-form .el-textarea {
  font-size: 16px;
}

.create-post-form .el-textarea {
  height: 120px;
}

/* 评论部分 */
.comments-section {
  max-height: 400px;
  overflow-y: auto;
}

.comment-input {
  margin-bottom: 24px;
}

.comment-input .el-input {
  font-size: 16px;
  height: 56px;
  border-radius: 28px;
}

.comment-item {
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f0;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.comment-header .el-avatar {
  width: 40px;
  height: 40px;
  font-size: 18px;
  font-weight: bold;
  background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%);
}

.comment-info {
  flex: 1;
}

.comment-author {
  font-size: 16px;
  font-weight: bold;
  color: #333;
  margin-bottom: 4px;
}

.comment-time {
  font-size: 14px;
  color: #999;
}

.comment-content {
  font-size: 16px;
  color: #666;
  line-height: 1.6;
}

/* 对话框样式 */
.el-dialog {
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.el-dialog__header {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
  color: white;
  border-radius: 16px 16px 0 0;
  padding: 20px;
}

.el-dialog__title {
  font-size: 18px;
  font-weight: bold;
  color: white;
}

.el-dialog__body {
  padding: 24px;
}

.el-dialog__footer {
  padding: 20px;
  border-top: 1px solid #f0f0f0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header {
    padding: 0 16px;
  }
  
  .search-bar {
    padding: 12px 16px;
  }
  
  .post-list {
    padding: 0 16px 120px;
  }
  
  .post-item {
    padding: 16px;
  }
  
  .post-title {
    font-size: 16px;
  }
  
  .post-text {
    font-size: 14px;
  }
  
  .media-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 8px;
  }
  
  .post-stats {
    gap: 16px;
  }
  
  .stat-item {
    font-size: 14px;
  }
  
  .category-tabs .el-tabs__item {
    font-size: 14px;
    padding: 0 16px;
  }
}
</style>