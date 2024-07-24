// LoginComponent.vue
<template>
  <div class="login">

    <el-container>
      <el-header>
        <BlogHeader />
      </el-header>
      <el-main>
        <h2>登录</h2>
        <form @submit.prevent="login">
          <input type="text" v-model="username" placeholder="用户名">
          <input type="password" v-model="password" placeholder="密码">
          <button type="submit">登录</button>
        </form>
      </el-main>
      <el-footer class="fixed-footer">
        <BlogFooter />
      </el-footer>
    </el-container>
  </div>
</template>


<script>
import BlogHeader from '../components/BlogHeader.vue';

export default {
  components: {
    BlogHeader,
  },
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    login() {
      this.$store.dispatch('login', { username: this.username, password: this.password })
        .then((loggedIn) => {
          if (loggedIn) {
            alert('登录成功！');
            this.$router.push('/');
          } else {
            alert('用户名或密码错误！');
          }
        })
        .catch(error => {
          // 处理错误情况
          console.error('登录失败:', error);
        });
    },
  },
};
</script>