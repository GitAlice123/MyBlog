<template>
  <footer>
    <div class="left">
      <p>每日签名：随心签</p>
    </div>
    <div class="right">
      <a href="https://github.com/GitAlice123" target="_blank" rel="noopener noreferrer">
        <img class="github-icon" src="../assets/github_PNG40.png" alt="GitHub">
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
  background-color: rgba(87, 83, 93, 0.5);
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1);
  color: white;
}

.left, .right {
  display: flex;
  align-items: center;
}

.right a {
  margin-left: 10px;
  color: white;
  text-decoration: none;
}

.github-icon {
  width: 24px; /* 根据需要调整图标大小 */
  height: auto;
  transition: transform 0.3s ease;
}

.github-icon:hover {
  transform: scale(1.1);
}
</style>