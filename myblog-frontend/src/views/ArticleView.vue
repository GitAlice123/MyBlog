<template>
  <div class="article-view">
    <!-- TOC容器 -->
    <!-- <div class="toc-container">
      <ul class="toc-list">
        <li v-for="item in tocData" :key="item.id" :class="`item-${item.tagName.charAt(1)}`" @click="anchor(item.id)">
          {{ item.textContent }}
        </li>
      </ul>
    </div> -->
    <img class="background-image" src="../assets/diarybg.jpg" alt="Background">
    <el-container>
      <el-aside class="toc-aside">
        <el-card style="max-width: 480px">
          <ul>
            <li v-for="item in tocData" :key="item.id" :class="`item-${item.tagName.charAt(1)}`"
              @click="anchor(item.id)">
              {{ item.textContent }}
            </li>
          </ul>
        </el-card>
      </el-aside>

      <el-container>
        <el-header>
          <BlogHeader />
        </el-header>

        <el-main class="content-wrapper">
          <!-- 条件渲染的编辑和删除按钮 -->
          <el-button v-if="isLoggedIn" type="primary" class="edit-article-button"
            @click="goToEditArticle">编辑文章</el-button>
          <el-button v-if="isLoggedIn" type="danger" class="delete-article-button"
            @click="deleteArticle">删除文章</el-button>
          <h1 v-if="title" class="title-font-style">{{ title }}</h1>
          <div v-if="compiledMarkdown" v-html="compiledMarkdown" class="article-font-style"></div>
          <p v-else>Loading article...</p>
        </el-main>

        <el-footer class="fixed-footer">
          <BlogFooter />
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from 'axios';
import BlogHeader from '../components/BlogHeader.vue';
import BlogFooter from '../components/BlogFooter.vue';
import { mapState } from 'vuex';
import { Marked } from 'marked';
import hljs from 'highlight.js';
import 'highlight.js/styles/github.css';
import { markedHighlight } from "marked-highlight";
import { ref, nextTick } from 'vue';

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
      articleContent: '',
      compiledMarkdown: '',
      tocData: ref([]),
    };
  },
  computed: {
    ...mapState(['isLoggedIn'])
  },
  mounted() {
    this.setupScrollSpy();
    this.fetchArticleContent();
  },
  methods: {
    initToc() {
      nextTick(() => {
        const contentArea = document.querySelector('.content-wrapper');
        const allHeadings = contentArea.querySelectorAll('.article-view h1, .article-view h2, .article-view h3');
        let headingIndex = 0;
        this.tocData = Array.from(allHeadings).map((el) => {
          let headingId = el.id || `heading-${headingIndex}`;
          if (!el.id) {
            // 如果元素没有id，创建一个新的id并设置到元素上
            el.id = headingId;
          }
          headingIndex++;
          return {
            id: headingId,
            tagName: el.tagName,
            textContent: el.textContent.trim()
          };
        });
        console.log(this.tocData);
      });
    },
    anchor(id) {
      // 滚动到指定的锚点元素
      const anchorElement = document.getElementById(id);
      if (anchorElement) {
        anchorElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    },
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
    fetchArticleContent() {
      axios.get(`http://localhost:8080/getArticleContent?idarticle=${this.articleId}`)
        .then(response => {
          if (response.data && response.data.code === 1) {
            const articleData = response.data.data;
            if (articleData) {
              this.title = articleData.title;
              this.articleContent = articleData.articleContent;
              this.compiledMarkdown = this.compileMarkdown(this.articleContent);
              this.initToc();
            }
          } else {
            console.error('Failed to fetch article content, code is not 1:', response);
          }
        })
        .catch(error => {
          console.error('Error fetching article content:', error);
        });
    },
    compileMarkdown(content) {
      // let renderer = new marked.Renderer();

      // console.log('content:', content);
      // console.log(hljs.highlightAuto(content).value);
      // marked.setOptions({
      //   renderer: renderer,
      //   highlight: function(code) {
      //     return hljs.highlightAuto(code).value;
      //   },
      //   pedantic: false,
      //   gfm: true,
      //   tables: true,
      //   breaks: false,
      //   sanitize: true,// 不忽略html标签
      //   smartLists: true,
      //   smartypants: false,
      //   xhtml: false
      // });

      // return marked(content);
      const marked = new Marked(
        markedHighlight({
          langPrefix: 'hljs language-',
          highlight(code, lang) {
            const language = hljs.getLanguage(lang) ? lang : 'plaintext';
            return hljs.highlight(code, { language }).value;
          }
        })
      );
      marked.image = (href, title, text) => {
        return `<img src="${href}" title="${title}" alt="${text}" style="max-width: 100%; height: auto; display: block;">`;
      };
      return marked.parse(content);
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
  },
};
</script>

<style scoped>
/* 限制图片大小 */
.article-font-style img {
  max-width: 100%;
  height: auto;
  display: block;
  /* 确保图片不会破坏布局 */
}

@font-face {
  font-family: 'AaDongQiChangYueYangLouJi';
  src: url('../assets/AaDongQiChangYueYangLouJi-2.ttf') format('truetype');
}

.toc-aside {
  position: fixed;
  top: 50%;
  left: 0;
  transform: translateY(-50%);
  width: 300px;
  /* 卡片的最大宽度 */
  margin: 0;
  /* 移除任何外边距 */
  /* 确保侧边栏不会遮挡页面内容 */
  z-index: 1000;
  /* 确保侧边栏在页面内容上方 */
  font-family: 'AaDongQiChangYueYangLouJi';
  font-size: 20px;
  /* 背景颜色透明度 */
  background-color: rgba(255, 255, 255, 0);
}

.toc-container {
  position: fixed;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 200px;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 100;
}

.toc-list {
  list-style-type: none;
  padding: 0;
}

.toc-list li a {
  display: block;
  padding: 5px 0;
  text-decoration: none;
  color: #333;
}

.item-1 {
  font-weight: bold;
  padding-left: 0;
}

.item-2 {
  padding-left: 10px;
}

.item-3 {
  padding-left: 20px;
}

.el-header,
.el-footer {
  transition: visibility 0.5s, opacity 0.5s ease;
  opacity: 0;
  visibility: hidden;
  /* 初始设为隐藏 */
  position: fixed;
  width: 100%;
  z-index: 10;
}

.el-header {
  top: 0;
  /* 初始时在视口顶部 */
  padding-top: 100px;
  /* 预留空间，避免覆盖内容 */
}

.el-footer {
  bottom: 0;
  /* 初始时在视口底部 */
  padding-bottom: 100px;
  /* 预留空间，避免覆盖内容 */
}

/* 滚动时显示Header和Footer */
.is-scrolling .el-header,
.is-scrolling .el-footer {
  opacity: 1;
  visibility: visible;
  /* 滚动时设置为可见 */
}

.content-wrapper {
  padding: 100px;
  /* 根据需要调整内边距 */
  max-width: 600x;
  /* 设置最大宽度，防止内容过宽 */
  box-sizing: border-box;
  /* 边框计算在宽度内 */
}



.title-font-style {
  font-family: 'AaDongQiChangYueYangLouJi';
  font-size: 40px;
}

.article-font-style {
  font-family: 'AaDongQiChangYueYangLouJi';
  /* 所有字体都放大 */
  font-size: 25px;
}

.article-view {
  position: relative;
  z-index: 1;
  min-height: 100vh;
  padding-top: 0px;
  overflow: hidden;
  background: rgba(255, 255, 255, 0.2);
  padding-left: 320px;
  /* 根据实际情况调整 */
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

pre code {
  background-color: #f8f8f8;
  border-radius: 8px;
  padding: 10px;
}
</style>