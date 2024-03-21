<template>
    <div class="demand">
        <el-header>
            <el-menu :default-active="menuIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false">
                <el-menu-item index="1">全部需求</el-menu-item>
                <el-menu-item index="2">我负责的需求</el-menu-item>
                <el-menu-item index="3">我创建的需求</el-menu-item>
            </el-menu>
        </el-header>
        <el-main>
            <el-table :data="taskList" border style="width: 100%">
                <el-table-column type="index" width="50" />
                <el-table-column prop="title" label="标题" width="180" />
                <el-table-column prop="assignName" label="负责人" width="180" />
                <el-table-column prop="status" label="状态"></el-table-column>
                <el-table-column prop="description" label="描述" />
                <el-table-column prop="creationTime" label="创建时间"></el-table-column>
                <el-table-column prop="deadline" label="截止时间"></el-table-column>
                <el-table-column prop="userName" label="创建者"></el-table-column>
            </el-table>
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
import { queryTasksByProjectId } from '@/api/task'
import { useProjectStore } from '@/store/project';
import { Task } from '@/api/interface'
const projectStore = useProjectStore()
const pageSize: number = 25
const paginationData = ref({
    // 总页数
    pageCount: 1,
    // 当前页
    currentPage: 1,
})
onMounted(() => {
    loadTasks()
})
const currentPageChange = async (val: number) => {
    paginationData.value.currentPage = val
    loadTasks()
}
const loadTasks = async () => {
    const { taskViews, total } = await queryTasksByProjectId(projectStore.projectInfo!.projectId, paginationData.value.currentPage, pageSize)
    paginationData.value.pageCount = Math.ceil(total / pageSize)
    taskList.value = taskViews
    totalSize.value = total
}
const menuIndex = ref('1')

const taskList = ref<Task[]>([])
const totalSize = ref<number>()

</script>

<style scoped lang="scss">
.demand {
    display: flex;
    width: 100%;
    height: 100%;
    flex-direction: column;

    .el-header {
        height: 8%;
        align-items: center;
        border-bottom: 1px solid #eee;
        padding: 0;

        .el-menu--horizontal {
            height: 100%;
        }
    }

    .el-main {
        position: relative;
        top: -1px;
        padding: 0;
        height: 82%;

        .el-table {
            overflow: auto;
        }
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