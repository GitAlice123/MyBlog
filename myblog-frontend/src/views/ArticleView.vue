<template>
  <div class="article-view">
    <el-container>
      <el-header>
        <BlogHeader />

      </el-header>

      <el-main>
        <!-- 添加一个条件渲染的按钮，仅当用户登录时显示 -->
        <el-button v-if="isLoggedIn" type="primary" class="edit-article-button"
          @click="goToEditArticle">编辑文章</el-button>
        <!-- 删除文章按钮，仅当用户登录时显示 -->
        <el-button v-if="isLoggedIn" type="danger" class="delete-article-button" @click="deleteArticle">删除文章</el-button>
        <h1 v-if="title">{{ title }}</h1>
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
import MarkdownIt from 'markdown-it';
import BlogHeader from '../components/BlogHeader.vue';
import BlogFooter from '../components/BlogFooter.vue';
import { mapState } from 'vuex'; // 确保引入了mapState

export default {
  name: 'ArticleView',
  components: {
    BlogHeader,
    BlogFooter
  },
  data() {
    return {
      articleId: this.$route.params.idarticle, // 初始化articleId
      title: '',
      articleContent: ''
    };
  },
  computed: {
    // 使用markdown-it编译markdown文本
    compiledMarkdown() {
      const md = new MarkdownIt({
        html: true,
        xhtmlOut: false,
        breaks: true,
        langPrefix: 'language-',
        linkify: false,
        typographer: true,
        quotes: '“”‘’',
        highlight: function (/*str, lang*/) { return ''; }
      });
      return md.render(this.articleContent);
    },
    // 从Vuex映射isLoggedIn状态
    ...mapState(['isLoggedIn'])
  },
  mounted() {
    this.fetchArticleContent();
  },
  watch: {
    // 监听路由变化，重新加载文章内容
    '$route'(to, from) {
      if (to.params.idarticle !== from.params.idarticle) {
        this.fetchArticleContent();
      }
    }
  },
  methods: {
    fetchArticleContent() {
      axios.get(`http://localhost:8080/getArticleContent?idarticle=${this.articleId}`)
        .then(response => {
          if (response.data && response.data.code === 1) {
            const articleData = response.data.data;
            if (articleData) {
              this.title = articleData.title;
              this.articleContent = articleData.articleContent;
              this.articleContent = this.articleContent.replace(/\\n/g, '\n');
            } else {
              console.error('No article data found in response:', response);
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
    // 删除文章的方法
    deleteArticle() {
      if (this.isLoggedIn) {
        // 确认删除的逻辑
        this.$confirm('此操作将永久删除文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 发送请求到后端删除文章
          axios.get(`http://localhost:8080/deleteArticle?idarticle=${this.articleId}`)
            .then(() => {
              // 删除成功后，返回目录页面
              this.$message.success('文章删除成功');
              this.$router.push('/techArticleMenu'); // 确保'ArticleList'是目录页面的路由名称
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
    },
  }
};
</script>

<style scoped>
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
</style>