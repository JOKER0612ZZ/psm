<template>
    <div>
        <el-table :data="users" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="30" />
            <el-table-column type="index" width="30" />
            <el-table-column>
                <template #default="{ row }">
                    <div class="show-user">
                        <el-icon size="25" color="#108ee9" v-if="row.gender === '男'">
                            <UserFilled />
                        </el-icon>
                        <el-icon size="25" color="#fc7265" v-else>
                            <UserFilled />
                        </el-icon>
                        <span>{{ row.nickname }}</span>
                    </div>
                </template>
            </el-table-column>

        </el-table>
        <el-button @click="invite" class="invite">邀请</el-button>
    </div>
</template>

<script setup lang="ts">
import { useProjectStore } from '@/store/project';
import { useUserStore } from '@/store/user';
import { ref } from 'vue';
defineProps({
    users: {
        type: Object as () => any,
        required: true
    }
})
const user = useUserStore().userInfo
const project = useProjectStore().projectInfo
const messages: {
    title: string
    publisherId: string; // 当前用户的ID
    receiverId: any; // 接收者的ID
    message: string; // message字符串需要正确的插值和引号
}[] = []
const userdatas = ref()
const emit = defineEmits(['messages'])
const invite = () => {
    emit('messages', messages)
}
let toMessage ={
    body: '',
    data: ''
}
const handleSelectionChange = (val: any) => {
    userdatas.value = val
    messages.length = 0
    Object.values(userdatas.value).forEach((userItem: any) => {
        toMessage.body = `<div class="invitation"><span>${user.nickname}邀请你参与项目${project?.projectName}</span><div class="invitation-button"><button class="confirm">确认</button><button class="cancel">取消</button></div></div>`
        console.log('toMessage.body: ', toMessage.body);
        toMessage.data=project?.projectId!
        const invitationMessage = {
            publisher: `${user.nickname}`,
            title: `${user.nickname}的信息`,
            publisherId: user.userId,
            receiverId: userItem.userId,
            message: JSON.stringify(toMessage),
        };
        messages.push(invitationMessage);
    })
}
</script>

<style scoped lang="scss">
.show-user {
    display: flex;
    flex-direction: row;
    align-items: center;

    .el-icon {
        margin-right: 20px;
        margin-left: 10px;
    }

    span {
        font-size: 16px;
    }
}

.invite {
    position: relative;
    margin-top: 20px;
    right: -140px;
}
</style>