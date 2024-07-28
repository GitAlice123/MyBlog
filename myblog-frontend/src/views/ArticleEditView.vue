<template>
    <el-container>
        <el-header>
            <BlogHeader />
        </el-header>
        <el-main>
            <el-form ref="articleForm" :model="article" label-width="120px" @submit.prevent="submitArticle">
                <!-- 文章标题 -->
                <el-form-item label="文章标题">
                    <el-input v-model="article.title" placeholder="请输入文章标题"></el-input>
                </el-form-item>

                <!-- 文章内容 -->
                <el-form-item label="文章内容" @paste="handlePaste">
                    <!-- <v-md-editor v-model="article.articleContent" height="500px" /> -->
                    <v-md-editor v-model="article.articleContent" left-toolbar="undo redo | image" :disabled-menus="[]"
                        @upload-image="handleUploadImage" height="500px" />
                </el-form-item>
                <!-- 标题图片展示及修改 -->
                <el-form-item label="标题图片">
                    <el-upload class="avatar-uploader" action="http://myalpine:8080/api/upload-image"
                        :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <template #trigger>
                            <div class="avatar-uploader-trigger">
                                <img v-if="article.title_img_path" :src="article.title_img_path" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </div>
                        </template>
                    </el-upload>
                    <div class="avatar-uploader-tip">点击或拖动图片到这里上传</div>
                </el-form-item>
                <!-- 提交按钮 -->
                <el-form-item>
                    <el-button type="primary" native-type="submit">提交文章</el-button>
                </el-form-item>
            </el-form>
        </el-main>
        <el-footer class="fixed-footer">
            <BlogFooter />
        </el-footer>
    </el-container>
</template>

<script>
import axios from 'axios';
import BlogHeader from '../components/BlogHeader.vue';
import BlogFooter from '../components/BlogFooter.vue';
import VMdEditor from '@kangc/v-md-editor';
import { mapState } from 'vuex';

export default {
    computed: {
        ...mapState(['isLoggedIn']),
    },
    components: {
        BlogHeader,
        BlogFooter,
        'v-md-editor': VMdEditor,
    },
    data() {
        return {
            article: {
                idarticle: this.$route.params.idarticle,
                title: '',
                articleContent: '',
                title_img_path: '',
            },
            fileList: [], // 用于绑定上传组件的文件列表
        };
    },
    created() {
        this.fetchArticleContent();
    },
    methods: {
        submitArticle() {
            this.article.title_img_path = this.article.title_img_path.trim();
            axios.post('http://myalpine:8080/addArticle', this.article, {
                headers: {
                    'Content-Type': 'application/json',
                },
            })
                .then(response => {
                    console.log('Article added successfully:', response);
                    this.$message({
                        message: '文章添加成功',
                        type: 'success',
                    });
                    this.$router.push('/techArticleMenu');
                })
                .catch(error => {
                    console.error('Error adding article:', error);
                });
        },
        fetchArticleContent() {
            axios.get(`http://myalpine:8080/getArticleContent?idarticle=${this.article.idarticle}`)
                .then(response => {
                    if (response.data.code === 1) {
                        const articleData = response.data.data;
                        this.article.title = articleData.title;
                        this.article.articleContent = articleData.articleContent;
                        this.article.title_img_path = articleData.title_img_path; // 初始化标题图片路径
                    } else {
                        console.error('Failed to fetch article content:', response.data.msg);
                    }
                })
                .catch(error => {
                    console.error('Error fetching article content:', error);
                });
        },
        handleAvatarSuccess(response) {
            if (response.code === 1) {
                this.article.title_img_path = response.data;
                this.$message.success('标题图片上传成功');
                // this.copyToClipboard(this.article.title_img_path); // 复制图片URL到剪贴板
            } else {
                this.$message.error('标题图片上传失败');
            }
        },
        beforeAvatarUpload(file) {
            const isJPGorPNG = file.type === 'image/jpeg' || file.type === 'image/png';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPGorPNG) {
                this.$message.error('标题图片必须是 JPG 或 PNG 格式!');
                return false;
            }
            if (!isLt2M) {
                this.$message.error('标题图片大小不能超过 2MB!');
                return false;
            }
            return true;
        },
        handleSuccess(response) {
            if (response.code === 1) {
                this.fileList.push(response.data);
                this.$message.success('文章内图片上传成功');
                this.copyToClipboard(response.data.replace(/ /g, '%20')); // 复制图片URL到剪贴板
            } else {
                this.$message.error('文章内图片上传失败');
            }
        },
        handleRemove(file) {
            const index = this.fileList.indexOf(file.url);
            if (index !== -1) {
                this.fileList.splice(index, 1);
            }
        },
        beforeUpload(file) {
            const isJPGorPNG = file.type === 'image/jpeg' || file.type === 'image/png';
            const isLt500K = file.size / 1024 < 500;

            if (!isJPGorPNG) {
                this.$message.error('只能上传 JPG 或 PNG 格式的图片!');
                return false;
            }
            if (!isLt500K) {
                this.$message.error('图片大小不能超过 500KB!');
                return false;
            }
            return true;
        },
        copyToClipboard(text) {
            navigator.clipboard.writeText(text).then(() => {
                this.$message.success('图片URL已复制到剪贴板');
            }).catch(err => {
                console.error('无法复制文本: ', err);
                this.$message.error('复制图片URL到剪贴板失败');
            });
        },
        handleUploadImage(event, insertImage) {
            // 定义一个变量来存储文件
            let file;

            // 处理粘贴事件
            if (event.type === 'paste') {
                file = this.extractFileFromClipboard(event.clipboardData);
                if (!file) {
                    console.log('剪贴板中没有文件或不支持的文件类型');
                    return;
                }
            }
            // 处理文件选择事件
            else {
                if (event.target.files.length === 0) {
                    console.log('没有选择文件');
                    return;
                }
                file = event.target.files[0];
            }

            // 配置axios请求
            const config = {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            };

            // 创建FormData对象并添加文件
            const formData = new FormData();
            formData.append('file', file);

            // 发送文件到后端
            axios.post('http://myalpine:8080/api/upload-image', formData, config)
                .then(response => {
                    if (response.data.code === 1) {
                        // 上传成功
                        insertImage({
                            url: response.data.data,
                            desc: '图片描述' // 可以替换为实际的图片描述
                        });
                    } else {
                        this.$message.error('图片上传失败: ' + response.data.message);
                    }
                })
                .catch(error => {
                    console.error('图片上传网络错误:', error);
                    this.$message.error('图片上传网络错误');
                });
        },

        // 工具方法：从 clipboardData 中提取文件
        extractFileFromClipboard(clipboardData) {
            if (!clipboardData || !clipboardData.items) return null;
            for (let index = 0; index < clipboardData.items.length; index++) {
                if (clipboardData.items[index].kind === 'file') {
                    return clipboardData.items[index].getAsFile();
                }
            }
            return null;
        }
    },
};
</script>

<style>
.avatar-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: border-color 0.3s;
}

.avatar-uploader-trigger {
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar-uploader .avatar {
    width: 100%;
    height: 100%;
    display: block;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
}

.avatar-uploader:hover {
    border-color: #409eff;
}

.avatar-uploader-tip {
    font-size: 12px;
    color: #666;
    margin-top: 10px;
}
</style>