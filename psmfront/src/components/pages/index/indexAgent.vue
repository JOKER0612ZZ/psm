<template>
    <el-header>
        <span>待处理的事项</span>
    </el-header>
    <el-main>
        <el-table :data="tasksData">
            <el-table-column prop="title" fixed label="标题" align="center"/>
            <el-table-column prop="status" label="状态" align="center" width="100">
                <template #default="{row}">
                    <normal-status :status="row.status"></normal-status>
                </template>
            </el-table-column>
            <el-table-column prop="creatorName" label="创建者" align="center" />
            <el-table-column prop="assignName" label="负责人" align="center" />
            <el-table-column prop="projectName" label="所属项目" align="center " />
            <el-table-column label="操作" align="center">
                <template #default="{ row }">
                    <el-button plain type="primary" @click="open(row)">提交任务</el-button>
                </template>
            </el-table-column>

        </el-table>
        <el-dialog :append-to-body="true" v-model="visible">
            <task-details :task="task!"></task-details>
        </el-dialog>
    </el-main>
</template>

<script setup lang="ts">
import { Task } from '@/api/interface';
import { queryTasksByProjectIdList } from '@/api/task';
import { useProjectStore } from '@/store/project';
import { useUserStore } from '@/store/user';
import { cloneDeep } from 'lodash';
import { ref, onMounted } from 'vue'
import taskDetails from '../task/taskDetails.vue';
import normalStatus from '../common/normalStatus.vue';
import eventBus from '@/utils/event';
const projectStore = useProjectStore()
const projects = projectStore.projects
const userId = useUserStore().userInfo.userId
const projectIdList: number[] = projects.map(project => parseInt(project.projectId))
const tasks = ref<Task[]>([])
const tasksData = ref<Task[]>([])
const visible = ref(false)
const task = ref<Task>()
onMounted(() => {
    queryAllTasks(projectIdList)
})
const open=(row:any)=>{
    visible.value=true
    task.value=row
}
eventBus.on('refreshAgent',()=>{
    queryAllTasks(projectIdList)
    visible.value=false
})
const queryAllTasks = async (projectIdList: number[]) => {
    tasks.value = await queryTasksByProjectIdList(projectIdList)
    tasksData.value = cloneDeep(tasks.value.filter((task: Task) => task.assignerId === userId && task.status !== '已完成' && task.status!=='已截止'))
}
</script>

<style scoped lang="scss">
.el-header {
    display: flex;
    align-items: center;
    height: 100px;

    span {
        display: block;
        position: relative;
        width: 100%;
        text-align: left;

        padding-left: 20px;
        font-size: 20px;
    }
}
</style>