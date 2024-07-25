<template>
  <footer>
    <div class="left">
      <p>每日签名：随心签</p>
    </div>
    <div class="right">
      <!-- 直接展示邮箱地址，不用超链接 -->
      <span>
        2822983966@qq.com
      </span>
      <a href="https://github.com/GitAlice123" target="_blank" rel="noopener noreferrer">
        <img class="github-icon" src="../assets/github.jpg" alt="GitHub">
      </a>


    </div>
  </footer>
</template>

<script>
export default {
  name: 'BlogFooter'
}
</script>

<style scoped>
@font-face {
  font-family: 'AaDongQiChangYueYangLouJi';
  src: url('../assets/AaDongQiChangYueYangLouJi-2.ttf') format('truetype');
}

footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: rgba(87, 83, 93, 0);
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1);
  color: white;
  /* 字体 */
  font-family: 'AaDongQiChangYueYangLouJi';
  font-size: 30px;
}

.left,
.right {
  display: flex;
  align-items: center;
}

.right a {
  margin-left: 10px;
  color: white;
  text-decoration: none;
}

.github-icon {
  width: 40px;
  /* 根据需要调整图标大小 */
  height: auto;
  transition: transform 0.3s ease;
}

.github-icon:hover {
  transform: scale(1.1);
}
</style>