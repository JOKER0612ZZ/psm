<template>
    <div id="home">
        <el-container>
            <el-menu :default-active="active" class="el-menu-vertical" :collapse="isCollapse">

                <el-menu-item index="1" class="top">
                    <el-icon>
                        <UserFilled />
                    </el-icon>
                    <template #title>
                        <span style="font-size: 20px; display: inline-block;">{{ userInfo.userName }}</span>
                    </template>
                </el-menu-item>
                <router-link to="/home/index">
                    <el-menu-item index="2">
                        <el-icon>
                            <HomeFilled />
                        </el-icon>

                        <template #title>
                            <span>工作台</span>
                        </template>
                    </el-menu-item>
                </router-link>
                <router-link to="/home/project" @click="projectMain">
                    <el-menu-item index="3">
                        <el-icon>
                            <Calendar />
                        </el-icon>

                        <template #title>
                            <span>项目管理</span>
                        </template>
                    </el-menu-item>
                </router-link>
                <router-link to="/home/team" @click="teamMain">
                    <el-menu-item index="4">
                        <el-icon>
                            <Avatar />
                        </el-icon>

                        <template #title>
                            <span>团队管理</span>
                        </template>
                    </el-menu-item>
                </router-link>
                <router-link to="/home/task">
                    <el-menu-item index="5">
                        <el-icon>
                            <document />
                        </el-icon>

                        <template #title>
                            <span>任务管理</span>
                        </template>
                    </el-menu-item>
                </router-link>
                <el-menu-item class="menu-bottom" index="7">
                    <div class="open" ref="draf" @click="handle">
                        <el-icon>
                            <Operation />
                        </el-icon>
                    </div>
                </el-menu-item>
            </el-menu>
            <el-main>
                <user-router :user-info="userInfo"></user-router>
                <message></message>
                <router-view>
                </router-view>
            </el-main>
        </el-container>
    </div>
</template>

<script setup lang="ts">
import userRouter from '@/components/main-views/user.vue'
import message from './message.vue';
import { ref, onBeforeMount } from 'vue'
import { useUserStore } from '@/store/user'
import eventBus from '@/utils/event';
import { projectMain, teamMain } from '@/utils/tools';
import { useWebSocket } from '@/store/websocket';
import { queryUserProject } from '@/api/project';
const store = useUserStore()
const socketStore =useWebSocket()
onBeforeMount(() => {
    userInfo.value = store.userInfo
    if(!socketStore.socket){
        const newsocket = new WebSocket(`ws://localhost:8080/websocket/${store.userInfo.userId}`)
        socketStore.setSocket(newsocket)
    }
    queryUserProject()
})


eventBus.on('userInfo', (data) => {
    userInfo.value = data;
})
let userInfo = ref<any>({
})
const isCollapse = ref(false)
const draf = ref<any>(null)


const active = ref('1')


const handle = () => {
    if (isCollapse.value == false) {
        isCollapse.value = true
    } else {
        isCollapse.value = false

    }
}

</script>

<style scoped lang="scss">
.menu-title {
    text-align: center;
}

#home {
    width: 100vw;
    height: 100vh;
    position: absolute;
    left: 0;
    top: 0;
    overflow: visible;
}

.el-menu {
    height: 100vh;
    overflow: hidden;
}

.el-main {
    height: 100vh;
    overflow: hidden;
    padding: 0;
}

.el-menu-vertical:not(.el-menu--collapse) {
    background: {
        color: #48525c;
    }

    ;

    color: #ddd;
    width: 200px;
    height: 100vh;
}

.el-menu-vertical:deep(.el-menu-item) {
    color: #ddd;

    &:hover {
        background-color: #39414c;
    }
}

.el-menu--collapse {
    background-color: #48525c;
    height: 100vh;
}

.el-menu--collapse .el-menu-item .menu-bottom {
    position: absolute;
    left: 0;

}

.el-menu--collapse:deep(.el-menu-item) {

    color: #ddd;

    &:hover {
        background-color: #39414c;
    }

}

.top {
    display: flex;
    align-items: center;
    height: 70px;
    margin: 0 auto;
    padding-top: 10px;
}

.el-menu-item span {
    display: block;
    height: 100%;
    font-size: .875rem
}

.menu-bottom {
    display: block;
    position: absolute;
    bottom: 0;
    right: 0;
}
</style>