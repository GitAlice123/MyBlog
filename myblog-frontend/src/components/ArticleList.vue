<template>
    <div class="article-list">
      <div v-for="article in articles" :key="article.id">
        <router-link :to="`/article/${article.id}`">{{ article.title }}</router-link>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        articles: [
          { id: 1, title: '文章1' },
          { id: 2, title: '文章2' },
          // 添加更多文章...
        ]
      }
    }
  }
  </script>