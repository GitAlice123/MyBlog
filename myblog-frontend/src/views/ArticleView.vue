<template>
  <div class="article-view">
    <img class="background-image" src="../assets/bg3.jpg" alt="Background">
    <el-container>
      <el-header>
        <BlogHeader />
      </el-header>

      <el-main>
        <!-- 条件渲染的编辑和删除按钮 -->
        <el-button v-if="isLoggedIn" type="primary" class="edit-article-button" @click="goToEditArticle">编辑文章</el-button>
        <el-button v-if="isLoggedIn" type="danger" class="delete-article-button" @click="deleteArticle">删除文章</el-button>
        <h1 v-if="title">{{ title }}</h1>
        <!-- 使用v-html渲染编译后的Markdown内容 -->
        <div v-if="articleContent" v-html="compiledMarkdown"></div>
        <p v-else>Loading article...</p>
      </el-main>

      <el-footer class="fixed-footer">
        <BlogFooter />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import axios from 'axios';
import BlogHeader from '../components/BlogHeader.vue';
import BlogFooter from '../components/BlogFooter.vue';
import { mapState } from 'vuex';
import { marked } from 'marked';
import 'highlight.js/styles/github.css'; // 引入highlight.js的样式
import hljs from 'highlight.js';

export default {
  name: 'ArticleView',
  components: {
    BlogHeader,
    BlogFooter
  },
  data() {
    return {
      articleId: this.$route.params.idarticle,
      title: '',
      articleContent: ''
    };
  },
  computed: {
    compiledMarkdown() {
      return marked(this.articleContent, {
        highlight: (code, lang) => {
          // 如果有语言标识，则尝试高亮，否则使用plaintext
          return lang ? hljs.highlight(code, { language: lang }).value : code;
        }
      });
    },
    ...mapState(['isLoggedIn'])
  },
  mounted() {
    this.fetchArticleContent();
  },
  methods: {
    fetchArticleContent() {
      axios.get(`http://localhost:8080/getArticleContent?idarticle=${this.articleId}`)
        .then(response => {
          if (response.data && response.data.code === 1) {
            const articleData = response.data.data;
            if (articleData) {
              this.title = articleData.title;
              this.articleContent = articleData.articleContent.replace(/\\n/g, '\n');
            }
          } else {
            console.error('Failed to fetch article content, code is not 1:', response);
          }
        })
        .catch(error => {
          console.error('Error fetching article content:', error);
        });
    },
    goToEditArticle() {
      if (this.isLoggedIn) {
        this.$router.push({ name: 'ArticleEdit', params: { idarticle: this.articleId } });
      } else {
        this.$message.error('您需要登录才能编辑文章！');
      }
    },
    deleteArticle() {
      if (this.isLoggedIn) {
        this.$confirm('此操作将永久删除文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.get(`http://localhost:8080/deleteArticle?idarticle=${this.articleId}`)
            .then(() => {
              this.$message.success('文章删除成功');
              this.$router.push('/techArticleMenu');
            })
            .catch(error => {
              console.error('Error deleting article:', error);
              this.$message.error('文章删除失败');
            });
        }).catch(() => {
          this.$message.info('已取消删除');
        });
      } else {
        this.$message.error('请先登录');
      }
    }
  }
};
</script>

<style scoped>
.article-view {
  position: relative;
  z-index: 1;
  min-height: 100vh;
  padding-top: 0px;
  overflow: hidden;
  background: rgba(255, 255, 255, 0.2);
  /* 设置透明度为 50% 的白色背景 */
}

/* 确保布局容器不超出视口 */
.el-container {
  position: relative;
  z-index: 2;
  /* 确保容器内容在背景图片和半透明背景之上 */
}

/* 添加一些样式来定位编辑按钮 */
.edit-article-button {
  float: right;
  /* 按钮靠右显示 */
  margin: 10px;
  /* 与header其他元素保持一定间距 */
}

.edit-article-button:hover {
  /* 鼠标悬停时改变背景颜色 */
  background-color: #409EFF;
  float: right;
  /* 按钮靠右显示 */
  margin: 10px;
  /* 与header其他元素保持一定间距 */
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

html,
body {
  margin: 0;
  padding: 0;
}
</style>