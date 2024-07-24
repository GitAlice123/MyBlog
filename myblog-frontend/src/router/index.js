import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ArticleView from '../views/ArticleView.vue'
import LoginView from '../views/LoginView.vue'
import TechArticleMenuView from '@/views/TechArticleMenuView.vue'
import LifeArticleMenuView from '@/views/LifeArticleMenuView.vue'
import ArticleEditView from '@/views/ArticleEditView.vue'
import ArticleAddView from '@/views/ArticleAddView.vue'
import TestView from '@/views/TestView.vue'
import DiaryAddView from '@/views/DiaryAddView.vue'
import DiaryEditView from '@/views/DiaryEditView.vue'
import DiaryView from '@/views/DiaryView.vue'

const routes = [
  { path: '/', component: HomeView },
  // { path: '/article/:id', component: ArticleView },
  { path: '/login', component: LoginView },
  { path: '/techArticleMenu', component: TechArticleMenuView },
  { path: '/lifeArticleMenu', component: LifeArticleMenuView },
  {
    path: '/article/:idarticle', // 注意这里的路径和参数
    name: 'ArticleView',
    component: ArticleView
  },
  // { path: '/ArticleEdit', component: ArticleEditView },
  {
    path: '/article-edit/:idarticle', // 注意这里的路径和参数
    name: 'ArticleEdit',
    component: ArticleEditView,
  },
  {path:'/ArticleAdd',component:ArticleAddView},
  {path:'/test',component:TestView},
  {path:'/diaryMenu',component:LifeArticleMenuView},
  {path:'/diaryAdd',component:DiaryAddView},
//   viewdiary(iddiary, type = 'view') {
//     const routeName = type === 'view' ? 'diaryView' : 'diaryEdit';
//     this.$router.push({ name: routeName, params: { iddiary } });
// },
  {path:'/diary-edit/:iddiary',name:'diaryEdit',component:DiaryEditView},
  {path:'/diary/:iddiary',name:'diaryView',component:DiaryView},
]


const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
