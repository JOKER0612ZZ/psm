<template>
    <el-header>
        <span>{{ title }}</span>
    </el-header>
    <el-main>
        <el-table :data="tasksData">
            <el-table-column prop="title" fixed label="标题" align="center" />
            <el-table-column prop="status" label="状态" align="center">
                <template #default="{ row }">
                    <normal-status :status="row.status"></normal-status>
                </template>
            </el-table-column>
            <el-table-column prop="creationTime" label="创建时间" align="center" />
            <el-table-column prop="deadline" label="截止时间" align="center" />
            <el-table-column prop="creatorName" label="创建者" align="center" />
            <el-table-column prop="assignName" label="负责人" align="center" />
            <el-table-column prop="projectName" label="所属项目" align="center " />
        </el-table>
    </el-main>
</template>

<script setup lang="ts">
import { ref, onBeforeMount } from 'vue'
import { Task } from '@/api/interface'
import { useProjectStore } from '@/store/project'
import { useUserStore } from '@/store/user'
import { queryTasksByProjectIdList } from '@/api/task'
import eventBus from '@/utils/event'
import { cloneDeep } from 'lodash'
import normalStatus from '../common/normalStatus.vue'
const projectStore = useProjectStore()
const projects = projectStore.projects
let projectIdList: number[]
const tasks = ref<Task[]>([])
const tasksData = ref<Task[]>([])
const userId = useUserStore().userInfo.userId
onBeforeMount(() => {
    if (projects) {
        projectIdList = projects.map(project => parseInt(project.projectId))
    }
    queryAllTasks(projectIdList)
})
const queryAllTasks = async (projectIdList: number[]) => {
    if (projectIdList) {
        tasks.value = await queryTasksByProjectIdList(projectIdList)
        tasksData.value = cloneDeep(tasks.value)
    }
}
eventBus.on('allTasks', () => {
    tasksData.value = cloneDeep(tasks.value)
})
eventBus.on('creatorTasks', () => {
    tasksData.value = cloneDeep(tasks.value.filter((task: Task) => task.creatorId === userId))
})
eventBus.on('joinTasks', () => {
    tasksData.value = cloneDeep(tasks.value.filter((task: Task) => task.assignerId === userId))
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