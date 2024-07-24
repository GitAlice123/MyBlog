// store/index.js
import { createStore } from 'vuex';

export default createStore({
  state: {
    isLoggedIn: false, // 初始化登录状态为 false
    articleCount: 0,   // 初始化文章总数为 0
  },
  mutations: {
    setLoginState(state, status) {
      state.isLoggedIn = status;
    },
    setArticleCount(state, count) {
      state.articleCount = count; // 更新文章总数
    },
  },
  actions: {
    // 处理登录状态的更改
    login({ commit }, credentials) {
      if (credentials.username === 'Alice' && credentials.password === 'yc030316') {
        commit('setLoginState', true);
        return true;
      }
      return false;
    },
    // 你可以在这里添加一个 action 来异步更新文章总数
    updateArticleCount({ commit }, count) {
      commit('setArticleCount', count);
    },
  },
  // 你可以添加 getters 来方便地访问状态
  getters: {
    getArticleCount: state => state.articleCount,
  }
});