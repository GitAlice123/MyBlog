import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import store from './store';
import 'element-plus/dist/index.css'
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import Prism from 'prismjs';
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import hljs from 'highlight.js';
// import 'highlight.js/styles/stackoverflow-light.css'
// import hljsVuePlugin from "@highlightjs/vue-plugin";


//预览的主题
VMdPreview.use(githubTheme, {
  Hljs: hljs,
});
//编辑器的主题
VueMarkdownEditor.use(vuepressTheme, {
  Prism,
});

const app = createApp(App)
//注册
app.use(VMdPreview);
app.use(VueMarkdownEditor);
// app.use(hljsVuePlugin)

app.use(ElementPlus)
app.use(router)
app.use(store)
// app.use(VMdEditor)
app.mount('#app')
const debounce = (fn, delay) => {
  let timer
   return (...args) => {
     if (timer) {
       clearTimeout(timer)
     }
     timer = setTimeout(() => {
       fn(...args)
     }, delay)
   }
}
  
const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver{
   constructor(callback) {
     callback = debounce(callback, 200);
     super(callback);
   }
}