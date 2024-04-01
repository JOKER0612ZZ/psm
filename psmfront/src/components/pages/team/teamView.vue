<template>
    <el-header>
        <span>{{ title }}</span>
    </el-header>
    <el-main>
        <el-table :data="tableData" @row-click="loadTeamMembers" :row-style="rowStyle">
            <el-table-column label="团队名称" prop="teamName" sortable></el-table-column>
            <el-table-column label="创建时间" prop="creationTime" sortable></el-table-column>
            <el-table-column label="创建者" prop="creatorName"></el-table-column>
        </el-table>
    </el-main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { queryTeamsByUserId,queryTeamMembers } from '@/api/team'
import { useUserStore } from '@/store/user'
import eventBus from '@/utils/event'
import { Team } from '@/api/interface'
const userInfo = useUserStore().userInfo
const tableData = ref<any>()
const teamData = ref<any>()
defineProps({
    title: {
        type: Object as () => string,
        required: true
    }
})
onMounted(async() => {
    teamData.value = await queryTeamsByUserId(userInfo.userId)
    console.log(teamData.value)
    tableData.value = JSON.parse(JSON.stringify(teamData.value))
    console.log(tableData.value)
})
const rowStyle = (_row: any, _index: number) => {
    return {
        height: '60px', // 设置行高
        // 其他样式
    };
};
eventBus.on('allTeamData', () => {
    tableData.value = JSON.parse(JSON.stringify(teamData.value))
})
eventBus.on('creatorTeamData', () => {
    tableData.value = teamData.value.filter((team: Team) => team.creatorId === userInfo.userId)
})
eventBus.on('JoinTeamData', () => {
    tableData.value = teamData.value.filter((team: Team) => team.creatorId !== userInfo.userId)
})
const loadTeamMembers = (row:any) => {
    console.log(row.teamId)
    queryTeamMembers(row.teamId)
}
</script>

<style scoped lang="scss">
.el-header{
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: space-between;
    align-items: center;
    padding: 0 40px;
    height: 100px;
    span{
        font-size: 20px;
    }
}
</style>