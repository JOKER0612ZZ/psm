<template>
    <div class="myHeader">
        <span>
            <svg t="1710426365171" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="12416" width="30" height="30">
                <path
                    d="M742.4 163.84h-2.56V204.8c0 30.72-25.6 58.88-58.88 58.88H343.04c-30.72 0-58.88-25.6-58.88-58.88V163.84H281.6c-46.08 0-81.92 38.4-81.92 81.92v494.08c0 46.08 38.4 81.92 81.92 81.92h460.8c46.08 0 81.92-38.4 81.92-81.92V248.32c0-46.08-35.84-84.48-81.92-84.48zM552.96 563.2h-207.36c-12.8 0-20.48-10.24-20.48-20.48 0-12.8 10.24-20.48 20.48-20.48h207.36c12.8 0 20.48 10.24 20.48 20.48s-10.24 20.48-20.48 20.48z m122.88-99.84H345.6c-12.8 0-20.48-10.24-20.48-20.48 0-12.8 10.24-20.48 20.48-20.48h330.24c12.8 0 20.48 10.24 20.48 20.48s-7.68 20.48-20.48 20.48z m0 0"
                    p-id="12417" fill="#d81e06"></path>
                <path
                    d="M358.4 222.72h307.2c17.92 0 33.28-15.36 33.28-33.28V140.8c0-17.92-15.36-33.28-33.28-33.28H358.4c-17.92 0-33.28 15.36-33.28 33.28v48.64c0 17.92 15.36 33.28 33.28 33.28z m0 0"
                    p-id="12418" fill="#d81e06"></path>
            </svg>
            任务管理
        </span>
    </div>
    <div class="myBody">
        <div class="aside">
            <span>任务</span>
            <div class="aside_menu">
                <a>
                    <el-icon size="30" color="rgb(102, 152, 255)">
                        <List />
                    </el-icon>
                    全部任务
                </a>
                <a>
                    <el-icon color="rgb(115, 216, 151)" size="30">
                        <List />
                    </el-icon>我负责的任务
                </a>
                <a>
                    <el-icon color="rgb(93, 207, 255)" size="30">
                        <List />
                    </el-icon>我创建的任务
                </a>
            </div>
        </div>
        <div class="main">
            <task-view :title="title" :taskList="taskList"></task-view>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Task } from '@/api/interface';
import taskView from '@/components/pages/task/taskView.vue'
const title = ref('全部任务')
const taskList = ref<Task[]>([])
</script>

<style scoped lang="scss">
@import url('../../css/index.css');
</style>