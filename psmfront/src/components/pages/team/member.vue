<template>
    <div class="member">
        <el-table :data="members" @row-click="handleRowClick">
            <el-table-column prop="memberName" align="center" width="120">
                <template #default="{ row }" style="border: none;">
                    <div class="member_name">
                        <el-icon size="20" v-if="row.roleName === 'manager'">
                            <Avatar />
                        </el-icon>
                        <el-icon size="20" v-if="row.roleName === 'member'">
                            <UserFilled />
                        </el-icon>
                        <span>{{ row.memberName }}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column align="center" width="100">
                <template #default="{ row }">
                    <span v-if="row.roleName === 'manager'">项目经理</span>
                    <span v-if="row.roleName === 'member'">成员</span>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
// import { useUserStore } from '@/store/user';
import {Members} from '@/api/interface'
import { queryProjectMembers,queryTeamMembers } from '@/api/team';
import { useProjectStore } from '@/store/project';
const projectId = useProjectStore().projectInfo?.projectId
onMounted(async () => {
    if(props.teamId!==''){
        members.value = await queryTeamMembers(props.teamId)
    }else{
        members.value = await queryProjectMembers(projectId!)
    }
    
    members.value = sortedMembers()
})
// const userId = useUserStore().userInfo.userId
const emit = defineEmits()
const members = ref<Members[]>([])
const sortedMembers = () => {
    const managerRows = members.value!.filter((member:Members) => member.roleName === 'manager');
    const memberRows = members.value!.filter((member:Members) => member.roleName === 'member');
    return managerRows.concat(memberRows);
};
const handleRowClick = (row:Members)=>{
    emit('row-clicked',row.memberId)
}

const props = defineProps({
    teamId: {
        type: String,
        default:''
    }
})
</script>

<style lang="scss" scoped>
:deep(.el-table){
    --el-table-border:none;
}


.member_name {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;

    span {
        display: inline-block;
        margin-left: 10px;
        width: 60px;
        overflow: hidden
    }
}
</style>
