<template>
    <div class="code-view">
        <el-container>
            <el-header>
                <h1>Java Code Example</h1>
            </el-header>

            <el-main>
                <!-- 直接渲染Java代码 -->
                <pre v-html="highlightedCode"></pre>
            </el-main>

            <el-footer>
                <p>Footer content here</p>
            </el-footer>
        </el-container>
    </div>
</template>

<script>
import hljs from 'highlight.js';
import 'highlight.js/styles/github.css';
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'CodeView',
    data() {
        return {
            // 硬编码的Java代码
            code: `
  public class HelloWorld {
      public static void main(String[] args) {
          System.out.println("Hello, world!");
      }
  }
        `
        };
    },
    computed: {
        highlightedCode() {
            // 使用highlight.js高亮Java代码
            return hljs.highlight(this.code, { language: 'java' }).value;
        }
    },
    mounted() {
        // 在组件挂载后，手动调用highlight.js来高亮代码块
        this.$nextTick(() => {
            document.querySelectorAll('pre code').forEach((block) => {
                hljs.highlightBlock(block);
            });
        });
    }
});
</script>

<style scoped>
.code-view {
    position: relative;
    min-height: 100vh;
    padding: 20px;
    background: rgba(255, 255, 255, 0.2);
}

.el-container {
    max-width: 800px;
    margin: 0 auto;
}

.el-header,
.el-main,
.el-footer {
    padding: 20px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.el-header {
    text-align: center;
}

.el-main {
    margin-bottom: 20px;
}
</style>