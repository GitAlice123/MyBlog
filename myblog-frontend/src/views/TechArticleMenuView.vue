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
  margin: -10px;
}

.article-column {
  width: calc(25% - 20px); /* 每个元素占25%减去20px的间隙 */
  margin: 10px;
  /* 由于我们希望在每个元素的左右两侧都有10px的间隙，
     我们将margin设置为10px，这样在水平方向上会有20px的间隙 */
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
  cursor: pointer;
  padding: 10px;
  box-sizing: border-box;
  background-color: #fff;
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  min-height: 300px; /* 根据需要调整固定最小高度 */
}

.article-column:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.article-title-img {
  width: 300px;
  /* 固定宽度 */
  height: 200px;
  /* 固定高度 */
  margin-bottom: 10px;
}

.article-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* 垂直居中对齐 */
  align-items: flex-start;
  /* 水平开始对齐 */
}

/* 其他样式保持不变 */

.create-article-button {
  margin: 20px 0;
  float: right;
}

.hover-effect {
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

.hover-effect:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

/* 其他样式 */
</style>

