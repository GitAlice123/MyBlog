<template>
    <div class="diary-menu-view">
        <img class="background-image" src="../assets/forest.jpg" alt="Background">
        <el-container>
            <el-header>
                <HomeHeader />
            </el-header>

            <el-main class="diary-list">
                <!-- 遍历文章列表 -->
                <div class="diary-column" v-for="diary in diarys" :key="diary.iddiary"
                    @click="viewdiary(diary.iddiary)">
                    <img v-if="titleImgs[diary.iddiary]" :src="titleImgs[diary.iddiary]" class="diary-title-img"
                        alt="Title Image">
                    <div class="diary-info">
                        <h3>{{ diary.title }}</h3>
                    </div>
                </div>
                <!-- 新建文章按钮，只有登录用户可以看到 -->
                <el-button v-if="isLoggedIn" type="primary" class="create-diary-button"
                    @click="creatediary">新建文章</el-button>
            </el-main>

            <el-footer class="fixed-footer">
                <HomeFooter />
            </el-footer>
        </el-container>
    </div>
</template>

<script>
import HomeHeader from '../components/HomeHeader.vue';
import HomeFooter from '../components/HomeFooter.vue';
// import axios from 'axios';
import { mapState } from 'vuex';
import { defineComponent } from 'vue'

export default  defineComponent({
    name: 'TechdiaryMenuView',
    computed: {
        ...mapState(['isLoggedIn', 'diaryCount'])
    },
    components: {
        HomeHeader,
        HomeFooter,
    },
    data() {
        return {
            diarys: [],
            titleImgs: {}
        };
    },
    created() {
        this.fetchdiarys();
    },
    methods: {
        fetchdiarys() {
            // axios.get('http://localhost:8080/api/diary/list')
            this.$http.get('/diary/list')
                .then(response => {
                    this.diarys = response.data.data;
                    this.$store.commit('setdiaryCount', this.diarys.length); // 更新 Vuex store 中的文章总数
                    this.fetchTitleImages();
                })
                .catch(error => {
                    console.error('Error fetching diarys:', error);
                });
        },
        fetchTitleImages() {
            this.diarys.forEach(diary => {
                // axios.get(`http://localhost:8080/api/diary/getTitleImg?iddiary=${diary.iddiary}`)
                this.$http.get(`/diary/getTitleImg?iddiary=${diary.iddiary}`)
                    .then(response => {
                        if (response.data && response.data.code === 1) {
                            this.titleImgs[diary.iddiary] = response.data.data;
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching title image:', error);
                    });
            });
        },
        viewdiary(iddiary, type = 'view') {
            const routeName = type === 'view' ? 'diaryView' : 'diaryEdit';
            this.$router.push({ name: routeName, params: { iddiary } });
        },

        editdiary(iddiary) {
            if (this.isLoggedIn) {
                // 如果用户已登录，直接导航到编辑页面
                this.$router.push({ name: 'diaryEdit', params: { iddiary } });
            } else {
                // 如果用户未登录，可以在这里处理，例如弹出提示
                this.$message.error('您需要登录才能编辑文章！');
                // 或者使用其他方式提示用户登录
            }
        },
        creatediary() {
            // 跳转到diaryAddView页面
            this.$router.push('/diaryAdd');
        }
    }
});
</script>

<style scoped>
@font-face {
  font-family: 'AaDongQiChangYueYangLouJi';
  src: url('../assets/AaDongQiChangYueYangLouJi-2.ttf') format('truetype');
}
.diary-menu-view {
  position: relative;
  z-index: 1;
  min-height: 100vh;
  padding-top: 0px;
  overflow: hidden;
  background: rgba(255, 255, 255, 0.2); /* 设置透明度为 50% 的白色背景 */
}

.diary-menu-view {
    display: flex;
    flex-direction: column;
}

.diary-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    margin: -10px;
}

.diary-column {
    width: calc(25% - 20px); /* 每个元素占25%减去20px的间隙 */
    margin: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    overflow: hidden;
    cursor: pointer;
    padding: 10px;
    box-sizing: border-box;
    background-color: rgba(255, 255, 255, 0.5);
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    height: 300px; /* 根据需要调整固定最小高度 */
}

html, body {
  margin: 0;
  padding: 0;
}

.diary-column:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.diary-title-img {
    width: 320px; /* 固定宽度 */
    height: 200px; /* 固定高度 */
    margin-bottom: 10px;
}

.diary-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    font-family: 'AaDongQiChangYueYangLouJi', sans-serif;
    font-size: 20px;
}

.create-diary-button {
    margin: 20px 0;
    float: right;
}

.background-image {
  display: block; /* 设置为块级元素 */
  position: fixed; /* 固定位置，不随滚动条滚动 */
  top: 0;
  left: 0;
  width: 100%; /* 覆盖整个视口宽度 */
  height: 100%; /* 覆盖整个视口高度 */
  object-fit: cover; /* 确保图片覆盖整个元素 */
  z-index: -1; /* 确保图片在所有内容之下 */
}

/* 其他样式保持不变 */
</style>