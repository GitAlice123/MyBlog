<template>
    <div class="sidebar">
      <ul>
        <li v-for="item in toc" :key="item.id">
          <a :href="`#${item.id}`">{{ item.text }}</a>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    props: ['toc']
  }
  </script>