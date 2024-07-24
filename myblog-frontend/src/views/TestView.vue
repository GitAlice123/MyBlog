<template>
    <h1>TestView</h1>
    <el-input v-model="input" style="width: 240px" placeholder="Please input" 
    @paste="handlePaste"/>
</template>

<script>
export default {
    data() {
        return {
            input: '',
        };
    },
    methods: {
        handlePaste(e) {
            console.log('Paste event:', e);
        },
    },
};
</script>

<!-- 输入框设成蓝色 -->
<style>
.el-input__inner {
    background-color: #f0f9eb;
}
</style>