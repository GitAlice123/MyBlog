<template>
    <footer>
      <div class="left">
        <p>每日签名：随心签</p>
      </div>
      <div class="right">
        <a href="https://github.com/GitAlice123" target="_blank" rel="noopener noreferrer">
          <i class="el-icon-github"></i> GitHub
        </a>
        <a href="mailto:youremail@example.com">邮箱</a>
      </div>
    </footer>
  </template>
  
  <script>
  export default {
    name: 'BlogFooter'
  }
  </script>
  
  <style scoped>
  footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    background-color: rgba(87, 83, 93, 0);
    position: fixed; /* 固定定位 */
    bottom: 0; /* 底部 */
    left: 0;
    right: 0;
    z-index: 1000; /* 确保在最上层 */
    box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影，提升视觉效果 */
    color: white;
  }
  
  .left, .right {
    display: flex;
    align-items: center;
  }
  
  .right a {
    margin-left: 10px; /* 增加链接之间的间距 */
    color: white;
    text-decoration: none;
  }
  
  .right i {
    margin-right: 5px; /* 图标和文本之间的间距 */
  }
  </style>