<template>
    <div class="verify">
        <el-main>
            <div>
                <span>待审核的任务</span>
                <el-table :data="tasks" :row-style="rowStyle" @row-click="openFile">

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
                    <el-table-column label="操作" align="center" width="200" fixed="right">
                        <template #default="{ row }">
                            <el-button type="success" @click.stop="access(row)" plain>通过</el-button>
                            <el-button type="danger" plain @click.stop="rebut(row)">驳回</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-dialog v-model="visible" width="80%">
                    <task-file :task-id="taskId!" v-if="visible"></task-file>
                </el-dialog>
            </div>
        </el-main>
    </div>
</template>

<script setup lang="ts">
import { Task } from '@/api/interface';
import { queryVerifyTask, updateTaskStatus } from '@/api/task';
import { useProjectStore } from '@/store/project';
import { ref, onBeforeMount } from 'vue';
import normalStatus from '../pages/common/normalStatus.vue';
import taskFile from './taskFile.vue';
import { useUserStore } from '@/store/user';
import { useWebSocket } from '@/store/websocket';
const user = useUserStore().userInfo
const tasks = ref<Task[]>([])
const project = useProjectStore().projectInfo
const projectId = useProjectStore().projectInfo?.projectId
const visible = ref(false)
const taskId = ref<number>()
const socket = useWebSocket().getSocket()
onBeforeMount(async () => {
    console.log('socket: ', socket);
    tasks.value = await queryVerifyTask(projectId!)
})
const openFile = (row: any) => {
    visible.value = true
    taskId.value = row.taskId
}

const access = (row: any) => {
    updateTaskStatus(row.projectId, row.taskId, '已完成').then(async () => {
        const accessMessage = [{
            publisher: `${user.nickname}`,
            title: '审核通知',
            publisherId: user.userId,
            receiverId: row.assignerId,
            message: JSON.stringify({ body: `${project?.projectName}中你负责的任务（${row.title}）审核已经通过`, data: '' })
        }]
        console.log('socket: ', socket);
        if (socket) {
            console.log('socket: ', socket);
            socket.send(JSON.stringify(accessMessage))
        }
        tasks.value = await queryVerifyTask(projectId!)
    })
}
const rebut = (row: any) => {
    updateTaskStatus(row.projectId, row.taskId, '进行中').then(async () => {
        const rebutMessage = [{
            publisher: `${user.nickname}`,
            title: '审核通知',
            publisherId: user.userId,
            receiverId: row.assignerId,
            message: JSON.stringify({ body: `${project?.projectName}中你负责的（${row.title}）审核没有通过，已经被驳回`, data: '' })
        }]
        if (socket) {
            socket.send(JSON.stringify(rebutMessage))
        }
        tasks.value = await queryVerifyTask(projectId!)
    })

}
const rowStyle = (_row: any, _index: number) => {
    return {
        height: '55px',
    };
};
</script>

<style scoped lang="scss">
.verify {
    background-color: #fafafa;
    height: 100%;
    width: 100%;

    .el-main {
        margin: 20px;
        height: 95%;
        padding: 20px;
        outline: 1px solid #eee;
        z-index: 1;
        background-color: #fff;

        span {
            display: inline-block;
            width: 100%;
            text-align: left;
            font-size: 18px;
            margin-top: 10px;
            margin-bottom: 20px;
        }
    }
}
</style>