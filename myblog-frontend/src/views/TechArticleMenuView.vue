<template>
  <div class="article-menu-view">
    <el-container>
      <el-header>
        <BlogHeader />
      </el-header>

      <el-main class="article-list">
        <!-- 遍历文章列表 -->
        <div class="article-column" v-for="article in articles" :key="article.idarticle"
          @click="viewArticle(article.idarticle)">
          <img v-if="titleImgs[article.idarticle]" :src="titleImgs[article.idarticle]" class="article-title-img"
            alt="Title Image">
          <div class="article-info">
            <h3>{{ article.title }}</h3>
          </div>
        </div>
        <!-- 新建文章按钮，只有登录用户可以看到 -->
        <el-button v-if="isLoggedIn" type="primary" class="create-article-button"
          @click="createArticle">新建文章</el-button>
      </el-main>

      <el-footer class="fixed-footer">
        <BlogFooter />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import BlogHeader from '../components/BlogHeader.vue';
import BlogFooter from '../components/BlogFooter.vue';
import axios from 'axios';
import { mapState } from 'vuex';

export default {
  name: 'TechArticleMenuView',
  computed: {
    ...mapState(['isLoggedIn', 'articleCount'])
  },
  components: {
    BlogHeader,
    BlogFooter,
  },
  data() {
    return {
      articles: [],
      titleImgs: {}
    };
  },
  created() {
    this.fetchArticles();
  },
  methods: {
    fetchArticles() {
      axios.get('http://localhost:8080/list')
        .then(response => {
          this.articles = response.data.data;
          this.$store.commit('setArticleCount', this.articles.length); // 更新 Vuex store 中的文章总数
          this.fetchTitleImages();
        })
        .catch(error => {
          console.error('Error fetching articles:', error);
        });
    },
    fetchTitleImages() {
      this.articles.forEach(article => {
        axios.get(`http://localhost:8080/getTitleImg?idarticle=${article.idarticle}`)
          .then(response => {
            if (response.data && response.data.code === 1) {
              this.titleImgs[article.idarticle] = response.data.data;
            }
          })
          .catch(error => {
            console.error('Error fetching title image:', error);
          });
      });
    },
    viewArticle(idarticle, type = 'view') {
      const routeName = type === 'view' ? 'ArticleView' : 'ArticleEdit';
      this.$router.push({ name: routeName, params: { idarticle } });
    },

    editArticle(idarticle) {
      if (this.isLoggedIn) {
        // 如果用户已登录，直接导航到编辑页面
        this.$router.push({ name: 'ArticleEdit', params: { idarticle } });
      } else {
        // 如果用户未登录，可以在这里处理，例如弹出提示
        this.$message.error('您需要登录才能编辑文章！');
        // 或者使用其他方式提示用户登录
      }
    },
    createArticle() {
      // 跳转到ArticleAddView页面
      this.$router.push('/ArticleAdd');
    }
  }
};
</script>

<style scoped>
.article-menu-view {
  display: flex;
  flex-direction: column;
}

.article-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  /* 项目向起始位置对齐 */
  margin: -10px;
  /* 调整负外边距以抵消列之间的间隔 */
}

.article-column {
  width: calc(25% - 20px);
  /* 每行四列，宽度设置为四分之一减去间隔 */
  margin: 10px;
  /* 间隔 */
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
  cursor: pointer;
  padding: 10px;
  box-sizing: border-box;
}

/* 其他样式保持不变 */

.article-title-img {
  width: 100%;
  height: auto;
  margin-bottom: 10px;
}

.create-article-button {
  margin: 20px 0;
  float: right;
}

/* .fixed-footer {
  padding: 20px 0;
  text-align: center;
} */

/* 其他样式 */
</style>
