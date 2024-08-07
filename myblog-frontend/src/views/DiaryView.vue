<template>
  <div class="diary-view">
    <img class="background-image" src="../assets/diarybg.jpg" alt="Background">
    <el-container>
      <el-header>
        <BlogHeader />

      </el-header>

      <el-main class="content-wrapper">
        <!-- 添加一个条件渲染的按钮，仅当用户登录时显示 -->
        <el-button v-if="isLoggedIn" type="primary" class="edit-diary-button" @click="goToEditdiary">编辑文章</el-button>
        <!-- 删除文章按钮，仅当用户登录时显示 -->
        <el-button v-if="isLoggedIn" type="danger" class="delete-diary-button" @click="deletediary">删除文章</el-button>
        <h1 v-if="title" class="title-font-style">{{ title }}</h1>
        <div v-if="diaryContent" v-html="compiledMarkdown" class="article-font-style"></div>
        <p v-else>Loading diary...</p>


      </el-main>

      <el-footer class="fixed-footer">
        <BlogFooter />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
// import axios from 'axios';
import BlogHeader from '../components/BlogHeader.vue';
import BlogFooter from '../components/BlogFooter.vue';
import { mapState } from 'vuex'; // 确保引入了mapState
import { Marked } from 'marked';
import hljs from 'highlight.js';
import 'highlight.js/styles/github.css';
import { markedHighlight } from "marked-highlight";

export default {
  name: 'diaryView',
  components: {
    BlogHeader,
    BlogFooter
  },
  data() {
    return {
      diaryId: this.$route.params.iddiary, // 初始化diaryId
      title: '',
      diaryContent: ''
    };
  },
  computed: {
    // 使用markdown-it编译markdown文本
    compiledMarkdown() {
      const marked = new Marked(
        markedHighlight({
          langPrefix: 'hljs language-',
          highlight(code, lang) {
            const language = hljs.getLanguage(lang) ? lang : 'plaintext';
            return hljs.highlight(code, { language }).value;
          }
        })
      );
      return marked.parse(this.diaryContent);
    },
    // 从Vuex映射isLoggedIn状态
    ...mapState(['isLoggedIn'])
  },
  mounted() {
    this.fetchdiaryContent();
    this.setupScrollSpy();
  },
  watch: {
    // 监听路由变化，重新加载文章内容
    '$route'(to, from) {
      if (to.params.iddiary !== from.params.iddiary) {
        this.fetchdiaryContent();
      }
    }
  },
  methods: {
    setupScrollSpy() {
      const htmlElement = document.documentElement;

      let scrollTimeout; // 用于保存setTimeout的返回值

      const handleScroll = () => {
        // 清除之前的定时器
        clearTimeout(scrollTimeout);
        // 立即显示header和footer
        htmlElement.classList.add('is-scrolling');
        // 设置新的定时器，3秒后隐藏header和footer
        scrollTimeout = setTimeout(() => {
          htmlElement.classList.remove('is-scrolling');
        }, 3000);
      };

      window.addEventListener('scroll', handleScroll, false);
    },
    fetchdiaryContent() {
      // axios.get(`http://localhost:8080/api/diary/getdiaryContent?iddiary=${this.diaryId}`)
      this.$http.get(`/diary/getdiaryContent?iddiary=${this.diaryId}`)
        .then(response => {
          if (response.data && response.data.code === 1) {
            const diaryData = response.data.data;
            if (diaryData) {
              this.title = diaryData.title;
              this.diaryContent = diaryData.diaryContent;
              this.diaryContent = this.diaryContent.replace(/\\n/g, '\n');
            } else {
              console.error('No diary data found in response:', response);
            }
          } else {
            console.error('Failed to fetch diary content, code is not 1:', response);
          }
        })
        .catch(error => {
          console.error('Error fetching diary content:', error);
        });
    },
    goToEditdiary() {
      if (this.isLoggedIn) {
        this.$router.push({ name: 'diaryEdit', params: { iddiary: this.diaryId } });
      } else {
        this.$message.error('您需要登录才能编辑文章！');
      }
    },
    // 删除文章的方法
    deletediary() {
      if (this.isLoggedIn) {
        // 确认删除的逻辑
        this.$confirm('此操作将永久删除文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 发送请求到后端删除文章
          // axios.get(`http://localhost:8080/api/diary/deletediary?iddiary=${this.diaryId}`)
          this.$http.get(`/diary/deletediary?iddiary=${this.diaryId}`)
            .then(() => {
              // 删除成功后，返回目录页面
              this.$message.success('文章删除成功');
              this.$router.push('/diaryMenu'); // 确保'diaryList'是目录页面的路由名称
            })
            .catch(error => {
              console.error('Error deleting diary:', error);
              this.$message.error('文章删除失败');
            });
        }).catch(() => {
          this.$message.info('已取消删除');
        });
      } else {
        this.$message.error('请先登录');
      }
    },
  }
};
</script>

<style scoped>
.el-header,
.el-footer {
  transition: visibility 0.5s, opacity 0.5s ease;
  opacity: 0;
  visibility: hidden; /* 初始设为隐藏 */
  position: fixed;
  width: 100%;
  z-index: 10;
}

.el-header {
  top: 0; /* 初始时在视口顶部 */
  padding-top: 100px; /* 预留空间，避免覆盖内容 */
}

.el-footer {
  bottom: 0; /* 初始时在视口底部 */
  padding-bottom: 100px; /* 预留空间，避免覆盖内容 */
}

/* 滚动时显示Header和Footer */
.is-scrolling .el-header,
.is-scrolling .el-footer {
  opacity: 1;
  visibility: visible; /* 滚动时设置为可见 */
}
/* 添加一个新类用于包裹主要内容 */
.content-wrapper {
  padding: 100px; /* 根据需要调整内边距 */
  max-width: 600x; /* 设置最大宽度，防止内容过宽 */
  box-sizing: border-box; /* 边框计算在宽度内 */
}

@font-face {
  font-family: 'AaDongQiChangYueYangLouJi';
  src: url('../assets/AaDongQiChangYueYangLouJi-2.ttf') format('truetype');
}

.title-font-style{
  font-family: 'AaDongQiChangYueYangLouJi';
  font-size: 40px;
}

.article-font-style {
  font-family: 'AaDongQiChangYueYangLouJi';
  /* 所有字体都放大 */
  font-size: 25px;
}

/* 添加一些样式来定位编辑按钮 */
.edit-diary-button {
  float: right;
  /* 按钮靠右显示 */
  margin: 10px;
  /* 与header其他元素保持一定间距 */
}

.edit-diary-button:hover {
  /* 鼠标悬停时改变背景颜色 */
  background-color: #409EFF;
  float: right;
  /* 按钮靠右显示 */
  margin: 10px;
  /* 与header其他元素保持一定间距 */
}

html,
body {
  /* margin: 0;
  padding: 0; */
  /* 左右两侧各填充一点，不要文字都顶在边上 */
  margin: 10px;
  padding: 10px;
}

.background-image {
  display: block;
  /* 设置为块级元素 */
  position: fixed;
  /* 固定位置，不随滚动条滚动 */
  top: 0;
  left: 0;
  width: 100%;
  /* 覆盖整个视口宽度 */
  height: 100%;
  /* 覆盖整个视口高度 */
  object-fit: cover;
  /* 确保图片覆盖整个元素 */
  z-index: -1;
  /* 确保图片在所有内容之下 */
  /* 透明度 */
  opacity: 0.5;
}
</style>