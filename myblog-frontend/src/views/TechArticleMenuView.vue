<template>
  <div class="article-menu-view">
    <img class="background-image" src="../assets/forest.jpg" alt="Background">
    <el-container>
      <el-header>
        <HomeHeader />
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
        <HomeFooter />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import HomeHeader from '../components/HomeHeader.vue';
import HomeFooter from '../components/HomeFooter.vue';
import axios from 'axios';
import { mapState } from 'vuex';

export default {
  name: 'TechArticleMenuView',
  computed: {
    ...mapState(['isLoggedIn', 'articleCount'])
  },
  components: {
    HomeHeader,
    HomeFooter,
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
@font-face {
  font-family: 'AaDongQiChangYueYangLouJi';
  src: url('../assets/AaDongQiChangYueYangLouJi-2.ttf') format('truetype');
}

.article-menu-view {
  position: relative;
  z-index: 1;
  min-height: 100vh;
  padding-top: 0px;
  overflow: hidden;
  background: rgba(255, 255, 255, 0.2); /* 设置透明度为 50% 的白色背景 */
}
/* 确保布局容器不超出视口 */
.el-container {
  position: relative;
  z-index: 2; /* 确保容器内容在背景图片和半透明背景之上 */
}

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

/* 移除html和body的默认边距和填充 */
html, body {
  margin: 0;
  padding: 0;
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
  background-color: rgba(255, 255, 255, 0.5);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  height: 300px; /* 根据需要调整固定最小高度 */
}

.article-column:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.article-title-img {
  width: 320px;
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
  font-family: 'AaDongQiChangYueYangLouJi', sans-serif;
  font-size: 20px;
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

.background-image {
  display: block; /* 设置为块级元素 */
  position: fixed; /* 固定位置，不随滚动条滚动 */
  top: 0;
  left: 0;
  width: 100%; /* 覆盖整个视口宽度 */
  height: 100%; /* 覆盖整个视口高度 */
  object-fit: cover; /* 确保图片覆盖整个元素 */
  z-index: -1; /* 确保图片在所有内容之下 */
}

/* 其他样式 */
</style>

