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
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" width="50" />
                <el-table-column prop="date" label="Date" width="180" />
                <el-table-column prop="name" label="Name" width="180" />
                <el-table-column prop="address" label="Address" />
            </el-table>
        </el-main>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
const menuIndex = ref('1')
const taskList = ref<any>([])

const tableData: any[] = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]
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
        .el-menu--horizontal{
            height: 100%;
        }
    }
    .el-main {
        position: relative;
        top: -1px;
        padding: 0;
        height: 92%;
    }
}
</style>