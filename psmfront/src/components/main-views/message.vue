<template>
    <div class="message">
        <el-dropdown trigger="click">
            <el-badge :value="notRead" max="20" :hidden="badgeHidden()" class="item">
                <el-icon size="30">
                    <MessageBox />
                </el-icon>
            </el-badge>
            <template #dropdown class="dropmenu">
                <el-dropdown-menu class="dropdown">
                    <el-dropdown-item v-for="(item, index) in messages" :key="index" class="dropdown-menu"
                        @click="openMessage(item)">
                        {{ item.title }}
                        <span class="read" v-if="item.isRead === 0" style="color: red;">未读</span>
                        <span class="read" v-if="item.isRead === 1" style="color: lightblue;">已读</span>
                        <el-button type="danger" @click.stop="deleteMessage(item.messageId)"
                            style="z-index: 3;">删除</el-button>
                    </el-dropdown-item>
                </el-dropdown-menu>
                <el-dropdown-menu v-if="messages.length === 0">
                    <el-dropdown-item class="dropdown-menu">
                        <span>暂无消息</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
        <el-dialog v-model="messageVisible" :title="message?.title">
            <p align="left" class="publisher">发件人：{{ publisher}}</p>
            <div class="messageBody" ref="messageRef" v-html="message"></div>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue';
import { changeReadStatus, deleteMessageById, getMessages } from '@/api/message';
import { Message } from '@/api/interface';
// import { useUserStore } from '@/store/user';
import { joinTeam } from '@/api/team';
import { queryUserProject } from '@/api/project';
// import socket,{initWebSocket} from '@/utils/websocket';
import eventBus from '@/utils/event';
let confirm: HTMLElement
let cancel: HTMLElement
// const userId = useUserStore().userInfo.userId
const messages = ref<Message[]>([])
const notRead = ref(0)
const message = ref<Message>()
const messageRef = ref<HTMLElement>()
const messageVisible = ref(false)
const publisher = ref()
eventBus.on('onmessage', (data) => {
    messages.value.push(data)
    setReadNumber()
})
const badgeHidden=()=>{
    if(notRead.value===0){
        return true
    }
    return false
}
const deleteMessage = (id: any) => {
    if (messages) messages.value = messages.value.filter(m => m.messageId !== id)
    deleteMessageById(id)
}
const openMessage = (val: any) => {
    publisher.value = val.publisher
    console.log('publihser.value: ', publisher.value);
    message.value = JSON.parse(val.message).body;
    const data = JSON.parse(val.message).data
    changeReadStatus(val.messageId!)
    messages.value.forEach(m => {
        if (m.messageId === val.messageId) {
            m.isRead = 1;
        }
    });
    setReadNumber()
    messageVisible.value = true;
    nextTick(() => {
        if (messageRef) {
            confirm = document.querySelector('.confirm')!
            confirm.addEventListener('click', () => {
                joinTeam(data)
                queryUserProject()
                messageVisible.value = false;
            })
            console.log('confirm: ', confirm);
            cancel = document.querySelector('.cancel')!
            cancel.addEventListener('click', () => {
                messageVisible.value = false
            })
            console.log('cancel: ', cancel);
        }
    });
}

onMounted(() => {
    loadMessages()
})
const setReadNumber = () => {
    if (messages) {
        notRead.value = messages.value.filter(message => message.isRead === 0).length
    }
}
const loadMessages = async () => {
    messages.value = await getMessages()
    setReadNumber()
}
</script>

<style lang="scss" scoped>
:deep(.invitation) {
    background-color: #fff;
    height: 100px;
    font-size: 16px;
    border-radius: 5px;
    display: flex;
    flex-direction: column;

    button {
        background-color: #3d90de;
        color: #ffffff;
        border-radius: 3px;
        padding: 5px 15px;
        cursor: pointer;
        margin-left: 20px;

        &:last-child {
            background-color: #f44336;
        }

        &:hover {
            opacity: 0.8;
        }
    }

    span {
        font-family: 'Arial', sans-serif;
        color: #333333;
        margin-right: 10px;
        margin-bottom: 20px;
    }
}


.message {
    position: absolute;
    right: 80px;
    top: 10px;
    z-index: 3;
}

.publisher {
    padding: 5px;
    /* 内边距 */
    font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
    color: #333;
    /* 文本颜色 */
    font-size: 16px;
    /* 文本大小 */
}

.dot {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 0;
    right: 0;
    width: 15px;
    height: 15px;
    background-color: red;
    border-radius: 50%;
    font-size: 10px;
    color: #fff;

}

:deep(.dropdown-menu) {
    width: 300px !important;
    display: flex;
    flex-direction: row;
    align-items: bottom;
    justify-content: space-between;
}

.read {
    display: inline-block;
    right: 0;
    font-size: 10px;
    color: gray;
}

.messageBody {
    width: 100%;
    height: 200px;
    box-sizing: border-box;
    outline: 1px solid #eee;
    text-align: left;
    padding: 20px;
}
</style>