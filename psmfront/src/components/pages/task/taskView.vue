<template>
    <el-header>
        <span>{{ title }}</span>
        <task-insert></task-insert>
    </el-header>
    <el-main>
        <el-table>
            <el-table-column prop="title" fixed label="标题"  align="center" />
            <el-table-column prop="status" label="状态"  align="center" />
            <el-table-column prop="creationTime" label="创建时间" align="center" />
            <el-table-column prop="deadline" label="截止时间"  align="center" />
            <el-table-column prop="userName" label="创建者"  align="center" />
            <el-table-column prop="assignName" label="负责人" align="center" />
            <el-table-column label="所属项目" align="center "/>
        </el-table>


    </el-main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Task } from '@/api/interface'
import taskInsert from './taskInsert.vue';
defineProps({
    title: {
        type: Object as () => string,
        require: true
    },
    taskList: {
        type: Object as () => Task[],
        require: true
    }
})
const title = ref('全部任务')
</script>

<style scoped lang="scss">
.el-header {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: space-between;
    align-items: center;
    padding: 0 40px;
    height: 100px;

    span {
        font-size: 20px;
    }
}
</style>