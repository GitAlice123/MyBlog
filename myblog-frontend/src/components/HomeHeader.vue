<template>
    <header>
      <div class="left" @click="goHome">
        <h1>Alice's Blog</h1>
      </div>
      <div class="middle">
        <!-- 如果已经登录，展示问候语 -->
        <span v-if="isLoggedIn">{{ greeting }}</span>
      </div>
      <div class="right">
        <router-link to="/techArticleMenu">技术笔记</router-link>
        <router-link to="/lifeArticleMenu">随心日记</router-link>
        <router-link to="/login">登录</router-link>
      </div>
    </header>
  </template>
  
  <script>
  import { mapState } from 'vuex';
  export default {
    computed: {
      ...mapState(['isLoggedIn'])
    },
    name: 'BlogHeader',
    data() {
      return {
        // 添加 greeting 变量来存储问候语
        greeting: ''
      };
    },
    created() {
      // 组件创建时设置问候语
      this.setGreeting();
    },
    methods: {
      goHome() {
        this.$router.push('/');
      },
      setGreeting() {
        const hour = new Date().getHours();
        if (hour < 12) {
          this.greeting = '早上好，余畅，今天又是新的一天！';
        } else if (hour < 18) {
          this.greeting = '下午好，余畅，吃点水果，补充能量！';
        } else {
          this.greeting = '晚上好，余畅，今天辛苦了，早点休息！';
        }
      }
    }
  }
  </script>
  
  <style scoped>
  @font-face {
    font-family: 'AaDongQiChangYueYangLouJi';
    src: url('../assets/AaDongQiChangYueYangLouJi-2.ttf') format('truetype');
  }
  header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    /* background-color: #57535D; */
    /* 透明度：50% */
    background-color: rgba(0, 0, 0, 0);
    position: fixed; /* 固定定位 */
    top: 0; /* 顶部 */
    left: 0;
    right: 0;
    z-index: 1000; /* 确保在最上层 */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影，提升视觉效果 */
    /* 字体：src/assets/AaDongQiChangYueYangLouJi-2.ttf */
    font-family: 'AaDongQiChangYueYangLouJi', sans-serif;
    font-size: 30px;
  }
  .left {
    cursor: pointer; /* 显示鼠标悬停时的手势 */
    display: flex;
    align-items: center;
  }
  .left h1 {
    margin: 0;
    color: white; /* 文字颜色 */
    text-decoration: none; /* 移除下划线 */
  }
  .middle {
    flex: 1; /* 占据剩余空间 */
    text-align: center;
  }
  /* 问候语设置成白色 */
  .middle span {
    color: white;
  }
  .right {
    display: flex;
    align-items: center;
  }
  .right a {
    margin-left: 10px; /* 增加链接之间的间距 */
    color: white;
    text-decoration: none;
  }
  .right span {
    color: white; /* 将问候语的颜色设置为白色 */
  }
  </style>