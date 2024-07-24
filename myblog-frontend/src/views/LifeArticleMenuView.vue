<template>
    <div class="diary-menu-view">
        <el-container>
            <el-header>
                <BlogHeader />
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
                <BlogFooter />
            </el-footer>
        </el-container>
    </div>
</template>

<script>
import BlogHeader from '../components/BlogHeader.vue';
import BlogFooter from '../components/BlogFooter.vue';
import axios from 'axios';
import { mapState } from 'vuex';

export default {
    name: 'TechdiaryMenuView',
    computed: {
        ...mapState(['isLoggedIn', 'diaryCount'])
    },
    components: {
        BlogHeader,
        BlogFooter,
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
            axios.get('http://localhost:8080/diary/list')
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
                axios.get(`http://localhost:8080/diary/getTitleImg?iddiary=${diary.iddiary}`)
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
};
</script>

<style scoped>
.diary-menu-view {
    display: flex;
    flex-direction: column;
}

.diary-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    /* 项目向起始位置对齐 */
    margin: -10px;
    /* 调整负外边距以抵消列之间的间隔 */
}

.diary-column {
    width: calc(25% - 20px);
    /* 每行四列，宽度设置为四分之一减去间隔 */
    margin: 10px;
    /* 间隔 */
    border: 1px solid #ddd;
    border-radius: 4px;
    overflow: hidden;
    cursor: pointer;
    padding: 10px;
    box-sizing: border-box;
}

/* 其他样式保持不变 */

.diary-title-img {
    width: 100%;
    height: auto;
    margin-bottom: 10px;
}

.create-diary-button {
    margin: 20px 0;
    float: right;
}

/* .fixed-footer {
    padding: 20px 0;
    text-align: center;
  } */

/* 其他样式 */
</style>