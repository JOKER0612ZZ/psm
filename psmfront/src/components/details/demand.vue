<template>

    <div class="demand">
        <el-header>
            <el-menu :default-active="menuIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false">
                <el-menu-item index="1" @click="selectOne">全部需求</el-menu-item>
                <el-menu-item index="2" @click="selectTwo">我负责的需求</el-menu-item>
                <el-menu-item index="3" @click="selectThree">我创建的需求</el-menu-item>
            </el-menu>
            <task-insert class="taskInsert" :project-id="projectStore.projectInfo?.projectId"></task-insert>
        </el-header>
        <el-main>
            <el-table :data="tabledata" border style="width: 100%" @cell-click="openAssign">
                <el-table-column type="index" fixed width="50" align="center" />
                <el-table-column prop="title" fixed label="标题" width="150" align="center" />
                <el-table-column prop="assignName" label="负责人" width="150" align="center" />
                <el-table-column prop="status" label="状态" width="100" align="center" />
                <el-table-column prop="description" label="描述" width="150" align="center" />
                <el-table-column prop="creationTime" label="创建时间" width="200" align="center" />
                <el-table-column prop="deadline" label="截止时间" width="200" align="center" />
                <el-table-column prop="creatorName" label="创建者" width="100" align="center" />
                <el-table-column align="center" label="操作" #default="scope">
                    <el-button type="primary" @click="open(scope.row)">编辑</el-button>

                </el-table-column>
            </el-table>
            <task-update></task-update>
            <el-dialog v-model="visible" :style="{ top: `${memberTop}px`, left: `${memberLeft}px` }"
                calss="member" width="400">
                <el-header>
                    团队成员
                </el-header>
                <el-main>
                    <member :team-id="projectStore.projectInfo?.teamId!" @row-clicked="assign"></member>
                </el-main>

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
import { ref, onMounted } from 'vue';
import { queryTasksByProjectId,assignTask } from '@/api/task'
import { useProjectStore } from '@/store/project'
import { Task } from '@/api/interface'
import { useUserStore } from '@/store/user'
import eventBus from '@/utils/event';
import taskUpdate from '@/components/pages/task/taskUpdate.vue'
import taskInsert from '../pages/task/taskInsert.vue';
import member from '../pages/team/member.vue';
const memberTop = ref()
const memberLeft = ref()
// import { hasProjectAuthority } from '@/utils/hasAuthority'
const userStore = useUserStore()
const projectStore = useProjectStore()
const pageSize: number = 25
let assignTaskId :string
const paginationData = ref({
    // 总页数
    pageCount: 1,
    // 当前页
    currentPage: 1,
})
const selector = ref(1)
const menuIndex = ref('1')

const taskList = ref<Task[]>([])
const taskMyAssign = ref<Task[]>([])
const taskMycreate = ref<Task[]>([])
const tabledata = ref<Task[]>([])
const totalSize = ref<number>()
const task = ref<Task>()
onMounted(() => {
    loadTasks()
})
const visible = ref(false)
const openAssign = (row: Task, column: any, _cell: HTMLTableCellElement, event: Event) => {
    if (column.label == '负责人') {
        memberTop.value = (event as MouseEvent).clientY - 90
        memberLeft.value = (event as MouseEvent).clientX - 550
        assignTaskId = row.taskId
        visible.value = true;
        
    }
}
const assign = async(row:string)=>{
    assignTask(projectStore.projectInfo?.projectId!,assignTaskId,row)   
    setTimeout(()=>{
        loadTasks()
    },250)
    
    console.log(tabledata.value)
    visible.value=false
}
const currentPageChange = async (val: number) => {
    paginationData.value.currentPage = val
    loadTasks()

}
const loadTasks = async () => {
    const { taskViews, total } = await queryTasksByProjectId(projectStore.projectInfo!.projectId, paginationData.value.currentPage, pageSize)
    if (taskViews != null) {
        paginationData.value.pageCount = Math.ceil(total / pageSize)
        taskList.value = taskViews
        totalSize.value = total
        taskMyAssign.value = taskList.value.filter(task => task.assignerId === userStore.userInfo.userId)
        taskMycreate.value = taskList.value.filter(task => task.creatorId === userStore.userInfo.userId)
    }
    lodaTableData()
    console.log(tabledata.value)
}
const selectOne = () => {
    selector.value = 1
    lodaTableData()
}
const selectTwo = () => {
    selector.value = 2
    lodaTableData()
}
const selectThree = () => {
    selector.value = 3
    lodaTableData()
}
const lodaTableData = () => {
    switch (selector.value) {
        case 1:
            tabledata.value = taskList.value
            break;
        case 2:
            tabledata.value = taskMyAssign.value
            break;
        case 3:
            tabledata.value = taskMycreate.value
            break;
        default:
            break;
    }

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
.member{
    position: relative;
    background-color: #fff;
    width: 300px;
    height: 200px;
    overflow-y: auto;
}
:deep(.el-overlay){
    background-color: #ffffff00;
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
</style>