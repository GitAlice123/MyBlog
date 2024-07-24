<!-- import axios from 'axios'
<template>
    <div class="common-layout">
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-header style="font-size:40px;background-color: rgb(66, 160,194);">
                学生管理系统
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-scrollbar>
                        <el-menu :default-openeds="['1', '3']">
                            <el-menu-item index="1">
                                <router-link to="/student">
                                    学生信息
                                </router-link>
                            </el-menu-item>
                            <el-menu-item index="2">
                                <router-link to="/teacher">
                                    教师信息
                                </router-link>
                            </el-menu-item>
<!-- <el-menu-item index="1-1">
<router-link to="/dept">部门管理</router-link>
</el-menu-item> -->
                        </el-menu>
                    </el-scrollbar>
                </el-aside>
                <el-main>
                    <el-form :inline="true" :model="formInline" class="demo-form-inline">
                        <!-- <el-form-item label="编号">
                            <el-input v-model="formInline.studentID" placeholder="编号" clearable />
                        </el-form-item> -->

                        <el-form-item label="专业">
                            <el-select v-model="formInline.major" placeholder="专业" clearable>
                                <el-option label="计算机科学与技术" value="计算机科学与技术" />
                                <el-option label="软件工程" value="软件工程" />
                                <el-option label="信息工程" value="信息工程" />
                                <el-option label="网络工程" value="网络工程" />
                            </el-select>
                        </el-form-item>
                        <!-- <el-form-item label="Activity time">
                            <el-date-picker v-model="formInline.date" type="date" placeholder="Pick a date" clearable />
                        </el-form-item> -->
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">Query</el-button>
                        </el-form-item>
                    </el-form>
                    <el-table :data="tableData" border style="width: 100%">
                        <el-table-column prop="studentID" label="学生编号" width="180" />
                        <el-table-column prop="studentName" label="学生姓名" width="180" />
                        <el-table-column prop="major" label="专业" width="180" />
                        <el-table-column prop="grade" label="年级" width="180" />
                        <el-table-column label="操作" width="180" v-slot="scope">
                            <!-- <el-button type="primary" @click="editFormVisible = true" size="mini">编辑</el-button>
                            <el-button type="danger" size="mini">删除</el-button> -->
                            <!-- <template #title="scope"> -->
                                <el-button type="primary" size="mini" @click="openEditDialog(scope.row)">编辑</el-button>
                                <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
                            <!-- </template> -->
                        </el-table-column>
                    </el-table>



                    <div class="example-pagination-block">
                        <div class="example-demonstration"></div>
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" background
                            layout="prev, pager, next" :total="1000" />
                    </div>

                </el-main>
            </el-container>
        </el-container>
    </div>

    <!-- 编辑对话框 -->
    <el-dialog v-model="editFormVisible" title="Shipping address" width="500">
        <el-form :model="form">
            <!-- 学生编号，禁止修改 -->
            <el-form-item label="学生编号" :label-width="formLabelWidth">
                <el-input v-model="form.studentID" disabled />
            </el-form-item>

            <el-form-item label="学生姓名" :label-width="formLabelWidth">
                <!-- <el-input v-model="form.name" autocomplete="off" /> -->
                <!-- 弹出来的时候就用本行学生studentName填充 -->
                <el-input v-model="form.name" autocomplete="on" />

            </el-form-item>
            <el-form-item label="专业" :label-width="formLabelWidth">
                <el-select v-model="form.major" placeholder="Please select a major">
                    <el-option label="计算机科学与技术" value="shanghai" />
                    <el-option label="软件工程" value="beijing" />
                    <el-option label="信息工程" value="nanjing" />
                    <el-option label="网络工程" value="chongqing" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <!-- 需要添加点击确认时的提交表单事件 -->
                <el-button @click="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click="editConfirm">
                    确认
                </el-button>

            </div>
        </template>
    </el-dialog>
</template>

<script>
import axios from 'axios'

export default {
    data() {
        return {
            tableData: [
                // 数据来自于http://localhost:8080/list
            ],
            formInline: {
                studentID: '',
                major: ''
            },
            editFormVisible: false,
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            },
            formLabelWidth: '140px'
        }
    },
    methods: {
        // 查询功能提交表单
        onSubmit() {
            // 控制台中打印刚才提交的表单
            console.log(this.formInline);
            // 从http://localhost:8080/listByMajor获取数据
            axios.get('http://localhost:8080/listByMajor', {
                params: {
                    major: this.formInline.major
                }
            }).then(res => {
                console.log(res.data);
                this.tableData = res.data.data;
            })
        },
        // // 处理分页
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        // 点击编辑按钮
        openEditDialog(row) {
            console.log(row);
            this.editFormVisible = true;
            this.form.studentID = row.studentID;
            this.form.name = row.studentName;
            this.form.major = row.major;
        },
        // 点击确认按钮
        editConfirm() {
            console.log("okokokookookokokok");
            console.log(this.form);
            // 提交表单
            axios.post('http://localhost:8080/update', {
                studentID: this.form.studentID,
                studentName: this.form.name,
                major: this.form.major,
                grade: '3'
            }).then(res => {
                // 弹窗提示
                this.$message({
                    message: '编辑成功',
                    type: 'success'
                });
                console.log(res.data);
                // 关闭对话框
                this.editFormVisible = false;
                // 重新获取数据
                axios.get('http://localhost:8080/list').then(res => {
                    console.log(res.data);
                    this.tableData = res.data.data;
                })
            })
        },

    },
    mounted() {
        // 页面加载时获取初始数据
        axios.get('http://localhost:8080/list').then(res => {
            console.log(res.data);
            this.tableData = res.data.data;
        })

    }

}
</script>

<style>
.demo-form-inline .el-input {
    --el-input-width: 220px;
}

.demo-form-inline .el-select {
    --el-select-width: 220px;
}
</style> -->
