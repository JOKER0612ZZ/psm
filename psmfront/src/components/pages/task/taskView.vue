<template>
    <el-header>
        <span>{{ title }}</span>
        <task-insert></task-insert>
    </el-header>
    <el-main>
        <el-table :data="tasksData">
            <el-table-column prop="title" fixed label="标题" align="center" />
            <el-table-column prop="status" label="状态" align="center" />
            <el-table-column prop="creationTime" label="创建时间" align="center" />
            <el-table-column prop="deadline" label="截止时间" align="center" />
            <el-table-column prop="creatorName" label="创建者" align="center" />
            <el-table-column prop="assignName" label="负责人" align="center" />
            <el-table-column prop="projectName" label="所属项目" align="center " />
        </el-table>
    </el-main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Task } from '@/api/interface'
import { useProjectStore } from '@/store/project'
import { useUserStore } from '@/store/user'
import taskInsert from './taskInsert.vue'
import { queryTasksByProjectIdList } from '@/api/task'
import eventBus from '@/utils/event'
import { cloneDeep } from 'lodash'
const projectStore = useProjectStore()
const projects = projectStore.projects
const projectIdList: number[] = projects.map(project => parseInt(project.projectId))
const tasks = ref<Task[]>([])
const tasksData = ref<Task[]>([])
const userId = useUserStore().userInfo.userId
onMounted(() => {
    queryAllTasks(projectIdList)
})
const queryAllTasks = async (projectIdList: number[]) => {
    tasks.value = await queryTasksByProjectIdList(projectIdList)
    console.log(projectIdList)
    console.log(tasks.value.length)
    tasksData.value = cloneDeep(tasks.value)
}
eventBus.on('allTasks', () => {
    tasksData.value = cloneDeep(tasks.value)
    // creatorId
    console.log(tasksData.value.length)
})
eventBus.on('creatorTasks', () => {
    tasksData.value = cloneDeep(tasks.value.filter((task: Task) => task.creatorId === userId))
    console.log(tasksData.value.length)
})
eventBus.on('joinTasks', () => {
    tasksData.value = cloneDeep(tasks.value.filter((task: Task) => task.assignerId === userId))
    console.log(tasksData.value.length)
})
defineProps({
    title: {
        type: String,
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