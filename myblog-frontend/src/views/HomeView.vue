<template>
  <div class="home">
    <img class="background-image" src="../assets/bg.jpg" alt="Background">
    <el-container>
      <el-header>
        <HomeHeader />
      </el-header>
      <el-main>
        <StarBackground />
      </el-main>
      <el-footer class="fixed-footer">
        <HomeFooter />
      </el-footer>
    </el-container>

  </div>
</template>

<script>
import HomeHeader from '../components/HomeHeader.vue'
import StarBackground from '@/components/StarBackground.vue'; 
import HomeFooter from '../components/HomeFooter.vue'
import router from '@/router';

export default {
  components: {
    HomeHeader,
    HomeFooter,
    StarBackground
    // ArticleList
  },
  data() {
    return {
      toc: [] // 假设你有一个 TOC (Table of Contents) 数组
    }
  },
  methods: {
    testFun() {
      router.push('/test')
    }
  }
}
</script>

<style>
.home {
  position: relative;
  z-index: 1;
  min-height: 100vh;
  padding-top: 0px;
  overflow: hidden;
  background: rgba(255, 255, 255, 0.2); /* 设置透明度为 50% 的白色背景 */
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

.el-container {
  position: relative;
  z-index: 2; /* 确保容器内容在背景图片和半透明背景之上 */
}
</style>