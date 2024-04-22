<template>

    <div class="demand">
        <el-header>
            <el-menu :default-active="menuIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false">
                <el-menu-item index="1" @click="selectOne">全部需求</el-menu-item>
                <el-menu-item index="2" @click="selectTwo">我负责的需求</el-menu-item>
                <el-menu-item index="3" @click="selectThree">我创建的需求</el-menu-item>
            </el-menu>
            <el-input class="search" v-model="condition" @change="searchTask()" placeholder="请输入任务标题进行查询">
                <template #suffix>
                    <el-icon class="search-button" @click="searchTask()">
                        <Search />
                    </el-icon>
                </template>
            </el-input>
            <task-insert class="taskInsert" :project-id="projectStore.projectInfo?.projectId"
                @inserted="loadTasks()"></task-insert>
        </el-header>
        <el-main>
            <el-table :data="tabledata" border style="width: 100%;overflow: auto; height: 100%;"
                @cell-click="openAssign" @row-click="print" v-if="show"
                :default-sort="{ prop: 'creationTime', order: 'descending' }">
                <el-table-column type="index" width="50" align="center" fixed="left" />
                <el-table-column prop="title" label="标题" width="150" align="center" fixed="left" />
                <el-table-column prop="assignName" label="负责人" width="150" align="center" />
                <el-table-column prop="status" label="状态" width="120" align="center">
                    <template #default="{ row }">
                        <status :id-select="row.taskId" :key="row.taskId" :status="row.status" :status-width="`100px`"
                            @changed="(value) => handleChanged(value, row.taskId)"
                            v-if="hasProjectAuthority('update:task', projectId!)">
                        </status>
                        <normal-status :status="row.status" v-else></normal-status>
                    </template>
                </el-table-column>

                <el-table-column prop="description" label="描述" width="150" align="center" />
                <el-table-column prop="creationTime" label="创建时间" width="200" align="center" />
                <el-table-column prop="deadline" label="截止时间" width="200" align="center" />
                <el-table-column prop="creatorName" label="创建者" width="100" align="center" />
                <el-table-column label="操作" #default="scope" width="200" fixed="right">
                    <el-button type="primary" @click="open(scope.row)"
                        v-if="projectInfo?.creatorId === userInfo.userId">编辑</el-button>
                    <el-button type="primary" plain @click="openSubmit(scope.row)"
                        v-if="canSubmit(scope.row)">提交任务</el-button>
                    <el-button type="danger" plain @click="deletetask(projectId!, scope.row.taskId)"
                        v-if="selector === 3">删除</el-button>
                </el-table-column>
            </el-table>
            <task-update></task-update>
            <el-dialog v-model="visible" :style="{ top: `${memberTop}px`, left: `${memberLeft}px` }" calss="member"
                width="300">
                <template #header>
                    <span calss="member_header">团队成员</span>
                </template>
                <el-main>
                    <member :team-id="projectStore.projectInfo?.teamId!" @row-clicked="assign"></member>
                </el-main>
            </el-dialog>
            <el-dialog :append-to-body="true" v-model="SubmitVisible">
                <task-details :task="task!"></task-details>
            </el-dialog>
        </el-main>
        <el-footer>
            <el-text>共{{ totalSize }}条</el-text>
            <el-pagination background :pager-count="10" layout="prev, pager, next" :total="totalSize"
                :page-size="pageSize" :page-count="paginationData.pageCount" @current-change="currentPageChange"
                :current-page="paginationData.currentPage" class="pageInterect" />
        </el-footer>

    </div>
</template>

<script setup lang="ts">
import { onBeforeMount, ref } from 'vue';
import {
    queryTasksByProjectId, assignTask, updateTaskStatus,
    queryTasksByProjectIdMyAssign, queryTasksByProjectIdMyCreate,
    deleteTask, queryTaskByConditon
} from '@/api/task'
import taskDetails from '../pages/task/taskDetails.vue';
import { useProjectStore } from '@/store/project'
import { Task } from '@/api/interface'
import { useUserStore } from '@/store/user'
import eventBus from '@/utils/event';
import taskUpdate from '@/components/pages/task/taskUpdate.vue'
import taskInsert from '../pages/task/taskInsert.vue';
import member from '../pages/team/member.vue';
import status from '../pages/common/status.vue';
import normalStatus from '../pages/common/normalStatus.vue';
import { hasProjectAuthority } from '@/utils/hasAuthority'
const searchTask = () => {
    if (condition.value) {
        queryTaskByConditon(projectId!, condition.value).then(res => {
            tabledata.value = res.data
        })
    }
}
const openSubmit = (row: any) => {
    SubmitVisible.value = true
    task.value = row
}
eventBus.on('refreshAgent', async () => {
    
    SubmitVisible.value = false
    show.value = false
    loadTasks()
    setTimeout(() => {
        show.value = true
    }, 300)
    
})
const canSubmit = (row: any) => {
    if (row.status !== '已完成' && row.status !== '审核中' && selector.value === 2 && row.status !== '已截止') {
        return true;
    }
    return false;
}
const SubmitVisible = ref(false)
const memberTop = ref()
const memberLeft = ref()
const userStore = useUserStore()
const projectStore = useProjectStore()
const userInfo = userStore.userInfo
const projectInfo = projectStore.projectInfo
const projectId = projectStore.projectInfo?.projectId
const show = ref(true)
const pageSize: number = 25
let assignTaskId: string
const paginationData = ref({
    // 总页数
    pageCount: 1,
    // 当前页
    currentPage: 1,
})

const selector = ref(1)
const menuIndex = ref('1')
const tabledata = ref<Task[]>([])
const totalSize = ref<number>()
const task = ref<Task>()
const visible = ref(false)
const condition = ref('')
onBeforeMount(() => {
    loadTasks()
})
const print = (row: any) => {
    console.log(row)
}
const handleChanged = (value: string, taskId: string) => {
    updateTaskStatus(projectStore.projectInfo?.projectId!, taskId, value).then(() => {
        loadTasks()
    })
}

const openAssign = (row: Task, column: any, _cell: HTMLTableCellElement, event: Event) => {
    if (column.label == '负责人' && (userStore.userInfo.userId === projectStore.projectInfo?.creatorId)) {
        memberTop.value = (event as MouseEvent).clientY - 90
        memberLeft.value = (event as MouseEvent).clientX - 590
        assignTaskId = row.taskId
        visible.value = true;
    }
}
const assign = async (row: string) => {
    assignTask(projectStore.projectInfo?.projectId!, assignTaskId, row).then(() => {
        show.value = false
        loadTasks()
        setTimeout(() => {
            show.value = true
        }, 300)
    })
    visible.value = false
}
const currentPageChange = async (val: number) => {
    paginationData.value.currentPage = val
    loadTasks()
}
const deletetask = async (projectId: string, taskId: string) => {
    await deleteTask(projectId, taskId)
    loadTasks()

}
const loadTasks = async () => {
    let data: any
    let totalNumber: number = 0
    switch (selector.value) {
        case 1:
            var { taskViews, total } = await queryTasksByProjectId(projectStore.projectInfo!.projectId, paginationData.value.currentPage, pageSize)
            data = taskViews
            totalNumber = total
            break;
        case 2:
            var { taskViews, total } = await queryTasksByProjectIdMyAssign(projectStore.projectInfo!.projectId, userStore.userInfo.userId, paginationData.value.currentPage, pageSize)
            data = taskViews
            totalNumber = total
            break;
        case 3:
            var { taskViews, total } = await queryTasksByProjectIdMyCreate(projectStore.projectInfo!.projectId, userStore.userInfo.userId, paginationData.value.currentPage, pageSize)
            data = taskViews
            totalNumber = total
            break;
        default:
            break;
    }
    if (data != null) {
        paginationData.value.pageCount = Math.ceil(totalNumber / pageSize)
        totalSize.value = totalNumber
        tabledata.value = data
    }
}
// loadTasks()
const selectOne = () => {
    selector.value = 1
    loadTasks()
}
const selectTwo = () => {
    selector.value = 2
    loadTasks()
}
const selectThree = () => {
    selector.value = 3
    loadTasks()
}

const open = (row: any) => {
    task.value = row
    eventBus.emit("task", task.value)
    eventBus.emit("showTaskUpdate", true)
}
eventBus.on('taskChange', () => {
    loadTasks()
})
</script>

<style scoped lang="scss">
.member {
    position: relative;
    background-color: #fff;
    width: 300px;
    overflow-y: auto;
}

:deep(.el-dialog) {
    padding-left: 0px;
    padding-right: 0px;
}

:deep(.el-dialog__header) {
    text-align: left;
    padding-left: 10px;
    font-weight: bold;
    padding-bottom: 0px;
}

:deep(.el-overlay) {
    background-color: #ffffff00;
}

.el-table {
    overflow: auto;
}

.demand {
    display: flex;
    width: 100%;
    height: 100%;
    flex-direction: column;

    .el-header {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        height: 8%;
        align-items: center;
        border-bottom: 1px solid #eee;
        padding: 0;

        .el-menu--horizontal {
            height: 100%;
        }

        .taskInsert {
            position: relative;
            right: 70px;
        }
    }

    .el-main {
        position: relative;
        top: -1px;
        padding: 0;
        height: 82%;
        overflow: auto;

    }

    .el-footer {
        display: flex;
        align-items: center;
        flex-direction: row;
        flex-wrap: nowrap;
        justify-content: space-between;
        height: 10%;
        border-top: 1px solid #eee;
    }

}

.search {
    width: 200px;

    .search-button {
        cursor: pointer;

        &:hover {
            color: #a8caff;
        }
    }
}
</style>