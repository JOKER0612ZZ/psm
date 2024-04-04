<template>
    <el-header>
        <span>{{ title }}</span>
    </el-header>
    <el-main>
        <el-table :data="tableData" :row-style="rowStyle" >
            <el-table-column type="expand">
                <template #default={row}>
                    <span>团队成员</span>
                    <member :team-id="row.teamId"></member>
                </template>
            </el-table-column>
            <el-table-column label="团队名称" prop="teamName" sortable></el-table-column>
            <el-table-column label="创建时间" prop="creationTime" sortable></el-table-column>
            <el-table-column label="创建者" prop="creatorName"></el-table-column>
        </el-table>
    </el-main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { queryTeamsByUserId } from '@/api/team'
import { useUserStore } from '@/store/user'
import eventBus from '@/utils/event'
import { Team } from '@/api/interface'
import {cloneDeep} from 'lodash'
import member from '@/components/pages/team/member.vue'
const userInfo = useUserStore().userInfo
const tableData = ref<any>()
const teamData = ref<any>()
defineProps({
    title: {
        type: String,
        required: true
    }
})
onMounted(async() => {
    teamData.value = await queryTeamsByUserId(userInfo.userId)
    console.log(teamData.value)
    tableData.value = cloneDeep(teamData.value)
    console.log(tableData.value)
})
const rowStyle = (_row: any, _index: number) => {
    return {
        height: '60px', // 设置行高
        // 其他样式
    };
};
eventBus.on('allteamData', () => {
    tableData.value = cloneDeep(teamData.value)
});

eventBus.on('creatorTeamData', () => {
    tableData.value = cloneDeep((teamData.value.filter((team:Team) => team.creatorId === userInfo.userId)))
});

eventBus.on('JoinTeamData', () => {
    tableData.value = cloneDeep((teamData.value.filter((team:Team) => team.creatorId !== userInfo.userId)))
});
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